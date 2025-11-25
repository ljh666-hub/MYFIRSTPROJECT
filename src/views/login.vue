<template>
<div id="app" style="background-image:url(http://localhost/images/background.jpg);background-size:cover;position:absolute;background-position: center;width: 1670px;height: 900px;background-color: rgba(255,255,255,0.3);background-blend-mode: overlay">
  <div style="position: absolute;margin-left: 360px;margin-top: 200px;width: 960px;height: 500px;background-color: white;border-radius: 20px 20px 20px 20px">
    <div style="margin-bottom: -490px;opacity: 1;z-index: 999">
      <img src="http://localhost/images/login.jpg" width="500px;" height="500px" style="border-radius: 20px 0 0 20px">
    </div>
    <div style="margin-left: 530px;margin-top: 100px;width: 400px;opacity: 1;z-index: 999">
      <img src="http://localhost/images/logo2.jpg" style="margin-left: 80px;margin-top: 10px" width="200px" height="50px">
    <el-input
        style="margin-top: 20px"
            placeholder="请输入您的账号"
            v-model="input"
            clearable>
    </el-input>
    <el-input
        style="margin-top: 20px"
            placeholder="请输入您的密码"
            v-model="password"
            type="password"
            clearable>
    </el-input>
      <el-button type="warning"  @click="handleRegister" style="background-color: white;border: none;color: blue;margin-left:300px">点我注册</el-button>
    <el-button type="warning"  @click="handleLogin" style="width: 340px;margin-top: 20px;margin-left: 30px;background-color: #f8c431;border: none;border-radius: 15px 15px 15px 15px;color: black">登录</el-button>

    <el-dialog
            title="用户注册"
            :visible.sync="dialogVisible"
            width="50%"
            >
        <span><el-form ref="form" :model="userForm" label-width="80px">
        <el-form-item label="用户名">
            <el-input v-model="userForm.userName"></el-input>
        </el-form-item>
        <el-form-item label="密码" >
            <el-input v-model="userForm.userPassword" type="password" placeholder="密码长度不能小于6位，且必须同时包含字母和数字"></el-input>
        </el-form-item>
            <el-form-item label="再次确认密码" label-width="100px">
            <el-input v-model="sure" type="password"></el-input>
        </el-form-item>
        <el-form-item label="常住地址">
            <el-input v-model="userForm.userAddress" placeholder="格式:  xx省/xx市"></el-input>
        </el-form-item>
        <el-form-item label="手机号码">
            <el-input v-model="userForm.userTelephone"></el-input>
        </el-form-item>
        <el-form-item label="头像">
        <input type = "file"  id ="fileInput"/>
        <el-button type="warning" @click="handleUpload">上传<i class="el-icon-upload el-icon--right"></i></el-button></el-form-item>
        <div style="padding-left: 100px;margin-left: 500px">
        <el-button type="warning" @click="register">立即注册</el-button>
            <el-button type="info" @click="dialogVisible=false">取消</el-button>
        </div>
    </el-form></span>
        <span slot="footer" class="dialog-footer">
  </span>
    </el-dialog>
  </div>
  </div>
</div>
</template>
<script>


import axios from "axios";

export default{
    data(){
        return{
            userForm:{
                userName:"",
                userPassword:"",
                userAddress:"",
                userTelephone:"",
                imageUrl:""
            },
            dialogVisible:false,
            input:"",
            password:"",
          sure:""
        }
    },
    methods:{
        handleRegister(){
          this.dialogVisible = true
          this.userForm={}
        },
        async handleUpload() {
            const file=document.getElementById("fileInput").files[0]
            const formData = new FormData()
            formData.append('file',file)
            console.log(file)
            var _this = this
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
                        message: "请先选择文件",
                        type: 'error',
                    })
                }
            })
        },
        handleLogin(){
            const _this = this
            if (_this.input === "Manage" && _this.password === "34091369"){
              sessionStorage.setItem("userName","Manage")
              location.href = "/dist2/manage.html"
            }else{
              axios({
                method:"get",
                url:`http://localhost/users/selectUser?userName=${_this.input}&userPassword=${_this.password}`
              }).then(function (resp){
                if(resp.data.status === 20121){
                  _this.$router.push('/admin')
                  console.log(resp.data.data)
                }else{
                  _this.$message({
                    message: resp.data.message,
                    type: 'error',
                  })
                }
              })
            }

        },
        register(){
            const _this = this
            if(_this.userForm.userName === null|| _this.userForm.userName === ""){
              _this.$message({
                message: "用户名不能为空",
                type: 'error',
              })
            }else{
              if(_this.userForm.userPassword.length>=6){
                  if (/^(?=.*[a-zA-Z])(?=.*\d)[^\u4e00-\u9fa5]*$/.test(_this.userForm.userPassword)) {
                    if (_this.userForm.userAddress !== null && _this.userForm.userAddress !== "") {
                      if (_this.userForm.userTelephone !== null && _this.userForm.userTelephone !== "" && _this.userForm.userTelephone.length===11) {
                        if (_this.userForm.imageUrl !== null && _this.userForm.imageUrl !== "") {
                          if (_this.userForm.userPassword === _this.sure) {
                            if (_this.userForm.userName.length<=8){
                              axios({
                                method: "post",
                                url: "http://localhost/users/addUser",
                                data: _this.userForm
                              }).then(function (resp) {
                                if (resp.data.status === 20101) {
                                  _this.$message({
                                    message: resp.data.message,
                                    type: 'success',
                                    onClose() {
                                      location.reload()
                                    }
                                  })
                                  location.reload()
                                } else {
                                  _this.$message({
                                    message: resp.data.message,
                                    type: 'error',
                                  })
                                }
                              })
                            }else {
                              _this.$message({
                                message: "用户名长度不能超过8位",
                                type: 'error',
                              })
                            }
                          } else {
                            _this.$message({
                              message: "确认密码与设置及密码不相同",
                              type: 'error',
                            })
                          }
                        } else {
                          _this.$message({
                            message: "请先上传头像",
                            type: 'error',
                          })
                        }
                      }else {
                        _this.$message({
                          message: "请正确输入手机号",
                          type: 'error',
                        })
                      }
                    }else {
                      _this.$message({
                        message: "常住地址不能为空",
                        type: 'error',
                      })

                    }
                  }else{
                    _this.$message({
                      message: "密码必须同时包含字母和数字",
                      type: 'error',
                    })
                  }
              }else{
                  _this.$message({
                    message: "密码长度不能小于6位",
                    type: 'error',
                  })
                }

              }


        }
    }
}
</script>
