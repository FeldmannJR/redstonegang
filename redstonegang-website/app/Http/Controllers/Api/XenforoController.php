<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Services\Vendor\XenforoService;

class XenforoController extends Controller
{

    private $xenforo;

    public function __construct(XenforoService $xenforo)
    {
        $this->xenforo = $xenforo;
    }

    public function index()
    {
        return response()->json($this->xenforo->getIndex());
    }

    public function auth($login, $password)
    {
        return response()->json($this->xenforo->doAuth($login, $password));
    }

    public function getUsers($page)
    {
        return response()->json($this->xenforo->getUsers($page));
    }

    public function createUser($username, $password, $email)
    {
        return response()->json($this->xenforo->createUser($username, $password, $email));
    }

    public function findUser($username)
    {
        return response()->json($this->xenforo->findUser($username));
    }

    public function getUser($id, $with_posts = false, $page = 0)
    {
        return response()->json($this->xenforo->getUser($id, $with_posts, $page));
    }

    public function updateUser(Request $request, $id)
    {
        $userInfo = $request->input('user');
        return response()->json($this->xenforo->updateUser($id, $userInfo));
    }

    public function deleteUser($id, $rename_to = '')
    {
        return response()->json($this->xenforo->deleteUser($id, $rename_to));
    }

    public function setXenforoGroup($account_id, $group_id)
    {

    }

}
