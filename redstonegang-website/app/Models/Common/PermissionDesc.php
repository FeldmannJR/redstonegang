<?php

namespace App\Models\Common;

use Illuminate\Database\Eloquent\Model;

class PermissionDesc extends Model
{
    protected $connection = "mysql_common";
    //
    protected $table = "permissions_desc";
}
