<template>
  <div id="app" style="position: fixed;width: 200px;">
<el-menu
    default-active="/admin"
    class="el-menu-vertical-demo"
    @open="handleOpen"
    @close="handleClose"
    background-color="#545c64"
    text-color="#fff"
    active-text-color="#ffd04b"
    height="100%">
  <router-link to="/admin">
<el-menu-item index="/admin" style="height: 100px;" router>
  <template slot="title" >
    <div style="line-height: 100px">
      <i class="el-icon-location"></i>
      <span slot="title">首页</span>
    </div>
  </template>
</el-menu-item></router-link>
<router-link to="/shoppingCar">
  <el-menu-item index="/shoppingCar" style="height: 100px" router>
    <template slot="title" >
  <div style="line-height: 100px">
    <i class="el-icon-shopping-cart-2"></i>
    <span slot="title">购物车</span>
  </div>
    </template>
  </el-menu-item></router-link>
  <router-link to="/discount">
<el-menu-item index="/discount" style="height: 100px">
  <div style="line-height: 100px">
    <i class="el-icon-bell"></i>
    <span slot="title">我的优惠卷</span>
  </div>
</el-menu-item>
  </router-link>
  <router-link to="/order">
<el-menu-item index="/order"  style="height: 100px">
  <template slot="title" >
  <div style="line-height: 100px">
    <i class="el-icon-document"></i>
    <span slot="title">我的包裹</span>
  </div>
  </template>
</el-menu-item>
  </router-link>
  <router-link to="/chat">
    <el-menu-item index="/chat"  style="height: 100px">
      <template slot="title" >
        <div style="line-height: 100px" @click="handleClear" v-if="value!==0">
          <i class="el-icon-service"></i>
          <el-badge  :value="value" :max="99" class="item">
            <div style="height: 20px;line-height: 18px;width: 80px;">
          <span slot="title">联系客服</span>
            </div>
          </el-badge>
        </div>
        <div style="line-height: 100px" @click="handleClear" v-else>
          <i class="el-icon-service"></i>
              <span slot="title">联系客服</span>
        </div>
      </template>
    </el-menu-item>
  </router-link>
  <router-link to="/mine">
<el-menu-item index="/mine" style="height: 100px">
  <div style="line-height: 100px">
    <i class="el-icon-setting"></i>
    <span slot="title">我的</span>
  </div>
</el-menu-item>
  </router-link>
</el-menu></div></template>

<style >
a {
  text-decoration: none;
}
.el-menu-vertical-demo {
  height: 100%;
}
</style>

<script>
  import Vue from "vue";
  import axios from "axios";

  export default {
    data() {
      return {
        ws:null,
         value:0,
        userId:0
      }


    },
    mounted() {
      var _this = this
      axios.get('http://localhost/users/selectByUserName').then(function (resp) {
        _this.userId = resp.data.userId
        _this.ws = new WebSocket(`ws://localhost/ws/${_this.userId}`)
        _this.ws.onmessage = function (event) {
          _this.value += 1
          console.log(_this.value)
        }

      })
    },
    beforeDestroy() {
      if(this.ws){
        this.ws.close();
        this.ws = null
      }
    },
    methods: {
     handleClear(){
       this.value = 0
     },
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        this.dialogVisible = false
      },
    }}

  </script>