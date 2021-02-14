@extends('layouts.basic')

@section('title', 'Registro')

@section('content')

<rg-register-premium :rg-data='@json($data)'></rg-register-premium>
@endsection