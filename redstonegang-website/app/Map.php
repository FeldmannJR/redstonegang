<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Map extends Model
{
    protected $guarded = [];
    //
    protected $casts = ['valid' => 'boolean', 'file' => 'boolean'];
}
