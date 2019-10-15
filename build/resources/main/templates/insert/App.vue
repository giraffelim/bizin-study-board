<template>
    <div id="app">
      <Nav/>
      <div id="page-wrapper" class="gray-bg">
        <div class="wrapper wrapper-content">
          <div class="row">
            <div class="col-lg-12">
              <div class="ibox float-e-margins">
                <div class="ibox-title">
                  <h5>Board <small>With custom checbox and radion elements.</small></h5>
                </div>
                <div class="ibox-content">
                  <form class="form-horizontal" name="frm">
                    <div class="form-group"><label class="col-sm-2 control-label">작성자</label>
                      <div class="col-sm-3"><input type="text" class="form-control" ref="writer" v-model="sendData.writer" placeholder="작성자를 입력해주세요."></div>
                    </div>
                    <div class="form-group"><label class="col-sm-2 control-label">제목</label>
                      <div class="col-sm-3"><input type="text" class="form-control" ref="title" v-model="sendData.title" placeholder="제목을 입력해주세요.">
                      </div>
                    </div>
                    <div class="form-group"><label class="col-sm-2 control-label">비밀번호</label>
                      <div class="col-sm-3"><input type="password" class="form-control" ref="password" v-model="sendData.password" placeholder="비밀번호를 입력해주세요."></div>
                    </div>
                    <div class="form-group"><label class="col-sm-2 control-label">상세내용</label>
                      <div class="col-sm-6"><textarea class="form-control" rows="10" ref="content" v-model="sendData.content" placeholder="상세 내용을 입력해주세요."></textarea></div>
                    </div>

                    <div class="form-group"><label class="col-sm-2 control-label">파일첨부</label>
                      <div class="col-sm-6">
                        <croppa v-model="myCroppa"
                                prevent-white-space
                                :width="130"
                                :height="130"
                                :canvas-color="'#fff'"
                                :placeholder="'Choose an image'"
                                :placeholder-font-size="12"
                                :accept="'image/*'"
                                :disable-drag-and-drop="false"
                                :disable-click-to-choose="false"
                                :disable-drag-to-move="false"
                                :disable-scroll-to-zoom="false"
                                :zoom-speed="20"
                                :disable-rotation="true"
                                :show-remove-button="true"
                                :remove-button-color="'red'"
                                :remove-button-size="20">
                          <img slot="initial" :src="checkImage">
                        </croppa>
                      </div>
                    </div>
                    <div class="form-group">
                      <div class="col-sm-4 col-sm-offset-2">
                      <button class="btn btn-success pull-left" v-on:click="goToMain($event)">목록</button>
                      </div>
                      <div class="col-sm-4 col-sm-offset-2" style="float: right;">
                        <button class="btn btn-danger" @click="reset($event)">취소</button>
                        <button class="btn btn-success" type="submit" v-on:click="insertPost($event)" v-if="!flags">작성</button>
                        <button class="btn btn-success" type="submit" v-on:click="modifyPost($event)" v-if="flags">수정</button>
                      </div>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
        <Footer/>
      </div>

    </div>
</template>
<script>

import Nav from '../include/nav';
import Footer from '../include/footer';
import axios from 'axios';

let wasData =  window.boardNo;
let flag = window.flag;

export default {
    name: 'Insert',
    components: {
      Nav,
      Footer,
    },
    data: function() {
        return {
            myCroppa: {},
            sendData: {
              writer: '',
              title: '',
              password: '',
              content: '',
              insertParam: {
                attach: '',
                fileName: '',
                flag: '',
                parentNo: wasData
              }
            },
            flags : flag
        };
    },
    computed: {
      checkImage(){
        return this.sendData.urlPath != null ? this.sendData.urlPath : ''
      }
    },
    methods: {
        goToMain(event){
          if(event) event.preventDefault();
          location.href = `/${window.pageNum}`;
        },
        reset(event){
          if (event) event.preventDefault();
          this.sendData.title = null;
          this.sendData.writer = null;
          this.sendData.password = null;
          this.sendData.content = null;
          this.sendData.insertParam.fileName = null;
          this.sendData.insertParam.attach = null;
        },
        validation(event){
          if (event) event.preventDefault();
          //writer validation

          if(this.sendData.writer.length === 0 || this.sendData.writer.length>10){
            if(this.sendData.writer.length > 10){
              alert("작성자는 10자 이내로 입력해주세요.");
              this.sendData.writer = this.sendData.writer.substring(0,10);
              this.$refs.writer.focus();
              return;
            }
            alert("작성자를 입력해주세요.");
            this.$refs.writer.focus();
            return false;
          }

          //title validation
          if(this.sendData.title.length === 0 || this.sendData.title.length>50){
            if(this.sendData.title.length > 50){
              alert("제목은 50자 이내로 입력해주세요.");
              this.sendData.title = this.sendData.title.substring(0, 50);
              this.$refs.title.focus();
              return;
            }
            alert("제목을 입력해주세요.");
            this.$refs.title.focus();
            return false;
          }

          //password validation
          if (!this.sendData.password) {
            alert("비밀번호를 입력해주세요.");
            this.$refs.password.focus();
            return false;
          }
          let regexp = /[^0-9]/g; //숫자만 가능한 정규식
          if (regexp.test(this.sendData.password)) {
            alert("숫자만 입력해주세요.");
            this.$refs.password.focus();
            return false;
          }

          //content validation
          if(this.sendData.content.length === 0 || this.sendData.content.length > 1000){
            if(this.sendData.content.length > 1000){
              alert("상세 내용은 1000자 이내로 입력해주세요.");
              this.sendData.content = this.sendData.content.substring(0, 1000);
              this.$refs.content.focus();
              return;
            }
            alert("상세 내용을 입력해주세요.");
            this.$refs.content.focus();
            return false;
          }
          return true;
        },
        insertPost:function (event) {
          if(!this.validation(event)){
            return;
          }
          // 서버로 FormData 전송.
          // 폼에 입력한 data binding
          this.sendData.insertParam.flag = this.vFlag;
          if(this.myCroppa.generateDataUrl().length){
            this.sendData.insertParam.attach = this.myCroppa.generateDataUrl(); //Base64 Encoding image
            this.sendData.insertParam.fileName = this.myCroppa.getChosenFile().name; //fileName
          }
          console.log(this.sendData);
          //post 비동기 전송
          if(!this.sendData.insertParam.parentNo){
            axios.post("/api/insert", this.sendData).then(res => {
              if (res.data.result === 'OK'){
                alert("등록되었습니다.");
                window.location.href="/1";
                return;
              }
              if (res.data.result === 'E999'){
                alert(res.data.message);
                window.location.href="/1";
                return;
              }
            }).catch(error => {
              alert(error.toString());
            });
          } else{
            axios.post("/api/replyInsert", this.sendData).then(res => {
              if (res.data.result === 'OK'){
                alert("등록되었습니다.");
                window.location.href="/1";
                return;
              }
              if (res.data.result === 'E999'){
                alert(res.data.message);
                window.location.href="/1";
                return;
              }
            }).catch(error => {
              alert(error.toString())
            });
          }
        },
        modifyPost(event){
          if(!this.validation(event)){
            return;
          }
          // 이미지 없이 수정 -> attach => null, 이미지 그대로 가는 경우 -> fileName => null

          // 기존 이미지 그대로 가는 경우
          if(this.myCroppa.generateDataUrl){
            // flag값 ?
            this.sendData.insertParam = {
              attach : this.myCroppa.generateDataUrl(),
              fileName : null
            }

          }
          // 이미지를 새로 업로드 한 경우 attach, fileName => not null
          if(this.myCroppa.getChosenFile()){
            this.sendData.insertParam = {
              attach: this.myCroppa.generateDataUrl(),
              fileName: this.myCroppa.getChosenFile().name
            }
          }

          console.log(this.sendData.insertParam);

          axios.put("/api/modify",this.sendData).then(res => {
            if(res.data.result === 'OK'){
              alert("수정되었습니다.");
              window.location.href=`/detail/${window.boardNo}/${window.pageNum}`;
            }else{
              alert(res.data.message);
            }
          }).catch(error => {
            alert(error.toString());
          });
        }
    },
    created() {

      if (this.flags) {
        axios.get(`/api/get/${this.sendData.insertParam.parentNo}`).then(res => {
          if (res.data.message === 'OK') {
            this.sendData = res.data.detailBoard;
          }
        }).catch(error => {
          alert(error.toString());
        });
      }
    }
};
</script>

<style>

</style>
