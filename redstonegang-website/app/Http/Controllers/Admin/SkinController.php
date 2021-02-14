<?php

namespace App\Http\Controllers\Admin;

use App\Jobs\UploadSkin;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use Illuminate\Validation\Rule;

class SkinController extends Controller
{


    public function index()
    {
        return view('admin.skins');
    }

    public function upload(Request $request)
    {
        $request->validate([
            'skin' => [
                'required',
                Rule::dimensions()->width('64')->minHeight('32'),
                'max:20',
                'mimes:png'
            ],
            'name' => ['string', 'min:2', 'max:13', 'regex:/^[a-zA-Z0-9_]{2,13}$/'],
            'model' => 'required|in:true,false'
        ]);
        $path = $request->file('skin')->getPathname();
        $heigth = getimagesize($path)[0];
        if ($heigth != 64 && $heigth != 32) {
            throw  \Illuminate\Validation\ValidationException::withMessages([
                'skin' => 'A skin precisa ter o tamanho de 64x64 ou 64x32!'
            ]);
        }
        $model = $request->input('model') === 'true';
        $path = $request->file('skin')->store('uploadskins');
        $accounts = config('redstonegang.minecraft.accounts');
        UploadSkin::dispatch(array_rand($accounts), $path, $request->input('name'), $model);
    }

}

