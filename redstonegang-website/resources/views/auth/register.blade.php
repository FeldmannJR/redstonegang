@extends('layouts.basic')

@section('title', 'Registro')

@section('content')
    <register :rg-data="{{json_encode($data)}}"></register>
@endsection