<template>
  <div class="page">
    <header class="page-header">
      <a-button type="text" size="small" @click="$router.back()">
        <LeftOutlined />
      </a-button>
      <h1>我的处方</h1>
      <div></div>
    </header>

    <div class="content">
      <a-tabs v-model:activeKey="activeTab">
        <a-tab-pane key="all" tab="全部">
          <a-spin :spinning="loading">
            <a-card v-for="item in prescriptionList" :key="item.id" class="card-item">
              <div class="card-header">
                <a-tag :color="item.status === 'DISPENSED' ? 'green' : 'blue'">{{ item.status }}</a-tag>
                <span class="card-date">{{ item.createdAt?.split('T')[0] }}</span>
              </div>
              <a-descriptions :column="1" size="small">
                <a-descriptions-item label="处方编号">{{ item.prescriptionNo }}</a-descriptions-item>
                <a-descriptions-item label="开方医生">{{ item.doctorName }}</a-descriptions-item>
                <a-descriptions-item label="诊断">{{ item.diagnosis }}</a-descriptions-item>
              </a-descriptions>
              <a-divider style="margin: 8px 0" />
              <div class="drug-list">
                <div v-for="drug in item.items" :key="drug.id" class="drug-item">
                  <span class="drug-name">{{ drug.drugName }}</span>
                  <span class="drug-usage">{{ drug.usage }} {{ drug.frequency }}</span>
                </div>
              </div>
            </a-card>
            <a-empty v-if="prescriptionList.length === 0" description="暂无处方记录" />
          </a-spin>
        </a-tab-pane>

        <a-tab-pane key="active" tab="进行中">
          <a-empty description="暂无进行中的处方" />
        </a-tab-pane>

        <a-tab-pane key="completed" tab="已完成">
          <a-empty description="暂无已完成的处方" />
        </a-tab-pane>
      </a-tabs>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { LeftOutlined } from '@ant-design/icons-vue'
import { useAuthStore } from '@/stores/auth'
import { pharmacyApi, type Prescription } from '@/api'

const authStore = useAuthStore()
const activeTab = ref('all')
const loading = ref(false)
const prescriptionList = ref<Prescription[]>([])

const fetchPrescriptions = async () => {
  if (!authStore.isLoggedIn) {
    message.warning('请先登录')
    return
  }

  loading.value = true
  try {
    const response = await pharmacyApi.getPrescriptions(authStore.userId!)
    prescriptionList.value = response.list
  } catch (error) {
    message.error('获取处方列表失败')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchPrescriptions()
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

.drug-list {
  padding: 4px 0;
}

.drug-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 6px 0;
}

.drug-name {
  font-size: 14px;
  color: #333;
  font-weight: 500;
}

.drug-usage {
  font-size: 12px;
  color: #999;
}
</style>
