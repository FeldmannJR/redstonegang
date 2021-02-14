<?php

namespace App\Models\Common;

use BenSampo\Enum\Traits\CastsEnums;
use Illuminate\Database\Eloquent\Model;

class Permission extends Model
{
    use CastsEnums;

    protected $connection = "mysql_common";
    protected $table = "permissions";
    public $timestamps = false;
    protected $guarded = [];

    protected $enumCasts = [
        // 'attribute_name' => Enum::class
        'value' => PermissionValue::class,
    ];

}
