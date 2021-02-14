<?php

namespace App;

use App\Models\Common\User;
use Illuminate\Database\Eloquent\Model;

class AccountKitSession extends Model
{

    protected $fillable = ['token', 'email', 'user_id'];
    protected $hidden = ['id', 'created_at', 'updated_at'];

    public function getToken()
    {
        return $this->token;
    }

    public function setToken($token)
    {
        return $this->token = $token;
    }

    public function getEmail()
    {
        return $this->email;
    }

    public function setEmail($email)
    {
        return $this->email = $email;
    }

    public function user()
    {
        return $this->belongsTo(User::class, 'user_id');
    }

    public function isValid()
    {
        return now()->isBefore($this->created_at->addMinutes(60));
    }

}
