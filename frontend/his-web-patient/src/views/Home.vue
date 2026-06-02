<template>
  <div class="page home-page">
    <header class="page-header">
      <h1>健康档案</h1>
      <a-button type="link" @click="$router.push('/profile')">
        <UserOutlined />
      </a-button>
    </header>

    <div class="greeting-card">
      <a-card>
        <div class="greeting-content">
          <a-avatar :size="56" icon="user" />
          <div class="greeting-text">
            <span class="greeting-label">早上好</span>
            <span class="greeting-name">点击登录</span>
          </div>
        </div>
      </a-card>
    </div>

    <div class="quick-actions">
      <a-row :gutter="[12, 12]">
        <a-col :span="8" v-for="item in quickActions" :key="item.path">
          <a-card hoverable class="action-card" @click="$router.push(item.path)">
            <div class="action-icon">
              <component :is="item.icon" :style="{ fontSize: '28px', color: item.color }" />
            </div>
            <span class="action-text">{{ item.label }}</span>
          </a-card>
        </a-col>
      </a-row>
    </div>

    <div class="section">
      <a-card title="健康概览">
        <a-empty description="暂无数据，请先登录" />
      </a-card>
    </div>

    <div class="section">
      <a-card title="快捷入口">
        <a-list :split="false">
          <a-list-item v-for="item in shortcuts" :key="item.path" @click="$router.push(item.path)">
            <template #extra>
              <RightOutlined :style="{ color: '#ccc' }" />
            </template>
            <a-list-item-meta>
              <template #title>{{ item.title }}</template>
              <template #avatar>
                <component :is="item.icon" :style="{ fontSize: '20px', color: item.color }" />
              </template>
            </a-list-item-meta>
          </a-list-item>
        </a-list>
      </a-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import {
  UserOutlined,
  RightOutlined,
  FormOutlined,
  CalendarOutlined,
  MedicineBoxOutlined,
  FileTextOutlined,
  DollarOutlined,
  ExperimentOutlined,
  HeartOutlined,
  MessageOutlined,
} from '@ant-design/icons-vue'

const quickActions = [
  { label: '在线挂号', path: '/registration', icon: FormOutlined, color: '#1890ff' },
  { label: '我的挂号', path: '/appointments', icon: CalendarOutlined, color: '#52c41a' },
  { label: '在线问诊', path: '/consultation', icon: MessageOutlined, color: '#722ed1' },
  { label: '我的处方', path: '/prescriptions', icon: MedicineBoxOutlined, color: '#fa8c16' },
  { label: '费用查询', path: '/bills', icon: DollarOutlined, color: '#eb2f96' },
  { label: '检查报告', path: '/reports', icon: ExperimentOutlined, color: '#13c2c2' },
  { label: '我的随访', path: '/followup', icon: HeartOutlined, color: '#f5222d' },
  { label: '个人中心', path: '/profile', icon: UserOutlined, color: '#2f54eb' },
  { label: '检查报告', path: '/reports', icon: FileTextOutlined, color: '#faad14' },
]

const shortcuts = [
  { title: '我的挂号记录', path: '/appointments', icon: CalendarOutlined, color: '#52c41a' },
  { title: '处方查询', path: '/prescriptions', icon: MedicineBoxOutlined, color: '#fa8c16' },
  { title: '检查报告查看', path: '/reports', icon: FileTextOutlined, color: '#faad14' },
]
</script>

<style scoped>
.home-page {
  padding-bottom: 24px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px;
  background: #fff;
  border-bottom: 1px solid #f0f0f0;
}

.page-header h1 {
  font-size: 20px;
  font-weight: 600;
  color: #333;
}

.greeting-card {
  padding: 16px;
}

.greeting-content {
  display: flex;
  align-items: center;
  gap: 16px;
}

.greeting-label {
  display: block;
  font-size: 14px;
  color: #999;
}

.greeting-name {
  display: block;
  font-size: 18px;
  font-weight: 600;
  color: #333;
  margin-top: 4px;
}

.quick-actions {
  padding: 0 16px;
  margin-bottom: 16px;
}

.action-card {
  text-align: center;
  padding: 12px 4px;
}

.action-icon {
  margin-bottom: 8px;
}

.action-text {
  font-size: 12px;
  color: #666;
  white-space: nowrap;
}

.section {
  padding: 0 16px;
  margin-bottom: 16px;
}
</style>
