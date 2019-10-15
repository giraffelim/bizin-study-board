<template>
  <div id="app">
    <Nav/>
    <div id="page-wrapper" class="gray-bg">
      <div class="wrapper wrapper-content">
        <div class="row">
          <div class="col-lg-12">
            <div class="ibox float-e-margins">
              <div class="ibox-title">
                <h5>boneis study project - board</h5>
              </div>
              <div class="ibox-content">
                <div class="row">
                  <div class="col-sm-2">
                    <div class="form-group">
                      <select name="status" id="status" class="form-control" v-model="searchType">
                        <option>제목</option>
                        <option>작성자</option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group">
                    <div class="col-sm-3">
                      <div class="input-group" style="margin-top: 2px;">
                        <input type="text" placeholder="Search" class="input-sm form-control" ref="searchValue"
                               v-model="searchValue" v-on:keyup.enter="search()"> <span class="input-group-btn">
                                        <button type="button" class="btn btn-sm btn-primary"
                                                @click="search()">검색</button> </span></div>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-4">
                    <h4>{{totalCount}}개의 글</h4>
                  </div>
                </div>
                <div class="table-responsive">
                  <button class="btn btn-primary" style="float: right;" v-on:click="insertForm()">등록</button>
                  <table class="table table-striped">
                    <thead>
                    <tr>
                      <th>번호</th>
                      <th>제목</th>
                      <th>작성자</th>
                      <th>작성일</th>
                    </tr>
                    </thead>
                    <tbody>
                    <!-- 게시물 목록 -->
                    <tr v-for="board in dataList" :key="board.boardNo">
                      <td>{{board.boardNo}}</td>
                      <td v-if="!board.writer && !board.reDepth">{{board.title}}</td>
                      <td v-else-if="board.reDepth >= 1 && board.writer">
                        <img src="https://ssl.pstatic.net/static/cafe/cafe_pc/icon_reply.png"
                             :style="{marginLeft:space(board.reDepth), width:'10px', position:'relative', top:'-5px'}"/>
                        <a :href="`/detail/${board.boardNo}/${pageNum}`">{{board.title}}</a>
                      </td>
                      <td v-else-if="board.reDepth >= 1 && !board.writer">
                        <img src="https://ssl.pstatic.net/static/cafe/cafe_pc/icon_reply.png"
                             :style="{marginLeft:space(board.reDepth), width:'10px', position:'relative', top:'-5px'}"/>
                        {{board.title}}
                      </td>
                      <td v-else><a :href="`/detail/${board.boardNo}/${pageNum}`">{{board.title}}</a></td>
                      <td>{{board.writer}}</td>
                      <td>{{ $moment(board.createDt).format('YYYY-MM-DD HH:mm:ss') }}</td>
                    </tr>
                    </tbody>
                  </table>
                  <Paginated class="pull-right" :boardList="boardList" :pageNumber="pageNum"
                             @dataPerPage="dataPerPage"/>
                </div>
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
  import Axios from 'axios';
  import Paginated from '../include/paginated';

  export default {
    name: 'Main',
    components: {
      Nav,
      Footer,
      Paginated
    },
    data: function () {
      return {
        boardList: [],
        searchValue: null,
        searchType: '제목',
        pageNum: window.pageNum * 1,
        dataList: null,
        searchFlag: null
      };
    },
    methods: {
      insertForm() {
        location.href = `/insert`;
      },
      search() {
        console.log(this.searchValue);
        // if(this.searchValue === null || this.searchValue === ''){
        //   alert("검색어를 입력해주세요!");
        //   this.$refs.searchValue.focus();
        //   this.searchValue = null;
        //   return;
        // }
        if (this.searchValue.charAt(0) === ' ') {
          alert("검색어 첫 글자는 공백일 수 없습니다.");
          this.$refs.searchValue.focus();
          this.searchValue = null;
          return;
        }
        //search value -> api call -> list get -> dom rerender
        let searchValue = encodeURIComponent(this.searchValue);
        Axios.get(`/api/search?value=${searchValue}&type=${this.searchType}`).then(res => {
          if (res.data.result === 'OK') {
            this.boardList = res.data.boardList;
            this.searchFlag = true;
            this.pageNum = 1;
          } else {
            alert(res.data.message);
          }
        }).catch(error => {
          alert(error.toString());
        });
      },
      dataPerPage(data, pageNum) {
        this.dataList = data;
        this.pageNum = pageNum;
      }
    },
    computed: {
      space() {
        // reDepth값 만큼 이동
        return (reDepth) => {
          return `${reDepth * 10}px`;
        };
      },
      totalCount() {
        return this.boardList.length;
      },
      dataCount() {
        return this.dataList.length;
      }
    },
    created() {
      Axios.get("/api/data").then(res => {
        if (res.data.result === 'OK') {
          this.boardList = res.data.boardList;
        } else {
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

