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
          <a-card v-for="item in prescriptionList" :key="item.id" class="card-item">
            <div class="card-header">
              <a-tag color="blue">{{ item.status }}</a-tag>
              <span class="card-date">{{ item.date }}</span>
            </div>
            <a-descriptions :column="1" size="small">
              <a-descriptions-item label="处方编号">{{ item.prescriptionNo }}</a-descriptions-item>
              <a-descriptions-item label="开方医生">{{ item.doctor }}</a-descriptions-item>
              <a-descriptions-item label="诊断">{{ item.diagnosis }}</a-descriptions-item>
            </a-descriptions>
            <a-divider style="margin: 8px 0" />
            <div class="drug-list">
              <div v-for="drug in item.drugs" :key="drug.name" class="drug-item">
                <span class="drug-name">{{ drug.name }}</span>
                <span class="drug-usage">{{ drug.usage }}</span>
              </div>
            </div>
          </a-card>
          <a-empty v-if="prescriptionList.length === 0" description="暂无处方记录" />
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
import { ref } from 'vue'
import { LeftOutlined } from '@ant-design/icons-vue'

const activeTab = ref('all')

const prescriptionList = [
  {
    id: 1,
    prescriptionNo: 'P20260511001',
    status: '使用中',
    date: '2026-05-11',
    doctor: '张主任',
    diagnosis: '高血压',
    drugs: [
      { name: '硝苯地平片', usage: '每日1次，每次1片，口服' },
      { name: '阿托伐他汀钙片', usage: '每日1次，每次1片，口服' },
    ],
  },
]
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
