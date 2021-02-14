<?php

namespace App\Services\Repositories;

use App\Services\Repositories\AccountRepository;
use Illuminate\Support\Facades\Facade;

class AccountRepositoryFacade extends Facade
{

    protected static function getFacadeAccessor()
    {
        return AccountRepository::class;
    }
    
}