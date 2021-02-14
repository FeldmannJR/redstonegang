<?php

namespace App\Providers;

use App\Services\Vendor\FacebookAccountKitService;
use App\Services\Vendor\RecaptchaService;
use App\Services\Vendor\MojangAccountService;
use Illuminate\Support\ServiceProvider;

class VendorServiceProvider extends ServiceProvider
{
    /**
     * Register services.
     *
     * @return void
     */
    public function register()
    {
        $this->app->singleton(FacebookAccountKitService::class, function ($app) {
            return new FacebookAccountKitService();
        });
        $this->app->singleton(RecaptchaService::class, function () {
            return new RecaptchaService;
        });
        $this->app->singleton(MojangAccountService::class, function ($app) {
            return new MojangAccountService();
        });

    }

    /**
     * Bootstrap services.
     *
     * @return void
     */
    public function boot()
    {
        //
    }
}
