<template>
  <div class="page">
    <header class="page-header">
      <a-button type="text" size="small" @click="$router.back()">
        <LeftOutlined />
      </a-button>
      <h1>我的挂号</h1>
      <div></div>
    </header>

    <div class="content">
      <a-tabs v-model:activeKey="activeTab">
        <a-tab-pane key="upcoming" tab="待就诊">
          <a-card v-if="hasData" class="card-item" v-for="item in upcomingList" :key="item.id">
            <div class="card-header">
              <a-tag color="blue">{{ item.status }}</a-tag>
              <span class="card-no">{{ item.queueNo }}</span>
            </div>
            <a-descriptions :column="1" size="small">
              <a-descriptions-item label="就诊科室">{{ item.department }}</a-descriptions-item>
              <a-descriptions-item label="就诊医生">{{ item.doctor }}</a-descriptions-item>
              <a-descriptions-item label="就诊时间">{{ item.time }}</a-descriptions-item>
            </a-descriptions>
          </a-card>
          <a-empty v-else description="暂无待就诊记录" />
        </a-tab-pane>

        <a-tab-pane key="completed" tab="已完成">
          <a-empty description="暂无已完成记录" />
        </a-tab-pane>

        <a-tab-pane key="cancelled" tab="已取消">
          <a-empty description="暂无已取消记录" />
        </a-tab-pane>
      </a-tabs>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { LeftOutlined } from '@ant-design/icons-vue'

const activeTab = ref('upcoming')
const hasData = ref(true)

const upcomingList = [
  {
    id: 1,
    status: '已预约',
    queueNo: '排队号: A012',
    department: '心内科',
    doctor: '张主任',
    time: '2026-05-15 09:00-09:30',
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

.card-no {
  font-size: 13px;
  color: #666;
}
</style>
