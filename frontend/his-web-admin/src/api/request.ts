import axios from 'axios'
import { message } from 'ant-design-vue'

const request = axios.create({
  baseURL: '/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
})

request.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('admin_token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  },
)

request.interceptors.response.use(
  (response) => {
    return response.data
  },
  (error) => {
    if (error.response) {
      const { status } = error.response
      if (status === 401) {
        localStorage.removeItem('admin_token')
        localStorage.removeItem('admin_userInfo')
        window.location.href = '/login'
      } else if (status === 403) {
        message.error('没有权限执行此操作')
      } else {
        message.error(error.response.data?.message || '请求失败')
      }
    } else {
      message.error('网络异常，请稍后重试')
    }
    return Promise.reject(error)
  },
)

export default request
