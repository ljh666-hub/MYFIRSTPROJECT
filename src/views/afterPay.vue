<template>
  <el-row>
  <el-col :sm="200" :lg="500" style="margin-top: 200px">
    <el-result icon="success" title="支付成功" subTitle="请根据提示进行操作">
      <template slot="extra">
        <router-link :to="{
      path:'/admin'
      }">
        <el-button type="primary" size="medium" >返回</el-button>
        </router-link>
      </template>
    </el-result>
  </el-col>
  </el-row>
</template>
<script>
export default{
  data(){
    return{

    }
  },
  methods:{

  }
}
</script>