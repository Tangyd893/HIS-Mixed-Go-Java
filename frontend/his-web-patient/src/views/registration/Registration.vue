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
            <a-list :split="true">
              <a-list-item v-for="dept in departments" :key="dept.value" @click="nextStep">
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
            <a-list :split="true">
              <a-list-item v-for="doc in doctors" :key="doc.value" @click="nextStep">
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
              <a-descriptions-item label="就诊科室">心内科</a-descriptions-item>
              <a-descriptions-item label="就诊医生">张主任</a-descriptions-item>
              <a-descriptions-item label="挂号费用">¥35.00</a-descriptions-item>
            </a-descriptions>
            <a-button type="primary" block size="large" class="submit-btn" @click="handleSubmit">
              确认挂号
            </a-button>
          </a-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { message } from 'ant-design-vue'
import { LeftOutlined, RightOutlined, MedicineBoxOutlined } from '@ant-design/icons-vue'

const current = ref(0)

const departments = [
  { label: '心内科', value: 'cardiology', desc: '诊治心脏及血管相关疾病' },
  { label: '呼吸内科', value: 'respiratory', desc: '诊治呼吸系统相关疾病' },
  { label: '消化内科', value: 'digestive', desc: '诊治消化系统相关疾病' },
  { label: '神经内科', value: 'neurology', desc: '诊治神经系统相关疾病' },
  { label: '骨科', value: 'orthopedics', desc: '诊治骨骼及关节相关疾病' },
]

const doctors = [
  { label: '张主任', value: 'zhang', title: '主任医师', deptLabel: '心内科' },
  { label: '李医生', value: 'li', title: '副主任医师', deptLabel: '心内科' },
  { label: '王医生', value: 'wang', title: '主治医师', deptLabel: '心内科' },
]

const nextStep = () => {
  if (current.value < 2) {
    current.value++
  }
}

const handleSubmit = () => {
  message.success('挂号成功')
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
