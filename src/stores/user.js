import { defineStore } from 'pinia'
import { ref } from 'vue'
import request from '@/utils/request'
import { ElMessage } from 'element-plus'

export const useUserStore = defineStore('user', () => {
  const token = ref('')
  const userInfo = ref(null)

  // 登录
  const login = async (loginForm) => {
    try {
      console.log('登录表单数据:', loginForm)

      const res = await request({
        url: '/travel/admins/login',
        method: 'post',
        data: loginForm
      })

      // 确保登录成功且返回了必要的数据
      if (!res.data || !res.data.token) {
        throw new Error('登录失败：返回数据格式错误')
      }

      // 登录成功，保存数据
      token.value = res.data.token
      userInfo.value = res.data.userInfo
      localStorage.setItem('token', res.data.token)

      return res
    } catch (error) {
      console.error('登录失败:', error)
      throw error // 继续向上抛出错误
    }
  }

  // 退出登录
  const logout = async () => {
    try {
      const currentToken = token.value
      const res = await request({
        url: '/travel/admins/logout',
        method: 'post',
        headers: {
          token: currentToken
        }
      })

      // 不管后端返回什么，都清除本地状态
      localStorage.removeItem('token')
      token.value = ''
      userInfo.value = null

      // 如果后端返回错误，才提示错误信息
      if (res.code !== '200' && res.code !== 200) {
        ElMessage.error(res.msg || '退出失败')
        return Promise.reject(new Error(res.msg || '退出失败'))
      }

      return res
    } catch (error) {
      console.error('退出失败:', error)
      // 即使请求失败，也要清除本地状态
      localStorage.removeItem('token')
      token.value = ''
      userInfo.value = null
      throw error
    }
  }

  return {
    token,
    userInfo,
    login,
    logout
  }
}, {
  persist: true
})
