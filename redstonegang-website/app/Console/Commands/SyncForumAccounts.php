<?php

namespace App\Console\Commands;

use App\Account;
use App\Jobs\SyncForumAccount;
use App\Models\Common\User;
use Illuminate\Console\Command;

class SyncForumAccounts extends Command
{
    /**
     * The name and signature of the console command.
     *
     * @var string
     */
    protected $signature = 'rg:syncforum';

    /**
     * The console command description.
     *
     * @var string
     */
    protected $description = 'Sincroniza as contas na fila de sincronização';

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
        $users = User::where('account_id', '!=', null)->get();
        foreach ($users as $user) {
            SyncForumAccount::dispatchNow($user->account_id, $user->group);
            $this->info("Sincronizando $user->name");
        }

    }
}
