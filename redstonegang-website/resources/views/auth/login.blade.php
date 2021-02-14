@extends('layouts.basic')

@section('title', 'Login')

@section('content')
    <login-form :rg-data="{{json_encode($data)}}"></login-form>
@endsection