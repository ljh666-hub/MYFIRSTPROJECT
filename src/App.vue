<template>
  <div id="app">
    <el-container v-if="!isLogin">
      <el-header style="height: 72px;padding:0;margin-top: -10px;z-index: 999">
       <myHeader />
      </el-header>
      <el-container>
        <el-aside width="200px"><Sidebar /></el-aside>
        <el-main>
          <router-view ></router-view>
        </el-main>
      </el-container>
    </el-container>
    <router-view v-else />
  </div>
</template>
<script>

import Sidebar from './component/Sidebar.vue';
import myHeader from './component/header.vue'

export default {
  components: { Sidebar,myHeader },
  computed:{
    isLogin(){
      return this.$route.meta.requiresLayout  === false
    }
  },
  mounted() {
    this.$nextTick(function (){

    })
  }
};



</script>