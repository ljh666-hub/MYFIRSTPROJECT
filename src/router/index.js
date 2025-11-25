import Vue from 'vue';
import Router from 'vue-router';
import Sidebar from '@/component/Sidebar.vue';
import myHeader from '@/component/header.vue'
import admin from '@/views/admin.vue';
import pay from '@/views/pay.vue'
import login from '@/views/login.vue'
import afterPay from '@/views/afterPay.vue'
import order from '@/views/order.vue'
import afterSell from "@/views/afterSell.vue";
import detail from "@/views/detail.vue"
import comment from "@/views/comment.vue"
import shoppingCar from "@/views/shoppingCar.vue"
import chat from "@/views/chat.vue";
import mine from "@/views/mine.vue"
import discount from "@/views/discount.vue";
import commentManage from "@/views/commentManage.vue";
Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            name:'login',
            component: login,
            meta:{ requiresLayout:false }
        },
        {
            path: '/admin',
            name:'admin',
            component: admin,
            meta:{ requiresLayout:true,
              keepAlive:false}
        },
        {
            path:'/pay',
            name:'pay',
            component:pay,
            meta:{ requiresLayout:true,
                keepAlive:false}
        },
        {
            path:'/sidebar',
            component:Sidebar,
            meta:{ requiresLayout:true,
                keepAlive:false}
        },
        {
            path:'/header',
            component:myHeader,
            meta:{ requiresLayout:true,
                keepAlive:false}
        },
        {
            path:'/afterPay',
            component:afterPay,
            meta:{ requiresLayout:false,
                keepAlive:false}
        },
        {
            path:'/order',
            component:order,
            meta:{ requiresLayout:true ,
                keepAlive:true}
        },
        {
            path:'/afterSell',
            component:afterSell,
            meta:{ requiresLayout:true,
                keepAlive:false}
        },
        {
            path:'/detail',
            component:detail,
            meta:{ requiresLayout:true,
                keepAlive:false}
        },
        {
            path:'/comment',
            component:comment,
            meta:{ requiresLayout:true,
                keepAlive:false}
        },
        {
            path:'/shoppingCar',
            component:shoppingCar,
            meta:{ requiresLayout:true,
                keepAlive:false}
        },
        {
            path:'/chat',
            component:chat,
            meta:{ requiresLayout:true,
                keepAlive:false}
        },
        {
            path:'/mine',
            component:mine,
            meta:{ requiresLayout:true,
                keepAlive:false}
        },
        {
            path:"/discount",
            component:discount,
            meta:{ requiresLayout:true,
                keepAlive:false}
        },
        {
            path:"/commentManage",
            component:commentManage,
            meta:{ requiresLayout:true,
                keepAlive:false}
        }
    ]
});