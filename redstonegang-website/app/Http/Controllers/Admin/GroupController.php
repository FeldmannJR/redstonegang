<?php


namespace App\Http\Controllers\Admin;


use App\Events\ChangeForumGroup;
use App\Http\Controllers\Controller;
use App\Models\Common\Group;
use App\Models\Common\Permission;
use App\Models\Common\PermissionDesc;
use App\Models\Common\PermissionServer;
use App\Models\Common\PermissionValue;
use App\Models\XF\XFUserGroup;
use BenSampo\Enum\Rules\EnumValue;
use Illuminate\Contracts\Validation\Rule;
use Illuminate\Http\Request;
use Illuminate\Validation\Validator;

class GroupController extends Controller
{

    public function listGroups(Request $request)
    {
        $groups = Group::with(['forum', 'parentGroup'])->get();
        return view('admin.groups.list', compact('groups'));

    }

    public function editGroup(Request $request, Group $group)
    {
        $request->validate([
            'id' => 'model_exists:' . Group::class,
            'forum_group' => ''
        ]);
        $permissions_desc = PermissionDesc::where('type', 0)->get();
        $xenforo_groups = XFUserGroup::orderBy('user_group_id', 'desc')->get(['title', 'user_group_id']);
        $data = compact('group', 'xenforo_groups', 'permissions_desc');
        return view('admin.groups.edit', $data);

    }

    public function setForumGroup(Request $request)
    {
        $v = \Validator::make($request->all(), [
            'id' => 'required|model_exists:' . Group::class,
            'forum_group' => 'required|model_exists:' . XFUserGroup::class
        ]);
        $v->validate();

        $data = $v->validated();
        $group = Group::find($data['id']);
        $group->forum()->associate(XFUserGroup::find($data['forum_group']));
        $group->save();
        event(new ChangeForumGroup($group));
        return $group;
    }

    public function removeForumGroup(Request $request)
    {
        $v = \Validator::make($request->all(), [
            'id' => 'required|model_exists:' . Group::class,
        ]);
        $v->validate();
        $data = $v->validated();
        $group = Group::find($data['id']);
        $group->forum()->dissociate();
        $group->save();
        event(new ChangeForumGroup($group));
        return $group;
    }

    public function listPermissions(Request $request)
    {
        $v = \Validator::make($request->all(), [
            'id' => 'required|model_exists:' . Group::class,
            'server' => [
                'required',
                new EnumValue(PermissionServer::class, false)
            ]]);
        $v->validate();
        $group = Group::find($v->validated()['id']);
        $server = PermissionServer::getInstance((integer)$v->validated()['server']);
        return $group->permissionsToArray($server);
    }

    public function setPermission(Request $request)
    {

        $v = \Validator::make($request->all(), [
            'id' => 'required|model_exists:' . Group::class,
            'server' => 'required|enum_value:' . PermissionServer::class,
            'permissions' => 'required|array|min:1',
            'permissions.*.value' => 'required|enum_value:' . PermissionValue::class,
            'permissions.*.key' => 'required|string|min:1|max:64'
        ]);
        $v->validate();
        $perms = $v->validated()['permissions'];
        $group = Group::find($v->validated()['id']);
        $server = PermissionServer::getInstance($v->validated()['server']);
        foreach ($perms as $perm) {
            $group->setPermission($perm['key'], PermissionValue::getInstance($perm['value']), $server);
        }
        return $group->permissionsToArray($server);
    }

}