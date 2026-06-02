<template>
  <div class="login-page">
    <div class="login-container">
      <div class="login-header">
        <div class="logo-icon">
          <MedicineBoxOutlined :style="{ fontSize: '48px', color: '#1890ff' }" />
        </div>
        <h1>HIS 医疗信息系统</h1>
        <p>管理端</p>
      </div>

      <a-card class="login-card">
        <a-form
          :model="formState"
          :rules="rules"
          layout="vertical"
          autocomplete="off"
          @finish="handleLogin"
        >
          <a-form-item label="用户名" name="username">
            <a-input
              v-model:value="formState.username"
              placeholder="请输入用户名"
              size="large"
            >
              <template #prefix>
                <UserOutlined />
              </template>
            </a-input>
          </a-form-item>

          <a-form-item label="密码" name="password">
            <a-input-password
              v-model:value="formState.password"
              placeholder="请输入密码"
              size="large"
            >
              <template #prefix>
                <LockOutlined />
              </template>
            </a-input-password>
          </a-form-item>

          <a-form-item>
            <a-button type="primary" block size="large" :loading="loading" html-type="submit">
              登录
            </a-button>
          </a-form-item>
        </a-form>

        <div class="login-tips">
          <a-alert message="测试账号: admin / admin123" type="info" show-icon />
        </div>
      </a-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { message } from 'ant-design-vue'
import {
  MedicineBoxOutlined,
  UserOutlined,
  LockOutlined,
} from '@ant-design/icons-vue'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const authStore = useAuthStore()

const formState = reactive({
  username: '',
  password: '',
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
}

const loading = ref(false)

const handleLogin = async () => {
  loading.value = true
  try {
    await authStore.login({
      username: formState.username,
      password: formState.password,
    })
    message.success('登录成功')
    router.push('/')
  } catch (error: any) {
    message.error(error.message || '登录失败')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-container {
  width: 100%;
  max-width: 420px;
  padding: 0 24px;
}

.login-header {
  text-align: center;
  margin-bottom: 32px;
}

.logo-icon {
  margin-bottom: 16px;
}

.login-header h1 {
  font-size: 28px;
  color: #fff;
  font-weight: 600;
  margin-bottom: 8px;
}

.login-header p {
  font-size: 16px;
  color: rgba(255, 255, 255, 0.85);
}

.login-card {
  border-radius: 12px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
}

.login-tips {
  margin-top: 16px;
}
</style>
