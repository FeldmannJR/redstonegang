@extends('layouts.basic')

@section('title', __('Não Encontrado'))
@section('code', '404')

@section('content')
    <error-page
        code='@yield("code")'
        message='@yield("title")'
    ></error-page>
@endsection