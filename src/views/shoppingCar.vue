<template>
  <div id="app" style="width: 100%;height: 90%;background-color: #fbfbfb;">
    <audio ref="mp3" preload="auto" style="display: none" id="mp3">
      <source src="http://localhost/通知多 AP1.668(NotificationMulti__爱给网_aigei_com.mp3" type="audio/mpeg">
    </audio>
    <div style="height: 630px;margin-bottom: 70px">
    <h2>我的购物车</h2>
    <div style="display:flex;">
    <el-button type="danger" @click="removeBrand" style="margin-left: 150px;margin-bottom: 30px">从购物车中移除</el-button>
    <el-input  style="width: 200px;margin: 0px 10px 0px 680px;height: 40px;border: 4px #f8c023 solid;border-radius: 10px 10px 10px 10px"
               placeholder="搜索商品信息"
               v-model="input"
               clearable>
    </el-input><el-button type="warning" icon="el-icon-search" @click="search" style=" height: 40px;margin-top: 5px"></el-button>
    </div>

    <keep-alive>

      <el-checkbox-group v-model="checkedData" @change="handleSingleSelect($event)">
        <div v-if="tableData.length>0">
          <el-row :data="tableData" style="width: 1120px;margin-left: 120px;" v-infinite-scroll="load"  infinite-scroll-disabled="disabled">
      <el-card v-for="(car,index) in tableData" :key="index" style="margin-bottom: 20px;min-height: 160px">
        <el-checkbox :label="JSON.stringify({'carId':car.carId,'brandId':car.brandId,'categoryName':car.categoryName,'num':car.amount,'price':car.price,'brandType':car.brandType})"  >
        <div style="margin-bottom: 10px;margin-left: 150px"><span id="brandname">商品名</span><span id="price2">价格</span><span id="label">数量</span><span id="label">操作</span></div>
        <div style="display: flex">
          <div style="width: 80px">
            <img :src=car.imageUrl height="90px" width="" auto>
          </div>
          <div style="margin-left: 150px;padding-top: 0px;width: 200px;" >
            <div id="name">{{car.brandName}}</div>
            <div id="detail">商品颜色/种类：{{car.categoryName}}</div>
          </div>
          <div style="margin-left: 120px;padding-top: 30px;width: 200px" >
            <div >￥{{car.price}}</div>
          </div>
          <div id="amount" style="margin-left: -250px;padding-top: 30px">x{{car.amount}}</div>
          <div>
            <router-link :to="{
      path:`detail?brandId=${car.brandId}`,
      }">
              <el-button type="warning" style="margin-left: -50px;margin-top: 30px">购买</el-button>
            </router-link>
          </div>
        </div>
        </el-checkbox>
      </el-card>
          </el-row></div>
        <div v-else>
        <el-empty description="购物车空空如也~"></el-empty>
      </div>
      </el-checkbox-group>
    </keep-alive>
      <p v-if="loading" style="margin-left: 700px;font-size: 16px;color:#cdcbcb;">加载中...</p>
      <p v-if="noMore" style="margin-left: 700px;font-size: 16px;color:#cdcbcb;">没有更多了</p>
    </div>
    <div style="height: 100px;"></div>
    <div style="display:flex;margin-left: -20px;">
    <el-card style="position: fixed;height: 100px;width: 70%;top: 830px;left: 350px;z-index: 999;background-color: #6a6969">
      <el-checkbox v-model="isAllSelected" @change="handleAllSelect($event)" style="margin-left: 30px;margin-top: 20px;color:white;;">全选</el-checkbox>
      <div style="margin-top: -20px;margin-bottom: -30px;margin-left: 700px;color: #f8c639;font-size: 19px;font-weight: 600">合计：￥{{total}}</div>
      <div v-if="checkedData.length>0">
      <router-link :to="{
        path:'/pay',
        query:{
            checkedData:this.checkedData
        }
      }">
      <el-button @click="handleBuy" type="warning" style="position:relative;left: 950px;">立即结算</el-button>
      </router-link>
      </div>
      <div v-else><el-button type="warning" disabled style="position:relative;left: 950px;">立即结算</el-button></div>
    </el-card>
    </div>
  </div>
</template>
<style>
#brandname{
  margin-left: 100px;
  color: #aaa9a9;
}
#label{
  margin-left: 150px;
}
#price2{
  color: #aaa9a9;
  margin-left: 250px;
  font-size: 16px;
}
</style>
<script>
import axios from "axios";

export default {
  data(){
    return{
      tableData:[],
      orderData:[],
      page:0,
      nowPage:0,
      loading:false,
      noMore:false,
      checkedData:[],
      isAllSelected: false,
      total:0,
      carIds:[],
      input:"",
      isLoading:true
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
    this.load()
  },
  beforeDestroy() {
    if(this.ws){
      this.ws.close();
      this.ws = null
    }
  },
  computed: {
    disabled () {
      return this.loading || this.noMore
    }
  },
 methods:{
   load () {
     const _this = this
       this.loading = true
       setTimeout(() => {
         _this.nowPage += 1
         axios({
           method: "get",
           url: `http://localhost/shoppingCar/selectAll?nowPage=${_this.nowPage}`
         }).then(function (resp) {
           _this.page = resp.data.status
           _this.tableData = _this.tableData.concat(resp.data.data)
           if (resp.data.data.length < 6) {
             _this.noMore = true
             _this.loading = false
           }

         })
         _this.loading = false

       }, 500)
   },
   handleSingleSelect(item){
     const _this = this
     this.total=0
     this.carIds=[]
     console.log(this.checkedData)
     if (item.length>0){
       for (let i = 0; i < item.length; i++) {
         _this.total+=JSON.parse(item[i]).price
         _this.carIds.push(JSON.parse(item[i]).carId)
       }
     }else {
       _this.checkedData=_this.checkedData.filter(function (id){return id!== brandId})
       _this.carIds= _this.carIds.filter(function (id){return id!== carId})
     }
     this.isAllSelected = this.checkedData.length === this.tableData.length
   },
   handleAllSelect(isAllSelected){
     this.total = 0
     const _this = this
     this.isAllSelected = isAllSelected
     if(isAllSelected){
         _this.checkedData=_this.tableData.map(item =>(JSON.stringify({
               "carId":item.carId,
               "brandId":item.brandId,
           "categoryName":item.categoryName,
           "num":item.amount,
           "price":item.price})
         ))
       for (let i = 0; i < _this.checkedData.length; i++) {
         console.log(JSON.parse(_this.checkedData[i]).price)
         _this.total += JSON.parse(_this.checkedData[i]).price
       }
       }else{
       _this.checkedData = []
     }
   },
   handleBuy(){

   },
   removeBrand(){
     const _this = this
     if(this.carIds.length>0) {
       console.log(this.carIds)
       for (let i = 0; i < this.carIds.length; i++) {
         axios.get(`http://localhost/shoppingCar/delete?carId=${_this.carIds[i]}`).then(function (resp) {
           if (resp.data.status === 20521) {
             _this.$message({
               message: resp.data.message,
               type: 'success',
               onClose: function () {
                 location.reload()
               }
             })
           } else {
             _this.$message({
               message: resp.data.message,
               type: 'error',
             })
           }
         })
       }
     }else{
       _this.$message({
         message: "请先选择商品",
         type: 'error',
       })
     }
   },
   search(){
       const _this = this
       axios.get(`http://localhost/shoppingCar/selectByBrandName?brandName=${_this.input}`).then(function (resp){
         _this.tableData = resp.data
       })
   }
 }
}
</script>