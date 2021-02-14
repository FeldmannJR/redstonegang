<?php

/* @var $factory \Illuminate\Database\Eloquent\Factory */

use App\Map;
use Faker\Generator as Faker;

$factory->define(Map::class, function (Faker $faker) {
    return [
        'name' => $faker->lastName,
        'game' => $faker->monthName,
        'valid' =>$faker->boolean,
    ];
});
