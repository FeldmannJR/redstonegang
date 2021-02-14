<?php

namespace App\Http\Controllers\Api;

use App\CustomSkin;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Services\Vendor\MojangAccountService;
use Symfony\Component\HttpFoundation\Response;

class MojangController extends Controller
{

    private $mojang;

    public function __construct(MojangAccountService $mojang)
    {
        $this->mojang = $mojang;
    }

    public function index()
    {
        return $this->mojang->getAPIStatus();
    }

    public function auth()
    {
        return \response()->json();
    }

    public function hasPaid($username)
    {
        return response()->json($this->mojang->hasPaid($username));
    }

    public function profileByUUID(Request $request, $uuid)
    {
        $unsigned = true;
        if ($request->has('unsigned')) {
            $unsigned = $request->get('unsigned');
            if (!in_array($unsigned, ['true', 'false'])) {
                throw  \Illuminate\Validation\ValidationException::withMessages([
                    'unsigned' => 'O valor precisa ser true or false!'
                ]);
            }
            $unsigned = $unsigned === 'true';
        }

        $value = $this->mojang->getCachedProfileSkin($uuid, $unsigned);
        if ($value === null) {
            return response()->json(['error' => 'Not processed'], Response::HTTP_UNPROCESSABLE_ENTITY);
        }
        return response()->json($value);
    }


    public function profileByName($username)
    {
        $profile = $this->mojang->getProfileCached($username);
        $value = null;
        if ($profile !== null && isset($profile->id)) {
            $value = $this->mojang->getCachedProfileSkin($profile->id, false);
        } else {
            $value = $this->findPirateSkin(strtolower($username));
        }
        if ($value === null) {
            return response()->json(['error' => 'Not Found'], Response::HTTP_NOT_FOUND);
        }
        return \response()->json($value, 200);
    }

    public function profileByNameCracked($username)
    {
        if (strpos($username, 'rg-') === 0) {
            $name = substr($username, 3);

            $skin = CustomSkin::where('name', $name)->first();
            if ($skin != null) {
                return response()->json($skin->skin->toGameProfile(), 200, [], JSON_UNESCAPED_SLASHES);
            }
        }
        return response()->json(['error' => 'Skin Not Found'], Response::HTTP_NOT_FOUND);
    }

    private function findPirateSkin($username)
    {
        if (strpos($username, 'rg-') === 0) {
            $name = substr($username, 3);

            $skin = CustomSkin::where('name', $name)->first();
            if ($skin != null) {
                return $skin->skin->toGameProfile();
            }
        }
        return null;
    }


}