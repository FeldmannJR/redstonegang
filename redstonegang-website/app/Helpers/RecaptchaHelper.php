<?php
if (!function_exists('recaptcha_site_key')) {
    function recaptcha_site_key()
    {
        return config('services.recaptcha.key');
    }
}
