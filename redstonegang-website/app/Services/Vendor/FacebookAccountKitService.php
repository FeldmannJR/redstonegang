<?php

namespace App\Services\Vendor;

use App\Models\Common\User;
use Carbon\Carbon;
use GuzzleHttp\Client;
use GuzzleHttp\Exception\GuzzleException;
use Illuminate\Support\Facades\URL;

class FacebookAccountKitService
{
    private $version = 'v1.3';

    private function getAppId()
    {
        return config('services.account_kit.id');
    }

    private function getAppSecret()
    {
        return config('services.account_kit.secret');
    }

    private function getAccessTokenParam()
    {
        return 'AA|' . $this->getAppId() . '|' . $this->getAppSecret();
    }

    private function getClient()
    {
        return new Client(['base_uri' => 'https://graph.accountkit.com/' . $this->version . '/']);
    }


    private function redirectToFacebook($phone = null, $email = null, $user_id = null, $redirect = null)
    {
        $account = \App\AccountKitSession::where('email', $email)->get()->first();
        $token = md5(uniqid($email, true));
        if ($account === null) {
            $account = \AccountKitSessionRepository::createAccount($token, $email, $user_id);
        } else {
            $account->setToken($token);
            $account->user_id = $user_id;
            $account->save();
        }
        if ($redirect === null) {
            $redirect = route('facebookCallback');
        }
        $params = [
            'app_id' => $this->getAppId(),
            'state' => $account->getToken(),
            'locale' => 'pt_BR',
            'redirect' => $redirect
        ];
        $dialogType = null;
        if ($phone !== null) {
            $params['phone_number'] = $phone;
            $params['contry_code'] = 55;
            $dialogType = 'sms_login';

        } else if ($email !== null) {
            $params['email'] = $email;
            $dialogType = 'email_login';
        } else return null;

        $params = http_build_query($params);
        return redirect()->away('https://www.accountkit.com/v1.3/basic/dialog/' . $dialogType . '/?' . $params);
    }

    public function redirectToPhoneLogin($phone)
    {
        return $this->redirectToFacebook($phone);
    }


    public function redirectToEmailLogin($email, $redirect = null)
    {
        return $this->redirectToFacebook(null, $email, null, $redirect);
    }

    public function redirectToEmailLoginWithUsername($email, $user_id, $redirect = null)
    {
        return $this->redirectToFacebook(null, $email, $user_id, $redirect);
    }

    public function returnFromFacebook($code, $state)
    {
        $account = \App\AccountKitSession::where('token', $state)->first();
        if ($account !== null) {
            if ($account->getEmail() !== null) {
                // Se já foi validado só retorna a conta
                if ($account->validated) {
                    return $account;
                }
                $accessToken = $this->getAccessToken($code);
                if ($accessToken !== null) {
                    $accountKitUser = $this->getUserFromToken($accessToken);
                    if ($accountKitUser !== null) {
                        if ($accountKitUser->email->address !== $account->getEmail()) {
                            // atualiza o email confirmado pelo AccountKit
                            $account->setEmail($accountKitUser->email->address);

                        }
                        $account->validated = true;
                        $account->save();
                        return $account;
                    }
                }
            }
        }
        return null;
    }


    public function getAccessToken(string $code)
    {
        $client = $this->getClient();
        try {
            $request = $client->request('GET', 'access_token?', [
                'query' => [
                    'grant_type' => 'authorization_code',
                    'code' => $code,
                    'access_token' => $this->getAccessTokenParam()
                ]
            ]);
            $response = json_decode($request->getBody());
            if (isset($response->access_token)) {
                return $response;
            }
        } catch (GuzzleException $e) {
        }
        return null;
    }

    public function getUserFromToken($access_token)
    {
        $client = $this->getClient();
        $tok = $access_token->access_token;
        $appsecret_proof = hash_hmac('sha256', $tok, $this->getAppSecret());
        try {
            $request = $client->request('GET', 'me', [
                'query' => [
                    'access_token' => $tok,
                    'appsecret_proof' => $appsecret_proof
                ]
            ]);

            $json = json_decode($request->getBody());
            return $json;
        } catch (GuzzleException $e) {
            return null;
        }
    }

}