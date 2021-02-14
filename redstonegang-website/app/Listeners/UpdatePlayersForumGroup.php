<?php

namespace App\Listeners;

use App\Account;
use App\Events\ChangeForumGroup;
use App\Jobs\SyncForumAccount;
use App\Models\Common\User;
use Illuminate\Queue\InteractsWithQueue;
use Illuminate\Contracts\Queue\ShouldQueue;

class UpdatePlayersForumGroup
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
     * @param ChangeForumGroup $event
     * @return void
     */
    public function handle(ChangeForumGroup $event)
    {
        $group = $event->getGroup();
        $users = \DB::table('accounts')
            ->join('redstonegang_common.users as users', 'users.account_id', '=', 'accounts.id')
            ->where('users.group', $group->id)
            ->select('accounts.id')->get();
        foreach ($users as $user) {
            // Manda pra queue de atualizar os grupos do forum
            SyncForumAccount::dispatch($user->id, $group === null ? null : $group->id);
        }

    }
}
