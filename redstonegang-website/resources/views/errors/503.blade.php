@extends('layouts.basic')

@section('title', __('Serviço Indisponível'))
@section('code', '503')
@section('message', __($exception->getMessage() ?: 'Serviço Indisponível'))

@section('content')
    <error-page
        code='@yield("code")'
        message='@yield("title")'
    ></error-page>
@endsection