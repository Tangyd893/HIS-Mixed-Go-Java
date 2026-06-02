<template>
  <div class="page">
    <header class="page-header">
      <a-button type="text" size="small" @click="$router.back()">
        <LeftOutlined />
      </a-button>
      <h1>在线问诊</h1>
      <div></div>
    </header>

    <div class="content">
      <!-- 发起问诊 -->
      <a-card class="section-card" title="发起问诊">
        <a-form :model="formData" layout="vertical">
          <a-form-item label="问诊描述">
            <a-textarea v-model:value="formData.complaint" placeholder="请描述您的症状" :rows="3" />
          </a-form-item>
          <a-form-item>
            <a-button type="primary" block :loading="creating" @click="handleCreate">
              提交问诊申请
            </a-button>
          </a-form-item>
        </a-form>
      </a-card>

      <!-- 问诊记录 -->
      <a-card class="section-card" title="问诊记录">
        <a-spin :spinning="loading">
          <template v-if="consultationList.length > 0">
            <a-list :data-source="consultationList" :split="true">
              <template #renderItem="{ item }">
                <a-list-item>
                  <a-list-item-meta>
                    <template #title>
                      <div class="consult-header">
                        <span>问诊 #{{ item.id }}</span>
                        <a-tag :color="getStatusColor(item.status)">{{ getStatusText(item.status) }}</a-tag>
                      </div>
                    </template>
                    <template #description>
                      <div>
                        <p class="consult-desc">{{ item.complaint }}</p>
                        <p class="consult-time">{{ formatDate(item.createdAt) }}</p>
                        <p v-if="item.doctorName" class="consult-doctor">接诊医生：{{ item.doctorName }}</p>
                      </div>
                    </template>
                  </a-list-item-meta>
                  <template #extra>
                    <a-button type="link" size="small" @click="viewDetail(item)">查看详情</a-button>
                  </template>
                </a-list-item>
              </template>
            </a-list>
          </template>
          <a-empty v-else description="暂无问诊记录" />
        </a-spin>
      </a-card>
    </div>

    <!-- 问诊详情弹窗 -->
    <a-modal v-model:open="detailVisible" title="问诊详情" :footer="null" width="600px">
      <template v-if="currentConsultation">
        <a-descriptions :column="2" bordered size="small">
          <a-descriptions-item label="问诊编号" :span="2">#{{ currentConsultation.id }}</a-descriptions-item>
          <a-descriptions-item label="状态">
            <a-tag :color="getStatusColor(currentConsultation.status)">
              {{ getStatusText(currentConsultation.status) }}
            </a-tag>
          </a-descriptions-item>
          <a-descriptions-item label="创建时间">{{ formatDate(currentConsultation.createdAt) }}</a-descriptions-item>
          <a-descriptions-item label="接诊医生" :span="2">
            {{ currentConsultation.doctorName || '待分配' }}
          </a-descriptions-item>
          <a-descriptions-item label="问诊描述" :span="2">{{ currentConsultation.complaint }}</a-descriptions-item>
        </a-descriptions>

        <!-- 消息列表 -->
        <a-divider>问诊消息</a-divider>
        <a-spin :spinning="messagesLoading">
          <div class="message-list" v-if="messageList.length > 0">
            <div v-for="msg in messageList" :key="msg.id" class="message-item" :class="{ 'self': msg.senderId === authStore.userId }">
              <div class="message-content">{{ msg.content }}</div>
              <div class="message-time">{{ formatDate(msg.createdAt) }}</div>
            </div>
          </div>
          <a-empty v-else description="暂无消息" />
        </a-spin>

        <!-- 发送消息 -->
        <div class="send-area" v-if="currentConsultation.status === 'IN_PROGRESS'">
          <a-input-group compact>
            <a-input v-model:value="newMessage" style="width: calc(100% - 80px)" placeholder="输入消息" @pressEnter="sendMessage" />
            <a-button type="primary" style="width: 80px" :loading="sending" @click="sendMessage">发送</a-button>
          </a-input-group>
        </div>
      </template>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { LeftOutlined } from '@ant-design/icons-vue'
import { message } from 'ant-design-vue'
import { consultationApi, type Consultation, type ConsultationMessage } from '@/api'
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()
const loading = ref(false)
const creating = ref(false)
const consultationList = ref<Consultation[]>([])
const detailVisible = ref(false)
const currentConsultation = ref<Consultation | null>(null)
const messagesLoading = ref(false)
const messageList = ref<ConsultationMessage[]>([])
const newMessage = ref('')
const sending = ref(false)

const formData = ref({
  complaint: '',
})

const fetchConsultations = async () => {
  if (!authStore.userId) return
  
  loading.value = true
  try {
    const res = await consultationApi.getConsultationsByPatient(authStore.userId)
    consultationList.value = Array.isArray(res) ? res : (res as any).content || []
  } catch (error) {
    console.error('获取问诊记录失败:', error)
  } finally {
    loading.value = false
  }
}

const handleCreate = async () => {
  if (!authStore.userId || !formData.value.complaint) {
    message.warning('请填写问诊描述')
    return
  }
  
  creating.value = true
  try {
    await consultationApi.createConsultation({
      patientId: authStore.userId,
      complaint: formData.value.complaint,
    })
    message.success('问诊申请已提交')
    formData.value.complaint = ''
    fetchConsultations()
  } catch (error) {
    console.error('创建问诊失败:', error)
  } finally {
    creating.value = false
  }
}

const viewDetail = async (item: Consultation) => {
  currentConsultation.value = item
  detailVisible.value = true
  await fetchMessages(item.id)
}

const fetchMessages = async (consultationId: number) => {
  messagesLoading.value = true
  try {
    const res = await consultationApi.getMessages(consultationId)
    messageList.value = Array.isArray(res) ? res : (res as any).content || []
  } catch (error) {
    console.error('获取消息失败:', error)
  } finally {
    messagesLoading.value = false
  }
}

const sendMessage = async () => {
  if (!currentConsultation.value || !newMessage.value.trim()) return
  
  sending.value = true
  try {
    await consultationApi.sendMessage(currentConsultation.value.id, newMessage.value)
    newMessage.value = ''
    await fetchMessages(currentConsultation.value.id)
  } catch (error) {
    console.error('发送消息失败:', error)
  } finally {
    sending.value = false
  }
}

const getStatusColor = (status: string) => {
  const map: Record<string, string> = {
    PENDING: 'orange',
    IN_PROGRESS: 'blue',
    CLOSED: 'green',
  }
  return map[status] || 'default'
}

const getStatusText = (status: string) => {
  const map: Record<string, string> = {
    PENDING: '待接诊',
    IN_PROGRESS: '问诊中',
    CLOSED: '已结束',
  }
  return map[status] || status
}

const formatDate = (dateStr?: string) => {
  if (!dateStr) return '-'
  return new Date(dateStr).toLocaleString('zh-CN')
}

onMounted(() => {
  fetchConsultations()
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

.section-card {
  border-radius: 8px;
  margin-bottom: 16px;
}

.consult-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.consult-desc {
  color: #666;
  margin: 4px 0;
}

.consult-time {
  color: #999;
  font-size: 12px;
  margin: 4px 0;
}

.consult-doctor {
  color: #1890ff;
  font-size: 12px;
  margin: 4px 0;
}

.message-list {
  max-height: 300px;
  overflow-y: auto;
  padding: 8px;
}

.message-item {
  margin-bottom: 12px;
  text-align: left;
}

.message-item.self {
  text-align: right;
}

.message-content {
  display: inline-block;
  padding: 8px 12px;
  background: #f0f0f0;
  border-radius: 8px;
  max-width: 80%;
  word-break: break-all;
}

.message-item.self .message-content {
  background: #1890ff;
  color: #fff;
}

.message-time {
  font-size: 11px;
  color: #999;
  margin-top: 4px;
}

.send-area {
  margin-top: 16px;
  padding-top: 16px;
  border-top: 1px solid #f0f0f0;
}
</style>
