<?php

namespace App\Jobs;

use App\Account;
use App\Models\Common\Group;
use App\Models\Common\User;
use App\Services\Vendor\XenforoService;
use Illuminate\Bus\Queueable;
use Illuminate\Queue\SerializesModels;
use Illuminate\Queue\InteractsWithQueue;
use Illuminate\Contracts\Queue\ShouldQueue;
use Illuminate\Foundation\Bus\Dispatchable;

class SyncForumAccount implements ShouldQueue
{
    use Dispatchable, InteractsWithQueue, Queueable, SerializesModels;


    private $account_id;
    private $group_id;

    public $tries = 10;

    public $retryAfter = 60;

    /**
     * Create a new job instance.
     *
     * @param $account_id
     * @param int $group_id
     */
    public function __construct($account_id, int $group_id = null)
    {
        //
        $this->account_id = $account_id;
        $this->group_id = $group_id;
    }

    /**
     * Execute the job.
     *
     * @param XenforoService $xenforo
     * @return void
     * @throws \Exception
     */
    public function handle(XenforoService $xenforo)
    {
        $account = Account::find($this->account_id);
        $forumAccount = $account->forum;
        /** @var User $user */
        if ($forumAccount !== null) {
            /** @var Group $user */
            $group = $this->group_id !== null ? Group::find($this->group_id) : null;
            $forum_group_ids = [];
            // Se tiver grupo seta ele, se não seta nada
            $staff = false;
            if ($group !== null && $group->forum_group !== null) {
                $forum_group_ids = [$group->forum_group];
                $staff = $group->isStaff();
            }
            $request = $xenforo->syncForumAccount($forumAccount->user_id, $forum_group_ids, $staff);
            if (!$xenforo->isSuccess($request)) {
                throw new \Exception('Request para o forum não válido!');
            }
            $current_groups_id = $request->user->secondary_group_ids;
            sort($current_groups_id);
            sort($forum_group_ids);
            if (!is_array($current_groups_id) || count($current_groups_id) !== count($forum_group_ids) || $current_groups_id != $forum_group_ids) {
                throw new \Exception('Request para o forum não válido esperado ' . implode(',', $forum_group_ids) . ' retornado ' . implode(',', $current_groups_id));
            }
            // Atualizou
        }

    }
}
