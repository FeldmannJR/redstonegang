export default {
    custom: {
        username: {
            required: () => "Você precisa informar um nome para usar em jogo.",
            regex: () => {
                return "O nome de usuário só pode conter letras, números e underlines!";
            }
        },
        password_confirmation:{
            confirmed: () => "As senhas devem ser iguais!",
            required: () => "Você deve confirmar sua senha."
        }

    },
}