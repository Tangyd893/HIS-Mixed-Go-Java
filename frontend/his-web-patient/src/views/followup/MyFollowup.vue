<template>
  <div class="page">
    <header class="page-header">
      <a-button type="text" size="small" @click="$router.back()">
        <LeftOutlined />
      </a-button>
      <h1>我的随访</h1>
      <div></div>
    </header>

    <div class="content">
      <a-tabs v-model:activeKey="activeTab">
        <a-tab-pane key="upcoming" tab="待随访">
          <a-card v-for="item in followupList" :key="item.id" class="card-item">
            <div class="card-header">
              <a-tag :color="item.type === '定期' ? 'blue' : 'orange'">{{ item.type }}</a-tag>
              <span class="card-status">{{ item.status }}</span>
            </div>
            <a-descriptions :column="1" size="small">
              <a-descriptions-item label="随访计划">{{ item.planName }}</a-descriptions-item>
              <a-descriptions-item label="计划时间">{{ item.plannedDate }}</a-descriptions-item>
              <a-descriptions-item label="负责医生">{{ item.doctor }}</a-descriptions-item>
              <a-descriptions-item label="随访内容">{{ item.content }}</a-descriptions-item>
            </a-descriptions>
            <div class="card-actions">
              <a-button v-if="item.status === '待随访'" type="primary" size="small" block @click="handleConfirm(item)">
                确认随访
              </a-button>
            </div>
          </a-card>
          <a-empty v-if="followupList.length === 0" description="暂无待随访计划" />
        </a-tab-pane>

        <a-tab-pane key="completed" tab="已完成">
          <a-empty description="暂无已完成的随访" />
        </a-tab-pane>

        <a-tab-pane key="missed" tab="已逾期">
          <a-empty description="暂无逾期随访" />
        </a-tab-pane>
      </a-tabs>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { message } from 'ant-design-vue'
import { LeftOutlined } from '@ant-design/icons-vue'

const activeTab = ref('upcoming')

const followupList = [
  {
    id: 1,
    planName: '高血压定期随访',
    type: '定期',
    status: '待随访',
    plannedDate: '2026-05-25',
    doctor: '张主任',
    content: '测量血压、询问用药情况、调整治疗方案',
  },
  {
    id: 2,
    planName: '术后随访',
    type: '临时',
    status: '待随访',
    plannedDate: '2026-05-18',
    doctor: '李医生',
    content: '伤口检查、恢复情况评估',
  },
]

const handleConfirm = (item: { planName: string }) => {
  message.success(`正在为您安排随访: ${item.planName}`)
}
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

.card-status {
  font-size: 13px;
  color: #fa8c16;
}

.card-actions {
  margin-top: 12px;
}
</style>
