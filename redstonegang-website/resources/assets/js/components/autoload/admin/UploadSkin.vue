<template>
    <div>
        <v-container align-center justify-center row>
            <v-flex xs6>
                <v-card>
                    <v-container fluid>
                        <div class="ma-5">
                            <v-layout wrap>
                                <v-container align-center justify-center>
                                    <v-flex xs12>
                                        <h1 class="display-2">Upload Skin</h1>
                                        <form v-on:submit.prevent ref="form" method="post"
                                              enctype="multipart/form-data">
                                            <laravel-csrf></laravel-csrf>
                                            <v-container row>
                                                <v-flex xs6>
                                                    <v-file-input
                                                            data-vv-name="skin"
                                                            name="skin"
                                                            :error-messages="errors.collect('skin')"
                                                            v-validate="'required|image'"
                                                            accept="image/png"
                                                            label="Skin"
                                                            v-model="skin"
                                                            prepend-icon="mdi-tshirt-crew"
                                                    ></v-file-input>
                                                </v-flex>
                                                <v-flex xs3>
                                                    <v-text-field
                                                            class="px-5"
                                                            data-vv-name="name"
                                                            name="name"
                                                            :error-messages="errors.collect('name')"
                                                            v-model="name"
                                                            v-validate="{min:2,max:13,required:true,regex: '^[a-zA-Z0-9_]{2,13}$'}"
                                                            label="Nome"
                                                    ></v-text-field>
                                                </v-flex>
                                                <v-flex xs3>
                                                    <v-switch
                                                            flat
                                                            v-model="model"
                                                            :label="`Modelo: ${model ? 'Alex' : 'Steve'}`"></v-switch>
                                                </v-flex>
                                            </v-container>

                                        </form>
                                        <v-btn @click="upload" :disabled="uploading" :loading="uploading"
                                               color="primary">
                                            Upload
                                        </v-btn>
                                        <v-alert
                                                class="my-5"
                                                v-model="successAlert"
                                                type="success"
                                                border="left"
                                                dense
                                                text
                                                prominent
                                                transition="scale-transition"
                                                dismissible>
                                            Skin enviada com sucesso! Está na fila de upload para mojang!
                                        </v-alert>
                                    </v-flex>
                                </v-container>
                            </v-layout>
                        </div>
                    </v-container>
                </v-card>
            </v-flex>
        </v-container>

    </div>
</template>

<script>
    export default {
        $_veeValidate: {
            validator: 'new'
        },
        name: "rg-upload-skin",
        data: () => ({
            skin: null,
            uploading: false,
            successAlert: false,
            model: false,
            name: "",
            skinRules: [
                value => !value || value.size < 20 * 1024 || 'A skin pode ter no maximo 20kb!'
            ]
        }),
        methods: {
            upload() {
                this.$validator.validate().then((valid) => {
                    this.successAlert = false;
                    if (valid) {
                        let form = new FormData();
                        this.uploading = true;
                        form.append('skin', this.skin);
                        form.append('model', this.model);
                        form.append('name', this.name);
                        axios.post('/admin/skin', form, {
                            headers: {
                                "Content-Type": "multipart/form-data"
                            },
                        }).then(this.onSuccess).catch(this.onError);
                    }
                });
            },
            onError(error) {
                addErrorsToForm(error, this);
                this.uploading = false;
                this.successAlert = false;
            },
            onSuccess() {
                this.uploading = false;
                this.skin = null;
                this.name = null;
                this.model = false;
                this.successAlert = true;
                this.$nextTick(() => this.$validator.reset());
            }

        }

    }
</script>
