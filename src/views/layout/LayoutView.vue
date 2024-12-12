<template>
  <el-container class="layout-container">
    <el-aside width="200px">
      <el-menu
        :router="true"
        class="el-menu-vertical"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b"
      >
        <el-menu-item index="/home">
          <el-icon><House /></el-icon>
          <span>首页</span>
        </el-menu-item>
        <!-- 可以根据需要添加更多菜单项 -->
      </el-menu>
    </el-aside>

    <el-container>
      <el-header>
        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <span class="el-dropdown-link">
              管理员
              <el-icon class="el-icon--right"><arrow-down /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
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
import { House, ArrowDown } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()

const handleCommand = async (command) => {
  if (command === 'logout') {
    try {
      await userStore.logout()
      ElMessage.success('退出成功')
      router.push('/login')
    } catch (error) {
      console.error('退出失败:', error)
    }
  }
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.el-header {
  background-color: #fff;
  border-bottom: 1px solid #ddd;
  display: flex;
  align-items: center;
  justify-content: flex-end;
}

.header-right {
  margin-right: 20px;
}

.el-dropdown-link {
  cursor: pointer;
  display: flex;
  align-items: center;
}

.el-menu-vertical {
  height: 100%;
}

.el-main {
  background-color: #f0f2f5;
  padding: 20px;
}
</style>
