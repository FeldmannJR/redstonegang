@extends('layouts.basic')

@section('title', __('Página Expirada'))
@section('code', '419')

@section('content')
    <error-page
        code='@yield("code")'
        message='@yield("title")'
    ></error-page>
@endsection