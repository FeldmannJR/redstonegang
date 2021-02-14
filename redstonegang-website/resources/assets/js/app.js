/**
 * First we will load all of this project's JavaScript dependencies which
 * includes Vue and other libraries. It is a great starting point when
 * building robust, powerful web applications using Vue and Laravel.
 */
import customvalidation from "./customvalidation";


require('./bootstrap');

require('./helperfunctions');

import moment from 'moment';

window.moment = moment;


window.Vue = require('vue');


import VueObserveVisibility from 'vue-observe-visibility'
import Vuetify from 'vuetify'
import '@mdi/font/css/materialdesignicons.css' // Ensure you are using css-loader
import 'vuetify/dist/vuetify.min.css'
import VueClipboard from 'vue-clipboard2'
import colors from 'vuetify/es5/util/colors'
import VeeValidate, {Validator} from 'vee-validate';
import messagesPortuguese from "vee-validate/dist/locale/pt_BR";

import VueTheMask from 'vue-the-mask'


Vue.use(VueTheMask);
Vue.use(VueClipboard);
VueClipboard.config.autoSetContainer = true;// add this line

Vue.use(VueObserveVisibility);

Vue.use(VeeValidate);
import customMessages from './lang/validate-messages';
import customFields from './lang/validate-fields';


Validator.localize(customFields);
Validator.localize('pt_BR', messagesPortuguese);
Validator.localize('pt_BR', customMessages);
const vuetify_opts = {
    theme: {
        themes: {
            light: {
                primary: '#D32F2F',
                secondary: colors.red.lighten4, // #FFCDD2
                accent: colors.red.base, // #3F51B5
                error: '#F57C00'
            }
        },
        primary: '#D32F2F',
        secondary: colors.red.lighten4, // #FFCDD2
        accent: colors.red.base, // #3F51B5
        error: '#F57C00'
    }
};
Vue.use(Vuetify, vuetify_opts);


/**
 * Next, we will create a fresh Vue application instance and attach it to
 * the page. Then, you may begin adding components to this application
 * or customize the JavaScript scaffolding to fit your unique needs.
 */
const files = require.context('./components/autoload/', true, /\.vue$/i);
files.keys().map(key => {
    let component = files(key).default;
    let name = component.name;
    if (name === undefined) {
        name = key.split('/').pop().split('.')[0];
    }
    Vue.component(name, component);
});

import minecraftColors from './vendor/MinecraftColorCodes';

Vue.mixin({
    methods: {
        minecraftColors,
        isLoggedIn() {
            return window.auth_user != null;
        }
    }
});
// Custom Validation
import customValidation from './customvalidation';



window.app = new Vue({
    el: '#app',
    vuetify: new Vuetify(vuetify_opts),
    data: {
        meta: {},
        dark: false
    },
    created() {
        customValidation.register(this.$validator);
        let metas = document.getElementsByTagName('meta');
        for (let i = 0; i < metas.length; i++) {
            let name = metas[i].getAttribute('name');
            if (metas[i].hasAttribute('content')) {
                this.meta[name] = metas[i].getAttribute('content');
            }
        }
    }
})
;
