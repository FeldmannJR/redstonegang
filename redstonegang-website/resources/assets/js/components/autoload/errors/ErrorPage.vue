<template>
    <div class="pa-5">
        <v-layout row justify-center>
            <v-img contain height="150" width="150" src="/img/logo/redstone-512p.png"></v-img>
        </v-layout>
        <v-layout row justify-center>
            <h2 class="display-1 font-weight-light primary--text">{{code}}</h2>
        </v-layout>
        <v-layout row justify-center>
            <p class="font-weight-light primary--text">{{message}}</p>
        </v-layout>
        <v-layout row justify-center v-if="redirectHome">
            <p class="font-weight-light primary--text" v-show="redirectTimer>0">Redirecionando em {{redirectTimer}}
                segundos</p>
            <p class="font-weight-light primary--text" v-show="redirectTimer===0">Redirecionando ...</p>


        </v-layout>
    </div>
</template>
<script>
    export default {
        name: "error-page",
        props: {
            code: String,
            message: String,
            redirectHome: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
                redirectTimer: 5
            }
        },
        methods: {
            redirectToHome() {
                redirect('/');
            },
            secondDown() {
                if (this.redirectTimer > 0) {
                    this.redirectTimer--;
                }else{
                    this.redirectToHome();
                }
            }
        },
        mounted() {
            if (this.redirectHome) {
                setInterval(this.secondDown, 1000);
            }
        }
    }
</script>