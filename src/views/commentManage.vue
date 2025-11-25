<template>
  <div style="width: 100%;overflow-y: auto;overflow-x: hidden;height: 822px;background-color: #fbfbfb;">
    <h2>我发布过的评论({{commentData.length}})</h2>
    <el-card v-for="(comm,index) in commentData" :key="index" style="margin-top: 30px;height: 150px">
      <div style="margin-top: 10px">
        <div style="display: flex"><img :src=comm.imageUrl width="50px" height="50px" style="border-radius: 50%">
          <span style="padding-top: -30px;margin-left:20px;font-size: 20px;font-weight: 600">{{comm.brandName}}</span>
          <span style="margin-left: 30px;font-size: 15px;color: #6a6969">发表时间：{{comm.stringCommentTime}}</span>
        </div>
        <el-rate
            disabled
            v-model="commentData[index].rate"
            show-score
            text-color="#ff9900"
            score-template="{value}"
            style="margin-left: 80px;margin-top: -20px">
        </el-rate>
        <div style="margin-top: 10px;font-size: 17px;margin-left: 70px;font-weight: 500;margin-bottom: -60px;width: 1000px" v-if="comm.commentDetail!== null && comm.commentDetail!==''">{{comm.commentDetail}}</div>
        <div v-else style="margin-top: 10px;font-size: 17px;margin-left: 70px;font-weight: 500;margin-bottom: -60px;">你没有填写该条评论哦~</div>
        <el-button type="danger" icon="el-icon-delete" style="margin-left: 1200px" @click="deleteComm(index)">删除</el-button>
      </div>
    </el-card>
  </div>
</template>
<script>
import axios from "axios";

export default {
  data(){
    return{
       commentData:[]
    }
  },
  mounted() {
    const _this = this
    axios.get(`http://localhost/comments/findByUserId`).then(function (resp){
      _this.commentData = resp.data
    })
  },
  methods:{
    deleteComm(index){
      const _this = this
      this.$confirm('评论删除后无法恢复, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.get(`http://localhost/comments/deleteByCommentId?commentId=${_this.commentData[index].commentId}`).then(function (resp){
          if (resp.data.status === 20410){
            _this.$message({
              message: resp.data.message,
              type: 'success',
              onClose(){
                location.reload()
              }
            })
          }else {
            _this.$message({
              message: resp.data.message,
              type: 'error',
            })
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消评论'
        });
      });
    }
  }
}
</script>