// https://github.com/js-cookie/js-cookie
import uaParser from 'ua_parser';

uaParser.userAgent(window.navigator.userAgent);

window.requestAnimFrame = (function() {
    return window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || function(callback) { window.setTimeout(callback, 1000 / 60); };
}());

const Utils = {
    wasData: null,
    mobilePopup: null,
    ipinPopup: null,
    /**
     * get URL parameter's value
     * @param b
     * @returns {*}
     */
    getUrlParam(name, search) {
        search = search || window.location.search;
        name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
        let regex = new RegExp(`[\\?&]${name}=([^&#]*)`),
            results = regex.exec(search);
        return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
    }
};

export default Utils;
