@extends('layouts.basic')

@section('title', __('Não Autorizado'))
@section('code', '401')

@section('content')
    <error-page
        code='@yield("code")'
        message='@yield("title")'
    ></error-page>
@endsection