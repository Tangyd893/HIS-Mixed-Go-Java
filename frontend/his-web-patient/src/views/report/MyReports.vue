<template>
  <div class="page">
    <header class="page-header">
      <a-button type="text" size="small" @click="$router.back()">
        <LeftOutlined />
      </a-button>
      <h1>检查报告</h1>
      <div></div>
    </header>

    <div class="content">
      <a-tabs v-model:activeKey="activeTab" @change="handleTabChange">
        <a-tab-pane key="all" tab="全部">
          <a-spin :spinning="loading">
            <template v-if="reportList.length > 0">
              <a-card v-for="item in reportList" :key="item.id" class="card-item">
                <div class="card-header">
                  <a-tag :color="getStatusColor(item.status)">{{ getStatusText(item.status) }}</a-tag>
                  <span class="card-date">{{ formatDate(item.reportDate || item.createdAt) }}</span>
                </div>
                <a-descriptions :column="1" size="small">
                  <a-descriptions-item label="报告编号">{{ item.reportNo }}</a-descriptions-item>
                  <a-descriptions-item label="检查项目">{{ item.examinationName }}</a-descriptions-item>
                  <a-descriptions-item label="检查类型">{{ item.examinationType }}</a-descriptions-item>
                  <a-descriptions-item label="检查科室">{{ item.department }}</a-descriptions-item>
                  <a-descriptions-item label="报告医生">{{ item.doctorName }}</a-descriptions-item>
                </a-descriptions>
                <a-button 
                  block 
                  size="small" 
                  class="view-btn" 
                  :disabled="item.status !== 'COMPLETED'" 
                  @click="handleView(item)"
                >
                  查看报告
                </a-button>
              </a-card>
            </template>
            <a-empty v-else description="暂无检查报告" />
          </a-spin>
        </a-tab-pane>

        <a-tab-pane key="PENDING" tab="待检查">
          <a-spin :spinning="loading">
            <template v-if="pendingList.length > 0">
              <a-card v-for="item in pendingList" :key="item.id" class="card-item">
                <div class="card-header">
                  <a-tag color="blue">待检查</a-tag>
                  <span class="card-date">{{ formatDate(item.createdAt) }}</span>
                </div>
                <a-descriptions :column="1" size="small">
                  <a-descriptions-item label="检查项目">{{ item.examinationName }}</a-descriptions-item>
                  <a-descriptions-item label="检查科室">{{ item.department }}</a-descriptions-item>
                </a-descriptions>
              </a-card>
            </template>
            <a-empty v-else description="暂无待检查项目" />
          </a-spin>
        </a-tab-pane>

        <a-tab-pane key="COMPLETED" tab="已完成">
          <a-spin :spinning="loading">
            <template v-if="completedList.length > 0">
              <a-card v-for="item in completedList" :key="item.id" class="card-item">
                <div class="card-header">
                  <a-tag color="green">已完成</a-tag>
                  <span class="card-date">{{ formatDate(item.reportDate) }}</span>
                </div>
                <a-descriptions :column="1" size="small">
                  <a-descriptions-item label="报告编号">{{ item.reportNo }}</a-descriptions-item>
                  <a-descriptions-item label="检查项目">{{ item.examinationName }}</a-descriptions-item>
                  <a-descriptions-item label="报告医生">{{ item.doctorName }}</a-descriptions-item>
                </a-descriptions>
                <a-button block size="small" class="view-btn" @click="handleView(item)">
                  查看报告
                </a-button>
              </a-card>
            </template>
            <a-empty v-else description="暂无已完成的报告" />
          </a-spin>
        </a-tab-pane>
      </a-tabs>
    </div>

    <!-- 报告详情弹窗 -->
    <a-modal v-model:open="detailVisible" title="检查报告详情" :footer="null" width="600px">
      <a-spin :spinning="detailLoading">
        <template v-if="currentReport">
          <a-descriptions :column="2" bordered size="small">
            <a-descriptions-item label="报告编号" :span="2">{{ currentReport.reportNo }}</a-descriptions-item>
            <a-descriptions-item label="检查项目">{{ currentReport.examinationName }}</a-descriptions-item>
            <a-descriptions-item label="检查类型">{{ currentReport.examinationType }}</a-descriptions-item>
            <a-descriptions-item label="检查科室">{{ currentReport.department }}</a-descriptions-item>
            <a-descriptions-item label="报告医生">{{ currentReport.doctorName }}</a-descriptions-item>
            <a-descriptions-item label="报告日期" :span="2">{{ formatDate(currentReport.reportDate) }}</a-descriptions-item>
          </a-descriptions>
          <a-divider />
          <h4>检查结果</h4>
          <p class="report-content">{{ currentReport.result || '暂无结果' }}</p>
          <a-divider />
          <h4>诊断结论</h4>
          <p class="report-content">{{ currentReport.conclusion || '暂无结论' }}</p>
        </template>
      </a-spin>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { LeftOutlined } from '@ant-design/icons-vue'
import { reportApi, type ExaminationReport } from '@/api'
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()
const activeTab = ref('all')
const loading = ref(false)
const reportList = ref<ExaminationReport[]>([])
const detailVisible = ref(false)
const detailLoading = ref(false)
const currentReport = ref<ExaminationReport | null>(null)

const pendingList = computed(() => reportList.value.filter(item => item.status === 'PENDING'))
const completedList = computed(() => reportList.value.filter(item => item.status === 'COMPLETED'))

const fetchReports = async () => {
  if (!authStore.userId) return
  
  loading.value = true
  try {
    const res = await reportApi.getReportsByPatient(authStore.userId)
    reportList.value = Array.isArray(res) ? res : (res as any).content || []
  } catch (error) {
    console.error('获取报告失败:', error)
  } finally {
    loading.value = false
  }
}

const handleTabChange = () => {
  fetchReports()
}

const handleView = async (item: ExaminationReport) => {
  detailVisible.value = true
  detailLoading.value = true
  try {
    const res = await reportApi.getReport(item.id)
    currentReport.value = res
  } catch (error) {
    console.error('获取报告详情失败:', error)
  } finally {
    detailLoading.value = false
  }
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
    PENDING: '待检查',
    COMPLETED: '已完成',
    CANCELLED: '已取消',
  }
  return map[status] || status
}

const formatDate = (dateStr?: string) => {
  if (!dateStr) return '-'
  return new Date(dateStr).toLocaleDateString('zh-CN')
}

onMounted(() => {
  fetchReports()
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

.card-date {
  font-size: 13px;
  color: #999;
}

.view-btn {
  margin-top: 12px;
}

.report-content {
  color: #666;
  line-height: 1.8;
  white-space: pre-wrap;
}
</style>
