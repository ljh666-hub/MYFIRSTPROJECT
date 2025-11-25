<template>
  <div id="app" style="width: 100%;background-color: #fbfbfb;margin-top: -49px">
    <audio ref="mp3" preload="auto" style="display: none" id="mp3">
      <source src="http://localhost/通知多 AP1.668(NotificationMulti__爱给网_aigei_com.mp3" type="audio/mpeg">
    </audio>
    <div style="margin-left: 120px">
      <el-card style="width: 88%;height: 200px;margin-top: 50px;background-color: #fdba03;display: flex;border-radius: 10px 10px 10px 10px">
        <el-button style="background-color:#fdba03;border-radius: 10px 10px 10px 10px;position: absolute;left: 1290px;top: 180px;border:1px solid #555454;" @click="openUpdate">修改个人信息</el-button>
       <img :src="tableData.imageUrl" style="width: 130px;height: 130px;border-radius: 50%;margin-left: 100px;margin-top: 15px">
        <div style="color: white;font-size: 28px;font-weight: 600;width: 350px;margin-left: 310px;margin-top: -130px">{{tableData.userName}}</div>
        <div style="margin-left: 60px;margin-top: 15px">
          <span style="padding-left: 250px"><span id="tag">手机号码：</span>{{ tableData.userTelephone }}</span>
          <span style="padding-left: 250px"><span id="tag">常驻地区：</span>{{ tableData.userAddress }}</span>
        </div>
        <div style="margin-left: 60px;margin-top: 15px">
          <span style="padding-left: 250px"><span id="tag">帐号状态：</span>{{ tableData.userStatus }}</span>
        </div>
        <div style="margin-left: 60px;margin-top: 35px">

        </div>
      </el-card>

      <div style="margin-top: 40px;width: 88%;">
        <el-card style="padding-top: 15px;padding-bottom: 10px;background: #fafafa;border-radius: 10px 10px 10px 10px">
          <div style="margin-bottom: 10px" ><span style="font-size: 21px;font-weight: 600;margin-left: 50px">我的订单</span>  <router-link :to="{path:'/order'
                         }"><span style="color: #6a6969;margin-left: 860px;font-size: 14px">查看全部  ></span></router-link></div>
          <div style="display: flex">
          <div style="height: 80px;border-right:2px solid #e4e4e4;width: 20%;">
            <router-link :to="{path:'/order',
                         query:{orderStatus:'已付款'}}">
           <div class="el-icon-collection" style="margin-left: 60px;font-size: 28px"></div>
            <span style="line-height: 70px;margin-left: 10px;font-size: 20px">已付款</span>
            </router-link>
          </div>
          <div style="height: 80px;border-right:2px solid #e4e4e4;width: 20%;">
            <router-link :to="{path:'/order',
                         query:{orderStatus:'已发货'}}">
            <div class="el-icon-box" style="margin-left: 60px;font-size: 28px"></div>
            <span style="line-height: 70px;margin-left: 10px;font-size: 20px">已发货</span>
            </router-link>
          </div>

          <div style="height: 80px;border-right:2px solid #e4e4e4;width: 20%;">
            <router-link :to="{path:'/order',
                         query:{orderStatus:'运输中'}}">
            <div class="el-icon-truck" style="margin-left: 60px;font-size: 28px"></div>
            <span style="line-height: 70px;margin-left: 10px;font-size: 20px">运输中</span>
            </router-link>
          </div>
          <div style="height: 80px;border-right:2px solid #e4e4e4;width: 20%;">
            <router-link :to="{path:'/order',
                         query:{orderStatus:'已签收'}}">
            <div class="el-icon-takeaway-box" style="margin-left: 60px;font-size: 28px"></div>
            <span style="line-height: 70px;margin-left: 10px;font-size: 20px">已签收</span>
            </router-link>
          </div>
          <div style="height: 80px;width: 18%;">
            <router-link :to="{path:'/order',
                         query:{orderStatus:'已收货'}}">
            <div class="el-icon-finished" style="margin-left: 60px;font-size: 28px"></div>
            <span style="line-height: 70px;margin-left: 10px;font-size: 20px">已收货</span>
            </router-link>
          </div>
          </div>
        </el-card>
      </div>
      <div style="margin-top: 50px;display: flex;">
      <el-card style="padding-bottom: 10px;background: #fafafa;width: 350px;height: 200px;border-radius: 10px 10px 10px 10px">
        <div style="margin-bottom: 10px" ><span style="font-size: 21px;font-weight: 600;margin-left: 20px">我的购物车</span>  <router-link :to="{path:'/shoppingCar'
                         }"><span style="color: #6a6969;margin-left: 100px;font-size: 14px;margin-top: -20px">查看全部  ></span></router-link></div>
        <div style="display: flex" v-if="carData.length>0">
        <img :src="carData[0].imageUrl" width="130px" height="130px" style="border-radius: 10px 10px 10px 10px;margin-left: 20px;margin-top: 5px">
          <div style="width: 100px;margin-left: 40px;margin-top: 25px">{{carData[0].brandName.slice(0,12)}}...</div>
          <div style="margin-top: 90px;margin-left: -90px;color: #ffcb37;font-size: 20px;font-weight: 600">￥{{carData[0].price}}</div>
        </div>
        <div v-else style="margin-top: -60px;">
          <el-empty description="您还没有添加地址哦~" style="margin-top: -20px"></el-empty>
        </div>
      </el-card>

        <el-card style="padding-bottom: 10px;background: #fafafa;width: 350px;height: 200px;border-radius: 10px 10px 10px 10px;margin-left: 60px">
          <div style="margin-bottom: 10px" ><span style="font-size: 21px;font-weight: 600;margin-left: 20px">我的地址簿</span>
                         <span style="color: #6a6969;margin-left: 100px;font-size: 14px;margin-top: -20px;cursor: pointer" @click="openAddress">查看全部  ></span></div>
          <div>
           <div v-if="addressList.length>0" style="border:2px solid #ffd04b;width: 300px;height: 110px;margin-top: 20px;margin-left: 5px;border-radius: 10px 10px 10px 10px">
            <div style="margin-left: 20px;font-size: 14px;margin-top: 10px"><span style="font-weight: 600">用户名：</span>{{ tableData.userName }}
              <span style="padding-left: 20px;font-size: 14px"><span style="font-weight: 600">手机号码：</span>{{ tableData.userTelephone }}</span>

            </div>
            <div style="margin-left: 20px;margin-top: 8px">
              <span style="font-size: 14px"><span style="font-weight: 600">收货人姓名：</span>{{ addressList[0].realName }}</span>
              <span style="margin-left: 40px;font-size: 14px"><span style="font-weight: 600">标签:</span><el-tag type="warning" style="margin-left: 20px;">{{ addressList[0].addressTag }}</el-tag></span>
            </div>
            <div style="margin-left: 20px;margin-top: 8px">
              <span style="font-size: 14px"><span style="font-weight: 600">详细地址：</span>{{ addressList[0].addressDetail.slice(0,13) }}...</span>
            </div>
           </div>
            <div v-else style="margin-top: -60px;">
              <el-empty description="您还没有添加地址哦~" style="margin-top: -20px"></el-empty>
            </div>
          </div>
      </el-card>

        <el-card style="padding-bottom: 10px;background: #fafafa;width: 350px;height: 200px;border-radius: 10px 10px 10px 10px;margin-left: 60px">
          <div style="margin-bottom: 10px" ><span style="font-size: 21px;font-weight: 600;margin-left: 20px">我的优惠卷</span>
            <router-link to="/discount">
            <span style="color: #6a6969;margin-left: 100px;font-size: 14px;margin-top: -20px">查看全部  ></span>
            </router-link>
            <div style="width: 290px;height: 90px;background-color: #ffe9e9;margin-left: 10px;border-radius: 10px 10px 10px 10px;margin-top: 30px" v-if="tableData2.length>0">
              <div style="display: flex">
                <div style="padding-top: 10px;padding-bottom: 5px">
                  <div v-if="tableData2[0].discountValue>1"  style="margin-left: 20px;color: red;font-size: 28px;font-weight: 600;">￥{{tableData2[0].discountValue}}</div>
                  <div v-else style="margin-left: 20px;color: red;font-size: 28px;font-weight: 600;">{{tableData2[0].discountValue*100}}折</div>
                  <div style="margin-left: 25px;color: red;font-size: 15px;font-weight: 600;">{{tableData2[0].cuponType}}</div>
                </div>
                <div style="padding-top: 15px;padding-bottom: 5px">
                  <div v-if="tableData2[0].moneyLimit>0" style="margin-left: 40px;">满{{tableData2[0].moneyLimit}}元可用</div>
                  <div v-else style="margin-left: 40px;">无门槛</div>
                  <div style="margin-left: 40px;font-size: 14px;color:grey;">{{tableData2[0].endTime}}到期</div>
                  <div style="margin-left: 40px;font-size: 13px;color:grey;" v-if="tableData2[0].TypeLimit = '不限'">所有商品类型都可使用</div>
                  <div style="margin-left: 40px;font-size: 13px;color:grey;" v-else>仅{{tableData2[0].TypeLimit}}类商品可使用</div>
                </div>
              </div>
            </div>
            <div v-else style="margin-top: -60px;">
              <el-empty description="您还没有可用优惠卷哦~" style="margin-top: -20px"></el-empty>
            </div>
          </div>
        </el-card>
    </div>

      <el-card style="width: 1160px;margin-top: 50px;height: 80px">
        <router-link to="commentManage">
      <el-button style="width: 500px;margin-left: 30px" type="warning">我的评论管理</el-button>
        </router-link>
        <el-button style="width: 500px;margin-left: 50px" type="info" @click="exitAccount">切换账号/退出登录</el-button>
      </el-card>

    </div>

    <el-dialog
        title="修改账号信息"
        :visible.sync="dialogVisible3"
        width="60%"
    >
      <h3>基础信息</h3>
      <el-form ref="form" :model="userForm" label-width="80px">
        <el-form-item label="更换头像">
          <input type = "file"  id ="fileInput"/>
          <el-button type="warning" @click="handleUpload">上传<i class="el-icon-upload el-icon--right"></i></el-button>
          <div slot="tip" class="el-upload__tip">只能上传一个jpg/png文件，且不超过500kb</div>
        </el-form-item>
        <el-form-item label="更改用户名" label-width="100px">
          <el-input v-model="userForm.userName"></el-input>
        </el-form-item>
        <el-form-item label="更改常住地址" label-width="100px">
          <el-input v-model="userForm.userAddress"></el-input>
        </el-form-item>
        <el-form-item label="更改手机号" label-width="100px">
          <el-input v-model="userForm.userTelephone"></el-input>
        </el-form-item>
        <h3>修改密码</h3>
        <el-form-item label="初始密码" type="password">
          <el-input v-model="input"></el-input>
        </el-form-item>
        <el-form-item label="设置新密码" label-width="100px">
          <el-input v-model="userForm.userPassword" type="password" placeholder="密码长度需大于6位，且同时包含字母和数字"></el-input>
        </el-form-item>
        <el-form-item style="margin-left: 700px">
          <el-button type="warning" @click="sure">确认修改</el-button>
          <el-button @click="dialogVisible3=false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>


    <el-dialog
        title="我的地址簿"
        :visible.sync="dialogVisible"
        width="75%"
    >
      <div>
        <br>
        <el-button type="danger" style="margin-left: 1100px;margin-bottom: 8px" @click="handleDelete">删除地址
        </el-button>
        <el-radio-group v-model="selectedAddress" @change="handleAddressChange"
                        v-if="addressList.length>0" class="radio">
          <el-card v-for="(addr,index) in addressList" :key="index"
                   style="display: flex;width: 1200px;height: 160px"
                   :class="{'active':selectedAddress === index}"
                   @click="selectedAddress=index"
          >
            <el-radio border style="width: 1150px;height: 120px;" :label="index">
              <div style="margin-left: 60px">用户名：{{ tableData.userName }}
                <span style="padding-left: 250px">手机号码：{{ tableData.userTelephone }}</span>
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
          <el-empty description="暂无收货地址"></el-empty>
        </el-card>
      </div>
      <div>
        <h2>新增地址</h2>
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

          <el-form-item style="margin-left: 900px">
            <el-button type="warning" @click="onSubmit">确认创建</el-button>
            <el-button @click="onCancel">取消</el-button>
          </el-form-item>


        </el-form>
      </div>
    </el-dialog>
  </div>

</template>
<style>
#tag {
  color: #717171;
  font-weight: 600;
}

a {
  color: black;
}

a:hover {
  color: #ffc520;
}
</style>
<script>
import axios from "axios";

export default {
  data() {
    return {
      tableData: [],
      tableData2: [],
      carData: [],
      addressList: [],
      dialogVisible: false,
      dialogVisible2: false,
      dialogVisible3: false,
      addressForm:{
        addressDetail:"",
        realName:"",
        addressTag:"",
        userId:"",
        imageUrl:""
      },
      selectedAddress:0,
      addressId:0,
      userForm:{
        userName:"",
        userTelephone:"",
        userAddress:"",
        userPassword:"",
        userStatus:""
      },
      oldPassword:"",
      input:""
    }
  },
  mounted() {
    var _this = this
    axios.get(`http://localhost/cupon/selectNewUser?cuponId=0`).then(function (resp){
      _this.tableData2 = resp.data
    })
    axios.get(`http://localhost/shoppingCar/findFirst`).then(function (resp){
      _this.carData = resp.data
    })
    axios.get(`http://localhost/address/findAddress`).then(function (resp){
      _this.addressList=resp.data
      if (_this.addressList.length>0){
        _this.addressId=_this.addressList[0].addressId
      }
    })
    axios.get("http://localhost/users/selectByUserName").then(function (resp){
      _this.tableData = resp.data
      _this.userId = resp.data.userId
      _this.userForm.userName = resp.data.userName
      _this.userForm.userTelephone = resp.data.userTelephone
      _this.userForm.userAddress  =resp.data.userAddress
      _this.oldPassword = resp.data.userPassword
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
  methods:{
    exitAccount(){
      const  _this = this
      this.$confirm('你确定要退出登陆吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        _this.$router.push('/')
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        });
      });
    },
    async handleUpload() {
      const file=document.getElementById("fileInput").files[0]
      const formData = new FormData()
      formData.append('file',file)
      console.log(file)
      var _this = this
      if(!file){
        _this.$message({
          message: "请先选择文件",
          type: 'error',
        })
      }else{
        axios({
          method:'post',
          url:"http://localhost/brands/getImage",
          data:formData
        }).then(function (resp){
          if(resp.data != null && resp.data !== ""){
            _this.$message({
              message: "上传成功",
              type: 'success',
            })
            _this.userForm.imageUrl = resp.data
          }else {
            _this.$message({
              message: "服务器繁忙，请稍后重试",
              type: 'error',
            })
          }
        })
      }
    },
    sure(){
      const _this = this
      if(_this.input !== ""){
        if (_this.oldPassword === _this.input){
          if (_this.userForm.userPassword.length>=6){
              if(/^(?=.*[a-zA-Z])(?=.*\d)[^\u4e00-\u9fa5]*$/.test(_this.userForm.userPassword)){
                axios({
                  method:"post",
                  url:"http://localhost/users/updateUserDetail",
                  data:_this.userForm
                }).then(function (resp){
                  if (resp.data.status === 20111){
                    _this.$message({
                      message: resp.data.message,
                      type: 'success',
                      onClose(){
                        location.reload();
                      }
                    })
                  }else{
                    _this.$message({
                      message: resp.data.message,
                      type: 'error',
                    })
                  }
                })
              }else{
                _this.$message({
                  message: "密码必须同时包含字母和数字",
                  type: 'error',
                })
              }
          }else {
            _this.$message({
              message: "密码不能小于6位",
              type: 'error',
            })
          }
        }else {
          _this.$message({
            message: "初始密码不正确",
            type: 'error',
          })
        }
      }else {
        axios({
          method:"post",
          url:"http://localhost/users/updateUserDetail",
          data:_this.userForm
        }).then(function (resp){
          if (resp.data.status === 20111){
            _this.$message({
              message: resp.data.message,
              type: 'success',
              onClose(){
                location.reload();
              }
            })
          }else{
            _this.$message({
              message: resp.data.message,
              type: 'error',
            })
          }
        })
      }
    },
    openUpdate(){
      this.dialogVisible3 =true
    },
    handleDelete(){
      const _this = this
      axios.get(`http://localhost/address/deleteAddress?addressId=${_this.addressId}`).then(function (resp){
        if (resp.data.status === 20221){
          _this.$message({
            message: resp.data.message,
            type: 'success',
            onClose(){
              location.reload();
            }
          })
        }else {
          _this.$message({
            message: resp.data.message,
            type: 'error',
          })
        }
      })
    },
    handleAddressChange(){
     this.addressId=this.addressList[this.selectedAddress].addressId
    },
    openAddress(){
      this.dialogVisible=true
    },
    update(){
      this.dialogVisible2=true
    },
    onSubmit(){
      const _this = this
      this.addressForm.userId= this.userId
      if(this.addressForm.realName === ""){
        _this.$message({
          message: "真实姓名不能为空！",
          type: 'error',
        })
      }else if(this.addressForm.addressTag === ""){
        _this.$message({
          message: "标签不能为空！",
          type: 'error',
        })
      }else if(this.addressForm.addressDetail === ""){
        _this.$message({
          message: "详细地址不能为空！",
          type: 'error',
        })
      }else {
        axios({
          method:"post",
          url:"http://localhost/address/addAddress",
          data:_this.addressForm
        }).then(function (resp){
          if(resp.data.status === 20211){
            _this.$message({
              message: resp.data.message,
              type: 'success',
              onClose(){
                location.reload()
              }
            })
          }else{
            _this.$message({
              message: resp.data.message,
              type: 'error',
            })
          }
        })
      }

    },
    onCancel() {
      this.dialogVisible=false
    }
  }
}
</script>