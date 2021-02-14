<template>
    <div class="ma-2">

        <v-card>
            <v-card-title>
                <v-btn color="primary" :disabled="!shouldSave || saving" :loading="saving"
                       @click="showConfirmModal = true">
                    <v-icon>mdi-content-save</v-icon>
                    Salvar
                </v-btn>
                <v-btn class="ml-3" color="blue" :disabled="saving" :loading="saving"
                       @click="add.modal = true">
                    <v-icon>mdi-playlist-plus</v-icon>
                    Adicionar Permissão
                </v-btn>
                <v-btn class="ml-3 grey " :disabled="!shouldSave || saving" :loading="saving"
                       @click="cleanUnsaved">
                    <v-icon>mdi-delete</v-icon>
                    Limpar Modificações
                </v-btn>
                <v-spacer></v-spacer>
                <v-text-field
                        v-model="search"
                        append-icon="mdi-file-document-box-search-outline"
                        label="Procurar"
                        single-line
                        hide-details
                ></v-text-field>
            </v-card-title>
            <v-data-table
                    class="mt-5"
                    :items="selectedPerms"
                    item-key="desc"
                    :search="search"
                    :headers="tableHeader"
            >
                <template v-slot:item="{ item }">
                    <tr>

                        <v-layout mb-2 mt-2>

                            <td style="min-width: 30%">
                                <v-tooltip right>
                                    <template v-slot:activator="{ on }">
                                        <v-icon v-on="on">mdi-help-circle</v-icon>
                                    </template>
                                    <span>{{item.desc}}</span>
                                </v-tooltip>
                                <span class="heading">{{item.name}}</span>
                            </td>
                            <td>

                                <v-layout justify-center>
                                    <v-btn-toggle v-model="item.selected" rounded mandatory>
                                        <v-btn :value="1" color="green" text>
                                            <v-icon :color="item.selected === 1 ?'#00FF00':null ">
                                                mdi-check-bold
                                            </v-icon>
                                        </v-btn>
                                        <v-btn :value="0" color="yellow" text>
                                            <v-icon :color="item.selected === 0 ?'rgb(211,141,0)':null ">
                                                mdi-drag-vertical
                                            </v-icon>

                                        </v-btn>
                                        <v-btn :value="2" color="red" text>
                                            <v-icon :color="item.selected === 2 ?'rgb(196, 0, 0)':null ">
                                                mdi-close
                                            </v-icon>
                                        </v-btn>
                                    </v-btn-toggle>
                                </v-layout>

                            </td>
                        </v-layout>

                    </tr>
                </template>
            </v-data-table>
        </v-card>

        <v-dialog persistent v-model="showConfirmModal" max-width="500">
            <v-card>
                <v-card-text class="headline">Atualizar Permissões</v-card-text>
                <v-card-text>
                    <span class="subtitle-1">Você irá atualizar estar permissões:</span>
                    <div class="mb-1" v-for="perm in editedPermissions" :key="perm.key">
                        <v-chip>

                            {{perm.name}}
                        </v-chip>
                        de
                        <v-chip :color="getColor(currentPerms[perm.key])">
                            {{getOriginalValue(perm)}}
                        </v-chip>
                        para
                        <v-chip :color="getColor(perm.selected)">
                            {{getEditedValue(perm)}}
                        </v-chip>
                    </div>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn text color="red darken-1" @click="showConfirmModal=false">Voltar</v-btn>
                    <v-btn text color="green darken-1" @click="sendRequest">Confirmar</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
        <v-dialog persistent v-model="add.modal" max-width="500">
            <v-card>
                <v-card-text class="headline">Adicionar Permissão</v-card-text>
                <v-card-text>
                    <v-form ref="form" lazy-validation>
                        <v-text-field
                                v-model="add.key"
                                outlined label="Permissão"
                                :rules="add.rules.key"
                                required
                        ></v-text-field>
                        <v-layout justify-center column align-center>
                            <p>Tem a permissão?</p>
                            <v-btn-toggle v-model="add.value" rounded mandatory>
                                <v-btn :value="1" color="green" text>
                                    <v-icon :color="add.value === 1 ?'#00FF00':null ">
                                        mdi-check-bold
                                    </v-icon>
                                </v-btn>
                                <v-btn :value="2" color="red" text>
                                    <v-icon :color="add.value === 2 ?'rgb(196, 0, 0)':null ">
                                        mdi-close
                                    </v-icon>
                                </v-btn>
                            </v-btn-toggle>
                        </v-layout>
                    </v-form>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn text color="red darken-1" @click="add.modal=false">Voltar</v-btn>
                    <v-btn text color="green darken-1" @click="sendAddPermission">Confirmar</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>

    export default {
        name: "rg-edit-group-permissions",
        props: ["permissions-desc", "edit-group", "server-id"],
        data() {
            return {
                currentPerms: [],
                selectedPerms: [],
                group: null,
                saving: false,
                search: "",
                showConfirmModal: false,
                tableHeader:
                    [
                        {
                            text: 'Nome',
                            value: 'name',
                            align: 'left'
                        },
                        {
                            text: 'Valor',
                            value: 'selected',
                            align: 'left'
                        },
                        {
                            text: 'Custom',
                            value: 'custom'
                        }
                    ],
                add: {
                    modal: false,
                    key: "",
                    value: 1,
                    valid: false,
                    rules: {
                        key: [
                            v => !!v || "Você precisa informar a permissão!",
                            v => !v.includes(' ') || "A permissão não pode conter espaços!"
                        ]
                    }
                }
            }
        },
        computed: {
            editedPermissions() {
                let edited = [];
                for (let perm of this.selectedPerms) {
                    if (this.currentPerms[perm.key] != null) {
                        if (perm.selected !== this.currentPerms[perm.key]) {
                            edited.push(perm);
                        }
                    } else {
                        if (perm.selected !== 0) {
                            edited.push(perm);
                        }

                    }
                }
                return edited;
            },
            shouldSave() {
                return this.editedPermissions.length > 0;
            }
        },
        methods: {
            getOriginalValue(perm) {
                return this.getNameValue(this.currentPerms[perm.key]);
            },
            getEditedValue(perm) {
                return this.getNameValue(perm.selected);
            },
            getNameValue(key) {
                switch (key) {
                    case 0:
                        return "Herdar";
                    case 1:
                        return "Tem";
                    case 2:
                        return "Não tem";
                }
                return "Herdar";
            },
            getColor(value) {
                switch (value) {
                    case 0:
                        return "yellow";
                    case 1:
                        return "green";
                    case 2:
                        return "red";
                }
                return "yellow";
            },
            saveGroup() {
                if (this.shouldSave) {
                    this.sendRequest();
                }
            },
            convertEditedToKeyValue() {
                let vetor = [];
                for (let perm of this.editedPermissions) {
                    vetor.push({
                        key: perm.key,
                        value: perm.selected
                    });
                }
                return vetor;

            },
            cleanUnsaved() {
                for (let perm of this.editedPermissions) {
                    let current = this.currentPerms[perm.key];
                    if (current == null) {
                        current = 0;
                    }
                    perm.selected = current;
                }
            },
            sendRequest() {
                this.saving = true;
                this.showConfirmModal = false;
                if (this.shouldSave) {
                    axios.post('/admin/group/update/permission', {
                        id: this.group.id,
                        server: this.serverId,
                        permissions: this.convertEditedToKeyValue()
                    }).then(this.then).catch(this.catch);
                }
            },
            then(post) {
                this.saving = false;
                this.currentPerms = post.data;
                this.checkForCustomPermissions();
            },

            catch(error) {
                this.saving = false;
                console.log(error.response);
            },

            loadedPermissions(get) {
                this.currentPerms = get.data;
                for (let x = 0; x < this.permissionsDesc.length; x++) {
                    let perm = this.permissionsDesc[x];
                    // none 0
                    // allow 1
                    // deny 2
                    if (this.currentPerms[perm.key] != null) {
                        perm.selected = this.currentPerms[perm.key];
                    } else {
                        perm.selected = 0;
                    }
                    this.selectedPerms.push(perm);
                }
                this.checkForCustomPermissions();

            },
            checkForCustomPermissions() {
                A:for (let left in this.currentPerms) {
                    for (let perm of this.selectedPerms) {
                        if (perm.key === left) {
                            continue A;
                        }
                    }
                    this.pushCustom(left, this.currentPerms[left]);

                }
            },
            pushCustom(left, sel) {
                this.selectedPerms.push({
                    key: left,
                    name: '' + left,
                    desc: "Permissão adicionada manualmente !",
                    selected: sel,
                    custom: true
                });
            },
            sendAddPermission() {
                if (this.$refs.form.validate()) {
                    this.add.modal = false;
                    this.pushCustom(this.add.key, this.add.value);
                    this.add.key = null;
                    this.add.value = 1;
                }

            }

        },
        created() {
            this.group = this.editGroup;
            axios.get('/admin/group/permissions', {
                params: {
                    server: this.serverId,
                    id: this.group.id,
                }
            }).then(this.loadedPermissions).catch(function (error) {
                // Deu merda nem sei oq fazer
            });

        }
    }
</script>

<style scoped>

</style>