<template>
  <div id="app" style="width: 100%;height: 100%;background-color: #fbfbfb;">
    <audio ref="mp3" preload="auto" style="display: none" id="mp3">
      <source src="http://localhost/通知多 AP1.668(NotificationMulti__爱给网_aigei_com.mp3" type="audio/mpeg">
    </audio>
    <h2>您对本次交易的评价</h2>
    <div style="display: flex;margin-left: 100px;line-height: 50px">
      <span>您对商品质量的满意度：</span>
      <el-rate
        v-model="value"
        show-text style="margin-left: 60px;line-height: 60px">
    </el-rate></div>
    <div style="display: flex;margin-left: 100px;line-height: 50px">
    <span>您对本次物流速度的满意度:</span>
    <el-rate
        v-model="value1"
        show-text style="margin-left: 60px;line-height: 60px">
    </el-rate>
    </div>
    <div style="display: flex;margin-left: 100px;line-height: 50px">
      <span>您对商家服务态度满意度:</span>
      <el-rate
          v-model="value2"
          show-text style="margin-left: 60px;line-height: 60px">
      </el-rate>
    </div>
    <div style="margin-left: 100px;margin-top: 50px">
      <span>您对本次购物体验的整体评价：</span>
      <el-button class="el-icon-picture" type="warning" style="margin-left: 850px">上传图片</el-button>
    <el-input
        type="textarea"
        placeholder="请文明评论哦"
        v-model="sendData.commentDetail"
        maxlength="100"
        show-word-limit
        style="width: 90%;margin-top: 30px;"
    >
    </el-input>
    </div>
    <div style="display: flex;margin-left: 1120px;margin-top: 30px">
      <router-link to="/order" style="margin-right: 10px">
      <el-button type="info">取消</el-button>
      </router-link>
    <el-button type="warning" @click="comment">提交评价</el-button>
    </div>
  </div>
</template>
<style>

</style>
<script>
import axios from "axios";


export default {
  data() {
    return {
      value:0,
      value1:0,
      value2:0,
      sendData:{
        commentDetail:"",
        brandId:0,
        userId:0,
        rate:0
      },
      orderData:{
        orderId:"",
        orderStatus:""
      }

    }
  },
  mounted() {
    const _this = this
    axios.get("http://localhost/users/selectByUserName").then(function (resp){
      _this.userId = resp.data.userId
      _this.ws = new WebSocket(`ws://localhost/ws/${_this.userId}`)
      _this.ws.onmessage = function (event) {
        _this.$refs.mp3.play().catch(err => {console.log("no")})
        const h = _this.$createElement;
        _this.$notify({
          title: '收到信息',
          message: h('i', { style: 'color: teal'}, `客服向你发送了一条消息`)
        });
      }
    })
  },
  beforeDestroy() {
    if(this.ws){
      this.ws.close();
      this.ws = null
    }
  },
  methods:{
    comment(){
      console.log(this.value)
      const _this = this
      this.sendData.brandId = this.$route.query.brandId
      this.sendData.rate = this.value
      this.$confirm('评论后将自动确认收货, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        console.log(_this.sendData)
        axios({
          method: "post",
          url:"http://localhost/comments/addComment",
          data:_this.sendData
        }).then(function (resp){
          if(resp.data.status === 20401){
            _this.orderData.orderId =_this.$route.query.orderId
            _this.orderData.orderStatus = "已收货"
            axios({
              method:"post",
              url:"http://localhost/orders/updateAddress",
              data:_this.orderData
            })
            _this.$message({
              message: resp.data.message,
              type: 'success',
              onClose(){
                _this.$router.push("/order")
              }
            })
          }else {
            _this.$message({
              message: resp.data.message,
              type: 'error',
            })
          }
        });
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