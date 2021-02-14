<?php

namespace App;

use App\Models\Common\User;
use DateTimeInterface;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Str;


class RegisterToken extends Model
{
    const UPDATED_AT = null;

    protected $fillable = ['token', 'user_id'];

    public function generateToken()
    {
        do {
            $token = Str::random(16);
        } while (RegisterToken::where('token', $token)->exists());
        $this->token = $token;
    }

    public function user()
    {
        return $this->belongsTo(User::class);
    }

    public function isValid()
    {
        return now()->isBefore($this->created_at->addMinutes(60));
    }

}
