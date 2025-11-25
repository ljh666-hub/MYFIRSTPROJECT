<template>
  <div id="app" style="width: 100%;height: 100%;background-color: #fbfbfb;">
    <audio ref="mp3" preload="auto" style="display: none" id="mp3">
      <source src="http://localhost/通知多 AP1.668(NotificationMulti__爱给网_aigei_com.mp3" type="audio/mpeg">
    </audio>
    <h1>申请原因</h1>
    <h4>修改收货地址</h4>
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
    <div style="margin-left: 1100px">
    <el-button type="warning" @click="handleUpdate">提交修改</el-button>
    </div>
    <div>
      <h3>其他退款原因：</h3>
      <div style="margin-left: 50px">
        <el-radio v-model="radio" label="1" border>宝贝买错了</el-radio>
        <el-radio v-model="radio" label="2" border>不想要了</el-radio>
        <el-radio v-model="radio" label="3" border>商品质量有问题</el-radio>
        <el-radio v-model="radio" label="4" border>物流运输时间过长</el-radio>
        <el-radio v-model="radio" label="5" border>包裹丢失了/未送到指定地址</el-radio>
        <el-radio v-model="radio" label="6" border>其他原因</el-radio>
      </div>
       <div style="margin-top: 20px;margin-bottom: 15px">具体原因描述：</div>
        <el-input type="textarea" v-model="sendData.orderRemark" style="width: 1200px;height: 100px"></el-input>
      <div style="margin-left: 1050px">
      <el-button type="warning" @click="handon">提交申请</el-button>
      <el-button @click="onCancel2">取消</el-button>
      </div>
    </div>
    <el-dialog
        title="新增收货地址"
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
          <el-button type="warning" @click="onSubmit">确认增加</el-button>
          <el-button @click="onCancel">取消</el-button>
        </el-form-item>


      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import axios from "axios";

export default {
  data(){
    return{
      userInfo:[],
      tableData:[],
      addressForm:{
        addressDetail:"",
        realName:"",
        addressTag:"",
        userId:""
      },
      addressList:[],
      selectedAddress:0,
      dialogVisible:false,
      radio:1,
      sendData:{
        orderId:"",
        orderAddress:"",
        userId:0,
        brandId:0,
        orderRemark:"",
        amount:"",
        orderPrice:""
      },
      orderData:{
        orderId:"",
        orderStatus:"退款中",
        orderRemark:""
      }
    }
  },
  mounted() {
    const _this = this
    const brandId = this.$route.query.brandId
    _this.ws = new WebSocket(`ws://localhost/ws/${_this.userId}`)
    _this.ws.onmessage = function (event) {
      _this.$refs.mp3.play().catch(err => {console.log("no")})
      const h = _this.$createElement;

      _this.$notify({
        title: '收到信息',
        message: h('i', { style: 'color: teal'}, `客服向你发送了一条消息`)
      });
    }
    axios({
      method:"get",
      url:`http://localhost/brands/findById?brandId=${brandId}`
    }).then(function (resp){
      _this.tableData = resp.data.data
      _this.originPrice = resp.data.data.brandPrice
    })
    axios.get("http://localhost/users/selectByUserName").then(function (resp){
      _this.userInfo = resp.data
      _this.addressForm.userId = resp.data.userId
      _this.userId = resp.data.userId
    })
    axios.get(`http://localhost/address/findAddress`).then(function (resp){
      _this.addressList=resp.data
    })
  },
  beforeDestroy() {
    if(this.ws){
      this.ws.close();
      this.ws = null
    }
  },
  methods:{
    update(){
      this.dialogVisible = true
    },
    onSubmit(){
      const _this = this
      axios({
        method:"post",
        url:"http://localhost/address/addAddress",
        data:_this.addressForm
      }).then(function (resp){
        if(resp.data.status === 20211){
          _this.$message({
            message: resp.data.message,
            type: 'success',
          })
        }else{
          _this.$message({
            message: resp.data.message,
            type: 'error',
          })
        }
      })
    },
    onCancel(){
      this.dialogVisible = false
    },
    onCancel2() {
      this.$router.push('/order')
    },
    handleAddressChange(index){
      this.sendData.orderAddress=this.addressList[index].addressDetail
      this.sendData.orderId=this.$route.query.orderId
    },
    handleUpdate(index){
      const _this = this
      this.sendData.orderId=this.$route.query.orderId
      console.log(index)
      this.sendData.orderAddress=this.addressList[this.selectedAddress].addressDetail

      axios({
          method:"post",
          url: `http://localhost/orders/updateAddress`,
          data: _this.sendData
      }).then(function (resp){
        _this.$message({
          message: resp.data.message,
          type: 'success',
        })
      })
    },
    handon(){
        const _this = this
      this.orderData.orderId = this.$route.query.orderId
      this.orderData.orderRemark=this.sendData.orderRemark
      axios({
        method:"post",
        url:`http://localhost/orders/updateAddress`,
        data:_this.orderData
      }).then(function (resp){
        if(resp.data.status === 20320){
          _this.$message({
            message: resp.data.message,
            type: 'success',
            onClose: function () {
              _this.$router.push('/order')
            }
          })
        }else{
          _this.$message({
            message: resp.data.message,
            type: 'error',
          })}
      })
    }
  }
}
</script>