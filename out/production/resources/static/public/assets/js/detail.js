webpackJsonp([2],{"+x3n":function(s,t){},CHSb:function(s,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var e=a("7+uW"),o=a("qjSu"),n=a("Fs1K"),l=a("mtWM"),i=a.n(l),r={props:["no"],data:function(){return{headerText:"비밀번호를 입력해주세요.",sendDatas:{password:null,boardNo:this.no},flag:!0}},computed:{chooseColor:function(){return this.flag?"#42b983":"red"}},methods:{confirmPass:function(){var s=this;this.sendDatas.password?i.a.post("/api/passConfirm",this.sendDatas).then(function(t){"OK"===t.data.result?s.$emit("close"):"FAIL"===t.data.result?(s.flag=!1,s.headerText=t.data.message):(alert(t.data.message),location.href="/1")}).catch(function(s){alert(s.toString())}):(this.headerText="비밀번호를 입력해주세요!",this.flag=!1,this.$refs.pass.focus())}}},c={render:function(){var s=this,t=s.$createElement,a=s._self._c||t;return a("div",{staticClass:"modal-mask"},[a("div",{staticClass:"modal-wrapper",staticStyle:{display:"block !important"}},[a("div",{staticClass:"modal-container",staticStyle:{"margin-top":"100px !important"}},[a("button",{staticClass:"btn btn-primary btn-circle btn-circle-sm m-1 pull-right",on:{click:function(t){return s.$emit("closeModal")}}},[a("i",{staticClass:"fa fa-times"})]),s._v(" "),a("div",{staticClass:"modal-header"},[a("h3",{style:{marginTop:0,color:s.chooseColor},attrs:{slot:"header"},slot:"header"},[s._v(s._s(s.headerText))])]),s._v(" "),a("div",{staticClass:"modal-body"},[s._t("body",[a("input",{directives:[{name:"model",rawName:"v-model",value:s.sendDatas.password,expression:"sendDatas.password"}],ref:"pass",staticClass:"form-control",attrs:{type:"password",placeholder:"password"},domProps:{value:s.sendDatas.password},on:{keyup:function(t){return!t.type.indexOf("key")&&s._k(t.keyCode,"enter",13,t.key,"Enter")?null:s.confirmPass()},input:function(t){t.target.composing||s.$set(s.sendDatas,"password",t.target.value)}}})])],2),s._v(" "),a("div",{staticClass:"modal-footer"},[a("button",{staticClass:"modal-default-button btn btn-primary",on:{click:function(t){return s.confirmPass()}}},[s._v("\n              OK\n            ")])])])])])},staticRenderFns:[]};var d=a("VU/8")(r,c,!1,function(s){a("+x3n")},null,null).exports,u={name:"Insert",components:{Nav:o.a,Footer:n.a,Modal:d},data:function(){return{detailBoard:{},boardNo:window.boardNo,showModal:!1,myCroppa:{},flags:null,removeFlags:null}},methods:{goToMain:function(){location.href="/"+window.pageNum},goToReply:function(){location.href="/reply/"+this.boardNo+"?pageNum="+window.pageNum},goToModify:function(){this.flags="modify",this.showModal=!0},goToDelete:function(){this.flags="delete",this.showModal=!0},editOrRemove:function(){var s=this;this.showModal=!1,"delete"===this.flags?i.a.delete("/api/remove/"+this.boardNo).then(function(t){"OK"===t.data.result&&(alert("삭제되었습니다."),location.href="/"+window.pageNum,s.flags=null)}).catch(function(s){alert(s.toString())}):(console.log(this.flags),location.href="/modify/"+this.boardNo+"/"+window.pageNum)}},created:function(){var s=this;i.a.get("/api/get/"+this.boardNo,this.boardNo).then(function(t){"OK"===t.data.result?s.detailBoard=t.data.detailBoard:alert(t.data.message)}).catch(function(s){alert(s.toString())})}},m={render:function(){var s=this,t=s.$createElement,a=s._self._c||t;return a("div",{attrs:{id:"app"}},[a("Nav"),s._v(" "),a("div",{staticClass:"gray-bg",attrs:{id:"page-wrapper"}},[a("div",{staticClass:"wrapper wrapper-content"},[a("div",{staticClass:"row"},[a("div",{staticClass:"col-lg-12"},[a("div",{staticClass:"ibox float-e-margins"},[s._m(0),s._v(" "),a("div",{staticClass:"ibox-content"},[a("div",{staticClass:"form-horizontal"},[a("div",{staticClass:"form-group"},[a("label",{staticClass:"col-sm-2 control-label"},[s._v("작성자")]),s._v(" "),a("div",{staticClass:"col-sm-3"},[a("span",{staticStyle:{position:"relative",top:"7px"}},[s._v(s._s(s.detailBoard.writer))])])]),s._v(" "),a("div",{staticClass:"form-group"},[a("label",{staticClass:"col-sm-2 control-label"},[s._v("제목")]),s._v(" "),a("div",{staticClass:"col-sm-3"},[a("span",{staticStyle:{position:"relative",top:"7px"}},[s._v(s._s(s.detailBoard.title))])])]),s._v(" "),a("div",{staticClass:"form-group"},[a("label",{staticClass:"col-sm-2 control-label"},[s._v("작성일")]),s._v(" "),a("div",{staticClass:"col-sm-6"},[a("span",{staticStyle:{position:"relative",top:"7px"}},[s._v(s._s(s.$moment(s.detailBoard.createDt).format("YYYY-MM-DD HH:mm:ss")))])])]),s._v(" "),null!=s.detailBoard.updateDt?a("div",{staticClass:"form-group"},[a("label",{staticClass:"col-sm-2 control-label"},[s._v("수정일")]),s._v(" "),a("div",{staticClass:"col-sm-6"},[a("span",{staticStyle:{position:"relative",top:"7px"}},[s._v(s._s(s.$moment(s.detailBoard.updateDt).format("YYYY-MM-DD HH:mm:ss")))])])]):s._e(),s._v(" "),a("div",{staticClass:"form-group"},[a("label",{staticClass:"col-sm-2 control-label"},[s._v("상세내용")]),s._v(" "),a("div",{staticClass:"col-sm-6"},[a("textarea",{staticClass:"form-control",attrs:{rows:"10",name:"content",readonly:""}},[s._v(s._s(s.detailBoard.content))])])]),s._v(" "),s.detailBoard.urlPath?a("div",{staticClass:"form-group"},[a("label",{staticClass:"col-sm-2 control-label"},[s._v("파일첨부")]),s._v(" "),a("div",{staticClass:"col-sm-6"},[a("croppa",{attrs:{"prevent-white-space":"",width:130,height:130,"canvas-color":"#fff","placeholder-font-size":12,accept:"image/*","disable-drag-and-drop":!0,"disable-click-to-choose":!0,"disable-drag-to-move":!1,"disable-scroll-to-zoom":!0,"zoom-speed":20,"disable-rotation":!1,"show-remove-button":!1,"remove-button-color":"red","remove-button-size":20},model:{value:s.myCroppa,callback:function(t){s.myCroppa=t},expression:"myCroppa"}},[a("img",{attrs:{slot:"initial",src:s.detailBoard.urlPath},slot:"initial"})])],1)]):s._e(),s._v(" "),a("div",{staticClass:"form-group"},[a("div",{staticClass:"col-sm-4 col-sm-offset-2"},[a("button",{staticClass:"btn btn-primary",on:{click:function(t){return s.goToReply()}}},[s._v("답글등록")]),s._v(" "),a("button",{staticClass:"btn btn-success",on:{click:function(t){return s.goToMain()}}},[s._v("목록")])]),s._v(" "),a("div",{staticClass:"col-sm-4 col-sm-offset-2",staticStyle:{float:"right"}},[a("button",{staticClass:"btn btn-success",on:{click:function(t){return s.goToModify()}}},[s._v("수정")]),s._v(" "),a("button",{staticClass:"btn btn-danger",on:{click:function(t){return s.goToDelete()}}},[s._v("삭제")])])])])])])])])]),s._v(" "),a("Footer")],1),s._v(" "),s.showModal?a("Modal",{attrs:{no:s.boardNo},on:{close:s.editOrRemove,closeModal:function(t){s.showModal=!1}}},[a("h3",{attrs:{slot:"header"},slot:"header"},[s._v("게시글 비밀번호를 입력해주세요.")])]):s._e()],1)},staticRenderFns:[function(){var s=this.$createElement,t=this._self._c||s;return t("div",{staticClass:"ibox-title"},[t("h5",[this._v("Board")])])}]};var v=a("VU/8")(u,m,!1,function(s){a("u5bw")},null,null).exports,f=a("86jz"),j=a.n(f),p=a("tJ9y"),h=a.n(p);e.default.use(h.a),e.default.use(j.a),new e.default({el:"#app",components:{Insert:v},template:"<Insert/>"})},Fs1K:function(s,t,a){"use strict";var e={render:function(){this.$createElement;this._self._c;return this._m(0)},staticRenderFns:[function(){var s=this.$createElement,t=this._self._c||s;return t("div",{staticClass:"footer"},[t("div",{staticClass:"pull-right"}),this._v(" "),t("div",[t("strong",[this._v("Copyright")]),this._v(" Example Company © 2014-2017\n  ")])])}]};var o=a("VU/8")({},e,!1,function(s){a("e6Bw")},"data-v-2c4f9862",null);t.a=o.exports},TQKY:function(s,t){},e6Bw:function(s,t){},qjSu:function(s,t,a){"use strict";var e={render:function(){this.$createElement;this._self._c;return this._m(0)},staticRenderFns:[function(){var s=this.$createElement,t=this._self._c||s;return t("nav",{staticClass:"navbar-default navbar-static-side",attrs:{role:"navigation"}},[t("div",{staticClass:"sidebar-collapse"},[t("ul",{staticClass:"nav metismenu",attrs:{id:"side-menu"}},[t("li",[t("a",{attrs:{href:"/1"}},[t("i",{staticClass:"fa fa-diamond"}),this._v(" "),t("span",{staticClass:"nav-label"},[this._v("Main")])])])])])])}]};var o=a("VU/8")({},e,!1,function(s){a("TQKY")},"data-v-31c95fc0",null);t.a=o.exports},u5bw:function(s,t){},uslO:function(s,t,a){var e={"./af":"3CJN","./af.js":"3CJN","./ar":"3MVc","./ar-dz":"tkWw","./ar-dz.js":"tkWw","./ar-kw":"j8cJ","./ar-kw.js":"j8cJ","./ar-ly":"wPpW","./ar-ly.js":"wPpW","./ar-ma":"dURR","./ar-ma.js":"dURR","./ar-sa":"7OnE","./ar-sa.js":"7OnE","./ar-tn":"BEem","./ar-tn.js":"BEem","./ar.js":"3MVc","./az":"eHwN","./az.js":"eHwN","./be":"3hfc","./be.js":"3hfc","./bg":"lOED","./bg.js":"lOED","./bm":"hng5","./bm.js":"hng5","./bn":"aM0x","./bn.js":"aM0x","./bo":"w2Hs","./bo.js":"w2Hs","./br":"OSsP","./br.js":"OSsP","./bs":"aqvp","./bs.js":"aqvp","./ca":"wIgY","./ca.js":"wIgY","./cs":"ssxj","./cs.js":"ssxj","./cv":"N3vo","./cv.js":"N3vo","./cy":"ZFGz","./cy.js":"ZFGz","./da":"YBA/","./da.js":"YBA/","./de":"DOkx","./de-at":"8v14","./de-at.js":"8v14","./de-ch":"Frex","./de-ch.js":"Frex","./de.js":"DOkx","./dv":"rIuo","./dv.js":"rIuo","./el":"CFqe","./el.js":"CFqe","./en-SG":"oYA3","./en-SG.js":"oYA3","./en-au":"Sjoy","./en-au.js":"Sjoy","./en-ca":"Tqun","./en-ca.js":"Tqun","./en-gb":"hPuz","./en-gb.js":"hPuz","./en-ie":"ALEw","./en-ie.js":"ALEw","./en-il":"QZk1","./en-il.js":"QZk1","./en-nz":"dyB6","./en-nz.js":"dyB6","./eo":"Nd3h","./eo.js":"Nd3h","./es":"LT9G","./es-do":"7MHZ","./es-do.js":"7MHZ","./es-us":"INcR","./es-us.js":"INcR","./es.js":"LT9G","./et":"XlWM","./et.js":"XlWM","./eu":"sqLM","./eu.js":"sqLM","./fa":"2pmY","./fa.js":"2pmY","./fi":"nS2h","./fi.js":"nS2h","./fo":"OVPi","./fo.js":"OVPi","./fr":"tzHd","./fr-ca":"bXQP","./fr-ca.js":"bXQP","./fr-ch":"VK9h","./fr-ch.js":"VK9h","./fr.js":"tzHd","./fy":"g7KF","./fy.js":"g7KF","./ga":"U5Iz","./ga.js":"U5Iz","./gd":"nLOz","./gd.js":"nLOz","./gl":"FuaP","./gl.js":"FuaP","./gom-latn":"+27R","./gom-latn.js":"+27R","./gu":"rtsW","./gu.js":"rtsW","./he":"Nzt2","./he.js":"Nzt2","./hi":"ETHv","./hi.js":"ETHv","./hr":"V4qH","./hr.js":"V4qH","./hu":"xne+","./hu.js":"xne+","./hy-am":"GrS7","./hy-am.js":"GrS7","./id":"yRTJ","./id.js":"yRTJ","./is":"upln","./is.js":"upln","./it":"FKXc","./it-ch":"/E8D","./it-ch.js":"/E8D","./it.js":"FKXc","./ja":"ORgI","./ja.js":"ORgI","./jv":"JwiF","./jv.js":"JwiF","./ka":"RnJI","./ka.js":"RnJI","./kk":"j+vx","./kk.js":"j+vx","./km":"5j66","./km.js":"5j66","./kn":"gEQe","./kn.js":"gEQe","./ko":"eBB/","./ko.js":"eBB/","./ku":"kI9l","./ku.js":"kI9l","./ky":"6cf8","./ky.js":"6cf8","./lb":"z3hR","./lb.js":"z3hR","./lo":"nE8X","./lo.js":"nE8X","./lt":"/6P1","./lt.js":"/6P1","./lv":"jxEH","./lv.js":"jxEH","./me":"svD2","./me.js":"svD2","./mi":"gEU3","./mi.js":"gEU3","./mk":"Ab7C","./mk.js":"Ab7C","./ml":"oo1B","./ml.js":"oo1B","./mn":"CqHt","./mn.js":"CqHt","./mr":"5vPg","./mr.js":"5vPg","./ms":"ooba","./ms-my":"G++c","./ms-my.js":"G++c","./ms.js":"ooba","./mt":"oCzW","./mt.js":"oCzW","./my":"F+2e","./my.js":"F+2e","./nb":"FlzV","./nb.js":"FlzV","./ne":"/mhn","./ne.js":"/mhn","./nl":"3K28","./nl-be":"Bp2f","./nl-be.js":"Bp2f","./nl.js":"3K28","./nn":"C7av","./nn.js":"C7av","./pa-in":"pfs9","./pa-in.js":"pfs9","./pl":"7LV+","./pl.js":"7LV+","./pt":"ZoSI","./pt-br":"AoDM","./pt-br.js":"AoDM","./pt.js":"ZoSI","./ro":"wT5f","./ro.js":"wT5f","./ru":"ulq9","./ru.js":"ulq9","./sd":"fW1y","./sd.js":"fW1y","./se":"5Omq","./se.js":"5Omq","./si":"Lgqo","./si.js":"Lgqo","./sk":"OUMt","./sk.js":"OUMt","./sl":"2s1U","./sl.js":"2s1U","./sq":"V0td","./sq.js":"V0td","./sr":"f4W3","./sr-cyrl":"c1x4","./sr-cyrl.js":"c1x4","./sr.js":"f4W3","./ss":"7Q8x","./ss.js":"7Q8x","./sv":"Fpqq","./sv.js":"Fpqq","./sw":"DSXN","./sw.js":"DSXN","./ta":"+7/x","./ta.js":"+7/x","./te":"Nlnz","./te.js":"Nlnz","./tet":"gUgh","./tet.js":"gUgh","./tg":"5SNd","./tg.js":"5SNd","./th":"XzD+","./th.js":"XzD+","./tl-ph":"3LKG","./tl-ph.js":"3LKG","./tlh":"m7yE","./tlh.js":"m7yE","./tr":"k+5o","./tr.js":"k+5o","./tzl":"iNtv","./tzl.js":"iNtv","./tzm":"FRPF","./tzm-latn":"krPU","./tzm-latn.js":"krPU","./tzm.js":"FRPF","./ug-cn":"To0v","./ug-cn.js":"To0v","./uk":"ntHu","./uk.js":"ntHu","./ur":"uSe8","./ur.js":"uSe8","./uz":"XU1s","./uz-latn":"/bsm","./uz-latn.js":"/bsm","./uz.js":"XU1s","./vi":"0X8Q","./vi.js":"0X8Q","./x-pseudo":"e/KL","./x-pseudo.js":"e/KL","./yo":"YXlc","./yo.js":"YXlc","./zh-cn":"Vz2w","./zh-cn.js":"Vz2w","./zh-hk":"ZUyn","./zh-hk.js":"ZUyn","./zh-tw":"BbgG","./zh-tw.js":"BbgG"};function o(s){return a(n(s))}function n(s){var t=e[s];if(!(t+1))throw new Error("Cannot find module '"+s+"'.");return t}o.keys=function(){return Object.keys(e)},o.resolve=n,s.exports=o,o.id="uslO"}},["CHSb"]);
//# sourceMappingURL=detail.js.map