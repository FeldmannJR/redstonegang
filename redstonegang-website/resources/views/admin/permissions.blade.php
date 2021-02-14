@extends('layouts.admin')

@section('title', 'Permissões')
@section('page_id','permissions')
@push('scripts')
@endpush
@section('content')
    <?php
    $header = [
        [
            'text' => 'Nome',
            'align' => 'left',
            'value' => 'name'
        ],
        [
            'text' => 'Permissão',
            'value' => 'key'
        ],
        [
            'text' => 'Descrição',
            'value' => 'desc'
        ],
    ];
    $headerOptions = [
        [
            'text' => 'Nome',
            'align' => 'left',
            'value' => 'name'
        ],
        [
            'text' => 'Opção',
            'value' => 'key'
        ],
        [
            'text' => 'Descrição',
            'value' => 'desc'
        ],
    ];
    ?>
    <v-tabs right centered>
        <v-tab key="permissions">
            Permissões
        </v-tab>
        <v-tab-item key="permissions" ripple>
            <v-data-table
                    :items='@json($permissions)'
                    :headers='@json($header)'
                    hide-default-footer>
                <template v-slot:item="props">
                    <tr>
                        <td>@{{ props.item.name }}</td>
                        <td>@{{ props.item.key }}
                            <span class="float-right">
                        <v-btn small text icon @click="$copyText(props.item.key)">
                            <v-icon>mdi-content-copy</v-icon>
                        </v-btn>
                        </span>
                        </td>
                        <td>@{{ props.item.desc }}</td>
                    </tr>
                </template>
            </v-data-table>
        </v-tab-item>
        <v-tab key="options" ripple>
            Opções
        </v-tab>
        <v-tab-item key="options">
            <v-data-table
                    :items='@json($options)'
                    :headers='@json($headerOptions)'
                    hide-default-footer>
                <template v-slot:item="props">
                    <tr>
                        <td>@{{ props.item.name }}</td>
                        <td>@{{ props.item.key }}
                            <span class="float-right">
                            <v-btn icon @click="$copyText(props.item.key)">
                                <v-icon>mdi-content-copy</v-icon>
                            </v-btn>
                            </span>
                        </td>
                        <td>@{{ props.item.desc }}</td>
                    </tr>
                </template>
            </v-data-table>
        </v-tab-item>
        <v-tab-item key="edit">
            <rg-edit-group :permissions='@json($permissions)'></rg-edit-group>
        </v-tab-item>
    </v-tabs>

@endsection
