export default {
    customMessage(value) {
        if (typeof value !== 'string') {
            return 'A senha é inválida!'
        }
        if (value.length < 6) {
            return 'A senha precisa conter pelo menos 6 caracteres!';
        }
        if (value.length > 24) {
            return 'A senha não pode ter mais que 24 caracteres!';
        }
        let numbers = value.replace(/[^0-9]/g, "").length;
        let especiais = value.replace(/[^@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/g, "").length;
        let total = numbers + especiais;

        if (total < 1) {
            return 'A senha precisa conter pelo menos um número ou caractere especial!';
        }
        return true;
    },
    register($validator) {
        let customMessage = this.customMessage;
        $validator.extend('password', {
            validate(value) {
                return new Promise(resolve => {
                    resolve({
                        valid: customMessage(value) === true,
                        data: {
                            val: value
                        }
                    });
                });
            },
            getMessage(field, value, data) {
                return customMessage(data.val);
            },
        });
    }

}