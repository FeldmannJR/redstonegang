<?php

namespace App\Servers;

use App\Servers\Machine;
use Illuminate\Contracts\Auth\Authenticatable;
use Illuminate\Database\Eloquent\Model;

class Server extends Model
{


    protected $fillable = ['name'];

    public function machine()
    {
        return $this->belongsTo(Machine::class);
    }




}
