<?php

namespace App\Http\Controllers\Api;

use App\ServerConfig;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

class ConfigController extends Controller
{
    //

    public function index()
    {
        $json = [
            'app_base_url' => config('app.url')
        ];
        $configs = ServerConfig::all();
        foreach ($configs as $config) {
            $json[$config->name] = $config->value;
        }
        return $json;
    }
}
