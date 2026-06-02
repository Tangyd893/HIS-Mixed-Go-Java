<template>
  <div class="page">
    <header class="page-header">
      <a-button type="text" size="small" @click="$router.back()">
        <LeftOutlined />
      </a-button>
      <h1>我的随访</h1>
      <div></div>
    </header>

    <div class="content">
      <a-tabs v-model:activeKey="activeTab" @change="handleTabChange">
        <a-tab-pane key="PENDING" tab="待随访">
          <a-spin :spinning="loading">
            <template v-if="pendingList.length > 0">
              <a-card v-for="item in pendingList" :key="item.id" class="card-item">
                <div class="card-header">
                  <a-tag :color="getTypeColor(item.followupType)">{{ item.followupType }}</a-tag>
                  <span class="card-status">待随访</span>
                </div>
                <a-descriptions :column="1" size="small">
                  <a-descriptions-item label="随访计划">{{ item.planName }}</a-descriptions-item>
                  <a-descriptions-item label="开始日期">{{ formatDate(item.startDate) }}</a-descriptions-item>
                  <a-descriptions-item label="随访频率">{{ item.frequency }}</a-descriptions-item>
                  <a-descriptions-item label="随访类型">{{ item.followupType }}</a-descriptions-item>
                </a-descriptions>
              </a-card>
            </template>
            <a-empty v-else description="暂无待随访计划" />
          </a-spin>
        </a-tab-pane>

        <a-tab-pane key="COMPLETED" tab="已完成">
          <a-spin :spinning="loading">
            <template v-if="completedList.length > 0">
              <a-card v-for="item in completedList" :key="item.id" class="card-item">
                <div class="card-header">
                  <a-tag color="green">已完成</a-tag>
                  <span class="card-status completed">已完成</span>
                </div>
                <a-descriptions :column="1" size="small">
                  <a-descriptions-item label="随访计划">{{ item.planName }}</a-descriptions-item>
                  <a-descriptions-item label="开始日期">{{ formatDate(item.startDate) }}</a-descriptions-item>
                  <a-descriptions-item label="结束日期">{{ formatDate(item.endDate) }}</a-descriptions-item>
                </a-descriptions>
              </a-card>
            </template>
            <a-empty v-else description="暂无已完成的随访" />
          </a-spin>
        </a-tab-pane>

        <a-tab-pane key="OVERDUE" tab="已逾期">
          <a-spin :spinning="loading">
            <template v-if="overdueList.length > 0">
              <a-card v-for="item in overdueList" :key="item.id" class="card-item">
                <div class="card-header">
                  <a-tag color="red">已逾期</a-tag>
                  <span class="card-status overdue">已逾期</span>
                </div>
                <a-descriptions :column="1" size="small">
                  <a-descriptions-item label="随访计划">{{ item.planName }}</a-descriptions-item>
                  <a-descriptions-item label="计划结束">{{ formatDate(item.endDate) }}</a-descriptions-item>
                </a-descriptions>
              </a-card>
            </template>
            <a-empty v-else description="暂无逾期随访" />
          </a-spin>
        </a-tab-pane>
      </a-tabs>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { LeftOutlined } from '@ant-design/icons-vue'
import { followupApi, type FollowupPlan } from '@/api'
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()
const activeTab = ref('PENDING')
const loading = ref(false)
const planList = ref<FollowupPlan[]>([])

const pendingList = computed(() => planList.value.filter(item => item.status === 'PENDING'))
const completedList = computed(() => planList.value.filter(item => item.status === 'COMPLETED'))
const overdueList = computed(() => planList.value.filter(item => item.status === 'OVERDUE'))

const fetchPlans = async () => {
  if (!authStore.userId) return
  
  loading.value = true
  try {
    const res = await followupApi.getPlansByPatient(authStore.userId)
    planList.value = Array.isArray(res) ? res : (res as any).content || []
  } catch (error) {
    console.error('获取随访计划失败:', error)
  } finally {
    loading.value = false
  }
}

const handleTabChange = () => {
  fetchPlans()
}

const getTypeColor = (type: string) => {
  const map: Record<string, string> = {
    '定期': 'blue',
    '临时': 'orange',
    '术后': 'purple',
  }
  return map[type] || 'default'
}

const formatDate = (dateStr?: string) => {
  if (!dateStr) return '-'
  return new Date(dateStr).toLocaleDateString('zh-CN')
}

onMounted(() => {
  fetchPlans()
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

.card-status {
  font-size: 13px;
  color: #fa8c16;
}

.card-status.completed {
  color: #52c41a;
}

.card-status.overdue {
  color: #f5222d;
}
</style>
