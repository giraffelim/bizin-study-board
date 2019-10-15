<template>
    <div id="app">
      <Nav/>
      <div id="page-wrapper" class="gray-bg">
        <div class="wrapper wrapper-content">
          <div class="row">
            <div class="col-lg-12">
              <div class="ibox float-e-margins">
                <div class="ibox-title">
                  <h5>Board</h5>
                  <div class="ibox-tools">
                    <a class="collapse-link">
                      <i class="fa fa-chevron-up"></i>
                    </a>
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                      <i class="fa fa-wrench"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                      <li><a href="#">Config option 1</a>
                      </li>
                      <li><a href="#">Config option 2</a>
                      </li>
                    </ul>
                    <a class="close-link">
                      <i class="fa fa-times"></i>
                    </a>
                  </div>
                </div>
                <div class="ibox-content">
                  <div class="form-horizontal">
                    <div class="form-group"><label class="col-sm-2 control-label">작성자</label>
                      <div class="col-sm-3"><span style="position: relative; top: 7px;">{{detailBoard.writer}}</span></div>
                    </div>
                    <div class="form-group"><label class="col-sm-2 control-label">제목</label>
                      <div class="col-sm-3"><span style="position: relative; top: 7px;">{{detailBoard.title}}</span>
                      </div>
                    </div>
                    <div class="form-group"><label class="col-sm-2 control-label">작성일</label>
                      <div class="col-sm-6"><span style="position: relative; top: 7px;">{{$moment(detailBoard.createDt).format('YYYY-MM-DD HH:mm:ss')}}</span></div>
                    </div>
                    <div class="form-group" v-if="detailBoard.updateDt != null"><label class="col-sm-2 control-label">수정일</label>
                      <div class="col-sm-6"><span style="position: relative; top: 7px;">{{$moment(detailBoard.updateDt).format('YYYY-MM-DD HH:mm:ss')}}</span></div>
                    </div>
                    <div class="form-group"><label class="col-sm-2 control-label">상세내용</label>
                      <div class="col-sm-6"><textarea class="form-control" rows="10" name="content" readonly>{{detailBoard.content}}</textarea></div>
                    </div>
                    <div class="form-group" v-if="detailBoard.urlPath">
                      <label class="col-sm-2 control-label">파일첨부</label>
                      <div class="col-sm-6">
                        <croppa v-model="myCroppa"
                                prevent-white-space
                                :width="130"
                                :height="130"
                                :canvas-color="'#fff'"
                                :placeholder-font-size="12"
                                :accept="'image/*'"
                                :disable-drag-and-drop="true"
                                :disable-click-to-choose="true"
                                :disable-drag-to-move="false"
                                :disable-scroll-to-zoom="true"
                                :zoom-speed="20"
                                :disable-rotation="false"
                                :show-remove-button="false"
                                :remove-button-color="'red'"
                                :remove-button-size="20">
                          <img slot="initial" :src="detailBoard.urlPath" />
                        </croppa>
                      </div>
                    </div>
                    <div class="form-group">
                      <div class="col-sm-4 col-sm-offset-2">
                        <button class="btn btn-primary"  v-on:click="goToReply()">답글등록</button>
                        <button class="btn btn-success" v-on:click="goToMain()">목록</button>
                      </div>
                      <div class="col-sm-4 col-sm-offset-2" style="float: right;">
                        <button class="btn btn-success" v-on:click="goToModify()">수정</button>
                        <button class="btn btn-danger" @click="goToDelete()">삭제</button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <Footer/>
      </div>
      <Modal v-if="showModal" @close="editOrRemove" @closeModal="showModal = false" :no = 'boardNo'>
        <h3 slot="header">게시글 비밀번호를 입력해주세요.</h3>

      </Modal>
    </div>
</template>
<script>

import Nav from '../include/nav';
import Footer from '../include/footer';
import axios from 'axios';
import Modal from '../include/modal'

export default {
    name: 'Insert',
    components: {
      Nav,
      Footer,
      Modal
    },
    data: function() {
        return {
          detailBoard: {},
          boardNo : window.boardNo,
          showModal : false,
          myCroppa : {},
          flags : null,
          removeFlags : null,
        };
    },
    methods: {
      goToMain(){
        location.href=`/${window.pageNum}`;
      },
      goToReply(){
        location.href=`/reply/${this.boardNo}`;
      },
      goToModify(){
        this.flags = 'modify';
        this.showModal = true;
      },
      goToDelete(){
        this.flags = 'delete';
        this.showModal = true;
      },
      editOrRemove(){
        this.showModal = false;
        if(this.flags === 'delete'){
          if(this.detailBoard.reDepth === 0){
              this.removeFlags = 0;
          } else {
              this.removeFlags = 1;
          }
          axios.delete(`/api/remove/${this.boardNo}?flag=${this.removeFlags}`).then(res => {
            if(res.data.result === 'OK'){
              alert('삭제되었습니다.');
              location.href = `/${window.pageNum}`;
              this.flags = null;
            }
          }).catch(error => {
            alert(error.toString());
          })
        } else {
          console.log(this.flags);
          location.href=`/modify/${this.boardNo}/${window.pageNum}`;
        }
      }
    },
    created() {
      axios.get(`/api/get/${this.boardNo}`, this.boardNo).then(res => {
        if(res.data.result === 'OK'){
          this.detailBoard = res.data.detailBoard;
        }else{
          alert(res.data.message);
        }
      }).catch(error => {
          alert(error.toString());
      });
    }
};
</script>

<style>

</style>
