<?php

namespace App\Http\Controllers\Api;

use App\CustomSkin;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

class SkinController extends Controller
{


    public function custom()
    {
        $all = CustomSkin::all()->toArray();
        $all = array_map(function ($el) {
            return $el['name'];
        }, $all);
        return response()->json($all);
    }


}
