<?php

namespace App\Models\Common;

use Illuminate\Database\Eloquent\Model;

class GroupOption extends Model
{
    protected $connection = "mysql_common";
    protected $table = "group_options";

    public function group()
    {
        return $this->belongsTo(Group::class, 'group');
    }
}
