<?php

namespace App\Servers;

use Illuminate\Database\Eloquent\Model;

class Machine extends Model
{

    protected $fillable = ['ip', 'ram_current', 'ram_total', 'boot_time'];

    public function servers()
    {
        return $this->hasMany(Server::class);
    }
}
