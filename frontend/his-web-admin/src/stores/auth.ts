import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { authApi, type LoginRequest } from '@/api'

interface AdminUserInfo {
  userId: number
  username: string
  realName: string
  roles: string[]
  permissions: string[]
}

export const useAuthStore = defineStore('admin_auth', () => {
  const token = ref<string>(localStorage.getItem('admin_token') || '')
  const refreshToken = ref<string>(localStorage.getItem('admin_refreshToken') || '')
  const userInfo = ref<AdminUserInfo | null>(null)

  const isLoggedIn = computed(() => !!token.value)
  const userId = computed(() => userInfo.value?.userId)
  const username = computed(() => userInfo.value?.username || '')
  const realName = computed(() => userInfo.value?.realName || '')
  const roles = computed(() => userInfo.value?.roles || [])

  function setTokens(accessToken: string, refresh: string) {
    token.value = accessToken
    refreshToken.value = refresh
    localStorage.setItem('admin_token', accessToken)
    localStorage.setItem('admin_refreshToken', refresh)
  }

  function setUserInfo(info: AdminUserInfo) {
    userInfo.value = info
    localStorage.setItem('admin_userInfo', JSON.stringify(info))
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
    localStorage.removeItem('admin_token')
    localStorage.removeItem('admin_refreshToken')
    localStorage.removeItem('admin_userInfo')
  }

  function initFromStorage() {
    const storedUserInfo = localStorage.getItem('admin_userInfo')
    if (storedUserInfo) {
      try {
        userInfo.value = JSON.parse(storedUserInfo)
      } catch (e) {
        localStorage.removeItem('admin_userInfo')
      }
    }
  }

  function hasPermission(permission: string): boolean {
    if (!userInfo.value?.permissions) return false
    return userInfo.value.permissions.includes(permission) || userInfo.value.permissions.includes('*')
  }

  function hasRole(role: string): boolean {
    if (!userInfo.value?.roles) return false
    return userInfo.value.roles.includes(role) || userInfo.value.roles.includes('admin')
  }

  return {
    token,
    refreshToken,
    userInfo,
    isLoggedIn,
    userId,
    username,
    realName,
    roles,
    setTokens,
    setUserInfo,
    login,
    refreshUserToken,
    logout,
    initFromStorage,
    hasPermission,
    hasRole,
  }
})
