<?php

namespace App\Models\Common;

use App\Models\XF\XFUserGroup;
use Illuminate\Database\Eloquent\Model;

class Group extends PermissionHolder
{
    protected $table = "groups";
    protected $connection = "mysql_common";
    public $timestamps = false;

    protected $hidden = ['forum'];

    public function getMorphClass()
    {
        return 0;
    }

    public function options()
    {
        return $this->hasMany(GroupOption::class, 'group');
    }


    public function forum()
    {
        return $this->belongsTo(XFUserGroup::class, 'forum_group');
    }

    protected function getParentFieldName()
    {
        return 'parent';
    }

    protected function useDefaultParent(): bool
    {
        return false;
    }
}
