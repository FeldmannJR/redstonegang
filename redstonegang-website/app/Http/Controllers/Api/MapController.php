<?php

namespace App\Http\Controllers\Api;

use App\Map;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

class MapController extends Controller
{

    public function list()
    {
        return Map::all();

    }

    public function listFilterGame($game)
    {
        return Map::where('game', $game)->get();
    }

    public function findId($id)
    {
        return Map::find($id);
    }

    public function findOrCreate(Request $request)
    {
        $request->validate([
            'name' => 'required|string|max:60',
            'game' => 'required|string|max:60'
        ]);

        $game = $request->input('game');
        $name = $request->input('name');
        return Map::firstOrCreate(['name' => $name, 'game' => $game]);
    }

    public function find(Request $request)
    {
        $request->validate([
            'name' => 'required|string|max:60',
            'game' => 'required|string|max:60'
        ]);

        $map = Map::where('game', $request->input('game'))->where('name', $request->input('name'))->first();
        if (!$map) {
            return response("Not Found", 404);
        }
        return $map;

    }

    public function games()
    {
        $return = [];
        $arr = Map::select("game")->groupBy('game')->get()->toArray();
        foreach ($arr as $ar) {
            $return[] = $ar['game'];
        }
        return $return;
    }

    public function upload(Request $request)
    {
        $validator = \Validator::make($request->all(), [
            'id' => 'required|exists:maps',
            'valid' => 'required|in:true,false',
            'map' => 'required|file|mimetypes:application/x-gzip'
        ]);
        if ($validator->fails()) {
            return response()->json($request->all(), 404);
        }

        $mapId = $request->input("id");
        $file = $request->file('map');
        if ($file->storeAs('maps', $request->input('id') . '.tar.' . $file->extension())) {
            $map = Map::find($mapId);
            $map->file = true;
            $map->valid = $request->input('valid') === 'true' ? 1 : 0;
            $map->save();

            return ["success" => true, 'id' => $mapId];
        }
        return ["success" => false, 'id' => $mapId];

    }

    public function download($id)
    {
        $path = 'maps/' . $id . '.tar.gz';
        if (\Storage::exists($path)) {
            return \Storage::download($path);
        }
        return response()->json([], 404);
    }


}
