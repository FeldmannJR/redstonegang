<?php

namespace App\Services\Vendor;

use GuzzleHttp\Client;
use GuzzleHttp\Exception\BadResponseException;
use GuzzleHttp\Exception\GuzzleException;
use GuzzleHttp\RequestOptions;
use Illuminate\Support\Facades\URL;
use Illuminate\Support\Facades\DB;

class MojangAccountService
{

    private function getClientAPI()
    {
        return new Client(['base_uri' => 'https://api.mojang.com/']);
    }

    private function getClientStatusAPI()
    {
        return new Client(['base_uri' => 'https://status.mojang.com/']);
    }

    private function getClientSessionServerAPI()
    {
        return new Client(['base_uri' => 'https://sessionserver.mojang.com/']);
    }

    private function getAuthServer()
    {
        return new Client(['base_uri' => 'https://authserver.mojang.com']);
    }

    public function getAPIStatus()
    {
        $client = $this->getClientStatusAPI();
        try {
            $request = $client->request('GET', 'check');
            return json_decode($request->getBody());
        } catch (GuzzleException $e) {
        }
        return null;
    }


    public function getProfileCached($username)
    {
        return \Cache::remember('profile-' . $username, 60 * 5, function () use ($username) {
            return $this->getProfileAtTime($username);
        });

    }

    public function getProfileAtTime($userName, $atTimestamp = null)
    {
        $client = $this->getClientAPI();
        $query = [];
        if ($atTimestamp != null) {
            $query['at'] = $atTimestamp;
        }
        try {
            $request = $client->request('GET', 'users/profiles/minecraft/' . $userName, [
                'query' => $query
            ]);

            if ($request->getStatusCode() == 200) {
                return json_decode($request->getBody());
            }
        } catch (GuzzleException $e) {
        }
        return null;
    }

    public function getProfileNames($UUID)
    {
        $client = $this->getClientAPI();
        try {
            $request = $client->request('GET', 'user/profiles/' . $UUID . '/names');

            if ($request->getStatusCode() == 200) {
                return json_decode($request->getBody());
            }
        } catch (GuzzleException $e) {
        }
        return null;
    }

    public function getProfiles($userNames)
    {
        $client = $this->getClientAPI();
        try {
            $request = $client->request('POST', 'profiles/minecraft', [
                'json' => $userNames
            ]);

            if ($request->getStatusCode() == 200) {
                return json_decode($request->getBody());
            }
        } catch (GuzzleException $e) {
        }
        return null;
    }

    public function hasPaidMojang($userName)
    {
        $profile = $this->getProfiles($userName);
        if (is_null($profile)) {
            return null;
        } else {
            $responseName = '';

            if (!empty($profile)) {
                $responseName = $profile[0]->name;
            }

            if (strtolower($responseName) == strtolower($userName)) {
                return true;
            } else {
                return false;
            }
        }
    }

    private function hasPaidMojangCode($userName)
    {
        $is_premium = $this->hasPaidMojang($userName);
        $status = 3;
        if (!is_null($is_premium)) {
            if ($is_premium == true || $is_premium == false) {
                $status = $is_premium ? 1 : 0;
            }
        }

        return $status;
    }

    private function getTimestamp($data)
    {
        $slices = explode('/', $data);
        return mktime(0, 0, 0, $slices[1], $slices[0], $slices[2]);
    }

    public function hasPaid($userName)
    {
        if (is_null($userName) || $userName == "") {
            return null;
        }

        $user = DB::table('haspaid_cache')->where('name', $userName)->first();

        if (is_null($user)) {
            $status = $this->hasPaidMojangCode($userName);

            DB::table('haspaid_cache')->insert(
                ['name' => $userName, 'status' => $status]
            );

            $user = DB::table('haspaid_cache')->where('name', $userName)->first();
        } else if ($user->status == 0 || $user->status == 3) {
            $lastUpdate = date("d/m/Y", strtotime($user->updated_at));
            $today = date("d/m/Y", strtotime("now"));
            $diffTime = $this->getTimestamp($today) - $this->getTimestamp($lastUpdate);
            $days = (int)floor($diffTime / (60 * 60 * 24));
            if ($days > 90 || $user->status == 3) {
                $status = $this->hasPaidMojangCode($userName);

                DB::table('haspaid_cache')
                    ->where('id', $user->id)
                    ->update(
                        ['name' => $userName, 'status' => $status, 'updated_at' => DB::raw('now()')]
                    );

                $user = DB::table('haspaid_cache')->where('id', $user->id)->first();
            }
        }

        return $user;
    }


    public function getCachedProfileSkin($uuid, bool $unsigned)
    {
        $key = 'mojangprofile' . ($unsigned ? '-unsigned' : '') . ':' . $uuid;
        $value = \Cache::remember($key, 60 * 5, function () use ($uuid, $unsigned) {
            return $this->getProfileSkin($uuid, $unsigned);
        });
        return $value;
    }

    public function getProfileSkin($UUID, bool $unsigned)
    {
        $client = $this->getClientSessionServerAPI();
        try {
            $q = 'session/minecraft/profile/' . $UUID . '?unsigned=' . ($unsigned ? 'true' : 'false');
            $request = $client->request('GET', $q);
            if ($request->getStatusCode() == 200) {
                $response = json_decode($request->getBody());
                return $response;
            }
        } catch (GuzzleException $e) {
        }
        return null;
    }

    public function uploadSkin($accessToken, $uuid, $skin, $model = '')
    {
        try {
            $data = [
                [
                    'contents' => $skin,
                    'name' => 'file'
                ],
                [
                    'name' => 'model',
                    'contents' => $model
                ]
            ];

            $response = $this->getClientAPI()->put('user/profile/' . $uuid . '/skin', [
                'multipart' => $data,
                'headers' => [
                    'Authorization' => 'Bearer ' . $accessToken
                ]
            ]);
            if ($response->getStatusCode() == 204) {
                // Deu upload
                return true;
            }
        } catch (GuzzleException $e) {
            return $e;
        }
        return false;
    }

    public function authenticate($username, $password)
    {
        try {
            $request = $this->getAuthServer()->post('/authenticate', [
                RequestOptions::JSON => [
                    'agent' => [
                        'name' => 'Minecraft',
                        'version' => '1'
                    ],
                    'username' => $username,
                    'password' => $password,
                ]
            ]);
            if ($request->getStatusCode() == 200) {
                return json_decode($request->getBody());
            }
        } catch (GuzzleException $e) {
        }
        return null;
    }


    public function getBlockedServers()
    {
        $client = $this->getClientSessionServerAPI();
        try {
            $request = $client->request('GET', 'blockedservers');
            $response = $request->getBody();
            return $response;
        } catch (GuzzleException $e) {
        }
        return null;
    }

}