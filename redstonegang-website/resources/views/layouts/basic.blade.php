@extends('layouts.master')

@section('master_content')
    <v-app>
        <v-content app class="page-background">
            <v-container fill-height>
                <v-layout justify-center align-center row>
                    <v-flex xl3 lg4 sm6 xs12>
                        <v-card class="pa-2">
                            <v-container fluid>
                                @yield('content')
                            </v-container>
                        </v-card>
                    </v-flex>
                </v-layout>

            </v-container>
        </v-content>
    </v-app>
@endsection