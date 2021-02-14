<template>
    <div class="pa-5">
        <v-layout row justify-center>
            <v-img contain height="150" width="150" src="/img/logo/redstone-512p.png"></v-img>
        </v-layout>
        <v-layout row justify-center>
            <p class="title font-weight-light primary--text">Login</p>
        </v-layout>

        <form v-on:submit.prevent>

        <v-layout column>
            <v-flex>
                <v-text-field
                        data-vv-name="email"
                        v-validate="'required|min:3'"
                        :error-messages="errors.collect('email')"
                        v-model="email"
                        append-icon="mdi-minecraft"
                        label="Usuário ou Email">
                </v-text-field>
            </v-flex>
            <v-flex>
                <v-text-field
                        data-vv-name="password"
                        v-validate="'required'"
                        ref="password"
                        :error-messages="errors.collect('password')"
                        v-model="password"
                        :type="showPassword ? 'text' : 'password'"
                        @click:append="showPassword = !showPassword"
                        @keyup.enter="submit"
                        :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
                        label="Senha">
                </v-text-field>
            </v-flex>
            <v-flex class="mt-2">
                <a 
                    class="body-1 font-weight-medium info--text" 
                    style="text-decoration: none;" 
                    href="http://www.redstonegang.com.br/lost-password/">
                Esqueceu a senha?</a>
            </v-flex>
        </v-layout>
        <v-layout row wrap justify-end>
            <v-flex sm4>
                <v-btn 
                    :loading="buttonLoading" 
                    :disabled="buttonLoading" 
                    block
                    color="primary" 
                    @click="submit">
                    Login
                </v-btn>
            </v-flex>
        </v-layout>

        </form>
    </div>

</template>
<script>
    export default {
        $_veeValidate: {
            validator: 'new'
        },
        name: "login-form",
        props : ['rg-data'],
        data() {
            return {
                showPassword: false,
                buttonLoading: false,
                email: '',
                password: '',
                alertText: '',
                alert: false,
            }
        },
        methods: {
            sendRequest() {
                this.buttonLoading = true;
                axios.post(this.rgData.postUrl, {
                    email: this.email,
                    password: this.password,
                }).then(this.then).catch(this.catch);
            },
            then(post) {
                this.buttonLoading = false;
                let response = post.data;
                if(response['success'])
                {
                    this.redirect(this.rgData.onSuccessRedirect);
                }
            },
            redirect(url) {
                redirect(url);
            },
            catch(error) {
                this.buttonLoading = false;
                addErrorsToForm(error, this);
            },
            submit() {
                let component = this;
                this.$validator.validate().then((valid) => {
                    if (valid) {
                        component.sendRequest();
                    }
                });
            },
        }

    }
</script>
