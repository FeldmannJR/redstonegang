{{--Inserindo o script do recaptcha e o meta para acessar as keys dentro do js--}}
@push('scripts')
    <meta name="recaptcha-site-key" content="{{ recaptcha_site_key() }}">
    <script src="https://www.google.com/recaptcha/api.js?render={{ recaptcha_site_key() }}"></script>
@endpush