<?php

namespace App\Services\Vendor;

use GuzzleHttp\Client;
use GuzzleHttp\Exception\GuzzleException;

class RecaptchaService
{

    private function getClient()
    {
        return new Client(['base_uri' => 'https://www.google.com/recaptcha/api/']);
    }

    private function getSecret()
    {
        return config('services.recaptcha.secret');
    }


    public function executeRequest($token)
    {
        $client = $this->getClient();
        $request = $client->post('siteverify', [
            'form_params' => [
                'secret' => $this->getSecret(),
                'response' => $token,
                'remoteip' => \Request::ip()
            ]
        ]);
        if ($request->getStatusCode() == 200) {
            return json_decode($request->getBody());
        }
        return null;
    }

    public function isTokenValid($token, $score = 0, string $scope = null)
    {
        $json = $this->executeRequest($token);
        if (!isset($json->success)) {
            return false;
        }
        if ($json->success !== true) {
            return false;
        }
        if ($score > 0) {
            if (isset($json->score)) {
                $providedScore = $json->score;
                if ($providedScore < $score) {
                    return false;
                }
            } else {
                return false;
            }
        }
        if ($scope !== null) {
            if (!isset($json->action)) {
                return false;
            }
            if ($json->action !== $scope) {
                return false;
            }
        }
        return true;
    }


}