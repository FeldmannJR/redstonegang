<?php

namespace App;

use App\Models\Common\User;
use App\Models\XF\XFUser;
use Illuminate\Contracts\Auth\Authenticatable;
use Illuminate\Database\Eloquent\Model;

/**
 * App\Account
 *
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Account newModelQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Account newQuery()
 * @method static \Illuminate\Database\Eloquent\Builder|\App\Account query()
 * @mixin \Eloquent
 */
class Account extends Model implements Authenticatable
{

    protected $fillable = ['username', 'forum_id', 'premium'];
    protected $hidden = ['forum_id', 'created_at', 'updated_at', 'premium', 'remember_token', 'id'];

    protected $connection = 'mysql';
    protected $table = "accounts";

    public function user()
    {
        return $this->hasOne(User::class, 'account_id');
    }

    public function forum()
    {
        return $this->belongsTo(XFUser::class, 'forum_id');
    }

    public function getRememberToken()
    {
        return $this->remember_token;
    }

    public function setRememberToken($token)
    {
        return $this->remember_token = $token;
    }

    public function isPremium(): bool
    {
        return $this->premium;
    }

    public function getUsername()
    {
        return $this->username;
    }

    public function setUsername($username)
    {
        return $this->username = $username;
    }

    /**
     * Get the name of the unique identifier for the user.
     *
     * @return string
     */
    public function getAuthIdentifierName()
    {
        return 'username';
    }

    /**
     * Get the unique identifier for the user.
     *
     * @return mixed
     */
    public function getAuthIdentifier()
    {
        return $this->id;
    }

    /**
     * Get the password for the user.
     *
     * @return string
     */
    public function getAuthPassword()
    {

    }

    /**
     * Get the column name for the "remember me" token.
     *
     * @return string
     */
    public function getRememberTokenName()
    {
        return 'remember_token';
    }
}
