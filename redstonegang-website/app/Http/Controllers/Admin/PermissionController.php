<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Models\Common\Group;
use App\Models\Common\PermissionDesc;
use App\Models\XF\XFUserGroup;
use Illuminate\Http\Request;

class PermissionController extends Controller
{


    public function index()
    {
        $permissions = PermissionDesc::where('type', 0)->get();
        $options = PermissionDesc::where('type', 1)->get();
        return view('admin.permissions', compact('options', 'permissions'));
    }

}
