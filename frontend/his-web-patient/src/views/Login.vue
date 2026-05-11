<template>
  <div class="page login-page">
    <header class="page-header">
      <h1>患者登录</h1>
    </header>

    <div class="login-container">
      <div class="login-logo">
        <div class="logo-icon">
          <MedicineBoxOutlined :style="{ fontSize: '48px', color: '#1890ff' }" />
        </div>
        <h2>HIS 医疗信息系统</h2>
        <p>患者端</p>
      </div>

      <a-card class="login-card">
        <a-form
          :model="formState"
          layout="vertical"
          autocomplete="off"
        >
          <a-form-item label="手机号">
            <a-input
              v-model:value="formState.phone"
              placeholder="请输入手机号"
              size="large"
              :maxlength="11"
            >
              <template #prefix>
                <PhoneOutlined />
              </template>
            </a-input>
          </a-form-item>

          <a-form-item label="密码">
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
            <a-button type="primary" block size="large" :loading="loading" @click="handleLogin">
              登录
            </a-button>
          </a-form-item>
        </a-form>

        <div class="login-extra">
          <a-button type="link" block @click="$router.push('/registration')">
            还没有账号？在线挂号注册
          </a-button>
        </div>
      </a-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { message } from 'ant-design-vue'
import {
  MedicineBoxOutlined,
  PhoneOutlined,
  LockOutlined,
} from '@ant-design/icons-vue'

const formState = reactive({
  phone: '',
  password: '',
})
const loading = ref(false)

const handleLogin = () => {
  if (!formState.phone || !formState.password) {
    message.warning('请输入手机号和密码')
    return
  }
  loading.value = true
  // TODO: 接入登录接口
  setTimeout(() => {
    loading.value = false
    message.success('登录成功')
  }, 1000)
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.page-header {
  padding: 16px;
  color: #fff;
}

.page-header h1 {
  font-size: 20px;
  font-weight: 600;
}

.login-container {
  padding: 24px 24px 0;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.login-logo {
  text-align: center;
  margin-bottom: 32px;
}

.logo-icon {
  margin-bottom: 16px;
}

.login-logo h2 {
  font-size: 22px;
  color: #fff;
  font-weight: 600;
}

.login-logo p {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.8);
  margin-top: 4px;
}

.login-card {
  width: 100%;
  border-radius: 12px;
}

.login-extra {
  text-align: center;
  padding: 8px 0;
}
</style>
