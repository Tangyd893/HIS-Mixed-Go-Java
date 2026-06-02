<template>
  <div class="schedule-list">
    <a-card>
      <template #title>
        <div class="card-title">
          <span>排班管理</span>
          <a-space>
            <a-button type="primary" @click="showBatchModal">
              <template #icon><PlusOutlined /></template>
              批量排班
            </a-button>
            <a-button @click="showAddModal">
              <template #icon><PlusOutlined /></template>
              新增排班
            </a-button>
          </a-space>
        </div>
      </template>

      <a-form layout="inline" :model="queryForm" style="margin-bottom: 16px">
        <a-form-item label="医生">
          <a-select
            v-model:value="queryForm.doctorId"
            placeholder="选择医生"
            allow-clear
            show-search
            :filter-option="filterDoctorOption"
            style="width: 180px"
          >
            <a-select-option v-for="doc in doctorList" :key="doc.id" :value="doc.id">
              {{ doc.name }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="科室">
          <a-select
            v-model:value="queryForm.departmentId"
            placeholder="选择科室"
            allow-clear
            style="width: 180px"
          >
            <a-select-option v-for="dept in departments" :key="dept.id" :value="dept.id">
              {{ dept.name }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="日期范围">
          <a-range-picker
            v-model:value="dateRange"
            :format="'YYYY-MM-DD'"
            @change="handleDateChange"
          />
        </a-form-item>
        <a-form-item label="状态">
          <a-select
            v-model:value="queryForm.status"
            placeholder="选择状态"
            allow-clear
            style="width: 120px"
          >
            <a-select-option :value="1">启用</a-select-option>
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
        :data-source="scheduleList"
        :loading="loading"
        :pagination="pagination"
        @change="handleTableChange"
        row-key="id"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'status'">
            <a-tag :color="record.status === 1 ? 'green' : 'red'">
              {{ record.status === 1 ? '启用' : '停用' }}
            </a-tag>
          </template>
          <template v-if="column.key === 'patients'">
            <span>
              <a-progress
                :percent="Math.round((record.currentPatients / record.maxPatients) * 100)"
                :size="'small'"
                :status="record.currentPatients >= record.maxPatients ? 'exception' : 'active'"
              />
              <span style="font-size: 12px; color: #999">
                {{ record.currentPatients }}/{{ record.maxPatients }}
              </span>
            </span>
          </template>
          <template v-if="column.key === 'action'">
            <a-space>
              <a-button type="link" size="small" @click="showEditModal(record)">编辑</a-button>
              <a-popconfirm
                title="确定要删除该排班吗？"
                @confirm="handleDelete(record.id)"
              >
                <a-button type="link" size="small" danger>删除</a-button>
              </a-popconfirm>
            </a-space>
          </template>
        </template>
      </a-table>
    </a-card>

    <a-modal
      v-model:open="modalVisible"
      :title="isEdit ? '编辑排班' : '新增排班'"
      @ok="handleModalOk"
      @cancel="handleModalCancel"
      :confirm-loading="submitLoading"
    >
      <a-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        layout="vertical"
      >
        <a-form-item label="医生" name="doctorId">
          <a-select
            v-model:value="formData.doctorId"
            placeholder="选择医生"
            show-search
            :filter-option="filterDoctorOption"
          >
            <a-select-option v-for="doc in doctorList" :key="doc.id" :value="doc.id">
              {{ doc.name }} - {{ doc.departmentName }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="排班日期" name="scheduleDate">
          <a-date-picker
            v-model:value="formData.scheduleDate"
            :format="'YYYY-MM-DD'"
            style="width: 100%"
          />
        </a-form-item>
        <a-form-item label="时段" name="timeSlot">
          <a-select v-model:value="formData.timeSlot" placeholder="选择时段">
            <a-select-option value="morning">上午</a-select-option>
            <a-select-option value="afternoon">下午</a-select-option>
            <a-select-option value="evening">晚间</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="最大接诊数" name="maxPatients">
          <a-input-number v-model:value="formData.maxPatients" :min="1" :max="100" style="width: 100%" />
        </a-form-item>
      </a-form>
    </a-modal>

    <a-modal
      v-model:open="batchModalVisible"
      title="批量排班"
      @ok="handleBatchOk"
      @cancel="handleBatchCancel"
      :confirm-loading="batchLoading"
      width="600px"
    >
      <a-form
        ref="batchFormRef"
        :model="batchFormData"
        :rules="batchFormRules"
        layout="vertical"
      >
        <a-form-item label="医生" name="doctorId">
          <a-select
            v-model:value="batchFormData.doctorId"
            placeholder="选择医生"
            show-search
            :filter-option="filterDoctorOption"
          >
            <a-select-option v-for="doc in doctorList" :key="doc.id" :value="doc.id">
              {{ doc.name }} - {{ doc.departmentName }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="日期范围" name="dateRange">
          <a-range-picker
            v-model:value="batchFormData.dateRange"
            :format="'YYYY-MM-DD'"
            style="width: 100%"
          />
        </a-form-item>
        <a-form-item label="时段配置">
          <div v-for="(slot, index) in batchFormData.timeSlots" :key="index" style="display: flex; gap: 16px; margin-bottom: 8px">
            <a-select v-model:value="slot.timeSlot" placeholder="选择时段" style="width: 200px">
              <a-select-option value="morning">上午</a-select-option>
              <a-select-option value="afternoon">下午</a-select-option>
              <a-select-option value="evening">晚间</a-select-option>
            </a-select>
            <a-input-number v-model:value="slot.maxPatients" :min="1" :max="100" placeholder="最大接诊数" style="width: 200px" />
            <a-button type="link" danger @click="removeTimeSlot(index)" v-if="batchFormData.timeSlots.length > 1">
              删除
            </a-button>
          </div>
          <a-button type="dashed" block @click="addTimeSlot">
            <template #icon><PlusOutlined /></template>
            添加时段
          </a-button>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { PlusOutlined, SearchOutlined } from '@ant-design/icons-vue'
import { scheduleApi, doctorApi, departmentApi, type Schedule, type Doctor, type Department } from '@/api'
import type { FormInstance } from 'ant-design-vue'
import dayjs, { type Dayjs } from 'dayjs'

const loading = ref(false)
const submitLoading = ref(false)
const batchLoading = ref(false)
const modalVisible = ref(false)
const batchModalVisible = ref(false)
const isEdit = ref(false)
const editId = ref<number>()
const formRef = ref<FormInstance>()
const batchFormRef = ref<FormInstance>()

const dateRange = ref<[Dayjs, Dayjs] | null>(null)

const queryForm = reactive({
  doctorId: undefined as number | undefined,
  departmentId: undefined as number | undefined,
  startDate: '',
  endDate: '',
  status: undefined as number | undefined,
})

const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showSizeChanger: true,
  showTotal: (total: number) => `共 ${total} 条`,
})

const scheduleList = ref<Schedule[]>([])
const doctorList = ref<Doctor[]>([])
const departments = ref<Department[]>([])

const formData = reactive({
  doctorId: undefined as number | undefined,
  scheduleDate: null as Dayjs | null,
  timeSlot: '',
  maxPatients: 30,
})

const batchFormData = reactive({
  doctorId: undefined as number | undefined,
  dateRange: null as [Dayjs, Dayjs] | null,
  timeSlots: [{ timeSlot: 'morning', maxPatients: 30 }],
})

const formRules = {
  doctorId: [{ required: true, message: '请选择医生', trigger: 'change' }],
  scheduleDate: [{ required: true, message: '请选择日期', trigger: 'change' }],
  timeSlot: [{ required: true, message: '请选择时段', trigger: 'change' }],
  maxPatients: [{ required: true, message: '请输入最大接诊数', trigger: 'blur' }],
}

const batchFormRules = {
  doctorId: [{ required: true, message: '请选择医生', trigger: 'change' }],
  dateRange: [{ required: true, message: '请选择日期范围', trigger: 'change' }],
}

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 60 },
  { title: '医生', dataIndex: 'doctorName', key: 'doctorName' },
  { title: '科室', dataIndex: 'departmentName', key: 'departmentName' },
  { title: '日期', dataIndex: 'scheduleDate', key: 'scheduleDate' },
  { title: '时段', dataIndex: 'timeSlot', key: 'timeSlot' },
  { title: '接诊情况', key: 'patients', width: 180 },
  { title: '状态', key: 'status', width: 80 },
  { title: '操作', key: 'action', width: 150 },
]

const fetchSchedules = async () => {
  loading.value = true
  try {
    const res = await scheduleApi.getList({
      page: pagination.current,
      pageSize: pagination.pageSize,
      ...queryForm,
    })
    scheduleList.value = res.list || []
    pagination.total = res.total || 0
  } catch (error) {
    console.error('获取排班列表失败:', error)
  } finally {
    loading.value = false
  }
}

const fetchDoctors = async () => {
  try {
    const res = await doctorApi.getList({ page: 1, pageSize: 100 })
    doctorList.value = res.list || []
  } catch (error) {
    console.error('获取医生列表失败:', error)
  }
}

const fetchDepartments = async () => {
  try {
    const res = await departmentApi.getList()
    departments.value = res || []
  } catch (error) {
    console.error('获取科室列表失败:', error)
  }
}

const handleDateChange = (dates: [Dayjs, Dayjs] | null) => {
  if (dates) {
    queryForm.startDate = dates[0].format('YYYY-MM-DD')
    queryForm.endDate = dates[1].format('YYYY-MM-DD')
  } else {
    queryForm.startDate = ''
    queryForm.endDate = ''
  }
}

const handleSearch = () => {
  pagination.current = 1
  fetchSchedules()
}

const handleReset = () => {
  queryForm.doctorId = undefined
  queryForm.departmentId = undefined
  queryForm.startDate = ''
  queryForm.endDate = ''
  queryForm.status = undefined
  dateRange.value = null
  handleSearch()
}

const handleTableChange = (pag: any) => {
  pagination.current = pag.current
  pagination.pageSize = pag.pageSize
  fetchSchedules()
}

const showAddModal = () => {
  isEdit.value = false
  editId.value = undefined
  resetForm()
  modalVisible.value = true
}

const showEditModal = (record: Schedule) => {
  isEdit.value = true
  editId.value = record.id
  Object.assign(formData, {
    doctorId: record.doctorId,
    scheduleDate: dayjs(record.scheduleDate),
    timeSlot: record.timeSlot,
    maxPatients: record.maxPatients,
  })
  modalVisible.value = true
}

const showBatchModal = () => {
  resetBatchForm()
  batchModalVisible.value = true
}

const handleModalOk = async () => {
  try {
    await formRef.value?.validateFields()
    submitLoading.value = true
    const data = {
      doctorId: formData.doctorId!,
      scheduleDate: formData.scheduleDate!.format('YYYY-MM-DD'),
      timeSlot: formData.timeSlot,
      maxPatients: formData.maxPatients,
    }
    if (isEdit.value && editId.value) {
      await scheduleApi.update(editId.value, data)
      message.success('更新成功')
    } else {
      await scheduleApi.create(data)
      message.success('创建成功')
    }
    modalVisible.value = false
    fetchSchedules()
  } catch (error: any) {
    if (error.errorFields) {
      return
    }
    message.error(error.message || '操作失败')
  } finally {
    submitLoading.value = false
  }
}

const handleModalCancel = () => {
  modalVisible.value = false
  resetForm()
}

const handleBatchOk = async () => {
  try {
    await batchFormRef.value?.validateFields()
    batchLoading.value = true
    await scheduleApi.batchCreate({
      doctorId: batchFormData.doctorId!,
      startDate: batchFormData.dateRange![0].format('YYYY-MM-DD'),
      endDate: batchFormData.dateRange![1].format('YYYY-MM-DD'),
      timeSlots: batchFormData.timeSlots,
    })
    message.success('批量创建成功')
    batchModalVisible.value = false
    fetchSchedules()
  } catch (error: any) {
    if (error.errorFields) {
      return
    }
    message.error(error.message || '操作失败')
  } finally {
    batchLoading.value = false
  }
}

const handleBatchCancel = () => {
  batchModalVisible.value = false
  resetBatchForm()
}

const resetForm = () => {
  formData.doctorId = undefined
  formData.scheduleDate = null
  formData.timeSlot = ''
  formData.maxPatients = 30
  formRef.value?.resetFields()
}

const resetBatchForm = () => {
  batchFormData.doctorId = undefined
  batchFormData.dateRange = null
  batchFormData.timeSlots = [{ timeSlot: 'morning', maxPatients: 30 }]
  batchFormRef.value?.resetFields()
}

const addTimeSlot = () => {
  batchFormData.timeSlots.push({ timeSlot: 'afternoon', maxPatients: 30 })
}

const removeTimeSlot = (index: number) => {
  batchFormData.timeSlots.splice(index, 1)
}

const handleDelete = async (id: number) => {
  try {
    await scheduleApi.delete(id)
    message.success('删除成功')
    fetchSchedules()
  } catch (error: any) {
    message.error(error.message || '删除失败')
  }
}

const filterDoctorOption = (input: string, option: any) => {
  return option.children?.toString().toLowerCase().includes(input.toLowerCase())
}

onMounted(() => {
  fetchSchedules()
  fetchDoctors()
  fetchDepartments()
})
</script>

<style scoped>
.card-title {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
</style>
