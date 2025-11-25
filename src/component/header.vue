<template>
  <div style="color:black;font-size: 28px ;font-weight:800;font-family: 'Consolas';text-indent: 200px;
  line-height: 75px;display: flex;width: 100%;position: fixed;background-color: #fdba03;height: 72px;">
    <img src="http://localhost/images/logo.jpg" style="margin-left: 200px" width="210px"><div style="padding-left: 1000px;font-size: 18px;font-weight: 500;display: flex">
    <router-link to="/chat">
    <el-button class="el-icon-chat-dot-round" style="background-color:#fdba03;height: 45px;
    font-size: 18px;margin:15px 5px 0 -400px;border: none;width: 130px;">联系客服</el-button>
    </router-link>
    <img :src="userInfo.imageUrl" style="width: 45px;height: 45px;border-radius: 50%;margin-top: 15px;margin-right: -30px">
    <div style="margin-left: -160px">{{ userInfo.userName }}，你好</div></div>
  </div>

</template>
<script>
import axios from "axios";

export default {
  data(){
    return{
      userInfo:[]
    }
  },
  mounted() {
    const _this = this
    axios.get("http://localhost/users/selectByUserName").then(function (resp){
      _this.userInfo = resp.data
      console.log(resp.data)
    })
  }
}
</script>