<?php

namespace App\Providers;

use App\Validators\ModelExistsValidator;
use Blade;
use App\Services\Repositories\AccountRepository;
use App\Services\Repositories\AccountKitSessionRepository;
use App\Rules\Recaptcha;
use App\Services\Vendor\FacebookAccountKitService;
use App\Services\Vendor\RecaptchaService;
use Illuminate\Support\ServiceProvider;
use Illuminate\Support\Facades\Validator;

class RedstoneServiceProvider extends ServiceProvider
{
    /**
     * Register services.
     *
     * @return void
     */
    public function register()
    {
        $this->app->singleton(XenforoService::class, function ($app) {
            return new XenforoService();
        });

        $this->app->singleton(AccountRepository::class, function () {
            return new AccountRepository();
        });

        $this->app->singleton(AccountKitSessionRepository::class, function () {
            return new AccountKitSessionRepository();
        });

        // Registrando todos os arquivos dentro do Helpers automaticamente
        foreach (glob(app_path() . '/Helpers/*.php') as $file) {
            require_once($file);
        }
        //
        $this->registerConfigs();
    }

    private function registerConfigs()
    {
        $accounts = config('redstonegang.minecraft.accounts');
        if(is_array($accounts))return;
        $accounts = array_filter(explode(',', $accounts), function ($el) {
            $arr = explode(':', $el);
            return count($arr) == 2;
        });
        $accounts = array_map_assoc(function ($key, $el) {
            $arr = explode(':', $el);
            return [$arr[0] => $arr[1]];
        }, $accounts);

        config(['redstonegang.minecraft.accounts' => $accounts]);
    }


    /**
     * Bootstrap services.
     *
     * @return void
     */
    public function boot()
    {
        Blade::directive('recaptcha_header', function () {
            return "<?php echo recaptcha_header(); ?>";
        });
        Validator::extend('recaptcha', function ($attribute, $value, $parameters) {
            return (new Recaptcha())->passes($attribute, $value, $parameters);
        }, (new Recaptcha())->message());
        Validator::extend('username', function ($attribute, $value, $parameters) {
            return preg_match('/^[a-zA-Z0-9_]{3,16}$/', $value);
        }, "O nome de usuário é inválido! Somente pode conter letras, números e underlines!");
        Validator::extend('password', function ($attribute, $value, $parameters) {
            if (is_string($value)) {
                $len = strlen($value);
                if ($len < 6 || $len > 24) {
                    return false;
                }
                return true;
            }
        }, "A senha precisa ter entre 6 e 24 caracteres!");

        Validator::extend('model_exists', ModelExistsValidator::class . '@validate', 'O modelo com o :attribute não foi encontrado.');

        if ($this->app->environment('production')) {
            \URL::forceScheme('https');
        }
        //
        \Horizon::night();

    }

}
