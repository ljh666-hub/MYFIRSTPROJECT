<template>
  <div style="width: 100%;height: 95%;background-color: #fbfbfb;">
    <h2>所有优惠卷</h2>

    <el-dialog
        :visible.sync="dialogVisible"
        width="30%"
    >
    <div style="background-color: #fa0606;width: 109%;height: 550px;margin-left: -20px;margin-top: -100px;margin-bottom: -30px;padding-top: 5px " @click="handleGet">
      <div style="color:#f3e6a5;margin-left: 120px;margin-top: 50px;font-size: 39px;font-weight: 700">新人专属优惠卷</div>
      <img src="http://localhost/images/a98036cc904656232d0f5277bc719dba.jpg" width="400px" height="auto" style="margin-left: 55px">
    </div>
    </el-dialog>

    <el-row :gutter="20"
            infinite-scroll-disabled="disabled">
      <el-col :span="6" v-for="(cupon,index) in tableData" :key="index">
    <div style="width: 320px;height: 90px;background-color: #ffe9e9;margin-left: 30px;border-radius: 10px 10px 10px 10px;margin-top: 30px">
      <div style="display: flex">
      <div style="padding-top: 10px;padding-bottom: 5px">
        <div v-if="cupon.discountValue>1"  style="margin-left: 20px;color: red;font-size: 28px;font-weight: 600;">￥{{cupon.discountValue}}</div>
        <div v-else style="margin-left: 20px;color: red;font-size: 28px;font-weight: 600;">{{cupon.discountValue*100}}折</div>
        <div style="margin-left: 25px;color: red;font-size: 15px;font-weight: 600;">{{cupon.cuponType}}</div>
      </div>
        <div style="padding-top: 15px;padding-bottom: 5px">
        <div v-if="cupon.moneyLimit>0" style="margin-left: 40px;">满{{cupon.moneyLimit}}元可用</div>
        <div v-else style="margin-left: 40px">无门槛</div>
        <div style="margin-left: 40px;font-size: 14px;color:grey;">{{cupon.endTime}}到期</div>
          <div style="margin-left: 40px;font-size: 13px;color:grey;" v-if="cupon.typeLimit === '不限'">所有商品类型都可使用</div>
          <div style="margin-left: 40px;font-size: 13px;color:grey;" v-else>仅{{cupon.typeLimit}}类商品可使用</div>
        </div>
    </div>
    </div>
      </el-col>

    </el-row>

    <h2 style="margin-top: 100px">我的优惠卷包</h2>
    <div v-if="tableData2.length>0">
    <el-row :gutter="20"
            infinite-scroll-disabled="disabled">
      <el-col :span="6" v-for="(cupon2,index) in tableData2" :key="index">
        <div style="width: 320px;height: 90px;background-color: #ffe9e9;margin-left: 30px;border-radius: 10px 10px 10px 10px;margin-top: 30px">
          <div style="display: flex">
            <div style="padding-top: 10px;padding-bottom: 5px">
              <div v-if="cupon2.discountValue>1"  style="margin-left: 20px;color: red;font-size: 28px;font-weight: 600;">￥{{cupon2.discountValue}}</div>
              <div v-else style="margin-left: 20px;color: red;font-size: 28px;font-weight: 600;">{{cupon2.discountValue*100}}折</div>
              <div style="margin-left: 25px;color: red;font-size: 15px;font-weight: 600;">{{cupon2.cuponType}}</div>
            </div>
            <div style="padding-top: 15px;padding-bottom: 5px">
              <div v-if="cupon2.moneyLimit>0" style="margin-left: 40px;">满{{cupon2.moneyLimit}}元可用</div>
              <div v-else style="margin-left: 40px;">无门槛</div>
              <div style="margin-left: 40px;font-size: 14px;color:grey;">{{cupon2.endTime}}到期</div>
              <div style="margin-left: 40px;font-size: 13px;color:grey;" v-if="cupon2.TypeLimit = '不限'">所有商品类型都可使用</div>
              <div style="margin-left: 40px;font-size: 13px;color:grey;" v-else>仅{{cupon2.TypeLimit}}类商品可使用</div>
            </div>
          </div>
        </div>
      </el-col>

    </el-row>
    </div>
    <div v-else>
      <el-empty description="还没有优惠卷哦~"></el-empty>
    </div>
    </div>

</template>
<script>
import axios from "axios";

export default {
  data(){
    return{
      InitData:{
        cuponStatus :"有效",
      },
       tableData:[],
      tableData2:[],
      dialogVisible:false,
    }
  },
  mounted() {
    const _this = this
    axios.get(`http://localhost/cupon/isNewUser?cuponId=4`).then(function (resp){
      if (resp.data===0){
        _this.dialogVisible = true
      }
    })
    axios.get(`http://localhost/cupon/selectNewUser?cuponId=0`).then(function (resp){
      _this.tableData2 = resp.data
    })
    axios({
      method:"post",
      url:`http://localhost/cupon/findByCondition`,
      data:_this.InitData
    }).then(function (resp){
      _this.tableData = resp.data
    })

  },
  methods:{
    handleGet(){
       const _this = this
       axios.get(`http://localhost/cupon/insertCuponUser?cuponId=4`).then(function (resp){
         if (resp.data.status === 20621){
           axios.get(`http://localhost/cupon/insertCuponUser?cuponId=3`).then(function (resp){
             if (resp.data.status === 20621){
               _this.$message({
                 message: resp.data.message,
                 type: 'success',
                 onClose:function (){
                   _this.dialogVisible = false
                   location.reload()
                 }
               })
             }else {
               _this.$message({
                 message: resp.data.message,
                 type: 'error',
                 onClose:function (){
                   _this.dialogVisible = false
                 }
               })
             }
           })
         }else {
           _this.$message({
             message: resp.data.message,
             type: 'error',
             onClose:function (){
               _this.dialogVisible = false
             }
           })
         }
       })
    }
  }
}
</script>