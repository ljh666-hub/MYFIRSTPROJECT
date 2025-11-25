<template>
  <div id="app" style="width: 100%;height: 100%;background-color: #fbfbfb;">
    <audio ref="mp3" preload="auto" style="display: none" id="mp3">
      <source src="http://localhost/通知多 AP1.668(NotificationMulti__爱给网_aigei_com.mp3" type="audio/mpeg">
    </audio>
    <div style="display: flex">
  <h2>我的所有包裹</h2>
    <el-input  style="width: 200px;margin: 30px 10px 0px 870px;height: 40px;border: 4px #f8c023 solid;border-radius: 10px 10px 10px 10px"
               placeholder="搜索商品信息"
               v-model="input"
               clearable>
    </el-input><el-button type="warning" icon="el-icon-search" @click="search2" style=" height: 40px;margin-top: 35px"></el-button>
    </div>
    <div style="margin-left: 50px;display: flex;margin-top: 30px;margin-bottom: 30px;">
      <el-button class="el-icon-d-caret" @click="search">筛选:</el-button>
      <div style="margin-left: 100px">日期范围：
        <el-date-picker
            v-model="value1"
            type="monthrange"
            range-separator="至"
            start-placeholder="开始月份"
            end-placeholder="结束月份">
        </el-date-picker>
      </div>
      <div style="margin-left: 50px">  商品类型：<el-select v-model="brandType" clearable placeholder="请选择">
        <el-option label="不限" value=""></el-option>
        <el-option label="运动" value="运动"></el-option>
        <el-option label="食品" value="食品"></el-option>
        <el-option label="服装" value="服装"></el-option>
        <el-option label="家具家用" value="家具家用"></el-option>
        <el-option label="电器及电子产品" value="电器及电子产品"></el-option>
        <el-option label="书籍文献" value="书籍文献"></el-option>
        <el-option label="美术字画古董" value="美术字画古董"></el-option>
        <el-option label="其它" value="其它"></el-option>
      </el-select></div>
      <div style="margin-left: 50px">  订单状态：<el-select v-model="orderStatus" clearable placeholder="请选择">
        <el-option label="不限" value=""></el-option>
        <el-option label="已付款" value="已付款"></el-option>
        <el-option label="已发货" value="已发货"></el-option>
        <el-option label="运输中" value="运输中"></el-option>
        <el-option label="已签收" value="已签收"></el-option>
        <el-option label="已收货" value="已收货"></el-option>
        <el-option label="已退款" value="已退款"></el-option>
        <el-option label="退款中" value="退款中"></el-option>
      </el-select></div>
    </div>
  <el-row  style="width: 1250px;margin-left: 80px">
    <el-card v-for="(order,index) in orderData" :key="index" style="margin-bottom: 20px;">
      <div style="margin-bottom: 10px">订单号：{{order[0].orderId}}<span id="brandname">商品名</span><span id="label">价格</span><span id="label">数量</span><span id="label">订单状态</span><span id="label">操作</span></div>
      <div style="display: flex" v-for="(brand,index) in order" :key="index">
        <div style="width: 80px">
        <img :src=brand.imageUrl height="90px" width="" auto>
      </div>
        <div style="margin-left: 180px;padding-top: 0px;width: 200px;" >
          <div id="name">{{brand.brandName}}</div>
          <div id="detail">商品颜色/种类：{{brand.categoryName}}</div>
        </div>
        <div style="margin-left: 20px;padding-top: 30px;width: 200px" >
          <div >￥{{brand.orderPrice}}</div>
        </div>
        <div id="amount" style="margin-left: -250px;padding-top: 30px">x{{brand.amount}}</div>
        <div style="padding-top: 20px">
        <div style="margin-left: -20px;margin-top: 10px;width: 50px;">{{brand.orderStatus}}</div>
      </div>
        <div style="padding-top: 30px;margin-left:150px;width: 50px " v-if="brand.orderStatus==='已付款'||brand.orderStatus==='已发货'||brand.orderStatus==='运输中'">
          <router-link :to="{
      path:'/afterSell',
      query:{brandId:brand.brandId,
             orderId:brand.orderId}
      }">
          <el-button type="danger">申请售后</el-button>
          </router-link>
        </div>
        <div v-else-if="brand.orderStatus==='退款中'">
          <el-button type="danger" plain style="margin-left: 150px;margin-top: 30px">撤销退款</el-button>
        </div>
        <div v-else-if="brand.orderStatus==='已签收'">
          <div id="sure" style="margin-left: 110px;line-height: 20px;height: 20px;margin-bottom: -20px">七天后将自动确认收货</div>
          <router-link :to="{
      path:'/comment',
      query:{brandId:brand.brandId,
             orderId:brand.orderId}
      }">
            <el-button type="warning" style="margin-left: 150px;margin-top: 30px">去评价</el-button>
          </router-link>
        </div>
        <div v-else-if="brand.orderStatus==='已收货'||brand.orderStatus==='已退款'">
          <router-link :to="{
      path:`detail?brandId=${brand.brandId}`,
      }">
            <el-button type="warning" style="margin-left: 150px;margin-top: 30px">再次购买</el-button>
          </router-link>
        </div>
        <div style="display:block;margin-top: 100px;margin-left: -1100px;width: 100%;">
        <el-collapse v-model="activeNames" @change="handleChange">
          <el-collapse-item title=查看物流详情 name="1" style="font-size: 25px">
            <el-steps :space="200" :active="change[brand.orderStatus]" finish-status="success">
              <el-step title="已付款"></el-step>
              <el-step title="已发货"></el-step>
              <el-step title="运输中"></el-step>
              <el-step title="已签收"></el-step>
            </el-steps>
          </el-collapse-item>
        </el-collapse>
        </div>
      </div>

    </el-card>
  </el-row>
  </div>
</template>
<style>
#sure{
  font-size: 12px;
}
#name{
  font-size: 16px;
  font-weight: 600;
  padding-top: 10px;
}
#label{
  color: #aaa9a9;
  margin-left: 150px;
}
#brandname{
  margin-left: 100px;
  color: #aaa9a9;
}
#detail{
  font-size: 12px;
  color: #6a6969;
}
</style>
<script>
import axios from "axios";
import moment from "moment"

export default {
  data(){
    return{
      tableData:[],
      orders:[],
      orderData:[],
      change:{
        "已付款":1,
        "已发货":2,
        "运输中":3,
        "已签收":4
      },
    value1:"",
      value2:"",
      brandType:"",
      orderTime1: "",
      orderTime2:"",
      input:"",
      orderStatus:""
    }

  },
  async mounted() {
    this.orderStatus = this.$route.query.orderStatus
    const _this =this
    if (this.orderStatus === undefined){
      await axios({
        method:"GET",
        url:`http://localhost/orders/findOrder`
      }).then(async function (resp) {
        _this.orders = resp.data.data
        for (let i = 0; i < _this.orders.length; i++) {
          let arr = []
          await axios.get(`http://localhost/orders/findByOrderId?orderId=${_this.orders[i].orderId}&brandName=null`).then(function (resp) {
            for (let j = 0; j < resp.data.length; j++) {
              arr.push(resp.data[j])
            }
            console.log(arr)
            console.log(!_this.orderData.some(item=>item.orderId === arr[0].orderId))
            if(!_this.orderData.some(item=>item[0].orderId === arr[0].orderId)){
              _this.orderData.push(arr)
            }
          })
        }
      })
    }else{
      await axios({
        method:"GET",
        url:`http://localhost/orders/findOrder?orderStatus=${_this.orderStatus}`
      }).then(async function (resp) {
        _this.orders = resp.data.data
        for (let i = 0; i < _this.orders.length; i++) {
          let arr = []
          await axios.get(`http://localhost/orders/findByOrderId?orderId=${_this.orders[i].orderId}&brandName=null`).then(function (resp) {
            for (let j = 0; j < resp.data.length; j++) {
              arr.push(resp.data[j])
            }
            console.log(arr)
            console.log(!_this.orderData.some(item=>item.orderId === arr[0].orderId))
            if(!_this.orderData.some(item=>item[0].orderId === arr[0].orderId)){
              _this.orderData.push(arr)
            }
          })
        }
      })
    }

    axios({
      method:"GET",
      url:`http://localhost/brands/findByUserName`
    }).then(function (resp){
      _this.tableData=resp.data
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
    search(){
      this.input=""
      this.tableData = []
      const _this = this
       this.orderTime1 =moment(this.value1[0]).format("yyyy-MM-DD HH:mm:ss")
      this.orderTime2 =moment(this.value1[1]).format("yyyy-MM-DD HH:mm:ss")
      console.log(_this.orderTime1)
      if(_this.orderTime1===_this.orderTime2){
        _this.orderTime1="1970-11-06 15:37:44"
        _this.orderTime2="2270-11-06 15:37:44"
      }
      axios.get(`http://localhost/orders/findByClient?orderTimeBegin=${_this.orderTime1}&orderTimeEnd=${_this.orderTime2}&brandType=${_this.brandType}&orderStatus=${_this.orderStatus}`).then(async function (resp) {
        _this.orderData = []
        _this.tableData = resp.data
        for (let i = 0; i < _this.tableData.length; i++) {
          let arr = []
          await axios.get(`http://localhost/orders/findByOrderId?orderId=${_this.tableData[i].orderId}`).then(function (resp) {
            for (let j = 0; j < resp.data.length; j++) {
              arr.push(resp.data[j])
            }
            if (!_this.orderData.some(item => item[0].orderId === arr[0].orderId)) {
              _this.orderData.push(arr)
            }
          })

        }

      })

    },
    search2(){
      this.tableData=[]
      const _this = this
      this.brandType = "不限"
      if(this.input===""){
        location.reload()
      }else {
        axios({
          method:"GET",
          url:`http://localhost/orders/findOrder?brandName=${_this.input}`
        }).then(async function (resp) {
          _this.orderData=[]
          _this.tableData = resp.data.data
          for (let i = 0; i < _this.tableData.length; i++) {
            let arr = []
            axios.get(`http://localhost/orders/findByOrderId?orderId=${_this.tableData[i].orderId}&brandName=${_this.tableData[i].brandName}`).then(function (resp) {
              for (let j = 0; j < resp.data.length; j++) {
                arr.push(resp.data[j])
              }
              if (!_this.orderData.some(item => item[0].orderId === arr[0].orderId)) {
                _this.orderData.push(arr)
              }
            })
          }
        })
      }
    }
  }
}
</script>