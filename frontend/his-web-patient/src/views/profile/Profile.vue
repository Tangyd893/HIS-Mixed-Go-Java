<template>
  <div class="page profile-page">
    <header class="page-header">
      <a-button type="text" size="small" @click="$router.back()">
        <LeftOutlined />
      </a-button>
      <h1>个人中心</h1>
      <div></div>
    </header>

    <div class="content">
      <a-card class="user-card">
        <div class="user-info">
          <a-avatar :size="64" icon="user" />
          <div class="user-detail">
            <span class="user-name" v-if="isLoggedIn">患者姓名</span>
            <span class="user-name" v-else>未登录</span>
            <span class="user-label" @click="$router.push('/login')" v-if="!isLoggedIn">点击登录</span>
            <a-tag v-else color="green" size="small">已认证</a-tag>
          </div>
        </div>
      </a-card>

      <a-card class="menu-card" title="账户信息">
        <a-list :split="true">
          <a-list-item>
            <a-list-item-meta title="姓名" description="张三" />
          </a-list-item>
          <a-list-item>
            <a-list-item-meta title="手机号" description="138****8888" />
          </a-list-item>
          <a-list-item>
            <a-list-item-meta title="身份证号" description="320***********1234" />
          </a-list-item>
          <a-list-item>
            <a-list-item-meta title="性别" description="男" />
          </a-list-item>
          <a-list-item>
            <a-list-item-meta title="年龄" description="35岁" />
          </a-list-item>
        </a-list>
      </a-card>

      <a-card class="menu-card" title="常用功能">
        <a-list :split="true">
          <a-list-item v-for="item in menuItems" :key="item.label" @click="$router.push(item.path)">
            <a-list-item-meta>
              <template #title>{{ item.label }}</template>
              <template #avatar>
                <component :is="item.icon" :style="{ fontSize: '20px', color: '#1890ff' }" />
              </template>
            </a-list-item-meta>
            <template #extra>
              <RightOutlined :style="{ color: '#ccc' }" />
            </template>
          </a-list-item>
        </a-list>
      </a-card>

      <div class="button-area">
        <a-button v-if="isLoggedIn" type="primary" danger block size="large" @click="handleLogout">
          退出登录
        </a-button>
        <a-button v-else type="primary" block size="large" @click="$router.push('/login')">
          登录 / 注册
        </a-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { message } from 'ant-design-vue'
import {
  LeftOutlined,
  RightOutlined,
  CalendarOutlined,
  MedicineBoxOutlined,
  DollarOutlined,
  ExperimentOutlined,
  HeartOutlined,
} from '@ant-design/icons-vue'

const isLoggedIn = ref(true)

const menuItems = [
  { label: '我的挂号', path: '/appointments', icon: CalendarOutlined },
  { label: '我的处方', path: '/prescriptions', icon: MedicineBoxOutlined },
  { label: '费用查询', path: '/bills', icon: DollarOutlined },
  { label: '检查报告', path: '/reports', icon: ExperimentOutlined },
  { label: '我的随访', path: '/followup', icon: HeartOutlined },
]

const handleLogout = () => {
  message.success('已退出登录')
  isLoggedIn.value = false
}
</script>

<style scoped>
.profile-page {
  min-height: 100vh;
  background: #f5f5f5;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 16px;
  background: #1890ff;
  border-bottom: 1px solid #f0f0f0;
}

.page-header h1 {
  font-size: 18px;
  font-weight: 600;
  color: #fff;
}

.page-header > div {
  width: 40px;
}

.content {
  padding: 16px;
}

.user-card {
  border-radius: 8px;
  margin-bottom: 16px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 16px;
}

.user-detail {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.user-name {
  font-size: 18px;
  font-weight: 600;
  color: #333;
}

.user-label {
  font-size: 13px;
  color: #1890ff;
  cursor: pointer;
}

.menu-card {
  border-radius: 8px;
  margin-bottom: 16px;
}

.button-area {
  padding: 16px 0;
}
</style>
