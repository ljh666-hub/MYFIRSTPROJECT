<template>
  <div id="app" style="display: flex;flex-direction: column;z-index: 999">
    <div style="display:flex;height: 72px;line-height: 40px;border-bottom: 8px solid #fac634;position:fixed;width: 100%;margin-left: -20px;top: 70px;background-color: #5e5d5d;z-index: 999">
      <h1 style="padding-left: 200px;font-size: 24px;color:white;">联系客服</h1>
    </div>
    <div style="margin-top: 30px;height:600px;overflow-y: auto;background-color: #f1f0f0" id="content">
      <div style="display: flex;margin-top: 30px">
       <div style="width: 500px"></div>
    </div>



    </div>
   <div style="position:fixed;top: 730px;border-top:1px solid #a5a4a4;width: 92%" >
     <textarea style="height: 180px;width: 82%;text-indent: 30px;font-size: 20px;margin-bottom: -16px" v-model="input"></textarea>
     <el-button type="warning" @click="send">发送</el-button>
   </div>
  </div>
</template>
<script>
import axios from "axios";

export default {
  data(){
    return{
      ws:null,
       userId:0,
       message:{
         "receiveId":0,
         "content":""
       },
      tableData:[],
      input:"",
      history:[]
    }
  },
  mounted() {
    const _this = this
    const content = document.getElementById("content")
    axios.get('http://localhost/users/selectByUserName').then(function (resp) {
      _this.tableData = resp.data
      _this.userId = resp.data.userId
      console.log(_this.userId)
      axios.get(`http://localhost/chat/getChat?sendId=${_this.userId}`).then(function (resp){
        _this.history = resp.data
        for (let i = 0; i < _this.history.length; i++) {
          if(_this.history[i].sendId === _this.userId){
            _this.createMy(_this.history[i].content)
          }else {
            _this.createOthers(_this.history[i].content)
          }
        }
        content.scrollTop = content.scrollHeight
      })

      _this.ws = new WebSocket(`ws://localhost/ws/${_this.userId}`)
      _this.ws.onmessage = function (event) {
        _this.createOthers(JSON.parse(event.data).content)
        content.scrollTop = content.scrollHeight
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
    createOthers (content){
      const div = document.createElement("div")
      const newDiv = document.createElement("div")
      const img = document.createElement("img")

      div.style.marginBottom ="20px"
      div.style.marginTop ="10px"
      div.style.display = "flex"
      div.style.alignItems="center"
      div.style.width = "500px"

      newDiv.style.maxWidth ="300px"
      newDiv.style.backgroundColor= "#d6d5d5"
      newDiv.style.width = "auto"
      newDiv.style.height="auto"
      newDiv.style.paddingLeft ="30px"
      newDiv.style.paddingRight="30px"
      newDiv.style.lineHeight="50px"
      newDiv.style.borderRadius ="8px 8px 8px 8px"
      newDiv.style.fontSize="18px"
      newDiv.style.marginRight ="20px"

      img.src = "http://localhost/images/true.jpg"
      img.style.width ="60px"
      img.style.height ="60px"
      img.style.borderRadius="50%"
      img.style.marginRight="20px"
      img.style.marginLeft="50px"

      newDiv.innerText = content
      div.appendChild(img)
      div.appendChild(newDiv)

      document.getElementById("content").appendChild(div)
    },
    createMy(content) {
      const div = document.createElement("div")
      const newDiv = document.createElement("div")
      const img = document.createElement("img")

      div.style.marginBottom ="20px"
      div.style.marginTop ="10px"
      div.style.display = "flex"
      div.style.alignItems="center"
      div.style.justifyContent="flex-end"
      div.style.marginRight="50px"

      newDiv.style.maxWidth ="300px"
      newDiv.style.backgroundColor= "#fac223"
      newDiv.style.width = "auto"
      newDiv.style.height="auto"
      newDiv.style.paddingLeft ="30px"
      newDiv.style.paddingRight="30px"
      newDiv.style.lineHeight="50px"
      newDiv.style.borderRadius ="8px 8px 8px 8px"
      newDiv.style.fontSize="18px"
      newDiv.style.marginRight ="20px"

      img.src = this.tableData.imageUrl
      img.style.width ="60px"
      img.style.height ="60px"
      img.style.borderRadius="50%"


      newDiv.innerText = content
      div.appendChild(newDiv)
      div.appendChild(img)

      document.getElementById("content").appendChild(div)

    },
    send(){
      const _this = this
      const content = document.getElementById("content")
      if(this.input !== ""){
        _this.createMy(_this.input)
        _this.message.content = _this.input
        _this.ws.send(JSON.stringify(_this.message))
        _this.input = ""
        content.scrollTop = content.scrollHeight
      }
        },
  }
}
</script>