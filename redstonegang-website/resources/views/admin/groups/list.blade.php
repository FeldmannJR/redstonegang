@extends('layouts.admin')
@section('page_id','groups')
@section('title','Grupos')
@section('content')

    <v-card>
        <v-container fluid>
            <div class="ma-5">
                <v-layout wrap>
                    @foreach($groups as $group)
                        <v-card class="ma-4" min-width="300px">
                            <v-card-text>
                                <div class="headline">{{$group->name}}</div>
                                @if($group->prefix!=null)
                                    <minecraft-text class="headline-2 mb-2">{{$group->prefix}}</minecraft-text>@else
                                @endif

                                <div class="subtitle-1">
                                    Forum: {{$group->forum!=null?$group->forum->title:'Não Setado'}}
                                </div>
                                @if($group->parent != null)
                                    <div class="subtitle-1">
                                        Parente: {{$group->parentGroup->name}}
                                    </div>
                                @endif
                            </v-card-text>
                            <v-card-actions>
                                <v-btn icon color="primary" href="{{route('editgroup',['group'=>$group->id])}}">
                                    <v-icon>mdi-circle-edit-outline</v-icon>
                                </v-btn>
                            </v-card-actions>
                        </v-card>
                    @endforeach
                </v-layout>
            </div>
        </v-container>
    </v-card>
@endsection
