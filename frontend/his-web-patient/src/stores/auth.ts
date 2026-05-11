import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

interface PatientInfo {
  id: string
  name: string
  phone: string
  idCard: string
  gender: string
  age: number
}

export const useAuthStore = defineStore('auth', () => {
  const token = ref<string>(localStorage.getItem('token') || '')
  const patientInfo = ref<PatientInfo | null>(null)

  const isLoggedIn = computed(() => !!token.value)

  function setToken(newToken: string) {
    token.value = newToken
    localStorage.setItem('token', newToken)
  }

  function setPatientInfo(info: PatientInfo) {
    patientInfo.value = info
  }

  function logout() {
    token.value = ''
    patientInfo.value = null
    localStorage.removeItem('token')
  }

  return {
    token,
    patientInfo,
    isLoggedIn,
    setToken,
    setPatientInfo,
    logout,
  }
})
