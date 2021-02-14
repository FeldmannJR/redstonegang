@extends('layouts.basic')

@section('title', __('Muitas Solicitações'))
@section('code', '429')

@section('content')
    <error-page
        code='@yield("code")'
        message='@yield("title")'
    ></error-page>
@endsection