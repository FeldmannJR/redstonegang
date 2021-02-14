<template>
    <div class="pa-4">


        <!-- Success -->
        <v-dialog
                v-model="showSuccessModal"
                persistent=""
                max-width="400">
            <v-card>

                <v-card-text class="primary headline">
                    Conta registrada com sucesso!
                </v-card-text>

                <v-card-actions>
                    <v-layout justify-center>
                        <v-btn color="primary darken-1" @click="redirectSuccess">Ok</v-btn>
                    </v-layout>
                </v-card-actions>
            </v-card>
        </v-dialog>

        <v-layout row justify-center>
            <v-img contain height="150" width="150" src="/img/logo/redstone-512p.png"></v-img>
        </v-layout>
        <v-layout row justify-center>
            <p class="tittle font-weight-light primary--text">Complete os Dados</p>
        </v-layout>

        <v-text-field
                data-vv-name="username"
                v-validate="{min:3,max:16,required:true,regex: '^[a-zA-Z0-9_]{3,16}$'}"
                :error-messages="errors.collect('username')"
                label="Nickname"
                v-model="username"
                :disabled="premium"
                append-icon="mdi-minecraft">
        </v-text-field>
        <!--A regra da senha está no app.js na criação do vue-->
        <v-text-field
                data-vv-name="password"
                v-validate="'required|password'"
                ref="password"
                :error-messages="errors.collect('password')"
                v-model="password"
                :type="showPassword ? 'text' : 'password'"
                @click:append="showPassword = !showPassword"
                :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
                label="Senha">
        </v-text-field>

        <v-text-field
                data-vv-name="password_confirmation"
                data-vv-delay="300"
                v-validate="'required|confirmed:password'"
                :error-messages="errors.collect('password_confirmation')"
                :type="showPassword ? 'text' : 'password'"
                v-model="repeatPassword"
                label="Confirmar Senha">
        </v-text-field>

        <v-text-field
                data-vv-name="email"
                :error-messages="errors.collect('email')"
                v-validate="''"
                append-icon="mdi-email"
                disabled
                label="Email"
                v-model="email"></v-text-field>

        <v-menu
                ref="menu"
                v-model="menu"
                :close-on-content-click="false"
                :nudge-right="40"
                lazy
                transition="scale-transition"
                offset-y
                full-width
                min-width="290px"
        >
            <template v-slot:activator="{ on }">
                <v-text-field
                        v-model="dateFormatted"
                        data-vv-name="birthday"
                        v-validate="'required'"
                        :error-messages="errors.collect('birthday')"
                        label="Data de Nascimento"
                        append-icon="mdi-cake-variant"
                        @blur="birthday = parseDate(dateFormatted)"
                        readonly
                        v-on="on"
                ></v-text-field>
            </template>
            <v-date-picker
                    ref="picker"
                    v-model="birthday"
                    min="1950-01-01"
                    :max="new Date().toISOString().substr(0, 10)"
                    landscape
                    reactive
                    @change="save"
            ></v-date-picker>
        </v-menu>
        <v-btn block color="primary" :disabled="submiting" @click="submit">Registrar!</v-btn>

    </div>
</template>

<script>
    export default {
        $_veeValidate: {
            validator: 'new' // give me my own validator scope.
        },
        name: "rg-register-finish",
        props: ['rg-data'],
        data() {
            return {
                submiting: false,
                premium: false,
                username: "",
                password: "",
                repeatPassword: "",
                birthday: "",
                email: this.rgData.emailValidated,
                showPassword: false,
                dateFormatted: null,
                birthdayMenu: false,
                menu: false,
                showSuccessModal: false

            }
        },
        watch: {
            menu(val) {
                this.dateFormatted = this.formatDate(this.birthday);
                val && setTimeout(() => (this.$refs.picker.activePicker = 'YEAR'))
            }
        },
        created() {
            this.premium = this.rgData.premium;
            if (this.premium === true && this.rgData.hasOwnProperty('username')) {
                this.username = this.rgData.username;
            }
        },
        mounted() {
            this.dateFormatted = this.formatDate(this.birthday);
        },
        computedDateFormatted() {
            return this.formatDate(this.birthday)
        },
        methods: {
            sendRequest(token) {
                this.submiting = true;
                axios.post(this.rgData.finish, {
                    recaptcha: token,
                    username: this.username,
                    password: this.password,
                    birthday: this.birthday,
                    email: this.email,
                    premium: this.premium,
                    state: this.rgData.state
                }).then(this.then).catch(this.catch);
            },
            then(response) {
                this.showSuccessModal = true;
            },
            redirectSuccess() {
                redirect(this.rgData.redirectSuccess);
            },
            catch(error) {
                if (error.response && error.response.data.errors) {

                    if (error.response.data.errors.hasOwnProperty('email')) {
                        // Deu ruim, deu pau no email manda ele pra cadastrar outro
                        window.redirectPost(this.rgData.redirectError, {alert: error.response.data.errors.email});
                        return;
                    }
                    if (error.response.data.errors.hasOwnProperty('state')) {
                        window.redirectPost(this.rgData.redirectError, {alert: error.response.data.errors.state});
                        return;
                    }
                    if (this.premium && error.response.data.errors.hasOwnProperty('username')) {
                        window.redirectPost(this.rgData.redirectError, {alert: error.response.data.errors.username});
                        return;
                    }
                }
                this.submiting = false;
                addErrorsToForm(error, this);
            },
            submit() {
                let component = this;
                this.$validator.validate().then((valid) => {
                    if (valid) {
                        grecaptcha.ready(function () {
                            grecaptcha.execute(window.app.$data.meta['recaptcha-site-key'], {action: 'register'}).then(component.sendRequest);
                        });
                    }
                });
            },
            save(date) {
                this.$refs.menu.save(date)
            },
            formatDate(date) {
                if (!date) return null;
                const [year, month, day] = date.split('-');
                return `${day}/${month}/${year}`
            },
            parseDate(date) {
                if (!date) return null;
                const [day, month, year] = date.split('/');
                return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`
            }


        }
    }
</script>

<style scoped>

</style>