<template>
  <div class="page">
    <header class="page-header">
      <a-button type="text" size="small" @click="$router.back()">
        <LeftOutlined />
      </a-button>
      <h1>检查报告</h1>
      <div></div>
    </header>

    <div class="content">
      <a-tabs v-model:activeKey="activeTab">
        <a-tab-pane key="all" tab="全部">
          <a-card v-for="item in reportList" :key="item.id" class="card-item">
            <div class="card-header">
              <a-tag :color="item.status === '已完成' ? 'green' : 'blue'">{{ item.status }}</a-tag>
              <span class="card-date">{{ item.date }}</span>
            </div>
            <a-descriptions :column="1" size="small">
              <a-descriptions-item label="报告编号">{{ item.reportNo }}</a-descriptions-item>
              <a-descriptions-item label="检查项目">{{ item.projectName }}</a-descriptions-item>
              <a-descriptions-item label="检查科室">{{ item.department }}</a-descriptions-item>
              <a-descriptions-item label="开单医生">{{ item.doctor }}</a-descriptions-item>
            </a-descriptions>
            <a-button block size="small" class="view-btn" :disabled="item.status !== '已完成'" @click="handleView(item)">
              查看报告
            </a-button>
          </a-card>
          <a-empty v-if="reportList.length === 0" description="暂无检查报告" />
        </a-tab-pane>

        <a-tab-pane key="pending" tab="待检查">
          <a-empty description="暂无待检查项目" />
        </a-tab-pane>

        <a-tab-pane key="completed" tab="已完成">
          <a-empty description="暂无已完成的报告" />
        </a-tab-pane>
      </a-tabs>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { message } from 'ant-design-vue'
import { LeftOutlined } from '@ant-design/icons-vue'

const activeTab = ref('all')

const reportList = [
  {
    id: 1,
    reportNo: 'R20260511001',
    projectName: '血常规',
    department: '检验科',
    doctor: '张主任',
    date: '2026-05-11',
    status: '已完成',
  },
  {
    id: 2,
    reportNo: 'R20260510002',
    projectName: '心电图',
    department: '心内科',
    doctor: '张主任',
    date: '2026-05-10',
    status: '待检查',
  },
]

const handleView = (item: { reportNo: string }) => {
  message.info(`正在加载报告 ${item.reportNo}`)
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

.card-date {
  font-size: 13px;
  color: #999;
}

.view-btn {
  margin-top: 12px;
}
</style>
