<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-left">
        <div class="overlay"></div>
        <img src="@/assets/travel-bg.jpg" alt="旅游背景" />
        <div class="left-content">
          <h1>旅游管理系统</h1>
          <p>探索世界的美好，从这里开始</p>
        </div>
      </div>
      <div class="login-right">
        <el-card class="login-card">
          <div class="login-header">
            <i class="el-icon-platform-eleme"></i>
            <h2>管理员登录</h2>
            <p class="welcome-text">欢迎回来，请登录您的账号</p>
          </div>
          <el-form :model="loginForm" :rules="rules" ref="loginFormRef">
            <el-form-item prop="username">
              <el-input
                v-model="loginForm.username"
                placeholder="请输入管理员账号"
                prefix-icon="User"
                class="custom-input"
              />
            </el-form-item>
            <el-form-item prop="pwd">
              <el-input
                v-model="loginForm.pwd"
                type="password"
                placeholder="请输入密码"
                prefix-icon="Lock"
                show-password
                class="custom-input"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleLogin" class="login-button">
                登 录
              </el-button>
            </el-form-item>
          </el-form>
          <div class="login-footer">
            <p>智慧旅游 · 用心服务</p>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()
const loginFormRef = ref(null)

const loginForm = reactive({
  username: '',
  pwd: ''
})

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  pwd: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
}

const handleLogin = () => {
  loginFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        await userStore.login(loginForm)
        ElMessage.success('登录成功')
        nextTick(() => {
          router.push('/admin/dashboard')
        })
      } catch (error) {
        console.error('登录失败:', error)
        ElMessage.error(error.message || '登录失败')
      }
    }
  })
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #1e9fff0d 0%, #4CAF500d 100%);
  position: relative;
  overflow: hidden;
}

.login-box {
  display: flex;
  width: 1000px;
  height: 600px;
  background: #fff;
  border-radius: 20px;
  overflow: hidden;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
}

.login-left {
  flex: 1.2;
  position: relative;
  overflow: hidden;
}

.login-left img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(135deg, rgba(30, 159, 255, 0.8) 0%, rgba(76, 175, 80, 0.8) 100%);
  z-index: 1;
}

.left-content {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
  color: white;
  z-index: 2;
}

.left-content h1 {
  font-size: 2.5em;
  margin-bottom: 20px;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
}

.left-content p {
  font-size: 1.2em;
  opacity: 0.9;
}

.login-right {
  width: 450px;
  padding: 40px;
  display: flex;
  align-items: center;
}

.login-card {
  width: 100%;
  border: none;
  box-shadow: none;
}

.login-header {
  text-align: center;
  margin-bottom: 40px;
}

.login-header h2 {
  color: #333;
  font-size: 28px;
  margin: 20px 0 10px;
}

.welcome-text {
  color: #666;
  font-size: 14px;
  margin-top: 10px;
}

.custom-input :deep(.el-input__wrapper) {
  border-radius: 8px;
  height: 45px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
}

.custom-input :deep(.el-input__wrapper:hover) {
  box-shadow: 0 2px 8px rgba(30, 159, 255, 0.2);
}

.login-button {
  width: 100%;
  height: 45px;
  font-size: 16px;
  border-radius: 8px;
  background: linear-gradient(to right, #1e9fff, #4CAF50);
  border: none;
  transition: transform 0.2s ease;
}

.login-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(30, 159, 255, 0.3);
}

.login-footer {
  text-align: center;
  margin-top: 30px;
  color: #999;
  font-size: 14px;
}

/* 添加一些动画效果 */
.login-box {
  animation: fadeIn 0.8s ease;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 响应式设计 */
@media (max-width: 1024px) {
  .login-box {
    width: 90%;
    height: auto;
    flex-direction: column;
  }

  .login-left {
    height: 200px;
  }

  .login-right {
    width: 100%;
    padding: 20px;
  }
}
</style>
