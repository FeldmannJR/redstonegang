<template>
    <v-card>
        <v-btn icon :href="listLink">
            <v-icon>mdi-arrow-left</v-icon>
        </v-btn>
        <v-container fluid>
            <div class="ma-5">

                <h2 class="display-2 font-weight-light">Editando Grupo {{group.name}}</h2>
                <minecraft-text class="headline pb-5">{{group.prefix}}</minecraft-text>

                <v-layout>

                    <v-select
                            v-model="selectedGroup"
                            :items="forumGroups"
                            label="Grupo do Forum"
                            item-text="name"
                            item-value="id"
                            clearable
                            return-object
                            outlined
                    >
                    </v-select>
                    <v-btn
                            icon
                            :loading="savingGroup"
                            :disabled="savingGroup"
                            @click="saveGroup"
                            :color="shouldSaveGroup ? 'primary' : ''"
                    >
                        <v-icon>mdi-content-save</v-icon>
                    </v-btn>
                </v-layout>

                <!--    Permissions   -->
                <v-expansion-panels class="pt-5" focusable popup>
                    <v-expansion-panel>
                        <v-expansion-panel-header expand-icon="mdi-menu-down">Permissões Gerais</v-expansion-panel-header>
                        <v-expansion-panel-content>
                            <rg-edit-group-permissions :server-id="0" :edit-group="editGroup"
                                                       :permissions-desc="permissionsDesc">
                            </rg-edit-group-permissions>
                        </v-expansion-panel-content>
                    </v-expansion-panel>
                    <v-expansion-panel>
                        <v-expansion-panel-header expand-icon="mdi-menu-down">Permissões Survival</v-expansion-panel-header>
                        <v-expansion-panel-content>
                            <rg-edit-group-permissions :server-id="2" :edit-group="editGroup"
                                                       :permissions-desc="permissionsDesc">
                            </rg-edit-group-permissions>
                        </v-expansion-panel-content>
                    </v-expansion-panel>
                </v-expansion-panels>
            </div>
        </v-container>
    </v-card>

</template>

<script>
    import MinecraftText from "../../MinecraftText";

    export default {
        name: "rg-edit-group",
        components: {MinecraftText},
        props: ["permissions-desc", "edit-group", "xenforo-groups", "list-link"],
        data() {
            return {
                selectedGroup: null,
                group: null,
                forumGroups: [],
                savingGroup: false
            }
        },
        computed: {
            shouldSaveGroup() {
                if (this.selectedGroup == null && this.group.forum_group === null) {
                    return false;
                }
                if (this.selectedGroup != null) {
                    if (this.selectedGroup.id === this.group.forum_group) {
                        return false;
                    }
                }
                return true;
            }
        },
        methods: {

            saveGroup() {
                if (this.shouldSaveGroup) {
                    this.sendRequest();
                }
            },
            sendRequest() {
                this.savingGroup = true;
                if (this.selectedGroup != null) {
                    axios.post('/admin/group/update/forum', {
                        id: this.group.id,
                        forum_group: this.selectedGroup.id,
                    }).then(this.then).catch(this.catch);
                } else {
                    axios.post('/admin/group/remove/forum', {
                        id: this.group.id,
                    }).then(this.then).catch(this.catch);
                }
            },
            then(post) {
                this.savingGroup = false;
                this.group = post.data;
                console.log(post.data);
            },

            catch(error) {
                this.buttonLoading = false;
                console.log(error.response);
            },

        },
        created() {
            this.group = this.editGroup;
            for (let g of this.xenforoGroups) {
                let jsonGroup = {
                    id: g.user_group_id,
                    name: g.title
                };
                this.forumGroups.push(jsonGroup);
                if (g.user_group_id === this.group.forum_group) {
                    this.selectedGroup = jsonGroup;
                }
            }
        }
    }
</script>

<style scoped>

</style>