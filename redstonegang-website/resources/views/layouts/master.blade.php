<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="csrf-token" content="{{ csrf_token() }}">

    @stack('meta')

    <title>@yield('title') | RedstoneGang</title>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <link rel="icon" type="image/png" href="img/logo/redstone-180p.png" sizes="180x180">
    <link rel="apple-touch-icon" href="img/logo/redstone-512p.png">
    <link href="{{ mix('/css/app.css') }}" rel="stylesheet">
    
    @stack('scripts')

</head>
<body>
<div id="app">
    @yield('master_content')
</div>
<script src="{{ mix('/js/app.js') }}"></script>
</body>
</html>