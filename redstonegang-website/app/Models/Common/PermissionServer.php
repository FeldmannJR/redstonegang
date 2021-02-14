<?php

namespace App\Models\Common;

use BenSampo\Enum\Enum;

final class PermissionServer extends Enum
{
    const GERAL = 0;
    const MINIGAMES = 1;
    const SURVIVAL = 2;
}
