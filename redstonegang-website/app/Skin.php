<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Skin extends Model
{
    protected $guarded = [];

    //

    public function toGameProfile()
    {
        $properties = [
            [
                'name' => 'textures',
                'value' => $this->textures,
                'signature' => $this->signature
            ]
        ];
        return [
            'id' => $this->uuid,
            'name' => $this->name,
            'properties' => $properties
        ];

    }
}
