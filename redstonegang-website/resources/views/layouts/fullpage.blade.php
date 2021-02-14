@extends('layouts.master')

@push('scripts')
    <script>
        window.auth_user = @json(auth()->user());
    </script>
@endpush
@section('master_content')
    <rg-layout large banner-url="{{asset('img/bg/2.jpg')}}">
        <v-container fluid>
            @yield('content')
        </v-container>
    </rg-layout>
@endsection