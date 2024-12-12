import axios from 'axios'
import { ElMessage } from 'element-plus'

// 创建axios实例
const request = axios.create({
  baseURL: 'http://localhost:8081',
  timeout: 5000
})

// 请求拦截器
request.interceptors.request.use(
  config => {
    // 从localStorage获取token
    const token = localStorage.getItem('token')
    if (token) {
      config.headers['token'] = token
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  response => {
    const res = response.data
    console.log('完整响应:', response)
    console.log('响应数据:', res)
    console.log('数据类型:', typeof res, Array.isArray(res?.data))

    // 成功状态
    if (res.code === '200' || res.code === 200) {
      return res
    }

    // 401: 未登录或token过期
    if (res.code === '401' || res.code === 401) {
      localStorage.removeItem('token')
      window.location.href = '/login'
      ElMessage.error('登录已过期，请重新登录')
      return Promise.reject(new Error('登录已过期，请重新登录'))
    }

    // 其他错误情况
    ElMessage.error(res.msg || '操作失败')
    return Promise.reject(new Error(res.msg || '操作失败'))
  },
  error => {
    console.log('网络错误:', error)
    ElMessage.error('网络请求失败，请稍后重试')
    return Promise.reject(error)
  }
)

export default request
