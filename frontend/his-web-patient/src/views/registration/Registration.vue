<template>
  <div class="page">
    <header class="page-header">
      <a-button type="text" size="small" @click="$router.back()">
        <LeftOutlined />
      </a-button>
      <h1>在线挂号</h1>
      <div></div>
    </header>

    <div class="content">
      <a-steps :current="current" size="small" class="steps">
        <a-step title="选择科室" />
        <a-step title="选择医生" />
        <a-step title="确认挂号" />
      </a-steps>

      <div class="step-content">
        <div v-if="current === 0">
          <a-card title="选择科室" class="section-card">
            <a-list :split="true" :loading="loading">
              <a-list-item v-for="dept in departments" :key="dept.value" @click="selectDepartment(dept)">
                <a-list-item-meta>
                  <template #title>{{ dept.label }}</template>
                  <template #description>{{ dept.desc }}</template>
                  <template #avatar>
                    <MedicineBoxOutlined :style="{ fontSize: '24px', color: '#1890ff' }" />
                  </template>
                </a-list-item-meta>
                <template #extra>
                  <RightOutlined :style="{ color: '#ccc' }" />
                </template>
              </a-list-item>
            </a-list>
          </a-card>
        </div>

        <div v-else-if="current === 1">
          <a-card title="选择医生" class="section-card">
            <a-list :split="true" :loading="loading">
              <a-list-item v-for="doc in doctors" :key="doc.value" @click="selectDoctor(doc)">
                <a-list-item-meta>
                  <template #title>{{ doc.label }}</template>
                  <template #description>{{ doc.title }} | {{ doc.deptLabel }}</template>
                  <template #avatar>
                    <a-avatar :size="40" icon="user" />
                  </template>
                </a-list-item-meta>
                <template #extra>
                  <RightOutlined :style="{ color: '#ccc' }" />
                </template>
              </a-list-item>
            </a-list>
          </a-card>
        </div>

        <div v-else>
          <a-card title="确认挂号信息" class="section-card">
            <a-descriptions :column="1" size="small">
              <a-descriptions-item label="就诊科室">{{ selectedDept?.label }}</a-descriptions-item>
              <a-descriptions-item label="就诊医生">{{ selectedDoctor?.label }}</a-descriptions-item>
              <a-descriptions-item label="挂号费用">¥{{ selectedDoctor?.fee || 35 }}.00</a-descriptions-item>
            </a-descriptions>
            <a-button type="primary" block size="large" class="submit-btn" :loading="loading" @click="handleSubmit">
              确认挂号
            </a-button>
          </a-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { message } from 'ant-design-vue'
import { LeftOutlined, RightOutlined, MedicineBoxOutlined } from '@ant-design/icons-vue'
import { useAuthStore } from '@/stores/auth'
import { scheduleApi, registrationApi, departmentApi, type ScheduleSlot } from '@/api'

const router = useRouter()
const authStore = useAuthStore()

const current = ref(0)
const loading = ref(false)
const selectedDate = ref(new Date().toISOString().split('T')[0])

interface Department {
  label: string
  value: number
  desc: string
}

interface Doctor {
  label: string
  value: number
  title: string
  deptLabel: string
  slotId: number
  fee: number
}

const departments = ref<Department[]>([])

const loadDepartments = async () => {
  try {
    const res = await departmentApi.getList()
    departments.value = (res || []).map(dept => ({
      label: dept.name,
      value: dept.id,
      desc: `${dept.name}专科`
    }))
  } catch (error) {
    message.error('获取科室列表失败')
  }
}

onMounted(() => {
  loadDepartments()
})

const doctors = ref<Doctor[]>([])
const slots = ref<ScheduleSlot[]>([])

const selectedDept = ref<Department | null>(null)
const selectedDoctor = ref<Doctor | null>(null)

const selectDepartment = async (dept: Department) => {
  selectedDept.value = dept
  loading.value = true
  try {
    const response = await scheduleApi.getSlotsByDate(dept.value, selectedDate.value)
    slots.value = response
    doctors.value = response.map(slot => ({
      label: slot.doctorName,
      value: slot.doctorId,
      title: slot.cardType || '主治医师',
      deptLabel: dept.label,
      slotId: slot.slotId,
      fee: 35,
    }))
    current.value = 1
  } catch (error) {
    message.error('获取医生列表失败')
  } finally {
    loading.value = false
  }
}

const selectDoctor = (doctor: Doctor) => {
  selectedDoctor.value = doctor
  current.value = 2
}

const handleSubmit = async () => {
  if (!authStore.isLoggedIn) {
    message.warning('请先登录')
    router.push('/login')
    return
  }

  if (!selectedDoctor.value || !selectedDept.value) {
    message.warning('请选择科室和医生')
    return
  }

  loading.value = true
  try {
    const response = await registrationApi.register({
      patientId: authStore.userId!,
      scheduleId: selectedDoctor.value.slotId,
      cardType: '普通号',
      visitDate: selectedDate.value,
      doctorId: selectedDoctor.value.value,
    })
    message.success(`挂号成功！序号：${response.serialNumber}`)
    router.push('/')
  } catch (error) {
    message.error('挂号失败')
  } finally {
    loading.value = false
  }
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
  width: 100%;
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

.steps {
  margin-bottom: 24px;
  padding: 16px;
  background: #fff;
  border-radius: 8px;
}

.section-card {
  border-radius: 8px;
}

.submit-btn {
  margin-top: 24px;
}
</style>
