@extends('layouts.admin')
@section('page_id','groups')
@section('title','Editando Grupo')
@section('content')

    <rg-edit-group
            :permissions-desc='@json($permissions_desc)'
            list-link="{{route('listgroups')}}"
            :xenforo-groups='@json($xenforo_groups) '
            :edit-group='@json($group)'
    ></rg-edit-group>
@endsection
