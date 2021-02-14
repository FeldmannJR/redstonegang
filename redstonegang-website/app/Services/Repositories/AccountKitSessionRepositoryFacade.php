<?php

namespace App\Services\Repositories;

use App\Services\Repositories\AccountKitSessionRepository;
use Illuminate\Support\Facades\Facade;

class AccountKitSessionRepositoryFacade extends Facade
{

    protected static function getFacadeAccessor()
    {
        return AccountKitSessionRepository::class;
    }
    
}