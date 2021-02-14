<?php

namespace App\Http\Controllers\Api;

use App\Models\Common\User;
use App\RegisterToken;
use App\Services\Vendor\XenforoService;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use Illuminate\Validation\ValidationException;

class AuthController extends Controller
{

    public function login(Request $request, XenforoService $xenforo)
    {
        $validated = $request->validate([
            'username' => 'required|string',
            'password' => 'required|string'
        ]);

        $loggedin = $xenforo->login($validated['username'], $validated['password']);
        if ($loggedin) {
            return response()->json(['success' => true]);
        } else {
            return response()->json(['success' => false], 422);
        }
    }

    public function generatePaidToken($user_id)
    {
        // Se tiver remove os antigos
        if (RegisterToken::where('user_id', $user_id)->exists()) {
            RegisterToken::where('user_id', $user_id)->delete();
        }
        $token = new RegisterToken(['user_id' => $user_id]);
        $token->generateToken();
        $token->save();
        return $token->only('token', 'user_id', 'created_at');
    }

}
