<template>
  <div class="home-container">
    <el-card class="welcome-card">
      <template #header>
        <div class="card-header">
          <h3>欢迎回来</h3>
        </div>
      </template>
      <div class="user-info">
        <el-avatar :size="64" icon="UserFilled" />
        <h2>{{ userInfo.username }}</h2>
        <p>{{ userInfo.email }}</p>
        <el-button type="primary" @click="handleLogout">退出登录</el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import request from '@/utils/request'

const router = useRouter()
const userInfo = ref({})

onMounted(() => {
  const storedUserInfo = localStorage.getItem('userInfo')
  if (storedUserInfo) {
    userInfo.value = JSON.parse(storedUserInfo)
  } else {
    router.push('/login')
  }
})

const handleLogout = async () => {
  try {
    const res = await request({
      url: '/travel/yonghu/logout',
      method: 'post',
      headers: {
        token: localStorage.getItem('token')
      }
    })
    if (res.code === '200') {
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
      ElMessage.success('退出成功')
      router.push('/login')
    }
  } catch (error) {
    ElMessage.error('退出失败')
  }
}
</script>

<style scoped>
.home-container {
  padding: 20px;
  min-height: 100vh;
  background-color: #f5f7fa;
}

.welcome-card {
  max-width: 600px;
  margin: 40px auto;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.user-info {
  text-align: center;
  padding: 20px;
}

.user-info h2 {
  margin: 15px 0;
  color: #333;
}

.user-info p {
  color: #666;
  margin-bottom: 20px;
}
</style>
