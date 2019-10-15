import Vue from 'vue';
import Insert from './App';
import Croppa from "vue-croppa";
import VueMomentLib from 'vue-moment-lib';

Vue.use(VueMomentLib);
Vue.use(Croppa)

new Vue({
    el: '#app',
    components: {
        Insert
    },
    template: '<Insert/>'
});
