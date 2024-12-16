<template>
  <el-dropdown trigger="click" @command="handleCommand">
    <div class="user-info">
      <span>{{ userInfo.username }}</span>
      <el-icon class="el-icon--right"><ArrowDown /></el-icon>
    </div>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { ArrowDown } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'
import request from '@/utils/request'

const router = useRouter()
const userInfo = ref({})

// 获取用户信息
const getUserInfo = async () => {
  try {
    const token = localStorage.getItem('token')
    if (!token) {
      router.push('/login')
      return
    }

    const res = await request({
      url: '/travel/admins/info',
      method: 'get',
      headers: {
        token: token
      }
    })
    userInfo.value = res.data
  } catch (error) {
    console.error('获取用户信息失败:', error)
    // 如果获取用户信息失败，直接跳转到登录页
    localStorage.removeItem('token')
    router.push('/login')
  }
}

// 处理下拉菜单命令
const handleCommand = (command) => {
  if (command === 'logout') {
    try {
      request({
        url: '/travel/admins/logout',
        method: 'post',
        headers: {
          token: localStorage.getItem('token')
        }
      })
    } catch (error) {
      console.error('退出登录失败:', error)
    } finally {
      localStorage.removeItem('token')
      router.push('/login')
    }
  }
}

onMounted(() => {
  getUserInfo()
})
</script>

<style scoped>
.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
  color: #fff;
}

.user-info span {
  margin-right: 4px;
}
</style>
