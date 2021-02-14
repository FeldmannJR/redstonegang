@extends('layouts.basic')

@section('title', 'Finalizar Registro')

@include('helpers.recaptcha')

@section('content')
    <rg-register-finish :rg-data='@json($data)'></rg-register-finish>
@endsection