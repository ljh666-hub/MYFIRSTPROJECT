<template>
  <div id="app" style="width: 100%;height: 100%;background-color: #fbfbfb;">
    <audio ref="mp3" preload="auto" style="display: none" id="mp3">
      <source src="http://localhost/通知多 AP1.668(NotificationMulti__爱给网_aigei_com.mp3" type="audio/mpeg">
    </audio>
    <div style="display: flex">
      <div style="width:550px;height: 550px;border-radius: 4px 4px 4px 4px;margin: 20px 0 0 20px">
      <img :src=tableData.imageUrl width="550px" height="550px">
    </div>
      <div>
      <div style="padding-left: 120px;padding-top: 50px;width: 300px;">
        <div id="name">{{tableData.brandName}}</div>
      </div>

      <div style="margin-left: 120px">
        <div id="price">￥{{tableData.brandPrice}}<span id="sell">已售：{{tableData.brandSell}}</span></div>
      </div>
        <div style="margin-left: 130px">
          <h5>三天内发货</h5>
          <h5>7天无理由退货</h5>
          <h5>支持信用卡支付</h5>
        </div>
        <hr style="margin-left: 120px;width: 700px;">
        <div style="margin-left: 140px">产品种类：
        <div style="margin-bottom: 10px;display: flex;margin-top: 50px" >
          <el-radio-group v-model="selectedType" class="type-selector">
            <el-radio v-for="(category, index) in categoryList" :key="index" :label="index"
                      @click="selectedType=index" @change="handleChange2(index)">
              {{ category }}
            </el-radio>
          </el-radio-group>
        </div>
      </div>
      <div id="amount" style="margin-left: -110px">数量：
        <template>
          <el-input-number size="mini" v-model="checkedData.num" :step="1" :min="1"
                           @change="handleAmountChange"></el-input-number>
        </template></div>
        <div v-if="tableData.brandAmount>0" style="margin-left: 120px;margin-top: 20px;width: 500px;height: 50px;
        padding-left:20px;padding-top:10px;border-radius: 8px 8px 8px 8px">
          <el-button type="info" @click="addBrands" >加入购物车</el-button>&nbsp;&nbsp;
          <router-link :to="{
      path:'/pay',
      query:{checkedData:[JSON.stringify(this.checkedData)]}
      }">
            <el-button type="warning"   style="width: 300px;">立即购买</el-button>
          </router-link>
        </div>
        <div v-else>
          <el-button type="info" @click="empty" round  style="cursor: not-allowed;margin-left: 120px;margin-top: 20px">已售罄</el-button>
        </div>
        <div style="margin-top: 100px;margin-left: -100px">
        <el-radio-group class="type-selector" v-model="selectedType1">
          <el-radio id="detail" v-for="(type, index) in typeList" :key="index" :label="index" @change="handleChange(index)"
                    @click="selectedType1=index">
            {{type}}
          </el-radio>
        </el-radio-group>
        </div>
        <hr style="font-weight: 800;margin: 20px 0 20px -500px">
        <div style="margin-left: -500px" v-if="selectedType1===0">
          <img :src="tableData.detailImageUrl" width="1200px">
        </div>
        <div v-else style="margin-left: -500px;font-size: 23px;font-weight: 700">
        商品评论({{count}})：
          <div style="margin-left: 80px">
          <el-radio-group class="type-selector" v-model="selectedType2">
            <el-radio id="detail1" v-for="(type, index) in typeList2" :key="index" :label="index" @change="handleChange1(index)"
                      @click="selectedType2=index" >
              {{type}}
            </el-radio>
          </el-radio-group>
          </div>
          <div   v-for="(comm,index) in commentList" :key="index"
                      style="width: 1200px;margin-top: 20px"
          >

            <div style="margin-top: 20px">
            <div style="display: flex"><img :src=comm.imageUrl width="60px" style="border-radius: 50%">
              <span style="padding-top: -30px;margin-left:20px;font-size: 20px;font-weight: 600">{{comm.userName}}</span>
              <span style="margin-left: 30px;font-size: 15px;color: #6a6969">发表时间：{{comm.stringCommentTime}}</span>
            </div>
              <el-rate
                  disabled
                  v-model="value[index]"
                  show-score
                  text-color="#ff9900"
                  score-template="{value}"
              style="margin-left: 80px;margin-top: -30px">
              </el-rate>
            <div style="margin-top: 10px;font-size: 17px;margin-left: 70px;font-weight: 500;" v-if="comm.commentDetail!== null && comm.commentDetail!==''">{{comm.commentDetail}}</div>
              <div v-else style="margin-top: 10px;font-size: 17px;margin-left: 70px;font-weight: 500">该用户没有填写评论</div>
              <hr>
            </div>
          </div>
        </div>
    </div>
    </div>
  </div>
</template>
<style scoped>

::v-deep #content .el-radio__label{
  border: none;
  font-size: 22px;
}
::v-deep #detail .el-radio__label{
  border:none;
  font-size: 22px;
  padding-right: 160px;
}

::v-deep #detail1 .el-radio__label{
  border:none;
  font-size: 16px;
  padding-right: 160px;
}


::v-deep .type-selector .el-radio {
  border:none;
  padding: 0;
}

::v-deep.type-selector .el-radio__inner {

  display: none;
}

::v-deep.type-selector .el-radio__label {
  padding:10px 20px 10px 20px;
  border-radius: 4px 4px 4px 4px;
  border: 2px solid #a6a5a5;
}

::v-deep.type-selector .el-radio.is-checked .el-radio__label {
  color: #f8c023;
  font-weight: bold;
  border:2px solid #f8c023;
}
h5{
  line-height: 10px;
  font-weight: 500;
}

#sell{
  font-size: 13px;
  color: #6a6969;
  padding-left: 30px;
}
#name{
  font-size: 27px;
  font-weight: 600;
  padding-top: 10px;
}
#price{
  color: #fac634;
  font-size: 27px;
  font-weight: 700;
}
</style>


<script >
import axios from "axios";

export default {
  data(){
    return{
      tableData:[],
      num:1,
      oldnum:1,
      categoryList:[],
      selectedType:0,
      selectedType1:0,
      selectedType2:0,
      typeList:["商品详情","宝贝评价"],
      typeList2:["全部","差评","中评","好评"],
      commentList:[],
      addList:{
        brandId:0,
        amount:0,
        price:0,
        categoryName:""
      },
      value:[],
      count:0,
      checkedData:{
        "brandId" :0,
        "categoryName":"",
        "num":0,
        "price":0,
        "brandType":""
      }
    }
  },
  mounted() {
    const _this = this
    const brandId = this.$route.query.brandId
    this.checkedData.brandId=brandId
    console.log(this.checkedData)
    axios({
      method:"get",
      url:`http://localhost/brands/findById?brandId=${brandId}`
    }).then(function (resp){
      _this.tableData = resp.data.data
      _this.originPrice = resp.data.data.brandPrice
      _this.checkedData.price = resp.data.data.brandPrice
      _this.checkedData.brandType = resp.data.data.brandType
      console.log(resp.data.brandType)
    })

    axios.get(`http://localhost/category/selectCategory?brandId=${brandId}`).then(function (resp){
      _this.categoryList=resp.data
      _this.checkedData.categoryName=_this.categoryList[_this.selectedType]
    })
    axios.get(`http://localhost/comments/findAll?brandId=${brandId}`).then(function (resp){
      _this.commentList = resp.data.data
      _this.count = resp.data.status
      for (let i = 0; i < resp.data.data.length; i++) {
        _this.value.push(resp.data.data[i].rate)
      }
    })
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
    handleAmountChange(){
      const _this = this
      console.log(this.num)
      this.num+=1
      if(this.tableData.brandAmount>=this.num){
        if(this.num>this.oldnum){
          this.oldnum += 1
          _this.checkedData.price=_this.originPrice * (_this.num)
          this.tableData.brandPrice = _this.originPrice * (this.num)
        }else{
          this.oldnum -= 1
          _this.checkedData.price=_this.originPrice * (_this.num)
          this.tableData.brandPrice = _this.originPrice * (this.num)
        }
      }else {
        _this.$message({
          message: "商品库存不足",
          type: 'error',
        })
      }

    },
    handleChange(index){
      console.log(this.selectedType1)
      this.selectedType1 = index
    },
    handleChange2(index){
      this.selectedType = index
      this.checkedData.categoryName = this.categoryList[this.selectedType]
    },
    handleChange1(index){
      this.selectedType2 = index
      const _this = this
      if (_this.typeList2[_this.selectedType2]==="全部"){
        axios.get(`http://localhost/comments/findAll?brandId=${_this.$route.query.brandId}`).then(function (resp){
          _this.commentList = resp.data.data
          _this.count = resp.data.status
          _this.value = []
          for (let i = 0; i < resp.data.data.length; i++) {
            _this.value.push(resp.data.data[i].rate)
          }
        })
      }else {
        axios.get(`http://localhost/comments/findAll?rateName=${_this.typeList2[_this.selectedType2]}&&brandId=${_this.$route.query.brandId}`).then(function (resp){
          _this.commentList = resp.data.data
          _this.count = resp.data.status
          _this.value = []
          for (let i = 0; i < resp.data.data.length; i++) {
            _this.value.push(resp.data.data[i].rate)
          }
        })
      }

    },
    addBrands() {
      const _this = this
        this.addList.brandId= this.$route.query.brandId
       this.addList.amount = this.num
      this.addList.price = this.tableData.brandPrice
      this.addList.categoryName = this.categoryList[this.selectedType]
      axios({
        method:"post",
        url:`http://localhost/shoppingCar/insert`,
        data:_this.addList
      }).then(function (resp){
        if(resp.data.status === 20511){
          _this.$message({
            message: resp.data.message,
            type: 'success',
          })
        }else {
          _this.$message({
            message: resp.data.message,
            type: 'error',
          })
        }
      })
    },
    empty(){
    }
  }
}
</script>