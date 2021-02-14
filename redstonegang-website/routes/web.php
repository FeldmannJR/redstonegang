<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', 'HomeController@index')->name('home');

Route::redirect('/cadastro', '/register');

Route::namespace('Auth')->group(function () {
    Route::get('/login', 'LoginController@index')
        ->name('login')
        ->middleware('guest');
    Route::get('/logout', 'LoginController@logout');
    Route::post('/login/post', 'LoginController@post');

    // Pirata
    Route::match(['GET', 'POST'], '/register', 'RegisterController@index');
    Route::post('/register/finish', 'RegisterController@register');
    Route::post('/register/prevalidate', 'RegisterController@loginWithFacebook');
    Route::get('/register/callback', 'RegisterController@facebookCallback')->name('facebookCallback');

    // Original
    Route::get('/register/premium', 'RegisterController@registerWithToken');
    Route::get('/register/premium/callback', 'RegisterController@facebookCallbackPremium');
});
Route::namespace('Admin')->prefix('admin')->middleware('permission:rg.staff')->group(function () {
    Route::get('/', 'AdminController@index');
    Route::get('/skin', 'SkinController@index');
    Route::post('/skin', 'SkinController@upload');
    Route::middleware('owner')->group(function () {
        Route::get('/permissions/', 'PermissionController@index');
        Route::get('/group/edit/{group}', 'GroupController@editGroup')->name('editgroup');
        Route::get('/group/', 'GroupController@listGroups')->name('listgroups');

        Route::middleware(\App\Http\Middleware\ForceJson::class)->group(function () {
            Route::post('/group/update/forum', 'GroupController@setForumGroup');
            Route::post('/group/update/permission', 'GroupController@setPermission');
            Route::post('/group/remove/forum', 'GroupController@removeForumGroup');
            Route::get('/group/permissions', 'GroupController@listPermissions');
        });
    });
});

