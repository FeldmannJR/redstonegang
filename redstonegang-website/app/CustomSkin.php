<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class CustomSkin extends Model
{
    protected $guarded = [];
    protected $hidden =[self::CREATED_AT,self::UPDATED_AT];
    //
    protected $primaryKey = 'name'; // or null
    public $incrementing = false;

    public function skin()
    {
        return $this->belongsTo(Skin::class, 'skin_id');
    }
}
