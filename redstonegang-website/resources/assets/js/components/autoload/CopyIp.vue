<template>
    <div>


        <div @click="click">
            <slot></slot>
        </div>
        <v-snackbar top :timeout="6000" v-model="warning" color="primary">IP copiado basta usar CTRL + V
            <v-btn
                    color="white"
                    text
                    @click="warning = false"
            >
                Fechar
            </v-btn>
        </v-snackbar>
    </div>


</template>

<script>
    export default {
        name: "rg-copy-ip",
        data() {
            return {
                warning: false
            }
        },
        methods: {
            click() {
                this.$copyText('jogar.redstonegang.com.br').then(() => {
                    this.warning = true;
                    console.log('copiou');
                });
            }
        }
    }
</script>
