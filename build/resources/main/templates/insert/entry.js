import Vue from 'vue';
import Insert from './App';
import Croppa from "vue-croppa";
Vue.use(Croppa);

new Vue({
    el: '#app',
    components: {
        Insert
    },
    template: '<Insert/>'
});
