<template>
  <el-container class="admin-container">
    <!-- 侧边栏 -->
    <el-aside :width="isCollapse ? '64px' : '200px'">
      <div class="logo" :class="{ 'collapsed': isCollapse }">
        {{ isCollapse ? '旅游' : '旅游管理系统' }}
      </div>
      <el-menu
        :router="true"
        :default-active="$route.path"
        :collapse="isCollapse"
        :collapse-transition="true"
        class="el-menu-vertical"
        background-color="#304156"
        text-color="#fff"
        active-text-color="#409EFF"
      >
        <el-menu-item index="/admin/dashboard">
          <el-icon><Monitor /></el-icon>
          <span>控制台</span>
        </el-menu-item>

        <!-- 景点管理 -->
        <el-sub-menu index="1">
          <template #title>
            <el-icon><Location /></el-icon>
            <span>景点管理</span>
          </template>
          <el-menu-item index="/admin/scenic/list">景点列表</el-menu-item>
          <el-menu-item index="/admin/scenic/category">景点分类</el-menu-item>
        </el-sub-menu>

        <!-- 美食管理 -->
        <el-sub-menu index="2">
          <template #title>
            <el-icon><Food /></el-icon>
            <span>美食管理</span>
          </template>
          <el-menu-item index="/admin/food/list">美食列表</el-menu-item>
          <el-menu-item index="/admin/food/category">美食分类</el-menu-item>
        </el-sub-menu>

        <!-- 线路管理 -->
        <el-sub-menu index="3">
          <template #title>
            <el-icon><Guide /></el-icon>
            <span>线路管理</span>
          </template>
          <el-menu-item index="/admin/route/list">线路列表</el-menu-item>
          <el-menu-item index="/admin/route/order">线路预订</el-menu-item>
        </el-sub-menu>

        <!-- 新闻管理 -->
        <el-sub-menu index="4">
          <template #title>
            <el-icon><Document /></el-icon>
            <span>新闻管理</span>
          </template>
          <el-menu-item index="/admin/news/list">新闻列表</el-menu-item>
          <el-menu-item index="/admin/news/category">新闻分类</el-menu-item>
        </el-sub-menu>

        <!-- 用户管理 -->
        <el-menu-item index="/admin/user">
          <el-icon><User /></el-icon>
          <span>用户管理</span>
        </el-menu-item>

        <!-- 留言管理 -->
        <el-menu-item index="/admin/message">
          <el-icon><ChatLineRound /></el-icon>
          <span>留言管理</span>
        </el-menu-item>

        <!-- 轮播图管理 -->
        <el-menu-item index="/admin/banner">
          <el-icon><Picture /></el-icon>
          <span>轮播图管理</span>
        </el-menu-item>

        <!-- 友情链接 -->
        <el-menu-item index="/admin/link">
          <el-icon><Link /></el-icon>
          <span>友情链接</span>
        </el-menu-item>

      </el-menu>
    </el-aside>

    <!-- 主要内容区 -->
    <el-container>
      <el-header>
        <div class="header-left">
          <el-icon class="fold-btn" @click="toggleCollapse">
            <Fold v-if="!isCollapse" />
            <Expand v-else />
          </el-icon>
          <!-- <breadcrumb /> -->
        </div>
        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <span class="el-dropdown-link">
              {{ userStore.userInfo?.username || '管理员' }}
              <el-icon class="el-icon--right"><arrow-down /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">个人信息</el-dropdown-item>
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'
import {
  Monitor,
  Location,
  Food,
  Guide,
  Document,
  User,
  ChatLineRound,
  Picture,
  Link,
  ArrowDown,
  Fold,
  Expand
} from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()
const isCollapse = ref(false)

const toggleCollapse = () => {
  isCollapse.value = !isCollapse.value
}

const handleCommand = async (command) => {
  switch (command) {
    case 'profile':
      router.push('/admin/profile')
      break
    case 'logout':
      try {
        // 先清除本地状态
        localStorage.removeItem('token')
        userStore.token = ''
        userStore.userInfo = null

        // 再调用后端接口
        await userStore.logout()

        ElMessage.success('退出成功')
        router.replace('/admin/login')
      } catch (error) {
        console.error('退出失败:', error)
        router.replace('/admin/login')
      }
      break
  }
}
</script>

<style scoped>
.admin-container {
  height: 100vh;
}

.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  font-size: 20px;
  font-weight: bold;
  color: #fff;
  background-color: #2b2f3a;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  transition: all 0.3s;
}

.logo.collapsed {
  padding: 0 10px;
}

.el-aside {
  background-color: #304156;
  transition: width 0.3s ease;
  .el-menu {
    border-right: none;
  }
}

.el-header {
  background-color: #fff;
  border-bottom: 1px solid #ddd;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
}

.header-left {
  display: flex;
  align-items: center;
  .fold-btn {
    font-size: 20px;
    cursor: pointer;
    margin-right: 20px;
    color: #333;
    transition: transform 0.3s;
  }
}

.header-left:hover .fold-btn {
  color: #409EFF;
}

.header-right {
  .el-dropdown-link {
    cursor: pointer;
    display: flex;
    align-items: center;
  }
}

.el-main {
  background-color: #f0f2f5;
  padding: 20px;
}
</style>
