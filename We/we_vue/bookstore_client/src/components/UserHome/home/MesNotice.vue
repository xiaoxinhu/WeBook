<template>
  <div class="content">
    <h1>消息通知</h1>
    
    <div class="box_info">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="全部消息" name="first">
         <div class="tab_box" v-show="total<1">
            <p class="noMesInfo" v-show="true">暂无数据</p>
          </div>
          <div class="tab_box" v-show="total>0">
            <div class="message_list" v-for="info in messageList">
              <i class="el-icon-message icon_message"  size="medium"></i>
              <p>
               <span class="caption-info">
                {{info.message}}
               </span>
                <span style="float: right">
                  <el-button
                      size="medium"
                      type="danger"
                      class="del"
                      @click="delMessage(info.id)"
                    >删除信息
                  </el-button> 
                </span>
              </p>
              <p class="message_date">
                {{info.date}}
              </p>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="物流动态" name="second">
          <div class="tab_box">
            <p class="noMesInfo">暂无数据</p>
          </div>
        </el-tab-pane>
        <el-tab-pane label="特惠活动" name="third">
          <div class="tab_box">
            <p class="noMesInfo">暂无数据</p>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div> 
  </div>
</template>

<script>
import {reqGetMessageList,reqDelMessage} from "../../../api/message";
    // <!--消息通知页面-->
    export default {
        name: "MesNotice",
        data() {
            return {
                activeName: 'first',
                total: 20,
                messageList:[{
                  id: null,
                  account: null,
                  message: null,
                  isRead: null,
                  date: null,
                }
                ]
            };
        },
        methods: {
            handleClick(tab, event) {
                console.log(tab, event);
            },
            getMessageList(){
              let account= this.$store.getters.getUser.account;
               reqGetMessageList(account).then(response=>{
                    if(response.code==200){
                        this.total = response.total;
                        console.log(this.total);
                        this.messageList = response.messageList;
                        console.log("0------0");
                        console.log(this.messageList);
                    }else {
                        this.$message({
                            message: response.message,
                            type: "warning"
                        })
                    }
                }).catch(err=>{
                    this.$message({
                        message: "获取用户消息出错了",
                        type: "warning"
                    })
                })
            },
            delMessage(id) {
                 this.$confirm('是否要进行删除操作?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    reqDelMessage(id).then(response=>{
                        console.log(response);
                        if(response.code==200){
                            this.$message({
                                message: response.message,
                                type: "success"
                            })
                            this.getMessageList();
                        }else{
                            this.$message({
                                message: response.message,
                                type: "warning"
                            })
                        }
                        this.GetSort(this.currentPage,this.page_size);
                    }).catch(err=>{
                        console.log(err);
                    })
                }).catch((err)=>{
                    console.log(err);
                });
            }
        },
        created() {
              console.log("****************");
              this.getMessageList();
              console.log("****************");
            },
    }
</script>

<style scoped>
.content{
  margin: 10px auto;
  width:1000px;
  padding: 30px 20px;
  background-color: white;
}
  h1{
    color: #757575;
    font-family: 新宋体;
  }
  .box_info{
    width: 960px;
    margin: 10px auto;
  }
::v-deep .el-tabs__item {
  height: 50px;
  line-height: 50px;
  font-size: 16px;
  color: #757575;
}
  .tab_box{
    width:960px;
  }

  .noMesInfo{
    text-align: center;
    font-size: 18px;
    color: #757575;
    line-height: 60px
  }

  .caption-info{
    font-size: 20px;
    color: #525252;
  }

   .message_list{
    border: 1px #f3f0e9 solid;
    margin: 12px auto;
    padding-left: 30px;
    width: 960px;
    height: 120px;
    max-height: 240px;
    line-height: 35px;
    border: 1px solid #cacaca;
  }
  .message_date {
    font-size: 12px;
    color: #757575;
  }
  .del {
    margin-right: 15px;
  }
  
</style>
