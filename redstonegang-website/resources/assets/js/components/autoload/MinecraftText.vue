<template>
    <div>
        <span style="font-weight: normal">
        <span v-for="s in computedText" v-bind:style="s.style">
                {{s.content}}
        </span>
        </span>
    </div>
</template>

<script>
    export default {
        name: "minecraft-text",
        data() {
            return {
                text: '',
                warning: false
            }
        },
        methods: {},
        computed: {
            computedText() {

                return this.minecraftColors(this.text);
            }
        },
        created() {
            this.text = this.$slots.default[0].text;
        }
    }
</script>
