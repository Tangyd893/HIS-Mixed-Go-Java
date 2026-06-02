<template>
  <div class="user-list">
    <a-card>
      <template #title>
        <div class="card-title">
          <span>用户管理</span>
        </div>
      </template>

      <a-form layout="inline" :model="queryForm" style="margin-bottom: 16px">
        <a-form-item label="关键词">
          <a-input
            v-model:value="queryForm.keyword"
            placeholder="用户名/姓名/手机号"
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
        :data-source="userList"
        :loading="loading"
        :pagination="pagination"
        @change="handleTableChange"
        row-key="id"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'roles'">
            <a-tag v-for="role in record.roles" :key="role" color="blue">
              {{ role }}
            </a-tag>
          </template>
          <template v-if="column.key === 'status'">
            <a-tag :color="record.status === 1 ? 'green' : 'red'">
              {{ record.status === 1 ? '启用' : '停用' }}
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
      title="用户详情"
      width="500"
    >
      <a-descriptions :column="1" bordered v-if="currentUser">
        <a-descriptions-item label="ID">{{ currentUser.id }}</a-descriptions-item>
        <a-descriptions-item label="用户名">{{ currentUser.username }}</a-descriptions-item>
        <a-descriptions-item label="姓名">{{ currentUser.realName }}</a-descriptions-item>
        <a-descriptions-item label="手机号">{{ currentUser.phone }}</a-descriptions-item>
        <a-descriptions-item label="邮箱">{{ currentUser.email }}</a-descriptions-item>
        <a-descriptions-item label="角色">
          <a-tag v-for="role in currentUser.roles" :key="role" color="blue">
            {{ role }}
          </a-tag>
        </a-descriptions-item>
        <a-descriptions-item label="状态">
          <a-tag :color="currentUser.status === 1 ? 'green' : 'red'">
            {{ currentUser.status === 1 ? '启用' : '停用' }}
          </a-tag>
        </a-descriptions-item>
      </a-descriptions>
    </a-drawer>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { SearchOutlined } from '@ant-design/icons-vue'
import { authApi, type UserInfo } from '@/api'

const loading = ref(false)
const drawerVisible = ref(false)
const currentUser = ref<UserInfo | null>(null)

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

const userList = ref<UserInfo[]>([])

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 60 },
  { title: '用户名', dataIndex: 'username', key: 'username' },
  { title: '姓名', dataIndex: 'realName', key: 'realName' },
  { title: '手机号', dataIndex: 'phone', key: 'phone' },
  { title: '角色', key: 'roles' },
  { title: '状态', key: 'status', width: 80 },
  { title: '操作', key: 'action', width: 100 },
]

const fetchUsers = async () => {
  loading.value = true
  try {
    const res = await authApi.getUserList({
      page: pagination.current,
      pageSize: pagination.pageSize,
      keyword: queryForm.keyword,
    })
    userList.value = res.list || []
    pagination.total = res.total || 0
  } catch (error) {
    console.error('获取用户列表失败:', error)
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  pagination.current = 1
  fetchUsers()
}

const handleReset = () => {
  queryForm.keyword = ''
  queryForm.status = undefined
  handleSearch()
}

const handleTableChange = (pag: any) => {
  pagination.current = pag.current
  pagination.pageSize = pag.pageSize
  fetchUsers()
}

const handleViewDetail = (record: UserInfo) => {
  currentUser.value = record
  drawerVisible.value = true
}

onMounted(() => {
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
