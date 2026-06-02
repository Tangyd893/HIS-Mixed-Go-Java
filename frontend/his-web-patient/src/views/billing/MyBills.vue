<template>
  <div class="page">
    <header class="page-header">
      <a-button type="text" size="small" @click="$router.back()">
        <LeftOutlined />
      </a-button>
      <h1>费用查询</h1>
      <div></div>
    </header>

    <div class="content">
      <a-tabs v-model:activeKey="activeTab">
        <a-tab-pane key="unpaid" tab="待支付">
          <a-card v-for="item in unpaidList" :key="item.id" class="card-item">
            <div class="card-header">
              <a-tag color="red">{{ item.status }}</a-tag>
              <span class="card-amount">¥{{ item.amount }}</span>
            </div>
            <a-descriptions :column="1" size="small">
              <a-descriptions-item label="费用编号">{{ item.billNo }}</a-descriptions-item>
              <a-descriptions-item label="费用项目">{{ item.items }}</a-descriptions-item>
              <a-descriptions-item label="就诊科室">{{ item.department }}</a-descriptions-item>
              <a-descriptions-item label="开单医生">{{ item.doctor }}</a-descriptions-item>
              <a-descriptions-item label="日期">{{ item.date }}</a-descriptions-item>
            </a-descriptions>
            <a-button type="primary" block size="small" class="pay-btn" @click="handlePay(item)">
              立即支付
            </a-button>
          </a-card>
          <a-empty v-if="unpaidList.length === 0" description="暂无待支付费用" />
        </a-tab-pane>

        <a-tab-pane key="paid" tab="已支付">
          <a-empty description="暂无已支付记录" />
        </a-tab-pane>

        <a-tab-pane key="refund" tab="已退费">
          <a-empty description="暂无退费记录" />
        </a-tab-pane>
      </a-tabs>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { message } from 'ant-design-vue'
import { LeftOutlined } from '@ant-design/icons-vue'

const activeTab = ref('unpaid')

const unpaidList = [
  {
    id: 1,
    billNo: 'B20260511001',
    status: '待支付',
    amount: '368.50',
    items: '挂号费、检查费、药品费',
    department: '心内科',
    doctor: '张主任',
    date: '2026-05-11',
  },
]

const handlePay = (item: { billNo: string; amount: string }) => {
  message.success(`正在支付 ${item.billNo}，金额 ¥${item.amount}`)
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

.card-amount {
  font-size: 18px;
  font-weight: 600;
  color: #f5222d;
}

.pay-btn {
  margin-top: 12px;
}
</style>
