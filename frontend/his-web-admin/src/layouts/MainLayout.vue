<template>
  <a-layout class="admin-layout">
    <a-layout-sider
      v-model:collapsed="collapsed"
      :trigger="null"
      collapsible
      theme="dark"
      width="220"
    >
      <div class="logo">
        <MedicineBoxOutlined />
        <span v-if="!collapsed">HIS 管理端</span>
      </div>
      <a-menu
        v-model:selectedKeys="selectedKeys"
        v-model:openKeys="openKeys"
        mode="inline"
        theme="dark"
        @click="handleMenuClick"
      >
        <a-menu-item key="/dashboard">
          <template #icon><DashboardOutlined /></template>
          <span>工作台</span>
        </a-menu-item>

        <a-sub-menu key="business">
          <template #icon><AppstoreOutlined /></template>
          <template #title>业务管理</template>
          <a-menu-item key="/doctors">
            <template #icon><UserOutlined /></template>
            <span>医生管理</span>
          </a-menu-item>
          <a-menu-item key="/schedules">
            <template #icon><CalendarOutlined /></template>
            <span>排班管理</span>
          </a-menu-item>
          <a-menu-item key="/drugs">
            <template #icon><MedicineBoxOutlined /></template>
            <span>药品管理</span>
          </a-menu-item>
          <a-menu-item key="/patients">
            <template #icon><TeamOutlined /></template>
            <span>患者管理</span>
          </a-menu-item>
        </a-sub-menu>

        <a-sub-menu key="system">
          <template #icon><SettingOutlined /></template>
          <template #title>系统管理</template>
          <a-menu-item key="/system/departments">
            <template #icon><BankOutlined /></template>
            <span>科室管理</span>
          </a-menu-item>
          <a-menu-item key="/system/users">
            <template #icon><UserSwitchOutlined /></template>
            <span>用户管理</span>
          </a-menu-item>
        </a-sub-menu>
      </a-menu>
    </a-layout-sider>

    <a-layout>
      <a-layout-header class="admin-header">
        <div class="header-left">
          <a-button
            type="text"
            :icon="collapsed ? MenuUnfoldOutlined : MenuFoldOutlined"
            @click="collapsed = !collapsed"
          />
          <a-breadcrumb class="breadcrumb">
            <a-breadcrumb-item v-for="item in breadcrumbs" :key="item">
              {{ item }}
            </a-breadcrumb-item>
          </a-breadcrumb>
        </div>

        <div class="header-right">
          <a-dropdown>
            <div class="user-info">
              <a-avatar :style="{ backgroundColor: '#1890ff' }">
                {{ authStore.realName?.charAt(0) || 'A' }}
              </a-avatar>
              <span class="username">{{ authStore.realName || authStore.username }}</span>
            </div>
            <template #overlay>
              <a-menu @click="handleUserMenuClick">
                <a-menu-item key="profile">
                  <UserOutlined />
                  <span>个人中心</span>
                </a-menu-item>
                <a-menu-divider />
                <a-menu-item key="logout">
                  <LogoutOutlined />
                  <span>退出登录</span>
                </a-menu-item>
              </a-menu>
            </template>
          </a-dropdown>
        </div>
      </a-layout-header>

      <a-layout-content class="admin-content">
        <router-view v-slot="{ Component }">
          <transition name="fade" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script setup lang="ts">
import { ref, computed, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { message } from 'ant-design-vue'
import {
  MedicineBoxOutlined,
  DashboardOutlined,
  AppstoreOutlined,
  UserOutlined,
  CalendarOutlined,
  TeamOutlined,
  SettingOutlined,
  BankOutlined,
  UserSwitchOutlined,
  MenuFoldOutlined,
  MenuUnfoldOutlined,
  LogoutOutlined,
} from '@ant-design/icons-vue'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const route = useRoute()
const authStore = useAuthStore()

const collapsed = ref(false)
const selectedKeys = ref<string[]>([route.path])
const openKeys = ref<string[]>(['business'])

const breadcrumbs = computed(() => {
  const titles: string[] = []
  if (route.matched) {
    route.matched.forEach((item) => {
      if (item.meta?.title) {
        titles.push(item.meta.title as string)
      }
    })
  }
  return titles
})

watch(
  () => route.path,
  (path) => {
    selectedKeys.value = [path]
    if (path.startsWith('/system')) {
      openKeys.value = ['system']
    } else if (path.startsWith('/doctors') || path.startsWith('/schedules') || path.startsWith('/drugs') || path.startsWith('/patients')) {
      openKeys.value = ['business']
    }
  },
)

const handleMenuClick = ({ key }: { key: string }) => {
  router.push(key)
}

const handleUserMenuClick = ({ key }: { key: string }) => {
  if (key === 'logout') {
    authStore.logout()
    message.success('已退出登录')
    router.push('/login')
  } else if (key === 'profile') {
    router.push('/profile')
  }
}
</script>

<style scoped>
.admin-layout {
  min-height: 100vh;
}

.logo {
  height: 64px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  color: #fff;
  font-size: 18px;
  font-weight: 600;
  background: rgba(255, 255, 255, 0.05);
}

.logo .anticon {
  font-size: 24px;
}

.admin-header {
  background: #fff;
  padding: 0 24px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.08);
}

.header-left {
  display: flex;
  align-items: center;
  gap: 16px;
}

.breadcrumb {
  margin-left: 8px;
}

.header-right {
  display: flex;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  padding: 0 12px;
  transition: background 0.3s;
}

.user-info:hover {
  background: rgba(0, 0, 0, 0.025);
}

.username {
  font-size: 14px;
  color: rgba(0, 0, 0, 0.85);
}

.admin-content {
  margin: 24px;
  padding: 24px;
  background: #fff;
  border-radius: 8px;
  min-height: 280px;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
