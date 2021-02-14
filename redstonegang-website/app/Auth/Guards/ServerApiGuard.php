<?php


namespace App\Auth\Guards;


use App\Servers\ApiToken;
use Illuminate\Contracts\Auth\Authenticatable;
use Illuminate\Contracts\Auth\Guard;
use Illuminate\Http\Request;

class ServerApiGuard implements Guard
{
    /**
     * @var Request
     */
    protected $request;

    private $headerName = 'RG-Api-Key';

    public function __construct(Request $request)
    {
        $this->request = $request;
    }

    /**
     * Check whether user is logged in.
     *
     * @return bool
     */
    public function check(): bool
    {
        return (bool)$this->user();
    }

    /**
     * Check whether user is not logged in.
     *
     * @return bool
     */
    public function guest()
    {
        return !$this->check();
    }

    /**
     * Return user id or null.
     *
     * @return null|int
     */
    public function id()
    {
        $user = $this->user();
        return $user->id ?? null;
    }

    /**
     * Manually set user as logged in.
     *
     * @param null|\App\User|\Illuminate\Contracts\Auth\Authenticatable $user
     * @return $this
     */
    public function setUser(Authenticatable $user)
    {
        $this->user = $user;
        return $this;
    }

    /**
     * @param array $credentials
     * @return bool
     */
    public function validate(array $credentials = [])
    {
        throw new \BadMethodCallException('Unexpected method call');
    }

    /**
     * Return user or throw AuthenticationException.
     *
     * @return \App\User
     * @throws AuthenticationException
     */
    public function authenticate()
    {
        $user = $this->user();
        if ($user instanceof ApiToken) {
            return $user;
        }
        throw new AuthenticationException();
    }

    /**
     * Return cached user or newly authenticate user.
     *
     * @return null|\App\User|\Illuminate\Contracts\Auth\Authenticatable
     */
    public function user()
    {

        if ($this->request->hasHeader($this->headerName)) {
            $token = $this->request->header($this->headerName);
            if ($token !== null && is_string($token)) {
                return ApiToken::findByToken($token);
            }
        }
        return null;

    }


}