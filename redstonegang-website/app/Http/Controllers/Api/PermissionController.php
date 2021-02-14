<?php

namespace App\Http\Controllers\Api;

use App\Jobs\SyncForumAccount;
use App\Models\Common\Group;
use App\Models\Common\User;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

class PermissionController extends Controller
{


    public function updateGroup(Request $request)
    {
        $data = $request->validate([
            'user_id' => 'required|model_exists:' . User::class,
            'group_id' => 'required'
        ]);
        $user = User::find($data['user_id']);
        $group_id = $request->input('group_id');
        if ($group_id === '-1') {
            $group_id = null;
        } else {
            $request->validate([
                'group_id' => 'required|model_exists:' . Group::class,
            ]);
            $group_id = (int)$group_id;
        }
        if ($user->account !== null) {
            $account_id = $user->account->id;
            SyncForumAccount::dispatch($account_id, $group_id);
        }
        return response()->json(['success' => true], 200);
    }
}
