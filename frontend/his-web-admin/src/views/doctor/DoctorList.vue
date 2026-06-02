<template>
  <div class="doctor-list">
    <a-card>
      <template #title>
        <div class="card-title">
          <span>医生管理</span>
          <a-button type="primary" @click="showAddModal">
            <template #icon><PlusOutlined /></template>
            新增医生
          </a-button>
        </div>
      </template>

      <a-form layout="inline" :model="queryForm" style="margin-bottom: 16px">
        <a-form-item label="关键词">
          <a-input
            v-model:value="queryForm.keyword"
            placeholder="姓名/工号"
            allow-clear
            @pressEnter="handleSearch"
          />
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
        :data-source="doctorList"
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
            <a-switch
              :checked="record.status === 1"
              checked-children="启用"
              un-checked-children="停用"
              @change="(checked: boolean) => handleStatusChange(record.id, checked ? 1 : 0)"
            />
          </template>
          <template v-if="column.key === 'action'">
            <a-space>
              <a-button type="link" size="small" @click="showEditModal(record)">编辑</a-button>
              <a-popconfirm
                title="确定要删除该医生吗？"
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
      :title="isEdit ? '编辑医生' : '新增医生'"
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
        <a-form-item label="关联用户" name="userId">
          <a-select
            v-model:value="formData.userId"
            placeholder="选择关联用户"
            show-search
            :filter-option="filterUserOption"
          >
            <a-select-option v-for="user in userList" :key="user.id" :value="user.id">
              {{ user.realName }} ({{ user.username }})
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="姓名" name="name">
          <a-input v-model:value="formData.name" placeholder="请输入姓名" />
        </a-form-item>
        <a-form-item label="性别" name="gender">
          <a-radio-group v-model:value="formData.gender">
            <a-radio value="M">男</a-radio>
            <a-radio value="F">女</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="职称" name="title">
          <a-select v-model:value="formData.title" placeholder="选择职称">
            <a-select-option value="主任医师">主任医师</a-select-option>
            <a-select-option value="副主任医师">副主任医师</a-select-option>
            <a-select-option value="主治医师">主治医师</a-select-option>
            <a-select-option value="住院医师">住院医师</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="科室" name="departmentId">
          <a-select v-model:value="formData.departmentId" placeholder="选择科室">
            <a-select-option v-for="dept in departments" :key="dept.id" :value="dept.id">
              {{ dept.name }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="专长" name="specialty">
          <a-textarea v-model:value="formData.specialty" placeholder="请输入专长" :rows="3" />
        </a-form-item>
        <a-form-item label="简介" name="introduction">
          <a-textarea v-model:value="formData.introduction" placeholder="请输入简介" :rows="3" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { PlusOutlined, SearchOutlined } from '@ant-design/icons-vue'
import { doctorApi, departmentApi, authApi, type Doctor, type DoctorForm, type Department, type UserInfo } from '@/api'
import type { FormInstance } from 'ant-design-vue'

const loading = ref(false)
const submitLoading = ref(false)
const modalVisible = ref(false)
const isEdit = ref(false)
const editId = ref<number>()
const formRef = ref<FormInstance>()

const queryForm = reactive({
  keyword: '',
  departmentId: undefined as number | undefined,
  status: undefined as number | undefined,
})

const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showSizeChanger: true,
  showTotal: (total: number) => `共 ${total} 条`,
})

const doctorList = ref<Doctor[]>([])
const departments = ref<Department[]>([])
const userList = ref<UserInfo[]>([])

const formData = reactive<DoctorForm>({
  userId: 0,
  name: '',
  gender: 'M',
  title: '',
  departmentId: 0,
  specialty: '',
  introduction: '',
})

const formRules = {
  userId: [{ required: true, message: '请选择关联用户', trigger: 'change' }],
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  title: [{ required: true, message: '请选择职称', trigger: 'change' }],
  departmentId: [{ required: true, message: '请选择科室', trigger: 'change' }],
}

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 60 },
  { title: '姓名', dataIndex: 'name', key: 'name' },
  { title: '性别', key: 'gender', width: 80 },
  { title: '职称', dataIndex: 'title', key: 'title' },
  { title: '科室', dataIndex: 'departmentName', key: 'departmentName' },
  { title: '专长', dataIndex: 'specialty', key: 'specialty', ellipsis: true },
  { title: '状态', key: 'status', width: 120 },
  { title: '操作', key: 'action', width: 150 },
]

const fetchDoctors = async () => {
  loading.value = true
  try {
    const res = await doctorApi.getList({
      page: pagination.current,
      pageSize: pagination.pageSize,
      ...queryForm,
    })
    doctorList.value = res.list || []
    pagination.total = res.total || 0
  } catch (error) {
    console.error('获取医生列表失败:', error)
  } finally {
    loading.value = false
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

const fetchUsers = async () => {
  try {
    const res = await authApi.getUserList({ page: 1, pageSize: 100 })
    userList.value = res.list || []
  } catch (error) {
    console.error('获取用户列表失败:', error)
  }
}

const handleSearch = () => {
  pagination.current = 1
  fetchDoctors()
}

const handleReset = () => {
  queryForm.keyword = ''
  queryForm.departmentId = undefined
  queryForm.status = undefined
  handleSearch()
}

const handleTableChange = (pag: any) => {
  pagination.current = pag.current
  pagination.pageSize = pag.pageSize
  fetchDoctors()
}

const showAddModal = () => {
  isEdit.value = false
  editId.value = undefined
  resetForm()
  modalVisible.value = true
}

const showEditModal = (record: Doctor) => {
  isEdit.value = true
  editId.value = record.id
  Object.assign(formData, {
    userId: record.userId,
    name: record.name,
    gender: record.gender,
    title: record.title,
    departmentId: record.departmentId,
    specialty: record.specialty,
    introduction: record.introduction,
  })
  modalVisible.value = true
}

const handleModalOk = async () => {
  try {
    await formRef.value?.validateFields()
    submitLoading.value = true
    if (isEdit.value && editId.value) {
      await doctorApi.update(editId.value, formData)
      message.success('更新成功')
    } else {
      await doctorApi.create(formData)
      message.success('创建成功')
    }
    modalVisible.value = false
    fetchDoctors()
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

const resetForm = () => {
  formData.userId = 0
  formData.name = ''
  formData.gender = 'M'
  formData.title = ''
  formData.departmentId = 0
  formData.specialty = ''
  formData.introduction = ''
  formRef.value?.resetFields()
}

const handleStatusChange = async (id: number, status: number) => {
  try {
    await doctorApi.updateStatus(id, status)
    message.success('状态更新成功')
    fetchDoctors()
  } catch (error: any) {
    message.error(error.message || '状态更新失败')
  }
}

const handleDelete = async (id: number) => {
  try {
    await doctorApi.delete(id)
    message.success('删除成功')
    fetchDoctors()
  } catch (error: any) {
    message.error(error.message || '删除失败')
  }
}

const filterUserOption = (input: string, option: any) => {
  return option.children?.toString().toLowerCase().includes(input.toLowerCase())
}

onMounted(() => {
  fetchDoctors()
  fetchDepartments()
  fetchUsers()
})
</script>

<style scoped>
.card-title {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
</style>
