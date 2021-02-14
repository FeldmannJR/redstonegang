<template>
    <v-app id="keep">
        <v-app-bar
                app
                clipped-left
                dark
                color="primary"
        >
            <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>
            <span class="title ml-3 mr-5">RedstoneGang Admin&nbsp</span>

            <v-spacer></v-spacer>
        </v-app-bar>

        <v-navigation-drawer
                v-model="drawer"
                app
                clipped
                color=""
        >
            <v-list
                    dense
            >
                <v-list-item-group v-model="item" color="accent">
                    <template v-for="(item, i) in items">
                        <v-divider
                                v-if="item.divider"
                                :key="i"
                                dark
                                class="my-4"
                        ></v-divider>
                        <v-list-item
                                v-else
                                link
                                :key="item.id"
                                :href="item.href"
                                @click=""
                        >
                            <v-list-item-action>
                                <v-icon>{{ item.icon }}</v-icon>
                            </v-list-item-action>
                            <v-list-item-content>
                                <v-list-item-title class="grey--text">
                                    {{ item.text }}
                                </v-list-item-title>
                            </v-list-item-content>
                        </v-list-item>
                    </template>
                </v-list-item-group>
            </v-list>
        </v-navigation-drawer>

        <v-content>
            <v-container fluid>
                <slot></slot>
            </v-container>
        </v-content>
    </v-app>
</template>

<script>
    export default {
        name: "rg-admin-layout",
        props: {
            source: String,
            selected: String
        },
        data: () => ({
            drawer: null,
            item: null,
            items: [
                {id: 'skins', icon: 'mdi-tshirt-crew', text: 'Upload Skins', href: '/admin/skin'},
                {id: 'groups', icon: 'mdi-account-group', text: 'Grupos', href: '/admin/group'},
                {id: 'permissions', icon: 'mdi-code-equal', text: 'Permissões/Opções', href: '/admin/permissions'},

                {divider: true},
            ],
        }),
        created() {
            for (let key in this.items) {
                if (this.items[key].id === this.selected) {
                    this.item = parseInt(key);
                }
            }
        }
    }
</script>


<style scoped>

</style>
