<template>
    <div>
        <form ref="form" method="post" :action="rgData.REDIRECT">
            <laravel-csrf></laravel-csrf>
            <input type="hidden" name="premium" v-model="rgData.premium">
            <input type="hidden" name="premium_token" v-model="rgData.token">
            <input type="hidden" name="email" v-model="email">

            <v-text-field
                    v-model="email"
                    data-vv-name="email"
                    v-validate="'required|email'"
                    :error-messages="errors.collect('email')"
                    outline
                    @keyup.enter="submit"
                    label="E-mail">
            </v-text-field>
            
        </form>
    </div>

</template>

<script>
    export default {
        $_veeValidate: {
            validator: 'new' // give me my own validator scope.
        },
        props: ['rg-data', 'loading'],
        name: "rg-register-email",
        data() {
            return {
                email: ""
            }
        },
        methods: {
            submit() {
                this.$validator.validate().then((valid) => {
                    if (valid) {
                        this.$refs.form.submit();
                        this.loading(true);
                    }
                });

            }
        },
        mounted() {
            if (this.rgData.errors) {
                let errors = JSON.parse(this.rgData.errors);
                if (errors.email) {
                    let field = this.$validator.fields.find({name: 'email'});
                    if (field != null) {
                        this.errors.add({
                            field: 'email',
                            msg: errors.email,
                            id: field.id,
                            scope: field.scope
                        });
                    }
                }
            }
        }


    }
</script>
