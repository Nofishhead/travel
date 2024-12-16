<template>
  <el-container class="layout-container">
    <el-aside width="200px">
      <div class="logo">旅游网站管理系统</div>
      <el-menu
        :default-active="route.path"
        router
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
      >
        <template v-for="item in menuList" :key="item.path">
          <el-sub-menu v-if="item.children" :index="item.path">
            <template #title>
              <el-icon><component :is="item.meta.icon" /></el-icon>
              <span>{{ item.meta.title }}</span>
            </template>
            <el-menu-item
              v-for="child in item.children"
              :key="child.path"
              :index="child.path"
            >
              {{ child.meta.title }}
            </el-menu-item>
          </el-sub-menu>
          <el-menu-item v-else :index="item.path">
            <el-icon><component :is="item.meta.icon" /></el-icon>
            <template #title>{{ item.meta.title }}</template>
          </el-menu-item>
        </template>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div class="header-left">
          <el-icon class="toggle-btn" @click="toggleCollapse">
            <Expand v-if="isCollapse" />
            <Fold v-else />
          </el-icon>
        </div>
        <div class="header-right">
          <UserInfo ref="userInfoRef" />
        </div>
      </el-header>
      <el-main>
        <router-view v-slot="{ Component }">
          <component :is="Component" />
        </router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref } from 'vue'
import { useRoute } from 'vue-router'
import menuList from '@/config/menu'
import { Expand, Fold } from '@element-plus/icons-vue'
import UserInfo from '@/components/UserInfo.vue'

const route = useRoute()
const isCollapse = ref(false)
const userInfoRef = ref(null)

const toggleCollapse = () => {
  isCollapse.value = !isCollapse.value
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  color: #fff;
  font-size: 16px;
  background-color: #2b2f3a;
}

.el-aside {
  background-color: #304156;
}

.el-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #304156;
  padding: 0 20px;
}

.header-left {
  display: flex;
  align-items: center;
}

.header-right {
  margin-left: auto;
}

.toggle-btn {
  font-size: 20px;
  cursor: pointer;
  color: #fff;
}

.el-menu {
  border-right: none;
}

:deep(.el-menu-item.is-active) {
  background-color: #263445 !important;
}

.el-menu-item [class^="el-icon"] {
  margin-right: 5px;
  width: 24px;
  text-align: center;
  font-size: 18px;
}

.el-sub-menu [class^="el-icon"] {
  margin-right: 5px;
  width: 24px;
  text-align: center;
  font-size: 18px;
}
</style>
