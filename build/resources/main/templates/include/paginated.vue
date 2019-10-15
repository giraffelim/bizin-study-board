<template>
  <div>
    <paginate
      v-model="pageNum"
      :page-count="pageTotalCount"
      :page-range="3"
      :margin-pages="2"
      :click-handler="clickCallback"
      :prev-text="'Prev'"
      :next-text="'Next'"
      :container-class="'pagination'"
      :page-class="'page-item'">
    </paginate>
  </div>
</template>

<script>
  import Paginate from 'vuejs-paginate';

  export default{
    props:['boardList','pageNumber'],
    components:{
      Paginate
    },
    data(){
      return{
        pageSize: 10,
        pageNum : null
      }
    },
    computed:{
      pageTotalCount(){ // 페이지 총 갯수
        this.pageNum = this.pageNumber;
        // list length
        let listLength = this.boardList.length;
        // 한 페이지에 나타낼 글 갯수
        let listSize = this.pageSize;
        // list length를 listSize로 나눈다.
        let page = Math.floor(listLength/listSize);
        // 나머지가 존재 한다면 + 1
        if(listLength % listSize > 0) page += 1;
        this.clickCallback();
        return page;
      },
      dataPerPage(){
        const start = (this.pageNum - 1) * this.pageSize;
        const end = start + this.pageSize;
        return this.boardList.slice(start, end);
      }
    },
    methods:{
      clickCallback(){
        this.$emit('dataPerPage', this.dataPerPage, this.pageNum);
      }
    },


  }
</script>

<style>

</style>

