<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use Ibonly\FacebookAccountKit\AccountKit;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Validator;

class AccountLogin extends Controller
{

    public function index()
    {
        $user = $this->getUser();
        return view('accountkit', [
            'user_account' => $user,
            'logado' => $user !== null,
            'APP_ID' => config('accountKit.appId')
        ]);
    }
    /*
     * Request é injetado pelo laravel como parametro!
     * */
    /**
     * @param Request $request
     */
    public function login(Request $request)
    {
        // Validando os dados da maneira correta
        $validator = Validator::make($request->all(), [
            'code' => 'required',
            'csrf' => 'required',
        ]);
        // Se não passou nos testes
        if ($validator->fails()) {
            return redirect(route('home'))
                ->withErrors($validator);
        }
        // Passou a validação
        // Seta sessão
        $code = $request->input('code');
        session([
            'code' => $request->input($code),
            'csfr' => $request->input('csfr')
        ]);
        $data = $this->getUser();
        return redirect(route('home'));
    }

    public function getUser()
    {
        if (session()->has('user')) {
            return session('user');
        }
        if (session()->has('code')) {
            $code = session()->get('code');
            $data = AccountKit::accountKitData($code);
            session('user', $data);
            return $data;
        }
        return null;
    }

    public function logout()
    {
        session()->remove('user');
        session()->remove('code');
        return redirect(route('home'));
    }


}