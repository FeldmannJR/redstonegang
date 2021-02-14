@extends('layouts.basic')

@section('title', __('Acesso Proibido'))
@section('code', '403')
@section('message', __($exception->getMessage() ?: 'Acesso Proibido'))

@section('content')
    <error-page
        code='@yield("code")'
        message='@yield("message")'
    ></error-page>
@endsection