<template>
    <div class="pa-4">
        <v-layout row justify-center>
            <v-img contain height="150" width="150" src="/img/logo/redstone-512p.png"></v-img>
        </v-layout>
        <v-layout column justify-center align-center>
            <v-flex>
                <p class="display-1 font-weight-light primary--text">Registro Original</p>
            </v-flex>
            <v-flex>
                <p class="headline font-weight-light primary--text">{{rgData.username}}</p>
            </v-flex>
        </v-layout>
        <rg-register-email ref="registerEmail" :rg-data="rgData" :loading="loadingSetter"></rg-register-email>

        <v-btn class="mt-4" :loading="loading" :disabled="loading" block color="primary" @click="submit">Próximo</v-btn>
    </div>
</template>
<script>


    export default {
        name: "rg-register-premium",
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
            submit() {
                this.$refs.registerEmail.submit();
            }

        }

    }
</script>
