<?php

namespace App\Models\XF;

use App\Account;
use App\Models\Common\Group;

class XFUserGroup extends XFModel
{
    protected $table = "xf_user_group";
    protected $primaryKey = 'user_group_id';

    public function groups()
    {
        return $this->hasMany(Group::class, 'group_id');
    }

}
