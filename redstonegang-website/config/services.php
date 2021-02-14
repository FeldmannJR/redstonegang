<?php

return [

    /*
    |--------------------------------------------------------------------------
    | Third Party Services
    |--------------------------------------------------------------------------
    |
    | This file is for storing the credentials for third party services such
    | as Stripe, Mailgun, SparkPost and others. This file provides a sane
    | default location for this type of information, allowing packages
    | to have a conventional place to find your various credentials.
    |
    */

    'mailgun' => [
        'domain' => env('MAILGUN_DOMAIN'),
        'secret' => env('MAILGUN_SECRET'),
    ],

    'ses' => [
        'key' => env('SES_KEY'),
        'secret' => env('SES_SECRET'),
        'region' => 'us-east-1',
    ],

    'sparkpost' => [
        'secret' => env('SPARKPOST_SECRET'),
    ],

    'stripe' => [
        'model' => App\User::class,
        'key' => env('STRIPE_KEY'),
        'secret' => env('STRIPE_SECRET'),
    ],

    'account_kit' => [
        'id' => env('ACCOUNTKIT_APP_ID'),
        'secret' => env('ACCOUNTKIT_APP_SECRET')
    ],

    'recaptcha' => [
        'key' => env('RECAPTCHA_SITE_KEY'),
        'secret' => env('RECAPTCHA_SECRET')
    ],

    'xenforo' => [
        'url' => env('XENFORO_API_URL', 'http://forum.rg/api/'),
        'key' => [
            // Não deveria colocar isso aqui, mas tu vai reclamar se parar de funcionar
            'super' => env('XENFORO_SUPER_KEY'),
            'user' => env('XENFORO_USER_KEY')
        ]
    ]


];
