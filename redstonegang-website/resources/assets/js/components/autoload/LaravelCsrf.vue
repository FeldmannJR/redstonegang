<template>
    <input type="hidden" name="_token" :value="$root.$data.meta['csrf-token']">
</template>

<script>
    export default {
        name: "laravel-csrf"
    }
</script>
