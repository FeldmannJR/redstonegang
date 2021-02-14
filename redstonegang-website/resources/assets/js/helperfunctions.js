window.redirectPost = (url, data) => {
    var form = document.createElement('form');
    document.body.appendChild(form);
    form.method = 'post';
    form.action = url;

    var token = document.head.querySelector('meta[name="csrf-token"]');
    if (token) {
        data['_token'] = token.content;
    }
    for (var name in data) {
        var input = document.createElement('input');
        input.type = 'hidden';
        input.name = name;
        input.value = data[name];
        form.appendChild(input);
    }
    form.submit();
}

window.addErrorsToForm = (error, component) => {
    if (error.response) {
        let data = error.response.data;
        if (typeof data.errors !== 'undefined') {

            let errors = data.errors;
            for (let fieldName in errors) {
                let field = component.$validator.fields.find({name: fieldName});
                if (field != null) {

                    component.errors.add({
                        field: fieldName,
                        msg: errors[fieldName],
                        id: field.id,
                        scope: field.scope
                    });
                }
            }
        }
    }
}

window.redirect = (url) => {
    if (typeof url !== 'undefined') {
        window.location.replace(url);
    } else {
        console.log('Redirect cancelled, undefined URL.');
    }
}