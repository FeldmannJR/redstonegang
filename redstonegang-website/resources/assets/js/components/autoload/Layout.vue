<template>
    <v-app>
        <template v-if="large">
            <v-parallax height="350" :src="bannerUrl">
                <rg-toolbar top :buttons="buttons"></rg-toolbar>
                <v-layout align-center column justify-center>
                    <v-img contain height="200" width="200" src="/img/logo/redstone-512p.png"></v-img>
                    <v-layout align-center column>
                        <p class="mb-0 display-2 font-weight-thin">0 jogadores online</p>
                        <rg-copy-ip>
                            <a class="title font-weight-thin primary--text">jogar.redstonegang.com.br</a>
                        </rg-copy-ip>
                    </v-layout>

                </v-layout>
            </v-parallax>
            <div v-observe-visibility="toolbarVisiblityChanged">
                <v-app-bar v-if="useMenu" text v-observe-visibility="toolbarVisiblityChanged" dark>
                    <v-layout justify-center row fill-height>
                        <v-toolbar-items>
                            <template v-for="button in menu">
                                <v-btn text color="primary">
                                    <v-icon left>{{button.icon}}</v-icon>
                                    {{button.name}}
                                </v-btn>
                            </template>

                        </v-toolbar-items>
                    </v-layout>
                </v-app-bar>
            </div>
        </template>
        <v-slide-y-transition>
            <rg-toolbar :buttons="both" app v-show="showToolbar"></rg-toolbar>
        </v-slide-y-transition>

        <v-content class="page-background">
            <slot></slot>
        </v-content>
    </v-app>
</template>
<script>

    import RgToolbar from "./ToolBar";
    import RgCopyIp from "./CopyIp";

    export default {
        components: {RgCopyIp, RgToolbar},
        name: 'rg-layout',
        props: {
            bannerUrl: {
                type: String
            },
            large: {
                type: Boolean,
                default: false
            }
        },
        computed: {
            tamanho() {
                return this.$vuetify.breakpoint.name;
            },
            both() {
                return this.filterList(this.menu_button_list.concat(this.navbar_button_list));
            },
            buttons() {
                if (!this.useMenu) {
                    return this.both;
                }
                return this.filterList(this.navbar_button_list);
            },
            useMenu() {
                return !this.$vuetify.breakpoint.xs;
            },
            menu() {
                if (!this.useMenu) {
                    return [];
                }
                return this.filterList(this.menu_button_list);
            },

        },
        data() {
            return {
                showToolbar: false,
                navbar_button_list: [
                    {name: "Login", icon: "mdi-key", url: "/login", auth: false},
                    {name: "Logout", icon: "mdi-logout", url: "/logout", auth: true},
                ],
                menu_button_list: [
                    {name: "Jogar", icon: "mdi-xbox-controller", url: "/forum"},
                    {name: "Forum", icon: "mdi-forum", url: "/forum"},
                    {name: "Loja", icon: "mdi-store", url: "/forum"},
                    {name: "Ranks", icon: "mdi-trophy", url: "/forum"},
                    {name: "Faq", icon: "mdi-help-circle", url: "/forum"},

                ]
            }
        },
        methods: {
            filterList(list) {
                return list.filter(value => {
                    if (value.hasOwnProperty('auth')) {
                        return value.auth === this.isLoggedIn();
                    }
                    return true;
                });
            },
            toolbarVisiblityChanged(isVisible, entry) {
                this.showToolbar = !isVisible;
            }
        },
        mounted() {
            if (!this.large) {
                this.showToolbar = true;
            }
        }

    }

</script>
<style>

</style>