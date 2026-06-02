<template>
  <div class="dashboard">
    <a-row :gutter="16">
      <a-col :span="6">
        <a-card class="stat-card" :bordered="false">
          <a-statistic title="今日挂号" :value="stats.todayRegistrations" suffix="人">
            <template #prefix>
              <FileTextOutlined style="color: #1890ff" />
            </template>
          </a-statistic>
        </a-card>
      </a-col>
      <a-col :span="6">
        <a-card class="stat-card" :bordered="false">
          <a-statistic title="今日就诊" :value="stats.todayConsultations" suffix="人">
            <template #prefix>
              <HeartOutlined style="color: #52c41a" />
            </template>
          </a-statistic>
        </a-card>
      </a-col>
      <a-col :span="6">
        <a-card class="stat-card" :bordered="false">
          <a-statistic title="今日处方" :value="stats.todayPrescriptions" suffix="张">
            <template #prefix>
              <MedicineBoxOutlined style="color: #faad14" />
            </template>
          </a-statistic>
        </a-card>
      </a-col>
      <a-col :span="6">
        <a-card class="stat-card" :bordered="false">
          <a-statistic title="今日收入" :value="stats.todayRevenue" prefix="¥" :precision="2">
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
            :pagination="false"
            size="small"
          />
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

const stats = ref({
  todayRegistrations: 128,
  todayConsultations: 96,
  todayPrescriptions: 156,
  todayRevenue: 28560.50,
})

const recentRegistrations = ref([
  { id: 1, patientName: '张三', doctorName: '李医生', departmentName: '内科', time: '2026-06-02 09:00', status: '已就诊' },
  { id: 2, patientName: '李四', doctorName: '王医生', departmentName: '外科', time: '2026-06-02 09:30', status: '就诊中' },
  { id: 3, patientName: '王五', doctorName: '赵医生', departmentName: '儿科', time: '2026-06-02 10:00', status: '待就诊' },
  { id: 4, patientName: '赵六', doctorName: '刘医生', departmentName: '妇产科', time: '2026-06-02 10:30', status: '待就诊' },
  { id: 5, patientName: '钱七', doctorName: '陈医生', departmentName: '骨科', time: '2026-06-02 11:00', status: '待就诊' },
])

const registrationColumns = [
  { title: '患者姓名', dataIndex: 'patientName', key: 'patientName' },
  { title: '医生', dataIndex: 'doctorName', key: 'doctorName' },
  { title: '科室', dataIndex: 'departmentName', key: 'departmentName' },
  { title: '时间', dataIndex: 'time', key: 'time' },
  { title: '状态', dataIndex: 'status', key: 'status' },
]

onMounted(() => {
  // TODO: 获取实际统计数据
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
