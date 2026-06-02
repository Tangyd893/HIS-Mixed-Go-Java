<template>
  <div class="patient-list">
    <a-card>
      <template #title>
        <div class="card-title">
          <span>患者管理</span>
        </div>
      </template>

      <a-form layout="inline" :model="queryForm" style="margin-bottom: 16px">
        <a-form-item label="关键词">
          <a-input
            v-model:value="queryForm.keyword"
            placeholder="姓名/手机号/身份证号"
            allow-clear
            @pressEnter="handleSearch"
          />
        </a-form-item>
        <a-form-item label="状态">
          <a-select
            v-model:value="queryForm.status"
            placeholder="选择状态"
            allow-clear
            style="width: 120px"
          >
            <a-select-option :value="1">正常</a-select-option>
            <a-select-option :value="0">停用</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="handleSearch">
            <template #icon><SearchOutlined /></template>
            查询
          </a-button>
          <a-button style="margin-left: 8px" @click="handleReset">重置</a-button>
        </a-form-item>
      </a-form>

      <a-table
        :columns="columns"
        :data-source="patientList"
        :loading="loading"
        :pagination="pagination"
        @change="handleTableChange"
        row-key="id"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'gender'">
            {{ record.gender === 'M' ? '男' : '女' }}
          </template>
          <template v-if="column.key === 'status'">
            <a-tag :color="record.status === 1 ? 'green' : 'red'">
              {{ record.status === 1 ? '正常' : '停用' }}
            </a-tag>
          </template>
          <template v-if="column.key === 'action'">
            <a-space>
              <a-button type="link" size="small" @click="handleViewDetail(record)">详情</a-button>
            </a-space>
          </template>
        </template>
      </a-table>
    </a-card>

    <a-drawer
      v-model:open="drawerVisible"
      title="患者详情"
      width="600"
    >
      <a-descriptions :column="2" bordered v-if="currentPatient">
        <a-descriptions-item label="ID">{{ currentPatient.id }}</a-descriptions-item>
        <a-descriptions-item label="姓名">{{ currentPatient.name }}</a-descriptions-item>
        <a-descriptions-item label="性别">{{ currentPatient.gender === 'M' ? '男' : '女' }}</a-descriptions-item>
        <a-descriptions-item label="年龄">{{ currentPatient.age }}</a-descriptions-item>
        <a-descriptions-item label="手机号">{{ currentPatient.phone }}</a-descriptions-item>
        <a-descriptions-item label="身份证号">{{ currentPatient.idCard }}</a-descriptions-item>
        <a-descriptions-item label="地址" :span="2">{{ currentPatient.address }}</a-descriptions-item>
        <a-descriptions-item label="创建时间" :span="2">{{ currentPatient.createdAt }}</a-descriptions-item>
      </a-descriptions>
    </a-drawer>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { SearchOutlined } from '@ant-design/icons-vue'

interface Patient {
  id: number
  name: string
  gender: string
  age: number
  phone: string
  idCard: string
  address: string
  status: number
  createdAt: string
}

const loading = ref(false)
const drawerVisible = ref(false)
const currentPatient = ref<Patient | null>(null)

const queryForm = reactive({
  keyword: '',
  status: undefined as number | undefined,
})

const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showSizeChanger: true,
  showTotal: (total: number) => `共 ${total} 条`,
})

const patientList = ref<Patient[]>([])

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 60 },
  { title: '姓名', dataIndex: 'name', key: 'name' },
  { title: '性别', key: 'gender', width: 80 },
  { title: '年龄', dataIndex: 'age', key: 'age', width: 80 },
  { title: '手机号', dataIndex: 'phone', key: 'phone' },
  { title: '身份证号', dataIndex: 'idCard', key: 'idCard' },
  { title: '状态', key: 'status', width: 80 },
  { title: '创建时间', dataIndex: 'createdAt', key: 'createdAt' },
  { title: '操作', key: 'action', width: 100 },
]

const fetchPatients = async () => {
  loading.value = true
  try {
    // TODO: 调用实际API
    // 模拟数据
    patientList.value = [
      { id: 1, name: '张三', gender: 'M', age: 35, phone: '13800138001', idCard: '110101199001011234', address: '北京市东城区', status: 1, createdAt: '2026-06-01 10:00:00' },
      { id: 2, name: '李四', gender: 'F', age: 28, phone: '13800138002', idCard: '110101199202021234', address: '北京市西城区', status: 1, createdAt: '2026-06-01 11:00:00' },
      { id: 3, name: '王五', gender: 'M', age: 45, phone: '13800138003', idCard: '110101198103031234', address: '北京市朝阳区', status: 1, createdAt: '2026-06-01 12:00:00' },
    ]
    pagination.total = 3
  } catch (error) {
    console.error('获取患者列表失败:', error)
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  pagination.current = 1
  fetchPatients()
}

const handleReset = () => {
  queryForm.keyword = ''
  queryForm.status = undefined
  handleSearch()
}

const handleTableChange = (pag: any) => {
  pagination.current = pag.current
  pagination.pageSize = pag.pageSize
  fetchPatients()
}

const handleViewDetail = (record: Patient) => {
  currentPatient.value = record
  drawerVisible.value = true
}

onMounted(() => {
  fetchPatients()
})
</script>

<style scoped>
.card-title {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
</style>
