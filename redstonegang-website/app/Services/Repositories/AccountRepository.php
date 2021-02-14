<?php

namespace App\Services\Repositories;

use App\Account;
use Illuminate\Support\Facades\Hash;

class AccountRepository
{

    public function createAccount($username, $forum_id, $premium = false)
    {
        return Account::create([
            'username' => $username,
            'forum_id' => $forum_id,
            'premium' => $premium
        ]);
    }

    public function hasAccountWithId($id)
    {
        return $this->hasAccountWithData(['id' => $id]);
    }

    public function hasAccountWithForumId($forum_id)
    {
        return $this->hasAccountWithData(['forum_id' => $forum_id]);
    }

    public function hasAccountWithUsername($username)
    {
        return $this->hasAccountWithData(['username' => $username]);
    }

    /**
     * @param array $data com chave sendo a coluna e o valor o valor
     * @return bool se existe alguma conta conflitando com a $data informada
     */
    private function hasAccountWithData(array $data)
    {
        $keywords = ['id', 'forum_id', 'username'];
        $select = null;
        foreach ($keywords as $keyword) {
            if (array_key_exists($keyword, $data)) {
                if ($select === null) {
                    $select = Account::where($keyword, $data[$keyword]);
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