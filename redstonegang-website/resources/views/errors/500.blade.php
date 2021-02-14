@extends('layouts.basic')

@section('title', __('Ocorreu um Erro'))
@section('code', '500')

@section('content')
    <error-page
        code='@yield("code")'
        message='@yield("title")'
    ></error-page>
@endsection