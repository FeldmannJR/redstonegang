<?php

namespace App\Providers;

use App\Auth\Guards\ServerApiGuard;
use App\Services\Vendor\XenforoService;
use App\XenforoAccountProvider;
use App\Auth\Guards\XenforoLoginGuard;
use Illuminate\Support\Facades\Gate;
use Illuminate\Foundation\Support\Providers\AuthServiceProvider as ServiceProvider;

class AuthServiceProvider extends ServiceProvider
{
    /**
     * The policy mappings for the application.
     *
     * @var array
     */
    protected $policies = [
        'App\Model' => 'App\Policies\ModelPolicy',
    ];

    /**
     * Register any authentication / authorization services.
     *
     * @return void
     */
    public function boot()
    {
        $this->registerPolicies();
        \Auth::extend('server_token', function ($app) {
            return new ServerApiGuard($app['request']);
        });
        \Auth::extend('xenforo', function ($app, $config) {
            return new XenforoLoginGuard($app['session.store'], $this->app['request']);
        });


        //
    }
}
