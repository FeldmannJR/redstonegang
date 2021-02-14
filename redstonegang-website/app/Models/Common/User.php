<?php

namespace App\Models\Common;

use App\Account;
use Illuminate\Database\Eloquent\Model;

class User extends PermissionHolder
{
    //
    protected $table = "users";
    protected $connection = "mysql_common";
    public $timestamps = false;

    public function account()
    {
        return $this->belongsTo(Account::class, 'account_id');
    }



    public function getMorphClass()
    {
        return 1;
    }

    protected function getParentFieldName()
    {
        return "group";
    }

    protected function useDefaultParent(): bool
    {
        return true;
    }



}
