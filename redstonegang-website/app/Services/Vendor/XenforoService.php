<?php

namespace App\Services\Vendor;

use Carbon\Carbon;
use GuzzleHttp\Client;
use GuzzleHttp\Exception\GuzzleException;
use GuzzleHttp\Exception\BadResponseException;
use Illuminate\Support\Facades\URL;
use Illuminate\Support\Facades\DB;

class XenforoService
{

    private $api_url;
    private $api_super_key; // Super User Key
    private $api_key; // RedstoneGang

    /**
     * XenforoService constructor.
     */
    public function __construct()
    {
        $this->api_url = config('services.xenforo.url');
        $this->api_super_key = config('services.xenforo.key.super');
        $this->api_key = config('services.xenforo.key.user');
        if (is_null($this->api_url)) {
            throw new \Exception('Xenforo Api Url not set!');
        }
        if (is_null($this->api_super_key)) {
            throw new \Exception('Xenforo Super Key not set!');
        }
        if (is_null($this->api_key)) {
            throw new \Exception('Xenforo User Key not set!');
        }

    }


    private function getClient()
    {
        return new Client(['base_uri' => $this->api_url]);
    }

    /*
     *  Auth
     */
    public function doAuth($login, $password, $limit_ip = '')
    {
        $fields = [
            'login' => $login,
            'password' => $password,
            'limit_ip' => $limit_ip
        ];

        return $this->request($this->api_super_key, 'POST', 'auth/', $fields);
    }

    /**
     * @param $login
     * @param $password
     */
    public function login($login, $password)
    {
        $response = $this->doAuth($login, $password);
        foreach ($response as $key => $value) {
            if ($key == 'errors') {
                return false;
            } else if ($key == 'success') {
                return true;
            }
        }
        return false;
    }

    /*
     *  Index
     */
    public function getIndex()
    {
        return $this->get('index/');
    }

    /*
     *  Me
     */
    public function getMe()
    {
        return $this->get('me/');
    }

    /*
     *  Users
     */
    public function getUsers($page = 1)
    {
        $fields = [
            'page' => $page
        ];

        return $this->get('users/', $fields);
    }

    public function createUser($username, $password, $email, Carbon $birthday)
    {
        $fields = [
            'username' => $username,
            'password' => $password,
            'email' => $email,
            'dob[day]' => $birthday->format('d'),
            'dob[month]' => $birthday->format('m'),
            'dob[year]' => $birthday->format('Y'),

        ];

        return $this->post('users/', $fields);
    }

    public function isSuccess($request)
    {
        if ($request === null) return false;
        if (isset($request->success)) {
            $success = $request->success;
            if ($success === true) {
                return true;
            }
        }
        return false;

    }

    public function findUser($username)
    {
        $fields = [
            'username' => $username
        ];

        return $this->get('users/find-name/', $fields);
    }

    public function getUser($id, $with_posts = false, $page = 0)
    {
        $fields = [
            'with_posts' => $with_posts,
            'page' => $page
        ];

        return $this->get('users/' . $id, $fields);
    }

    public function syncForumAccount($id, $groups = [], $is_staff = false)
    {
        $params = ['secondary_group_ids' => $groups];
        if (count($groups) === 0) {
            $params['secondary_group_ids'] = '[]';
        }
        $params['is_staff'] = $is_staff;
        return $this->updateUser($id, $params);
    }

    public function updateUser($id, $userInfo = [])
    {
        return $this->post('users/' . $id, $userInfo);
    }

    public function deleteUser($id, $rename_to = '')
    {
        $fields = [
            'rename_to' => $rename_to
        ];

        return $this->delete('users/' . $id, $fields);
    }

    private function post($endPoint, $fields = [])
    {
        return $this->request($this->api_key, 'POST', $endPoint, $fields);
    }

    private function get($endPoint, $fields = [])
    {
        return $this->request($this->api_key, 'GET', $endPoint, $fields);
    }

    private function delete($endPoint, $fields = [])
    {
        return $this->request($this->api_key, 'DELETE', $endPoint, $fields);
    }

    private function request($api_key, $method = 'GET', $endPoint = 'index/', $fields = [])
    {
        $client = $this->getClient();
        try {
            $request = $client->request($method, $endPoint, [
                'headers' => [
                    'XF-Api-Key' => $api_key
                ],
                $method == 'POST' ? 'form_params' : 'query' => $fields
            ]);
            return json_decode($request->getBody());
        } catch (BadResponseException $e) {
            return json_decode($e->getResponse()->getBody());
        }
        return null;
    }

}