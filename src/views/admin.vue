<template>
<div id="app" style="width: 100%;overflow-y: auto;overflow-x: hidden;height: 822px;background-color: #fbfbfb;">
  <audio ref="mp3" preload="auto" style="display: none" id="mp3">
    <source src="http://localhost/通知多 AP1.668(NotificationMulti__爱给网_aigei_com.mp3" type="audio/mpeg">
  </audio>
  <div style="display: flex;margin-top: 15px;margin-bottom: 20px;position: relative">
    <img src="http://localhost/images/logo2.jpg" style="margin-left: 50px;margin-top: 18px" width="200px" height="50px">
    <el-input  style="width: 900px;height:40px;margin-left:10px;margin-bottom:15px;margin-top:15px;border: 6px #f8c023 solid;border-radius: 10px 10px 10px 10px"
               placeholder="搜索商品信息"
               v-model="input"
               @input="handleInput"
               clearable>
    </el-input><el-button type="warning" icon="el-icon-search" @click="search" style="height: 50px;margin-top: 16px;margin-left: 20px">搜索</el-button>
    <div style="height: auto;margin-top: 70px;position: absolute;background-color: white;z-index: 999;margin-left: 270px;width: 900px;line-height: 20px;border-radius: 0 0 10px 10px" id="select">
      <div v-for="(brand,index) in tableData1" :key="index" style="height: 50px;line-height: 50px">
        <div style="padding-left: 20px;" @click="go(index)">{{brand.brandName}}</div>
      </div>
    </div>
  </div>
  <div style="margin-bottom: 10px;margin-left: 80px;display: flex" >分类：
  <el-radio-group v-model="selectedType" class="type-selector">
    <el-radio v-for="(type, index) in typeList" :key="index" :label="index" @change="handleChange(index)">
      {{ type }}
    </el-radio>
  </el-radio-group>
</div>
  <el-carousel indicator-position="outside" style="width: 90%;margin-left: 70px;">
    <el-carousel-item v-for="item in pictureForm" :key="item">
      <img :src="item" width="100%" height="100%" style="border-radius: 20px 20px 20px 20px;">
    </el-carousel-item>
  </el-carousel>

  <div style="margin-left: 50px;display: flex;margin-bottom: 10px">
  <el-button class="el-icon-d-caret" @click="search2" style="background-color:#fbfbfb;border: none">筛选:</el-button>
    <div style="margin-left: 50px;font-size: 14px">  销量：<el-select v-model="brandSell" clearable placeholder="请选择" >
      <el-option label="不限" value=""></el-option>
      <el-option label="从高到低" value="desc"></el-option>
      <el-option label="从低到高" value="asc"></el-option>
    </el-select></div>
    <div style="margin-left: 50px;font-size: 14px">  价格区间：<el-select v-model="brandPriceBegin" clearable placeholder="请选择" style="width: 150px">
      <el-option label="0元" value="0"></el-option>
      <el-option label="50元" value="50"></el-option>
      <el-option label="200元" value="200"></el-option>
      <el-option label="500元" value="500"></el-option>
      <el-option label="1000元" value="1000"></el-option>
      <el-option label="3000元" value="3000"></el-option>
    </el-select>  到
      <el-select v-model="brandPriceEnd" clearable placeholder="请选择" style="width: 150px">
      <el-option label="50元" value="50"></el-option>
      <el-option label="200元" value="200"></el-option>
      <el-option label="500元" value="500"></el-option>
      <el-option label="1000元" value="1000"></el-option>
        <el-option label="3000元" value="3000"></el-option>
        <el-option label="5000元" value="5000"></el-option>
    </el-select>
    </div>
  </div>
  <keep-alive>
  <div v-if="tableData.length>0" style="margin-left: 30px;">

    <el-row :gutter="80"
            v-infinite-scroll="load"
            infinite-scroll-disabled="disabled">
      <el-col :span="6" style="margin-bottom: 25px" v-for="(brand,index) in tableData" :key="brand.brandId">
        <a :href="`/dist/taowu.html#/detail?brandId=${brand.brandId}`" target="_blank">
          <el-card id="card">
            <img :src=brand.imageUrl width="240px" height="240px">

            <div id="brandName" style="width: 240px;">{{ brand.brandName }}</div>

            <div id="detail">{{ brand.brandDetail }}</div>
            <div id="price">￥{{ brand.brandPrice }}<span id="sell">已售：{{ brand.brandSell }}</span></div>

          </el-card>
        </a>
      </el-col>
    </el-row>

  </div>

    <div v-else>
      <el-empty description="暂未找到相关商品"></el-empty>
    </div>
  </keep-alive>
    <p v-if="loading" style="margin-left: 700px;font-size: 16px;color:#cdcbcb;">加载中...</p>
    <p v-if="noMore" style="margin-left: 700px;font-size: 16px;color:#cdcbcb;">没有更多了</p>
</div>

</template>

<style>
#select :hover{
  background-color: #e8e7e7;
}
a {
  text-decoration: none;
}
a :hover{
  color: #f8bf1d;
}
#sell{
  font-size: 13px;
  color: #6a6969;
  padding-left: 10px;
}
.type-selector .el-radio {
  border: none;
  padding: 0;
}

.type-selector .el-radio__inner {
  display: none;
}

.type-selector .el-radio__label {
  padding-left: 100px;

}

.type-selector .el-radio.is-checked .el-radio__label {
  color: #f8c023;
  font-weight: bold;
}
#label{
  margin-left: 100px;
}
#detail{
  padding-top: 5px;
  color: #6a6969;
  height: 30px;
  line-height: 30px;
}
#price{
  font-size: 22px;
  color: #fac634;
  font-weight: 700;
  padding-top: 15px;
  padding-bottom: 5px;

}
#brandName{
  font-size: 19px;
  font-weight: 600;
}
#card{
  min-height: 440px;
  padding: 0;
  text-decoration: none;
}
.el- card{
  border-radius: 4px;

}
.el- row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
.el- col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  background: #d3dce6;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 300px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
</style>
<script>
import axios from "axios";

export default {
data() {
  return {
      tableData:[],
    tableData1:[],
    userInfo:[],
    input:"",
    typeList:["全部","运动","食品","服饰","家具家用","书籍文献","其它"],
    selectedType:0,
    loading: false,
    page:0,
    nowPage:0,
    nowPage1:0,
    noMore:false,
    pictureForm:[],
    brandSell:"",
    brandPriceBegin:"",
    brandPriceEnd:"",
    isLoading:true
  }


},
  mounted() {
    var _this = this
    axios.get(`http://localhost/adminPicture/selectAll`).then(function (resp){
      _this.pictureForm = resp.data
    })
    axios.get("http://localhost/users/selectByUserName").then(function (resp){
      _this.userInfo = resp.data
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
  computed: {
    disabled () {
      return this.loading || this.noMore
    }
  },
  beforeDestroy() {
    if(this.ws){
      this.ws.close();
      this.ws = null
    }
  },
  methods: {
  go(index){
    this.input = this.tableData1[index].brandName
    this.search(this.tableData1[index].brandName)
    this.tableData1 = []
  },
    addBrands(){

    },
    buyBrands(){

    },
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      this.dialogVisible = false
    },
    empty(){

    },
    search(item){
      const _this = this
        // axios({
        //   method:"get",
        //   url:`http://localhost/brands/selectByCondition?brandName=${_this.input}&brandType=${brandType}&brandSell=${_this.brandSell}&priceBegin=${_this.brandPriceBegin}&priceEnd=${_this.brandPriceEnd}&nowPage=1`
        // }).then(function (resp){
        //   _this.tableData = resp.data
          _this.tableData1 = []
      _this.tableData=[]
          _this.isLoading =false
      _this.nowPage1=0
      _this.noMore=false
      _this.loading =false
          _this.load()

        // })


    },
    search2(){
    const _this = this
    if(this.input !== ""){
      this.search(_this.input)
    }else{
      this.search("")
    }
    },
    handleInput(){
      const _this = this
      if (this.input !== ""){
        axios.get(`http://localhost/brands/selectByCondition?brandName=${_this.input}&&nowPage=1`).then(function (resp){
          _this.tableData1 = resp.data
        })
      }else{
        _this.tableData1 =[]
      }
    },
    handleChange(index){
      const _this = this
      if(this.input !== ""){
        this.search(_this.input)
      }else{
        this.search("")
      }
    },
    load () {
    const _this = this
    if(this.isLoading){
      this.loading = true
      setTimeout(() => {
        _this.nowPage += 1
        axios({
          method:"get",
          url:`http://localhost/brands/brandPage?nowPage=${_this.nowPage}`
        }).then(function (resp){
          _this.page = resp.data.status
          _this.tableData = _this.tableData.concat(resp.data.data)
          if(resp.data.data.length<8){
            _this.noMore = true
            _this.loading = false
          }
        })
        this.loading = false
      }, 500)
    }else {
      _this.loading = true
      let brandType = this.typeList[this.selectedType]
      if(_this.typeList[this.selectedType]==="全部"){
        brandType = ""
      }
      setTimeout(() => {
        _this.nowPage1 += 1
        axios({
          method:"get",
          url:`http://localhost/brands/selectByCondition?brandName=${_this.input}&brandType=${brandType}&brandSell=${_this.brandSell}&priceBegin=${_this.brandPriceBegin}&priceEnd=${_this.brandPriceEnd}&nowPage=${_this.nowPage1}`
        }).then(function (resp){
          console.log(_this.nowPage1)
          _this.page = resp.data.status
          _this.tableData = _this.tableData.concat(resp.data)
          if(resp.data.length<8){
            _this.noMore = true
            _this.loading = false
          }
        })
        _this.loading = false
      }, 500)
    }
    }
  }
}
</script>