<template>
  <div class="drug-list">
    <a-card>
      <template #title>
        <div class="card-title">
          <span>药品管理</span>
          <a-button type="primary" @click="showAddModal">
            <template #icon><PlusOutlined /></template>
            新增药品
          </a-button>
        </div>
      </template>

      <a-form layout="inline" :model="queryForm" style="margin-bottom: 16px">
        <a-form-item label="关键词">
          <a-input
            v-model:value="queryForm.keyword"
            placeholder="药品名称/编码"
            allow-clear
            @pressEnter="handleSearch"
          />
        </a-form-item>
        <a-form-item label="分类">
          <a-select
            v-model:value="queryForm.category"
            placeholder="选择分类"
            allow-clear
            style="width: 150px"
          >
            <a-select-option v-for="cat in categories" :key="cat" :value="cat">
              {{ cat }}
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
        :data-source="drugList"
        :loading="loading"
        :pagination="pagination"
        @change="handleTableChange"
        row-key="id"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'price'">
            ¥{{ record.price?.toFixed(2) }}
          </template>
          <template v-if="column.key === 'stock'">
            <a-tag :color="record.stock <= record.minStock ? 'warning' : 'default'">
              {{ record.stock }}
            </a-tag>
            <span v-if="record.stock <= record.minStock" style="color: #faad14; font-size: 12px">
              (库存不足)
            </span>
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
              <a-button type="link" size="small" @click="showStockModal(record)">库存</a-button>
              <a-popconfirm
                title="确定要删除该药品吗？"
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
      :title="isEdit ? '编辑药品' : '新增药品'"
      @ok="handleModalOk"
      @cancel="handleModalCancel"
      :confirm-loading="submitLoading"
      width="600px"
    >
      <a-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        layout="vertical"
      >
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="药品编码" name="code">
              <a-input v-model:value="formData.code" placeholder="请输入药品编码" :disabled="isEdit" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="药品名称" name="name">
              <a-input v-model:value="formData.name" placeholder="请输入药品名称" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="通用名" name="genericName">
              <a-input v-model:value="formData.genericName" placeholder="请输入通用名" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="分类" name="category">
              <a-select v-model:value="formData.category" placeholder="选择分类">
                <a-select-option v-for="cat in categories" :key="cat" :value="cat">
                  {{ cat }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="16">
          <a-col :span="12">
            <a-form-item label="规格" name="specification">
              <a-input v-model:value="formData.specification" placeholder="请输入规格" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="单位" name="unit">
              <a-input v-model:value="formData.unit" placeholder="请输入单位" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item label="生产厂家" name="manufacturer">
          <a-input v-model:value="formData.manufacturer" placeholder="请输入生产厂家" />
        </a-form-item>
        <a-row :gutter="16">
          <a-col :span="8">
            <a-form-item label="单价" name="price">
              <a-input-number v-model:value="formData.price" :min="0" :precision="2" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="库存" name="stock">
              <a-input-number v-model:value="formData.stock" :min="0" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="最低库存" name="minStock">
              <a-input-number v-model:value="formData.minStock" :min="0" style="width: 100%" />
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-modal>

    <a-modal
      v-model:open="stockModalVisible"
      title="库存调整"
      @ok="handleStockOk"
      @cancel="handleStockCancel"
      :confirm-loading="stockLoading"
    >
      <a-form layout="vertical">
        <a-form-item label="药品">
          <a-input :value="currentDrug?.name" disabled />
        </a-form-item>
        <a-form-item label="当前库存">
          <a-tag color="blue">{{ currentDrug?.stock }}</a-tag>
        </a-form-item>
        <a-form-item label="调整类型">
          <a-radio-group v-model:value="stockForm.type">
            <a-radio value="in">入库</a-radio>
            <a-radio value="out">出库</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="数量">
          <a-input-number v-model:value="stockForm.quantity" :min="1" style="width: 100%" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { PlusOutlined, SearchOutlined } from '@ant-design/icons-vue'
import { drugApi, type Drug, type DrugForm } from '@/api'
import type { FormInstance } from 'ant-design-vue'

const loading = ref(false)
const submitLoading = ref(false)
const stockLoading = ref(false)
const modalVisible = ref(false)
const stockModalVisible = ref(false)
const isEdit = ref(false)
const editId = ref<number>()
const formRef = ref<FormInstance>()
const currentDrug = ref<Drug | null>(null)

const categories = ['西药', '中成药', '中药', '医疗器械', '其他']

const queryForm = reactive({
  keyword: '',
  category: undefined as string | undefined,
  status: undefined as number | undefined,
})

const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showSizeChanger: true,
  showTotal: (total: number) => `共 ${total} 条`,
})

const drugList = ref<Drug[]>([])

const formData = reactive<DrugForm>({
  code: '',
  name: '',
  genericName: '',
  category: '',
  specification: '',
  unit: '',
  manufacturer: '',
  price: 0,
  stock: 0,
  minStock: 10,
})

const stockForm = reactive({
  type: 'in' as 'in' | 'out',
  quantity: 1,
})

const formRules = {
  code: [{ required: true, message: '请输入药品编码', trigger: 'blur' }],
  name: [{ required: true, message: '请输入药品名称', trigger: 'blur' }],
  category: [{ required: true, message: '请选择分类', trigger: 'change' }],
  unit: [{ required: true, message: '请输入单位', trigger: 'blur' }],
  price: [{ required: true, message: '请输入单价', trigger: 'blur' }],
}

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 60 },
  { title: '编码', dataIndex: 'code', key: 'code', width: 100 },
  { title: '药品名称', dataIndex: 'name', key: 'name' },
  { title: '通用名', dataIndex: 'genericName', key: 'genericName' },
  { title: '分类', dataIndex: 'category', key: 'category', width: 100 },
  { title: '规格', dataIndex: 'specification', key: 'specification' },
  { title: '单价', key: 'price', width: 100 },
  { title: '库存', key: 'stock', width: 120 },
  { title: '状态', key: 'status', width: 100 },
  { title: '操作', key: 'action', width: 200 },
]

const fetchDrugs = async () => {
  loading.value = true
  try {
    const res = await drugApi.getList({
      page: pagination.current,
      pageSize: pagination.pageSize,
      ...queryForm,
    })
    drugList.value = res.list || []
    pagination.total = res.total || 0
  } catch (error) {
    console.error('获取药品列表失败:', error)
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  pagination.current = 1
  fetchDrugs()
}

const handleReset = () => {
  queryForm.keyword = ''
  queryForm.category = undefined
  queryForm.status = undefined
  handleSearch()
}

const handleTableChange = (pag: any) => {
  pagination.current = pag.current
  pagination.pageSize = pag.pageSize
  fetchDrugs()
}

const showAddModal = () => {
  isEdit.value = false
  editId.value = undefined
  resetForm()
  modalVisible.value = true
}

const showEditModal = (record: Drug) => {
  isEdit.value = true
  editId.value = record.id
  Object.assign(formData, {
    code: record.code,
    name: record.name,
    genericName: record.genericName,
    category: record.category,
    specification: record.specification,
    unit: record.unit,
    manufacturer: record.manufacturer,
    price: record.price,
    stock: record.stock,
    minStock: record.minStock,
  })
  modalVisible.value = true
}

const showStockModal = (record: Drug) => {
  currentDrug.value = record
  stockForm.type = 'in'
  stockForm.quantity = 1
  stockModalVisible.value = true
}

const handleModalOk = async () => {
  try {
    await formRef.value?.validateFields()
    submitLoading.value = true
    if (isEdit.value && editId.value) {
      await drugApi.update(editId.value, formData)
      message.success('更新成功')
    } else {
      await drugApi.create(formData)
      message.success('创建成功')
    }
    modalVisible.value = false
    fetchDrugs()
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

const handleStockOk = async () => {
  if (!currentDrug.value) return
  stockLoading.value = true
  try {
    await drugApi.updateStock(currentDrug.value.id, stockForm.quantity, stockForm.type)
    message.success('库存调整成功')
    stockModalVisible.value = false
    fetchDrugs()
  } catch (error: any) {
    message.error(error.message || '库存调整失败')
  } finally {
    stockLoading.value = false
  }
}

const handleStockCancel = () => {
  stockModalVisible.value = false
}

const resetForm = () => {
  formData.code = ''
  formData.name = ''
  formData.genericName = ''
  formData.category = ''
  formData.specification = ''
  formData.unit = ''
  formData.manufacturer = ''
  formData.price = 0
  formData.stock = 0
  formData.minStock = 10
  formRef.value?.resetFields()
}

const handleStatusChange = async (id: number, status: number) => {
  try {
    await drugApi.updateStatus(id, status)
    message.success('状态更新成功')
    fetchDrugs()
  } catch (error: any) {
    message.error(error.message || '状态更新失败')
  }
}

const handleDelete = async (id: number) => {
  try {
    await drugApi.delete(id)
    message.success('删除成功')
    fetchDrugs()
  } catch (error: any) {
    message.error(error.message || '删除失败')
  }
}

onMounted(() => {
  fetchDrugs()
})
</script>

<style scoped>
.card-title {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
</style>
