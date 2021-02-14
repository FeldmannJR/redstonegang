<?php

namespace App\Listeners;

use App\Events\RegisteredAccount;
use App\Jobs\SyncForumAccount;
use Illuminate\Queue\InteractsWithQueue;
use Illuminate\Contracts\Queue\ShouldQueue;

class SyncForumGroupOnRegister
{
    /**
     * Create the event listener.
     *
     * @return void
     */
    public function __construct()
    {
        //
    }

    /**
     * Handle the event.
     *
     * @param RegisteredAccount $event
     * @return void
     */
    public function handle(RegisteredAccount $event)
    {
        $account = $event->getAccount();
        if (!$account->isPremium()) {
            // Se for pirata não tem grupo ainda n precisa syncronizar
            return;
        }
        if ($account->user === null) {
            // Se não tem usuario linkado com o minecraft não precisa tbm
            return;
        }
        $group = $account->user->group;
        SyncForumAccount::dispatch($account->id, $group);

    }
}
