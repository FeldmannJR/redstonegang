<?php

namespace App\Services\Repositories;

use App\AccountKitSession;
use Illuminate\Support\Facades\Hash;

class AccountKitSessionRepository
{

    public function createAccount($token, $email, $user_id)
    {
        return AccountKitSession::create([
            'token' => $token,
            'email' => $email,
            'user_id' => $user_id
        ]);
    }

    public function hasAccountWithId($id)
    {
        return $this->hasAccountWithData(['id' => $id]);
    }

    public function hasAccountWithToken($token)
    {
        return $this->hasAccountWithData(['token' => $token]);
    }

    /**
     * @param array $data com chave sendo a coluna e o valor o valor
     * @return bool se existe alguma conta conflitando com a $data informada
     */
    private function hasAccountWithData(array $data)
    {
        $keywords = ['id', 'token', 'email', 'username'];
        $select = null;
        foreach ($keywords as $keyword) {
            if (array_key_exists($keyword, $data)) {
                if ($select === null) {
                    $select = AccountKitSession::where($keyword, $data[$keyword]);
                } else {
                    $select = $select->orWhere($keyword, $data[keyword]);
                }
            }
        }
        if ($select !== null) {
            return $select->exists();
        }
    }
}