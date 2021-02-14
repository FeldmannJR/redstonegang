<?php

namespace App\Models\Common;

use BenSampo\Enum\Enum;

final class PermissionValue extends Enum
{
    const NONE = 0;
    const ALLOW = 1;
    const DENY = 2;
}
