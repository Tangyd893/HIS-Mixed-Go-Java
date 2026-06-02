<template>
  <div class="department-list">
    <a-card>
      <template #title>
        <div class="card-title">
          <span>科室管理</span>
          <a-button type="primary" @click="showAddModal">
            <template #icon><PlusOutlined /></template>
            新增科室
          </a-button>
        </div>
      </template>

      <a-table
        :columns="columns"
        :data-source="departmentList"
        :loading="loading"
        :pagination="false"
        row-key="id"
        :default-expand-all-rows="true"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'status'">
            <a-tag :color="record.status === 1 ? 'green' : 'red'">
              {{ record.status === 1 ? '启用' : '停用' }}
            </a-tag>
          </template>
          <template v-if="column.key === 'action'">
            <a-space>
              <a-button type="link" size="small" @click="showEditModal(record)">编辑</a-button>
              <a-popconfirm
                title="确定要删除该科室吗？"
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
      :title="isEdit ? '编辑科室' : '新增科室'"
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
        <a-form-item label="上级科室" name="parentId">
          <a-tree-select
            v-model:value="formData.parentId"
            :tree-data="departmentTree"
            :field-names="{ label: 'name', value: 'id', children: 'children' }"
            placeholder="选择上级科室（不选则为顶级）"
            allow-clear
            tree-default-expand-all
          />
        </a-form-item>
        <a-form-item label="科室名称" name="name">
          <a-input v-model:value="formData.name" placeholder="请输入科室名称" />
        </a-form-item>
        <a-form-item label="科室编码" name="code">
          <a-input v-model:value="formData.code" placeholder="请输入科室编码" />
        </a-form-item>
        <a-form-item label="排序" name="sort">
          <a-input-number v-model:value="formData.sort" :min="0" style="width: 100%" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { PlusOutlined } from '@ant-design/icons-vue'
import { departmentApi, type Department, type DepartmentForm } from '@/api'
import type { FormInstance } from 'ant-design-vue'

const loading = ref(false)
const submitLoading = ref(false)
const modalVisible = ref(false)
const isEdit = ref(false)
const editId = ref<number>()
const formRef = ref<FormInstance>()

const departmentList = ref<Department[]>([])
const departmentTree = ref<Department[]>([])

const formData = reactive<DepartmentForm>({
  name: '',
  code: '',
  parentId: 0,
  sort: 0,
})

const formRules = {
  name: [{ required: true, message: '请输入科室名称', trigger: 'blur' }],
  code: [{ required: true, message: '请输入科室编码', trigger: 'blur' }],
}

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 60 },
  { title: '科室名称', dataIndex: 'name', key: 'name' },
  { title: '编码', dataIndex: 'code', key: 'code' },
  { title: '排序', dataIndex: 'sort', key: 'sort', width: 80 },
  { title: '状态', key: 'status', width: 80 },
  { title: '操作', key: 'action', width: 150 },
]

const fetchDepartments = async () => {
  loading.value = true
  try {
    const res = await departmentApi.getList()
    departmentList.value = res || []
    departmentTree.value = buildTree(departmentList.value)
  } catch (error) {
    console.error('获取科室列表失败:', error)
  } finally {
    loading.value = false
  }
}

const buildTree = (list: Department[], parentId: number = 0): Department[] => {
  return list
    .filter((item) => item.parentId === parentId)
    .map((item) => ({
      ...item,
      children: buildTree(list, item.id),
    }))
}

const showAddModal = () => {
  isEdit.value = false
  editId.value = undefined
  resetForm()
  modalVisible.value = true
}

const showEditModal = (record: Department) => {
  isEdit.value = true
  editId.value = record.id
  Object.assign(formData, {
    name: record.name,
    code: record.code,
    parentId: record.parentId,
    sort: record.sort,
  })
  modalVisible.value = true
}

const handleModalOk = async () => {
  try {
    await formRef.value?.validateFields()
    submitLoading.value = true
    if (isEdit.value && editId.value) {
      await departmentApi.update(editId.value, formData)
      message.success('更新成功')
    } else {
      await departmentApi.create(formData)
      message.success('创建成功')
    }
    modalVisible.value = false
    fetchDepartments()
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
  formData.name = ''
  formData.code = ''
  formData.parentId = 0
  formData.sort = 0
  formRef.value?.resetFields()
}

const handleDelete = async (id: number) => {
  try {
    await departmentApi.delete(id)
    message.success('删除成功')
    fetchDepartments()
  } catch (error: any) {
    message.error(error.message || '删除失败')
  }
}

onMounted(() => {
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
