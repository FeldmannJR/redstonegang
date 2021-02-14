<?php

namespace App\Models\XF;

use App\Account;

class XFUser extends XFModel
{
    protected $table = "xf_user";

    protected $primaryKey = "user_id";

    public function account()
    {
        return $this->hasOne(Account::class, 'forum_id');
    }

}
