<?php

namespace App\Http\Requests;

use Carbon\Carbon;
use Illuminate\Foundation\Http\FormRequest;
use Facades\App\Services\Vendor\FacebookAccountKitService;

class RegisterAccountPost extends FormRequest
{
    /**
     * Determine if the user is authorized to make this request.
     *
     * @return bool
     */
    public function authorize()
    {
        return true;
    }

    public function birthday()
    {
        return Carbon::createFromFormat('Y-m-d', $this->validated()['birthday']);
    }


    /**
     * Get the validation rules that apply to the request.
     *
     * @return array
     */
    public function rules()
    {
        return [
            'username' => 'required|unique:accounts,username',
            'email' => 'required|email',
            'state' => 'required|exists:account_kit_sessions,token',
            'password' => 'required|password',
            'birthday' => 'required|date_format:Y-m-d',
            'recaptcha' => 'required|recaptcha:register',
            'premium' => 'required|boolean'
        ];
    }
}
