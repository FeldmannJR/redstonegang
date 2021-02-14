@extends('layouts.master')

@push('scripts')
    <script>
        window.auth_user = @json(auth()->user());
    </script>
@endpush
@section('master_content')
    <rg-admin-layout selected="@yield('page_id')">
        @yield('content')
    </rg-admin-layout>
@endsection
