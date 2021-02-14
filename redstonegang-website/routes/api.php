<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

/**
 * APIs 1.0.0
 */
$api_domain = App::environment('production') ? 'api.redstonegang.com.br' : 'api.' . parse_url(config('app.url'), PHP_URL_HOST);
Route::namespace('Api')->domain($api_domain)->prefix('/v1')->group(function () {
    Route::get('/', 'ApiController@index');

    Route::prefix('/mojang')->group(function () {
        Route::get('/', 'MojangController@index');
        Route::get('/haspaid/{username}', 'MojangController@hasPaid');
        Route::get('/profile/uuid/{uuid}', 'MojangController@profileByUUID');
        Route::get('/profile/name/{username}', 'MojangController@profileByName');
        Route::get('/profile/cracked/{username}', 'MojangController@profileByNameCracked');
        Route::get('/skin/{username}', 'MojangController@skin');
    });
    Route::prefix('/skins')->group(function () {
        Route::get('/custom', 'SkinController@custom');
    });
    Route::prefix('/xenforo')->group(function () {
        Route::get('/', 'XenforoController@index');
        Route::get('/auth/{username}/{password}', 'XenforoController@auth');
        Route::get('/getusers/{page}', 'XenforoController@getUsers');
        Route::get('/createuser/{username}/{password}/{email}', 'XenforoController@createUser');
        Route::get('/finduser/{username}', 'XenforoController@findUser');
        Route::get('/getuser/{id}/{with_posts?}/{page?}', 'XenforoController@getUser');
        Route::post('/updateuser/{id}', 'XenforoController@updateUser');
        Route::get('/deleteuser/{id}/{rename_to?}', 'XenforoController@deleteUser');
    });
    Route::prefix('/auth')->group(function () {
        Route::get('/paid/token/{user_id}', "AuthController@generatePaidToken");
        // where é preciso adicionar para poder suportar / como parametro
        // https://laravel.com/docs/5.8/routing#route-parameters
        Route::post('/login/', 'AuthController@login');
    });
    Route::prefix('/config')->group(function () {
        Route::get('/server/{dev}', 'ConfigController@index');
    });
    Route::prefix('/maps')->group(function () {
        Route::get('/id/{id}', 'MapController@findId');
        Route::get('/find/', 'MapController@find');
        Route::get("/games", "MapController@games");
        Route::get('/list/', 'MapController@list');
        Route::get('/list/{game}', 'MapController@listFilterGame');
        Route::post("/create/", "MapController@findOrCreate");
        Route::post('/upload/', "MapController@upload");
        Route::get('/download/{id}', "MapController@download");
    });
    Route::prefix('/permissions')->group(function () {
        Route::post('/setgroup/', 'PermissionController@updateGroup');
    });
});