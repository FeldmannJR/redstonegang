<?php

namespace App\Http\Controllers\Auth;

use App\Http\Controllers\Controller;
use App\Http\Requests\LoginAccountPost;
use App\Services\Vendor\XenforoService;

class LoginController extends Controller
{

    private $xenforo;

    public function __construct(XenforoService $xenforo)
    {
        $this->xenforo = $xenforo;
        // Só quem n ta logado pode logar :P
    }

    public function index()
    {
        $data = [
            'postUrl' => action('Auth\LoginController@post'),
        ];
        $prev = \Session::get('previousLocation');
        if ($prev !== null) {
            $data['onSuccessRedirect'] = $prev;
        } else {
            $data['onSuccessRedirect'] = route('home');
        }
        return view('auth/login', compact('data'));
    }

    public function logout(\Request $request)
    {
        if (\Auth::check()) {
            \Auth::logout();
        }
        return redirect()->route('home');
    }

    public function post(LoginAccountPost $request)
    {
        $data = $request->validated();
        if (\Auth::attempt($data)) {
            // o nego logou
            $data = [
                'success' => true,
                'username' => \Auth::user()->username
            ];
            return response()->json($data);
        } else {
            // Deu merda
            $errors = \Auth::guard()->getErrors();
            if ($errors != null) {
                foreach ($errors as $erroKey => $erro) {
                    switch ($erro->code) {
                        case 'unsync_account':
                        case 'your_account_has_temporarily_been_locked_due_to_failed_login_attempts':
                        case 'requested_user_x_not_found':
                            $fieldName = 'email';
                            $msg = $erro->message;
                            break;
                        case 'incorrect_password':
                            $fieldName = 'password';
                            $msg = $erro->message;
                            break;
                        default:
                            $fieldName = 'email';
                            $msg = 'Código de erro indefinido, tente novamente.';
                            break;
                    }
                    //já retorna o erro formado bonito com error code
                    throw  \Illuminate\Validation\ValidationException::withMessages([
                        $fieldName => $msg
                    ]);
                }
            }
            throw  \Illuminate\Validation\ValidationException::withMessages([
                'email' => 'Ocorreu um Erro, Tente Novamente!'
            ]);

        }

    }


}