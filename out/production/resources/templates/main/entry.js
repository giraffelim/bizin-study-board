import Vue from 'vue';
import Main from './App';
import VueMomentLib from 'vue-moment-lib';

Vue.use(VueMomentLib);
// import 가져올 컴포넌트
// Component를 전역으로 사용하려면
// Vue.component('컴포넌트명', '옵션')

new Vue({
    el: '#app',
    components: {
        Main
    },
    template: '<Main/>'
});
