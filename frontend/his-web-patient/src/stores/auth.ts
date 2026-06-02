import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { authApi, type LoginRequest } from '@/api'

interface UserInfo {
  userId: number
  username: string
  realName: string
  roles: string[]
  permissions: string[]
}

export const useAuthStore = defineStore('auth', () => {
  const token = ref<string>(localStorage.getItem('token') || '')
  const refreshToken = ref<string>(localStorage.getItem('refreshToken') || '')
  const userInfo = ref<UserInfo | null>(null)

  const isLoggedIn = computed(() => !!token.value)
  const userId = computed(() => userInfo.value?.userId)

  function setTokens(accessToken: string, refresh: string) {
    token.value = accessToken
    refreshToken.value = refresh
    localStorage.setItem('token', accessToken)
    localStorage.setItem('refreshToken', refresh)
  }

  function setUserInfo(info: UserInfo) {
    userInfo.value = info
    localStorage.setItem('userInfo', JSON.stringify(info))
  }

  async function login(loginRequest: LoginRequest) {
    const response = await authApi.login(loginRequest)
    setTokens(response.accessToken, response.refreshToken)
    setUserInfo({
      userId: response.userId,
      username: response.username,
      realName: response.realName,
      roles: response.roles,
      permissions: response.permissions,
    })
    return response
  }

  async function refreshUserToken() {
    if (!refreshToken.value) {
      throw new Error('No refresh token')
    }
    const response = await authApi.refreshToken(refreshToken.value)
    setTokens(response.accessToken, response.refreshToken)
    return response
  }

  function logout() {
    token.value = ''
    refreshToken.value = ''
    userInfo.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('refreshToken')
    localStorage.removeItem('userInfo')
  }

  function initFromStorage() {
    const storedUserInfo = localStorage.getItem('userInfo')
    if (storedUserInfo) {
      try {
        userInfo.value = JSON.parse(storedUserInfo)
      } catch (e) {
        localStorage.removeItem('userInfo')
      }
    }
  }

  return {
    token,
    refreshToken,
    userInfo,
    isLoggedIn,
    userId,
    setTokens,
    setUserInfo,
    login,
    refreshUserToken,
    logout,
    initFromStorage,
  }
})
