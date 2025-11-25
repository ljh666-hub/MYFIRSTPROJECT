<template>
  <div id="app" style="margin-left: 50px;height: 100%;background-color: #fbfbfb;">
    <audio ref="mp3" preload="auto" style="display: none" id="mp3">
      <source src="http://localhost/通知多 AP1.668(NotificationMulti__爱给网_aigei_com.mp3" type="audio/mpeg">
    </audio>
    <div style="display: flex;font-size: 22px;font-weight: 600">收货地址信息</div>
    <div>
    <el-button type="warning" style="margin-left: 1000px">
      <el-link icon="el-icon-edit" :underline="false"  @click="update">新增收货地址</el-link>
    </el-button>
    <br>
    <br>
    <el-radio-group v-model="selectedAddress" @change="handleAddressChange"
                    v-if="addressList.length>0" class="radio">
<el-card    v-for="(addr,index) in addressList" :key="index"
            style="display: flex;width: 1200px;height: 160px"
            :class="{'active':selectedAddress === index}"
            @click="selectedAddress=index"
             >
<el-radio  border style="width: 1150px;height: 120px;" :label="index">
  <div style="margin-left: 60px">用户名：{{ userInfo.userName }}
  <span style="padding-left: 250px">手机号码：{{ userInfo.userTelephone }}</span>
    <span style="padding-left: 250px">收货人姓名：{{ addr.realName }}</span>
    </div>
  <div style="margin-left: 60px;margin-top: 35px">
    <span>标签:<el-tag type="warning" style="margin-left: 20px">{{ addr.addressTag }}</el-tag></span>
    <span style="padding-left: 250px">详细地址：{{ addr.addressDetail }}</span>
  </div>
</el-radio>
</el-card>
    </el-radio-group>
    <el-card v-else style="width: 1200px">
    <el-empty description="暂无收货地址" ></el-empty>
  </el-card>
    </div>
    <el-dialog
        title="创建收货地址"
        :visible.sync="dialogVisible"
        width="50%"
        >
      <el-form ref="form" :model="addressForm" label-width="80px">
        <el-form-item label="真实姓名">
          <el-input v-model="addressForm.realName"></el-input>
        </el-form-item>
        <el-form-item label="标签">
          <el-select v-model="addressForm.addressTag" placeholder="请选择商品类型">
            <el-option label="家" value="家"></el-option>
            <el-option label="公司" value="公司"></el-option>
            <el-option label="学校" value="学校"></el-option>
            <el-option label="其它" value="其它"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="详细地址">
          <el-input type="textarea" v-model="addressForm.addressDetail"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="warning" @click="onSubmit">确认创建</el-button>
          <el-button @click="onCancel">取消</el-button>
        </el-form-item>


      </el-form>
    </el-dialog>

    <div>
  <div id="brandinfo">确认商品信息</div>
  <el-row :data="tableData" style="width: 1250px">

      <el-card v-for="(brand,index) in tableData" :key="index">

        <div style="display: flex">
        <img :src=brand.imageUrl width="130px" height=auto>

          <div style="padding-left: 120px;padding-top: 10px">
            <div id="name">{{tableData[index].brandName}}</div>
           <div id="detail">商品颜色/种类：{{categoryName[index]}}</div>
          </div>

          <div style="padding-left: 200px;padding-top: 20px">
            <div id="price">￥{{tableData[index].brandPrice}}</div>
          </div>
          <div id="amount">
          <template>
            <el-input-number size="mini" v-model="num[index]" :step="1" :min="1"
            @change="handleAmountChange(index)"></el-input-number>
          </template></div>
        </div>
        <el-collapse v-model="activeNames" @change="handleChange">
        <el-collapse-item title=其它服务 name="1" style="font-size: 25px">
          <div style="display: flex">
            <div style="width: 50px;">备注：</div><el-input
              placeholder="请输入内容"
              v-model="input"
              clearable>

          </el-input></div>
          <br>
          <div style="display: flex">
          <div style="width: 50px">发票：</div><el-checkbox v-model="checked">需要发票</el-checkbox>
        </div>
        </el-collapse-item>
        </el-collapse>
      </el-card>
    <el-card style="width: 1220px;margin-top: 20px;padding-left: 30px;color: #6a6969">
      <div style="padding-left: 30px;color: #6a6969" v-if="isUse===false">请选择优惠卷</div>
      <div v-if="isUse===true">已使用{{cuponType}}</div>
      <div style="margin-left: 980px;color: #ffd04b;margin-top: -20px;cursor: pointer" @click="dialogVisible1=true" v-if="isUse===false">有{{cuponData.length}}张可用优惠卷  ></div>
      <div style="margin-left: 980px;color: #ffd04b;margin-top: -20px;cursor: pointer" @click="dialogVisible1=true" v-if="isUse===true && discountValue>1">已优惠{{discountValue}}元 ></div>
      <div style="margin-left: 980px;color: #ffd04b;margin-top: -20px;cursor: pointer" @click="dialogVisible1=true" v-if="isUse===true && discountValue<1">已打{{discountValue*100}}折 ></div>
    </el-card>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <div style="display: flex">
    <el-card style="width: 80%;position: fixed;top: 820px;height: 100px;line-height: 60px;z-index: 999;display:flex;background-color: #757575;margin-left: -30px">
      <div style="margin-bottom: -60px;margin-left: 150px;font-size: 19px;font-weight: 600;color: #ffd04b">合计：￥{{total.toFixed(2)}}</div>
    <el-button type="info"  style="width: 200px;position:relative;left: 800px;" @click="handleBack" >取消订单</el-button>&nbsp;
    <el-button type="warning" @click="payOrder" style="width: 200px;position:relative;left: 800px">立即支付</el-button>
    </el-card>
    </div>
  </el-row>
</div>

    <el-dialog
        title="可用优惠卷"
        :visible.sync="dialogVisible1"
        width="50%"
    >
      <div style="font-size: 13px;color: #6a6969;margin-left: 30px;margin-bottom: -20px">注：优惠卷不可以叠加使用，一单只能使用一张优惠卷</div>
    <div v-for="(cupon,index) in cuponData" :key="index">
      <div style="width: 90%;height: 90px;background-color: #ffe9e9;margin-left: 30px;border-radius: 10px 10px 10px 10px;margin-top: 30px">
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
          <el-button style="margin-left: 350px;height: 40px;width: 80px;margin-top: 24px;border: none;background-color:#ffd04b" @click="handleUse(index)">使用</el-button>
        </div>
      </div>
    </div>
    </el-dialog>
  </div>


</template>

<style>
.radio{
  color:#ffd04b;
  border-color: #ffd04b;
}
#p{
  padding-left: 480px;
}

#a{
  padding-left: 780px;

}
#amount{
  padding-left: 250px;
  padding-top: 40px;
  width: 200px;
  text-indent: 0px;
}
#brandinfo{
  font-size: 21px;
  padding-top: 50px;
  padding-bottom: 20px;
  font-weight: 600;
}
#name{
  font-size: 19px;
  font-weight: 600;
  padding-top: 10px;
}
#detail{
  padding-top: 20px;
}
#price{
  color: #fac634;
  font-size: 21px;
  font-weight: 700;
}
</style>


<script>
import axios from "axios";

export default {
  data() {
    return {
      InitData:{
        typeLimit:"",
        moneyLimit:0
      },
      cuponData:[],
      tableData: [],
      num:[],
      oldnum:[],
      userInfo:[],
      address:"",
      activeNames: ['1'],
      input:"",
      checked:true,
      dialogVisible : false,
      userId:0,
      selectedAddress:0,
      addressForm:{
      addressDetail:"",
        realName:"",
        addressTag:"",
        userId:""
      },
      addressList:[],
      sendData:{
        orderId:"",
        orderAddress:"",
        userId:0,
        brandId:0,
        orderRemark:"",
        amount:"",
        orderPrice:"",
        categoryName:""
      },
      originPrice:[],
      categoryName:[],
      checkedData:[],
      total:0,
      isUse:false,
      dialogVisible1:false,
      cuponType:"",
      discountValue:1,
      cuponId:0
    }


  },
  async mounted() {
    const _this = this
    let data = null
    if (Array.isArray(this.$route.query.checkedData)){
      data = this.$route.query.checkedData
    }else {
      data = [this.$route.query.checkedData]
    }
    this.checkedData=[]
    console.log(data)
    for (let i = 0; i < data.length; i++) {
    _this.checkedData.push(JSON.parse(data[i]))
    }

    await axios({
      method:"post",
      url:'http://localhost/cupon/selectUseful',
      data:_this.InitData
    }).then(function (resp){
      _this.cuponData = [].concat(resp.data)
    })
    for (let i = 0; i < this.checkedData.length; i++) {
      _this.oldnum.push(1)
      _this.num.push(_this.checkedData[i].num)
      _this.categoryName.push(_this.checkedData[i].categoryName)
      _this.total += _this.checkedData[i].price
      _this.InitData.moneyLimit = _this.checkedData[i].price
      _this.InitData.typeLimit = _this.checkedData[i].brandType
      console.log(_this.checkedData[i].brandType)
      await axios({
        method: "get",
        url: `http://localhost/brands/findById?brandId=${_this.checkedData[i].brandId}`
      }).then(function (resp) {
        _this.tableData.push(resp.data.data)
        _this.originPrice.push(resp.data.data.brandPrice)
      })
    }
    axios.get("http://localhost/users/selectByUserName").then(function (resp) {
      _this.userInfo = resp.data
      _this.addressForm.userId = resp.data.userId
      _this.userId = resp.data.userId
      _this.ws = new WebSocket(`ws://localhost/ws/${_this.userId}`)
      _this.ws.onmessage = function (event) {
        _this.$refs.mp3.play().catch(err => {
          console.log("no")
        })
        const h = _this.$createElement;
        _this.$notify({
          title: '收到信息',
          message: h('i', {style: 'color: teal'}, `客服向你发送了一条消息`)
        });
      }
    })
    axios.get(`http://localhost/address/findAddress`).then(function (resp) {
      _this.addressList = resp.data
    })
  },
  beforeDestroy() {
    if(this.ws){
      this.ws.close();
      this.ws = null
    }
  },
  methods: {
    handleUse(index){
      this.dialogVisible1 = false
      this.isUse = true
      this.cuponType = this.cuponData[index].cuponType
      this.discountValue = this.cuponData[index].discountValue
      this.cuponId = this.cuponData[index].cuponId
      this.total = this.total*this.discountValue
    },
    update() {
      this.dialogVisible = true
    },
    onSubmit() {
      const _this = this
      if (this.addressForm.realName === "") {
        _this.$message({
          message: "真实姓名不能为空！",
          type: 'error',
        })
      } else if (this.addressForm.addressTag === "") {
        _this.$message({
          message: "标签不能为空！",
          type: 'error',
        })
      } else if (this.addressForm.addressDetail === "") {
        _this.$message({
          message: "详细地址不能为空！",
          type: 'error',
        })
      } else {
        axios({
          method: "post",
          url: "http://localhost/address/addAddress",
          data: _this.addressForm
        }).then(function (resp) {
          if (resp.data.status === 20211) {
            _this.$message({
              message: resp.data.message,
              type: 'success',
              onClose() {
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
    },
    onCancel() {
      this.dialogVisible = false
    },
    handleAddressChange(index) {
      for (let i = 0; i < this.checkedData.length; i++) {
        const _this = this
        this.sendData.orderId = orderId
        this.sendData.userId = this.userId
        this.sendData.brandId = JSON.parse(this.checkedData[i]).brandId
        this.sendData.amount = this.num[i]
        this.sendData.orderAddress = this.addressList[this.selectedAddress].addressDetail
        this.sendData.orderRemark = this.input
        this.sendData.orderPrice = this.tableData[i].brandPrice*this.discountValue
        this.sendData.categoryName = JSON.parse(_this.checkedData[i]).categoryName
      }
    },
    cancelOrder() {

    },
    async payOrder() {
      const list = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"
        , "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"];

      const _this = this
      let orderId = ""
      for (let i = 0; i < 10; i++) {
        const randomIndex = Math.ceil(Math.random() * 35)
        orderId += list[randomIndex];
      }
      this.sendData.orderId = orderId
      this.sendData.userId = this.userId
      if (this.addressList.length>0){
        this.sendData.orderAddress = this.addressList[this.selectedAddress].addressDetail
        this.sendData.orderRemark = this.input
        for (let i = 0; i < this.checkedData.length; i++) {
          this.sendData.brandId = JSON.parse(this.checkedData[i]).brandId
          this.sendData.amount = this.num[i]
          this.sendData.categoryName = JSON.parse(_this.checkedData[i]).categoryName
          if(this.discountValue<=1){
            this.sendData.orderPrice = this.tableData[i].brandPrice*this.discountValue
          }else {
            this.sendData.orderPrice = this.tableData[i].brandPrice-this.discountValue
          }
          await axios({
            method: "POST",
            url: "http://localhost/orders/addOrder",
            data: _this.sendData
          }).then(function (resp) {
            if (_this.tableData[i].brandAmount >= _this.num[i]) {
              if (resp.data.status === 20311) {
                axios.get(`http://localhost/cupon/updateCuponUse?cuponId=${_this.cuponId}&&orderId=${_this.sendData.orderId}`)
                location.href = "http://localhost:8080/#/afterPay"
              } else {
                _this.$message({
                  message: resp.data.message,
                  type: 'error',
                })
              }
            } else {
              _this.$message({
                message: "商品库存不足",
                type: 'error',
              })
            }
          })
        }
      }else{
        this.$message({
          message: "您还未选择收货地址哦",
          type: 'error',
        })
      }
    },
    handleChange() {

    },
    handleAmountChange(index) {
      const _this = this
      if (this.tableData[index].brandAmount > this.num[index]) {
        if (this.num[index] > this.oldnum[index]) {
          this.oldnum[index] += 1
          console.log(this.oldnum)
          this.tableData[index].brandPrice = _this.originPrice[index] * (this.num[index])
          this.total += _this.originPrice[index]
        } else {
          this.oldnum[index] -= 1
          this.tableData[index].brandPrice = _this.originPrice[index] * (this.num[index])
          this.total -= _this.originPrice[index]
        }
      } else {
        _this.$message({
          message: "商品库存不足",
          type: 'error',
        })
      }
    },
    handleBack() {
      const _this = this
      this.$confirm('你确定要取消该订单吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        _this.$message({
          message: "订单取消成功",
          type: 'success',
          onClose() {
            location.href = "http://localhost:8080/#/admin"
          }
        })

      })
    },
  }
}
</script>