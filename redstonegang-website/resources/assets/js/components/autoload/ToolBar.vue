<template>
    <v-app-bar
            flat
            :fixed="top ? null : true"
            :app="app"
            :color="top ? 'transparent' : ''"
            transition="slide-y-transition"
    >
        <v-toolbar-title>
            <h1 class="display-1 font-weight-light red--text text--lighten-1">RedstoneGang</h1>
        </v-toolbar-title>
        <v-spacer></v-spacer>

        <v-toolbar-items>
            <v-btn v-for="button in nonDropdown" :key="button.name" :href="button.url != null ? button.url : ''" text color="primary">
                {{button.name}}
                <v-icon v-if="button.icon != null" right>{{button.icon}}</v-icon>
            </v-btn>
            <v-menu v-if="hasDropdown" offset-y>
                <template v-slot:activator="{ on }">
                    <v-btn icon v-on="on">
                        <v-icon>mdi-menu</v-icon>
                    </v-btn>
                </template>
                <v-list>
                    <v-subheader>
                        Navegação
                    </v-subheader>
                    <template v-for="(button,index) in dropdown">
                        <v-divider></v-divider>
                        <v-list-tile :href="button.url != null ? button.url : ''" flat
                                     color="primary">
                            <v-list-tile-content>
                                <v-list-tile-title>
                                    {{button.name}}
                                </v-list-tile-title>
                            </v-list-tile-content>
                            <v-list-tile-action>
                                <v-icon v-if="button.icon != null">{{button.icon}}</v-icon>
                            </v-list-tile-action>
                        </v-list-tile>
                    </template>
                </v-list>
            </v-menu>
        </v-toolbar-items>
    </v-app-bar>
</template>

<script>
    export default {
        name: "rg-toolbar",
        props: {
            top: {
                type: Boolean,
                default: false
            },
            app: {
                type: Boolean,
                default: false
            },
            buttons:{
                type: Array,
                default(){
                    return []
                }
            }
        },
        data() {
            return {

            }
        },
        methods: {},
        computed: {
            hasDropdown() {
                return this.buttons.length > this.maxButtons;
            },
            maxButtons() {
                switch (this.$vuetify.breakpoint.name) {
                    case 'xs':
                        return 0;
                    case 'sm':
                        return 2;
                    case 'md':
                        return 3;
                    case 'lg':
                        return 5;
                    case 'xl':
                        return 6;
                }
            },
            nonDropdown() {
                if (!this.hasDropdown) {
                    return this.buttons;
                }
                let filtered = [];
                for (let x = 0; x < this.buttons.length; x++) {
                    if (x < this.maxButtons) {
                        filtered.push(this.buttons[x]);
                    }
                }
                return filtered;
            },
            dropdown() {
                if (this.hasDropdown) {
                    let filtered = [];
                    for (let x = 0; x < this.buttons.length; x++) {
                        if (x >= this.maxButtons) {
                            filtered.push(this.buttons[x]);
                        }
                    }
                    return filtered;
                } else {
                    return null;
                }
            }
        },

        calculatedData: {
            color() {
                return true;
            }
        }
    }
</script>

<style scoped>

</style>