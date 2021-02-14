<template>
    <div class = "pa-3">
        <v-layout row justify-center>
            <v-img contain height="150" width="150" src="/img/logo/redstone-512p.png"></v-img>
        </v-layout>

        <v-window v-model="step" style="">
            <v-window-item :value="1">
                <v-layout row justify-center>
                    <p class="title font-weight-light primary--text">Registro</p>
                </v-layout>
                <v-layout column>
                    <v-flex>

                    <v-alert dismissible type="error" outlined v-model="showAlert">
                        {{rgData.alert}}
                    </v-alert>
                    </v-flex>
                    <v-btn color="primary" class="mb-3" @click="step = 2">Original (Pago)</v-btn>
                    <v-btn color="primary" @click="step = 3">Pirata</v-btn>
                </v-layout>
            </v-window-item>

            <v-window-item :value="2">
                <v-layout class="mb-3" row justify-center>
                    <h1 class="title font-weight-light primary--text">Registro Original</h1>
                </v-layout>
                <p class="subheading">Para registrar é necessário abrir o jogo e entrar no servidor e digitar o comando /registrar</p>
                <p class="subheading">Clique no link que receberá no chat!</p>
                <p class="subheading">Isto é necessário para verificarmos que você é o dono da conta oficial do minecraft!</p>
                <v-btn  :loading="loading" :disabled="loading" class="primary" block @click="step = 1">Voltar</v-btn>
            </v-window-item>

            <v-window-item :value="3">
                <v-layout class="mb-3" row justify-center>
                    <h1 class="title font-weight-light primary--text">Registro Pirata</h1>
                </v-layout>
                <rg-register-email ref="registerEmail" :rg-data="rgData" :loading="loadingSetter"></rg-register-email>
                <!-- Botoes de enviar e voltar -->
                <v-layout row mt-4>
                    <v-flex xs5>
                        <v-btn block :loading="loading" :disabled="loading" class="primary" @click="step = 0">Voltar</v-btn>
                    </v-flex>
                    <v-spacer></v-spacer>
                    <v-flex xs5>
                        <v-btn block :loading="loading" :disabled="loading"  color="primary" @click="nextStep">Próximo</v-btn>
                    </v-flex>
                </v-layout>
            </v-window-item>

        </v-window>


    </div>

</template>
<script>


    export default {
        name: "register",
        props: ['rg-data'],
        data() {
            return {
                step: 1,
                showAlert: false,
                loading: false,
            }
        },
        mounted() {
            if (this.rgData.errors) {
                let errors = JSON.parse(this.rgData.errors);
                if (errors.email) {
                    this.step = 3;
                }
            }
            this.showAlert = this.rgData.alert !== undefined;
        },
        methods: {
            loadingSetter(l) {
                this.loading = l;
            },
            nextStep() {
                if (this.step == 3)
                {
                    this.$refs.registerEmail.submit();
                }
            }

        }

    }
</script>
