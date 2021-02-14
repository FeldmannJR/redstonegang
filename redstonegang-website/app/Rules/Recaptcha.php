<?php

namespace App\Rules;

use Facades\App\Services\Vendor\RecaptchaService;
use Illuminate\Contracts\Validation\Rule;

class Recaptcha implements Rule
{


    /**
     * Create a new rule instance.
     *
     * @return void
     */
    public function __construct()
    {

    }

    /**
     * Determine if the validation rule passes.
     *
     * @param string $attribute
     * @param mixed $value
     * @param array $parameters
     * @return bool
     */
    public function passes($attribute, $value, $parameters = [])
    {
        if ($value !== null && $value !== '' && is_string($value)) {
            $length = count($parameters);
            $scope = null;
            if ($length >= 1) {
                if (is_string($parameters[0])) {
                    $scope = $parameters[0];
                }
            }
            $score = 0;
            if ($length >= 2) {
                $score = $parameters[1];
                if (is_numeric($score)) {
                    $score = floatval($score);
                }
                if ($score < 0 || $score > 10) {
                    $score = 0;
                }
            }
            return RecaptchaService::isTokenValid($value, $score, $scope);

        }
        return false;
    }

    /**
     * Get the validation error message.
     *
     * @return string
     */
    public function message()
    {
        return 'Ocorreu um erro com a validação do formulário.';
    }
}
