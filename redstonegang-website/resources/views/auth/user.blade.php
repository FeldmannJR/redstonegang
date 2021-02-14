@extends('layouts.basic')

@section('title', 'Painel')

@section('content')
    <rg-user :rg-data='@json($data)'></rg-user>
@endsection