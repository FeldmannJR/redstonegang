<?php


namespace App\Auth;

use Facades\App\Services\Vendor\XenforoService;
use Facades\App\Services\Vendor\MojangAccountService;
use Illuminate\Contracts\Auth\Authenticatable;
use Illuminate\Contracts\Auth\Authenticatable as UserContract;
use Illuminate\Contracts\Auth\UserProvider;
use App\Account;


trait XenforoAccountProvider
{


    protected $identificator = 'id';

    /**
     * Retrieve a user by their unique identifier.
     *
     * @param mixed $identifier
     * @return \Illuminate\Contracts\Auth\Authenticatable|null
     */
    public function retrieveById($identifier)
    {
        return Account::where($this->identificator, $identifier)
            ->first();
    }

    /**
     * Retrieve a user by their unique identifier and "remember me" token.
     *
     * @param mixed $identifier
     * @param string $token
     * @return \Illuminate\Contracts\Auth\Authenticatable|null
     */
    public function retrieveByToken($identifier, $token)
    {

        $retrievedModel = $this->retrieveById($identifier);

        if (!$retrievedModel) {
            return;
        }

        $rememberToken = $retrievedModel->getRememberToken();

        return $rememberToken && hash_equals($rememberToken, $token)
            ? $retrievedModel : null;
    }

    /**
     * Update the "remember me" token for the given user in storage.
     *
     * @param \Illuminate\Contracts\Auth\Authenticatable|\Illuminate\Database\Eloquent\Model $user
     * @param string $token
     * @return void
     */
    public function updateRememberToken(UserContract $user, $token)
    {
        $user->setRememberToken($token);

        $timestamps = $user->timestamps;

        $user->timestamps = false;

        $user->save();

        $user->timestamps = $timestamps;
    }

    /**
     * Retrieve a user by the given credentials.
     *
     * @param array $credentials
     * @return \Illuminate\Contracts\Auth\Authenticatable|null
     */
    public function retrieveByCredentials(array $credentials)
    {

        if (empty($credentials) || (count($credentials) === 1 && array_key_exists('email', $credentials))) {
            return;
        }


    }

    /**
     * @param $login
     * @param $password
     * @param $ip
     * @return Account
     */
    public function findAccount($login, $password, $ip = '')
    {
        $response = XenforoService::doAuth($login, $password, $ip);

        foreach ($response as $key => $value) {
            if ($key == 'errors') {
                $this->errors = $value;
            }
            if ($key == 'success' && $value == true) {
                $user = $response->user;
                $forum_id = $user->user_id;
                $account = Account::where('forum_id', $forum_id)->get()->first();
                if ($account === null) {
                    $this->errors = [];
                    $error = new \stdClass();
                    $error->code = 'unsync_account';
                    $error->message = 'Conta do forum não sincronizada!';
                    $this->errors[] = $error;
                }
                /*
                // Não importa mais pq as contas vão ficar desincronizadas, e isso n pode acontecer
                // As contas que foram criadas antes do sistema precisam ser sincronizadas na mão
                if($account == null)
                {
                    //Import AppAccount from XenforoAccount fields
                    $mojagUser = MojangAccountService::hasPaid($user->username);
                    if ($mojagUser->status !== 3)
                    {
                        $isMojangPremium = $mojagUser->status === 1;
                        $account = \AccountRepository::createAccount($user->username, $forum_id, $isMojangPremium);

                }
                else if($account->getUsername() !== $user->username)
                {
                    // Update AppAccount from XenforoAccount fields
                    $account->setUsername($user->username);
                    $account->save();
                }
                */
                return $account;
            }
        }
        return null;
    }

    /**
     * Validate a user against the given credentials.
     *
     * @param \Illuminate\Contracts\Auth\Authenticatable $user
     * @param array $credentials
     * @return bool
     */
    public function validateCredentials(UserContract $user, array $credentials)
    {
        $plain = $credentials['password'];
        return XenforoService::login($user->username, $plain);
    }


}