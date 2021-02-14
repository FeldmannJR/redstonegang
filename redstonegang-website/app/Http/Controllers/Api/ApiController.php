<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

class ApiController extends Controller
{

    public function index()
    {
        return $this->response(['running']);
    }

    private function response($arrResponse)
    {
        $response = $this->info();
        $response['response'] = $arrResponse;
        return response()->json($response);
    }

    private function info()
    {
        return [
            'version' => '1.0.0',
            'environment' => app()->environment()
        ];
    }
    
}
