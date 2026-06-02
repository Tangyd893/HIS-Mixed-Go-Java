<template>
  <div class="page">
    <header class="page-header">
      <a-button type="text" size="small" @click="$router.back()">
        <LeftOutlined />
      </a-button>
      <h1>我的挂号</h1>
      <div></div>
    </header>

    <div class="content">
      <a-tabs v-model:activeKey="activeTab" @change="handleTabChange">
        <a-tab-pane key="PENDING" tab="待就诊">
          <a-spin :spinning="loading">
            <template v-if="filteredList.length > 0">
              <a-card class="card-item" v-for="item in filteredList" :key="item.id">
                <div class="card-header">
                  <a-tag :color="getStatusColor(item.status)">{{ getStatusText(item.status) }}</a-tag>
                  <span class="card-no">排队号: {{ item.serialNumber }}</span>
                </div>
                <a-descriptions :column="1" size="small">
                  <a-descriptions-item label="就诊科室">{{ item.departmentName || '待分配' }}</a-descriptions-item>
                  <a-descriptions-item label="就诊医生">{{ item.doctorName || '待分配' }}</a-descriptions-item>
                  <a-descriptions-item label="就诊时间">{{ item.visitDate }}</a-descriptions-item>
                </a-descriptions>
                <div class="card-actions" v-if="item.status === 'PENDING'">
                  <a-popconfirm title="确定取消挂号吗？" @confirm="handleCancel(item.id)">
                    <a-button type="link" danger size="small">取消挂号</a-button>
                  </a-popconfirm>
                </div>
              </a-card>
            </template>
            <a-empty v-else description="暂无待就诊记录" />
          </a-spin>
        </a-tab-pane>

        <a-tab-pane key="COMPLETED" tab="已完成">
          <a-spin :spinning="loading">
            <template v-if="filteredList.length > 0">
              <a-card class="card-item" v-for="item in filteredList" :key="item.id">
                <div class="card-header">
                  <a-tag color="green">已完成</a-tag>
                  <span class="card-no">{{ item.serialNumber }}</span>
                </div>
                <a-descriptions :column="1" size="small">
                  <a-descriptions-item label="就诊科室">{{ item.departmentName }}</a-descriptions-item>
                  <a-descriptions-item label="就诊医生">{{ item.doctorName }}</a-descriptions-item>
                  <a-descriptions-item label="就诊时间">{{ item.visitDate }}</a-descriptions-item>
                </a-descriptions>
              </a-card>
            </template>
            <a-empty v-else description="暂无已完成记录" />
          </a-spin>
        </a-tab-pane>

        <a-tab-pane key="CANCELLED" tab="已取消">
          <a-spin :spinning="loading">
            <template v-if="filteredList.length > 0">
              <a-card class="card-item" v-for="item in filteredList" :key="item.id">
                <div class="card-header">
                  <a-tag color="red">已取消</a-tag>
                  <span class="card-no">{{ item.serialNumber }}</span>
                </div>
                <a-descriptions :column="1" size="small">
                  <a-descriptions-item label="就诊科室">{{ item.departmentName }}</a-descriptions-item>
                  <a-descriptions-item label="就诊医生">{{ item.doctorName }}</a-descriptions-item>
                  <a-descriptions-item label="原定时间">{{ item.visitDate }}</a-descriptions-item>
                </a-descriptions>
              </a-card>
            </template>
            <a-empty v-else description="暂无已取消记录" />
          </a-spin>
        </a-tab-pane>
      </a-tabs>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { LeftOutlined } from '@ant-design/icons-vue'
import { message } from 'ant-design-vue'
import { registrationApi, type Registration } from '@/api'
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()
const activeTab = ref('PENDING')
const loading = ref(false)
const registrationList = ref<Registration[]>([])

const filteredList = computed(() => {
  return registrationList.value.filter(item => item.status === activeTab.value)
})

const fetchRegistrations = async () => {
  if (!authStore.userId) return
  
  loading.value = true
  try {
    const res = await registrationApi.getRegistrations(authStore.userId!)
    registrationList.value = res.list || res || []
  } catch (error) {
    console.error('获取挂号记录失败:', error)
  } finally {
    loading.value = false
  }
}

const handleCancel = async (id: number) => {
  try {
    await registrationApi.cancelRegistration(id)
    message.success('取消成功')
    fetchRegistrations()
  } catch (error) {
    console.error('取消挂号失败:', error)
  }
}

const handleTabChange = () => {
  fetchRegistrations()
}

const getStatusColor = (status: string) => {
  const map: Record<string, string> = {
    PENDING: 'blue',
    COMPLETED: 'green',
    CANCELLED: 'red',
  }
  return map[status] || 'default'
}

const getStatusText = (status: string) => {
  const map: Record<string, string> = {
    PENDING: '已预约',
    COMPLETED: '已完成',
    CANCELLED: '已取消',
  }
  return map[status] || status
}

onMounted(() => {
  fetchRegistrations()
})
</script>

<style scoped>
.page {
  min-height: 100vh;
  background: #f5f5f5;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 16px;
  background: #fff;
  border-bottom: 1px solid #f0f0f0;
}

.page-header h1 {
  font-size: 18px;
  font-weight: 600;
  color: #333;
}

.page-header > div {
  width: 40px;
}

.content {
  padding: 16px;
}

.card-item {
  border-radius: 8px;
  margin-bottom: 12px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.card-no {
  font-size: 13px;
  color: #666;
}

.card-actions {
  margin-top: 12px;
  text-align: right;
  border-top: 1px solid #f0f0f0;
  padding-top: 8px;
}
</style>
