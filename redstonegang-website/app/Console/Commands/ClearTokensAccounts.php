<?php

namespace App\Console\Commands;

use Illuminate\Console\Command;

class ClearTokensAccounts extends Command
{
    /**
     * The name and signature of the console command.
     *
     * @var string
     */
    protected $signature = 'rg:cleartokens';

    /**
     * The console command description.
     *
     * @var string
     */
    protected $description = 'Limpa Tokens expirados';

    /**
     * Create a new command instance.
     *
     * @return void
     */
    public function __construct()
    {
        parent::__construct();
    }

    /**
     * Execute the console command.
     *
     * @return mixed
     */
    public function handle()
    {
        $tokens = \App\RegisterToken::get();
        foreach ($tokens as $token) 
        {
            if(!$token->isValid())
            {
                echo "Removendo Token ID($token->id): $token->token";
                $token->delete();
            }
        }
        echo 'Total de RegisterTokens removidos: '.count($tokens);

        $accountKitSession = \App\AccountKitSession::get();
        foreach ($accountKitSession as $session) 
        {
            if(!$session->isValid())
            {
                echo "Removendo Session ID($session->id): $session->token";
                $session->delete();
            }
        }
        echo 'Total de AccountKitSession removidos: '.count($accountKitSession);
    }
}
