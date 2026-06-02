<template>
  <div class="dashboard">
    <a-spin :spinning="loading">
      <a-row :gutter="16">
        <a-col :span="6">
          <a-card class="stat-card" :bordered="false">
            <a-statistic title="今日挂号" :value="stats.totalRegistrations" suffix="人">
              <template #prefix>
                <FileTextOutlined style="color: #1890ff" />
              </template>
            </a-statistic>
          </a-card>
        </a-col>
        <a-col :span="6">
          <a-card class="stat-card" :bordered="false">
            <a-statistic title="今日就诊" :value="stats.totalOutpatients" suffix="人">
              <template #prefix>
                <HeartOutlined style="color: #52c41a" />
              </template>
            </a-statistic>
          </a-card>
        </a-col>
        <a-col :span="6">
          <a-card class="stat-card" :bordered="false">
            <a-statistic title="今日处方" :value="stats.totalPrescriptions" suffix="张">
              <template #prefix>
                <MedicineBoxOutlined style="color: #faad14" />
              </template>
            </a-statistic>
          </a-card>
        </a-col>
        <a-col :span="6">
          <a-card class="stat-card" :bordered="false">
            <a-statistic title="今日收入" :value="stats.totalRevenue" prefix="¥" :precision="2">
              <template #prefix>
                <DollarOutlined style="color: #f5222d" />
              </template>
            </a-statistic>
          </a-card>
        </a-col>
      </a-row>

      <a-row :gutter="16" style="margin-top: 16px">
        <a-col :span="16">
          <a-card title="最近挂号" :bordered="false">
            <a-table
              :columns="registrationColumns"
              :data-source="recentRegistrations"
              :loading="registrationsLoading"
              :pagination="false"
              size="small"
            >
              <template #bodyCell="{ column, record }">
                <template v-if="column.key === 'status'">
                  <a-tag :color="getStatusColor(record.status)">{{ getStatusText(record.status) }}</a-tag>
                </template>
              </template>
            </a-table>
          </a-card>
        </a-col>
        <a-col :span="8">
          <a-card title="快捷操作" :bordered="false">
            <a-space direction="vertical" style="width: 100%">
              <a-button type="primary" block @click="$router.push('/doctors')">
                <template #icon><UserOutlined /></template>
                医生管理
              </a-button>
              <a-button block @click="$router.push('/schedules')">
                <template #icon><CalendarOutlined /></template>
                排班管理
              </a-button>
              <a-button block @click="$router.push('/drugs')">
                <template #icon><MedicineBoxOutlined /></template>
                药品管理
              </a-button>
              <a-button block @click="$router.push('/patients')">
                <template #icon><TeamOutlined /></template>
                患者管理
              </a-button>
            </a-space>
          </a-card>

          <a-card title="系统信息" :bordered="false" style="margin-top: 16px">
            <a-descriptions :column="1" size="small">
              <a-descriptions-item label="系统版本">v1.0.0</a-descriptions-item>
              <a-descriptions-item label="后端服务">
                <a-badge status="success" text="运行中" />
              </a-descriptions-item>
              <a-descriptions-item label="数据库">
                <a-badge status="success" text="正常" />
              </a-descriptions-item>
              <a-descriptions-item label="缓存">
                <a-badge status="success" text="正常" />
              </a-descriptions-item>
            </a-descriptions>
          </a-card>
        </a-col>
      </a-row>
    </a-spin>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import {
  FileTextOutlined,
  HeartOutlined,
  MedicineBoxOutlined,
  DollarOutlined,
  UserOutlined,
  CalendarOutlined,
  TeamOutlined,
} from '@ant-design/icons-vue'
import { statisticsApi, type DashboardStats, type Registration } from '@/api'

const loading = ref(false)
const registrationsLoading = ref(false)

const stats = ref<DashboardStats>({
  totalRegistrations: 0,
  totalOutpatients: 0,
  totalInpatients: 0,
  totalPrescriptions: 0,
  totalRevenue: 0,
  deptStats: [],
})

const recentRegistrations = ref<Registration[]>([])

const registrationColumns = [
  { title: '患者姓名', dataIndex: 'patientName', key: 'patientName' },
  { title: '医生', dataIndex: 'doctorName', key: 'doctorName' },
  { title: '科室', dataIndex: 'departmentName', key: 'departmentName' },
  { title: '时间', dataIndex: 'visitDate', key: 'visitDate' },
  { title: '状态', key: 'status' },
]

const fetchDashboard = async () => {
  loading.value = true
  try {
    const res = await statisticsApi.getDashboard({ period: '今天' })
    stats.value = res
  } catch (error) {
    console.error('获取仪表盘数据失败:', error)
  } finally {
    loading.value = false
  }
}

const fetchRecentRegistrations = async () => {
  registrationsLoading.value = true
  try {
    const res = await statisticsApi.getTodayRegistrations({ page: 0, size: 5 })
    recentRegistrations.value = res.content || []
  } catch (error) {
    console.error('获取挂号列表失败:', error)
  } finally {
    registrationsLoading.value = false
  }
}

const getStatusColor = (status: string) => {
  const map: Record<string, string> = {
    PENDING: 'blue',
    IN_PROGRESS: 'orange',
    COMPLETED: 'green',
    CANCELLED: 'red',
  }
  return map[status] || 'default'
}

const getStatusText = (status: string) => {
  const map: Record<string, string> = {
    PENDING: '待就诊',
    IN_PROGRESS: '就诊中',
    COMPLETED: '已就诊',
    CANCELLED: '已取消',
  }
  return map[status] || status
}

onMounted(() => {
  fetchDashboard()
  fetchRecentRegistrations()
})
</script>

<style scoped>
.dashboard {
  padding: 0;
}

.stat-card {
  border-radius: 8px;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.06);
}

.stat-card :deep(.ant-statistic-title) {
  font-size: 14px;
  color: rgba(0, 0, 0, 0.65);
}

.stat-card :deep(.ant-statistic-content) {
  font-size: 24px;
}
</style>
