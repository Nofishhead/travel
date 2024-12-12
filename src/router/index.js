import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import AdminLayout from '@/views/layout/AdminLayout.vue'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/admin',
    component: AdminLayout,
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/admin/DashboardView.vue')
      },
      {
        path: 'scenic/list',
        component: () => import('@/views/admin/scenic/ListView.vue'),
        meta: { title: '景点列表' }
      },
      {
        path: 'scenic/category',
        component: () => import('@/views/admin/scenic/CategoryView.vue'),
        meta: { title: '景点分类' }
      },
      {
        path: 'food/list',
        component: () => import('@/views/admin/food/ListView.vue'),
        meta: { title: '美食列表' }
      },
      {
        path: 'food/category',
        component: () => import('@/views/admin/food/CategoryView.vue'),
        meta: { title: '美食分类' }
      },
      {
        path: 'route/list',
        component: () => import('@/views/admin/route/ListView.vue'),
        meta: { title: '线路列表' }
      },
      {
        path: 'route/order',
        component: () => import('@/views/admin/route/OrderView.vue'),
        meta: { title: '线路预订' }
      },
      {
        path: 'news/list',
        component: () => import('@/views/admin/news/ListView.vue'),
        meta: { title: '新闻列表' }
      },
      {
        path: 'news/category',
        component: () => import('@/views/admin/news/CategoryView.vue'),
        meta: { title: '新闻分类' }
      },
      {
        path: 'user',
        component: () => import('@/views/admin/user/ListView.vue'),
        meta: { title: '用户管理' }
      },
      {
        path: 'message',
        component: () => import('@/views/admin/message/ListView.vue'),
        meta: { title: '留言管理' }
      },
      {
        path: 'banner',
        component: () => import('@/views/admin/banner/ListView.vue'),
        meta: { title: '轮播图管理' }
      },
      {
        path: 'link',
        component: () => import('@/views/admin/link/ListView.vue'),
        meta: { title: '友情链接' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path !== '/login' && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
