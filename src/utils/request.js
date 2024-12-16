import axios from 'axios'

const request = axios.create({
  baseURL: 'http://localhost:8081',
  timeout: 5000
})

// 请求拦截器
request.interceptors.request.use(
  config => {
    // 如果有token，添加到请求头
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.token = token
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
    // 如果返回的状态码不是200，说明出错了
    if (res.code !== '200') {
      return Promise.reject(new Error(res.msg || '操作失败'))
    }
    return res
  },
  error => {
    return Promise.reject(error)
  }
)

export default request
