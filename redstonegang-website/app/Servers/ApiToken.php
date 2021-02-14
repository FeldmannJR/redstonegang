<?php

namespace App\Servers;

use App\Servers\Server;
use App\Servers\TokenAuthentication;
use Illuminate\Contracts\Auth\Authenticatable;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Str;

class ApiToken extends Model implements Authenticatable
{
    use TokenAuthentication;

    protected $fillable = ["token"];

    public static function findByToken($token)
    {
        return ApiToken::where('token', $token)->first();

    }

    public static function generateLocal()
    {
        return ApiToken::create(['token' => "localapitoken"]);
    }

    public static function generate()
    {
        return ApiToken::create(['token' => Str::random(32)]);
    }
}
