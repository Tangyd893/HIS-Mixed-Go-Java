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
      <a-tabs v-model:activeKey="activeTab" @change="handleTabChange">
        <a-tab-pane key="UNPAID" tab="待支付">
          <a-spin :spinning="loading">
            <template v-if="unpaidList.length > 0">
              <a-card v-for="item in unpaidList" :key="item.id" class="card-item">
                <div class="card-header">
                  <a-tag color="red">待支付</a-tag>
                  <span class="card-amount">¥{{ item.amount?.toFixed(2) }}</span>
                </div>
                <a-descriptions :column="1" size="small">
                  <a-descriptions-item label="费用编号">{{ item.billNo }}</a-descriptions-item>
                  <a-descriptions-item label="费用项目">{{ item.itemName }}</a-descriptions-item>
                  <a-descriptions-item label="费用类型">{{ item.billType }}</a-descriptions-item>
                  <a-descriptions-item label="日期">{{ formatDate(item.createdAt) }}</a-descriptions-item>
                </a-descriptions>
                <a-button type="primary" block size="small" class="pay-btn" @click="handlePay(item)">
                  立即支付
                </a-button>
              </a-card>
            </template>
            <a-empty v-else description="暂无待支付费用" />
          </a-spin>
        </a-tab-pane>

        <a-tab-pane key="PAID" tab="已支付">
          <a-spin :spinning="loading">
            <template v-if="paidList.length > 0">
              <a-card v-for="item in paidList" :key="item.id" class="card-item">
                <div class="card-header">
                  <a-tag color="green">已支付</a-tag>
                  <span class="card-amount paid">¥{{ item.amount?.toFixed(2) }}</span>
                </div>
                <a-descriptions :column="1" size="small">
                  <a-descriptions-item label="费用编号">{{ item.billNo }}</a-descriptions-item>
                  <a-descriptions-item label="费用项目">{{ item.itemName }}</a-descriptions-item>
                  <a-descriptions-item label="支付时间">{{ formatDate(item.paidAt) }}</a-descriptions-item>
                </a-descriptions>
              </a-card>
            </template>
            <a-empty v-else description="暂无已支付记录" />
          </a-spin>
        </a-tab-pane>

        <a-tab-pane key="REFUND" tab="已退费">
          <a-spin :spinning="loading">
            <a-empty description="暂无退费记录" />
          </a-spin>
        </a-tab-pane>
      </a-tabs>
    </div>

    <!-- 支付弹窗 -->
    <a-modal v-model:open="payModalVisible" title="确认支付" @confirm="confirmPay" :confirmLoading="paying">
      <p>费用编号：{{ currentBill?.billNo }}</p>
      <p>费用项目：{{ currentBill?.itemName }}</p>
      <p class="pay-amount">支付金额：<span>¥{{ currentBill?.amount?.toFixed(2) }}</span></p>
      <a-radio-group v-model:value="payMethod" class="pay-method">
        <a-radio-button value="WECHAT">微信支付</a-radio-button>
        <a-radio-button value="ALIPAY">支付宝</a-radio-button>
        <a-radio-button value="CARD">银行卡</a-radio-button>
      </a-radio-group>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { LeftOutlined } from '@ant-design/icons-vue'
import { message } from 'ant-design-vue'
import { billingApi, type BillItem } from '@/api'
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()
const activeTab = ref('UNPAID')
const loading = ref(false)
const billList = ref<BillItem[]>([])
const payModalVisible = ref(false)
const currentBill = ref<BillItem | null>(null)
const payMethod = ref('WECHAT')
const paying = ref(false)

const unpaidList = computed(() => billList.value.filter(item => item.status === 'UNPAID'))
const paidList = computed(() => billList.value.filter(item => item.status === 'PAID'))

const fetchBills = async () => {
  if (!authStore.userId) return
  
  loading.value = true
  try {
    const res = await billingApi.getBillItems({ patientId: authStore.userId })
    billList.value = res.content || res || []
  } catch (error) {
    console.error('获取账单失败:', error)
  } finally {
    loading.value = false
  }
}

const handleTabChange = () => {
  fetchBills()
}

const handlePay = (item: BillItem) => {
  currentBill.value = item
  payModalVisible.value = true
}

const confirmPay = async () => {
  if (!currentBill.value || !authStore.userId) return
  
  paying.value = true
  try {
    await billingApi.createPayment({
      patientId: authStore.userId,
      billItemIds: [currentBill.value.id],
      paymentMethod: payMethod.value,
    })
    message.success('支付成功')
    payModalVisible.value = false
    fetchBills()
  } catch (error) {
    console.error('支付失败:', error)
  } finally {
    paying.value = false
  }
}

const formatDate = (dateStr?: string) => {
  if (!dateStr) return '-'
  return new Date(dateStr).toLocaleDateString('zh-CN')
}

onMounted(() => {
  fetchBills()
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

.card-amount {
  font-size: 18px;
  font-weight: 600;
  color: #f5222d;
}

.card-amount.paid {
  color: #52c41a;
}

.pay-btn {
  margin-top: 12px;
}

.pay-amount {
  font-size: 16px;
  margin: 16px 0;
}

.pay-amount span {
  font-size: 24px;
  font-weight: 600;
  color: #f5222d;
}

.pay-method {
  width: 100%;
  display: flex;
}

.pay-method .ant-radio-button-wrapper {
  flex: 1;
  text-align: center;
}
</style>
