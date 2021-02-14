<?php

namespace App\Http\Controllers\Auth;

use App\AccountKitSession;
use App\Events\RegisteredAccount;
use App\Http\Controllers\Controller;
use App\Http\Requests\RegisterAccountPost;
use App\RegisterToken;
use App\Services\Vendor\RecaptchaService;
use App\Services\Vendor\FacebookAccountKitService;
use App\Services\Vendor\MojangAccountService;
use App\Services\Vendor\XenforoService;
use Carbon\Carbon;
use Closure;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;


class RegisterController extends Controller
{
    const SESSION_PREMIUM_TOKEN = 'premiumToken';

    /**
     * Pagina inicial do registro onde tem os botões pra escolher entre original e pirata
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function index()
    {

        $data = [
            'REDIRECT' => action('Auth\RegisterController@loginWithFacebook'),
            'premium' => false
        ];
        $errors = \Session::get('errors');
        if ($errors !== null) {
            $errors = $errors->getMessages();
            if ($errors !== null) {
                $data['errors'] = json_encode($errors);
            }
        }

        if (\request()->has('alert') && is_string(\request()->get('alert'))) {
            $data['alert'] = \request()->get('alert');
        }
        if (session()->has('alert') && is_string(session('alert'))) {
            $data['alert'] = \session('alert');
        }
        return view('auth.register', compact('data'));
    }

    public function registerWithToken(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'token' => 'required|exists:register_tokens'
        ]);
        if ($validator->fails()) {
            return redirect(action('Auth\RegisterController@index'))->with('alert', 'Erro ao procurar conta!');
            return;
        }
        $validated = $validator->validated();
        $token = RegisterToken::where('token', $validated['token'])->first();
        if (!$token->isValid()) {
            $token->delete();
            return redirect(action('Auth\RegisterController@index'))->with('alert', 'Erro ao procurar conta!');
        }
        if ($token->user->account_id !== null) {
            return redirect(action('Auth\RegisterController@index'))->with('alert', 'Contá já registrada!!!');
            $token->delete();
        }
        $name = $token->user->name;

        $data = [
            'REDIRECT' => action('Auth\RegisterController@loginWithFacebook'),
            'premium' => true,
            'token' => $token->token,
            'username' => $name,
        ];
        // Se voltou com erro de valdiação manda pra view
        $errors = \Session::get('errors');
        if ($errors !== null) {
            $errors = $errors->getMessages();
            if ($errors !== null) {
                $data['errors'] = json_encode($errors);
            }
        }
        return view('auth.registerpremium', compact('data'));
    }


    /**
     * Verifica se o email já está cadastrado, caso não enviará para confirmar pelo account kit
     * @param Request $request
     * @param FacebookAccountKitService $facebook
     * @return \Illuminate\Http\RedirectResponse|\Illuminate\Routing\Redirector|null
     * @throws \Illuminate\Validation\ValidationException
     */
    public function loginWithFacebook(Request $request, FacebookAccountKitService $facebook)
    {
        $validator = Validator::make($request->all(), [
            'email' => 'required|unique:mysql_xenforo.xf_user,email',
            'premium' => 'required|in:true,false'
        ]);
        $validated = $validator->validate();
        if ($validated['premium'] === 'false') {
            return $facebook->redirectToEmailLogin($validated['email'], action('Auth\RegisterController@facebookCallback'));
        } else {
            // Se for original verifica se o token de registro ta vindo junto com o pedido
            $validator = Validator::make($request->all(), [
                'premium_token' => 'required|string|exists:register_tokens,token'
            ]);
            if ($validator->fails()) {
                // Se não tá manda pro registro com erro
                return redirect(action('Auth\RegisterController@index'))->with('alert', 'O pedido expirou por favor repita o processo!');
            }

            $token = RegisterToken::where('token', $validator->validated()['premium_token'])->first();
            // Não precisa mais do token, o usuário ta salvo no accounts temporario
            $token->delete();
            // Verifica se o token já expirou
            if (!$token->isValid()) {
                return redirect(action('Auth\RegisterController@index'))->with('alert', 'O pedido expirou por favor repita o processo!');
            }
            return $facebook->redirectToEmailLoginWithUsername($validated['email'], $token->user_id, action('Auth\RegisterController@facebookCallbackPremium'));
        }
    }


    public function facebookValidate(Request $request, FacebookAccountKitService $facebook, Closure $callback)
    {
        $validator = Validator::make($request->all(), ['code' => 'required|string', 'state' => 'required|string', 'status' => 'required|string|in:PARTIALLY_AUTHENTICATED']);
        if ($validator->passes()) {
            // Cria session
            $account = $facebook->returnFromFacebook($request->input('code'), $request->input('state'));

            if ($account !== null) {
                // Data que vai ser lida pelo vue component
                return $callback($account);
            }
        }
        return redirect(action('Auth\RegisterController@index'))->with('alert', 'Não conseguimos confirmar seu email! Por favor tente novamente!');
    }


    /**
     * Callback do facebook para confirmar o email
     * @param Request $request
     * @param FacebookAccountKitService $facebook
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\Http\RedirectResponse|\Illuminate\Routing\Redirector|\Illuminate\View\View
     * @throws \Illuminate\Validation\ValidationException
     */
    public function facebookCallbackPremium(Request $request, FacebookAccountKitService $facebook)
    {
        return $this->facebookValidate($request, $facebook, function ($account) use ($request) {

            if ($account->user !== null) {
                $username = $account->user->name;
                $data = [
                    'emailValidated' => $account->email,
                    'redirectSuccess' => route('home'),
                    'username' => $username,
                    'state' => $account->token,
                    'redirectError' => action('Auth\RegisterController@index'),
                    'finish' => action('Auth\RegisterController@register'),
                    'premium' => true,
                ];
                return view('auth.finishregister', compact('data'));
            }
            return redirect(action('Auth\RegisterController@index'))->with('alert', 'O pedido expirou por favor repita o processo!');
        });
    }

    /**
     * Callback do facebook para confirmar o email
     * @param Request $request
     * @param FacebookAccountKitService $facebook
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\Http\RedirectResponse|\Illuminate\Routing\Redirector|\Illuminate\View\View
     * @throws \Illuminate\Validation\ValidationException
     */
    public function facebookCallback(Request $request, FacebookAccountKitService $facebook)
    {
        return $this->facebookValidate($request, $facebook, function ($account) {
            $data = [
                'emailValidated' => $account->getEmail(),
                'state' => $account->token,
                'redirectSuccess' => route('home'),
                'redirectError' => action('Auth\RegisterController@index'),
                'finish' => action('Auth\RegisterController@register'),
                'premium' => false,
            ];
            return view('auth.finishregister', compact('data'));
        });
    }

    /**
     * Registra uma conta, verificando se o email é o mesmo que foi confirmado
     * @param RegisterAccountPost $request
     * @param FacebookAccountKitService $fb
     * @return \AccountRepository|\Illuminate\Database\Eloquent\Model
     */
    public
    function register(RegisterAccountPost $request, FacebookAccountKitService $fb, MojangAccountService $mojang, XenforoService $xenforo)
    {
        $data = $request->validated();

        $accountKit = AccountKitSession::where('token', $data['state'])->first();
        $username = $data['username'];
        $password = $data['password'];
        $email = $data['email'];
        // Se o email que o nego mandou é diferente do que tava na sessão manda embora
        if ($accountKit->email !== $email) {
            $accountKit->delete();
            return response()->json($this->formatErrors('email', 'Email enviado diferente do confirmado!'), 422);
        }
        $birthday = $request->birthday();
        $mojagUser = $mojang->hasPaid($username);
        $isMojangPremium = $mojagUser->status === 1;
        $isPremiumRegister = $data['premium'];
        $originalValidated = false;
        // Se for nick premium vai verificar se o cara confirmou o nick antes
        if ($isMojangPremium) {
            if ($isPremiumRegister) {
                if ($accountKit->user !== null && $accountKit->user->name === $username) {
                    $originalValidated = true;
                } else {
                    // Remove a sessão pq tentou registrar nick difrente do que confirmou
                    $accountKit->delete();
                }
            }
            // Se não manda embora
            if (!$originalValidated)
                return response()->json($this->formatErrors('username', 'Nickname original, utilize outro por favor.'), 422);
        }


        $response = $xenforo->createUser($username, $password, $email, $birthday);

        foreach ($response as $key => $value) {
            if ($key == 'errors') {
                foreach ($value as $erroKey => $erro) {
                    switch ($erro->code) {
                        case 'usernames_must_be_unique':
                            $fieldName = 'username';
                            // User names must be unique. The specified user name is already in use.
                            $msg = 'O nickname já está em uso, utilize outro por favor.';
                            break;
                        case 'email_addresses_must_be_unique':
                            $fieldName = 'email';
                            // Email addresses must be unique. The specified email address is already in use.
                            $msg = 'O E-mail já está em uso, utilize outro por favor.';
                            break;
                        default:
                            $fieldName = 'username';
                            $msg = 'Código de erro indefinido, tente novamente.';
                            break;
                    }
                    if ($fieldName === 'email' || ($fieldName === 'username' && $isPremiumRegister)) {
                        // Se vai redirecionar o cara pq o email/user ja sendo usado remove a sessão
                        $accountKit->delete();
                    }
                    return response()->json($this->formatErrors($fieldName, $msg), 422);
                }
            } else if ($key == 'success') {
                $user = $response->user;
                $forum_id = $user->user_id;
                $username = $user->username;


                $account = \AccountRepository::createAccount($username, $forum_id, $isMojangPremium);
                if ($isMojangPremium) {
                    // Se for premium já estava registrado no jogo, linka as tabelas
                    $accountKit->user->account_id = $account->id;
                    $accountKit->user->save();
                }
                // Deleta o token gerado pra não pode registrar dnv
                $accountKit->delete();
                event(new RegisteredAccount($account));
                return response()->json($account, 201);
            }

        }
    }

    public
    function formatErrors($fieldName, $msg)
    {
        return [
            'errors' => [
                $fieldName => $msg
            ]
        ];
    }
}
