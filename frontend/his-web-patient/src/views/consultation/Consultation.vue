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
      <a-card class="section-card" title="选择问诊方式">
        <a-radio-group v-model:value="consultType" :style="{ display: 'flex', gap: '16px' }">
          <a-radio value="text">图文问诊</a-radio>
          <a-radio value="voice">语音问诊</a-radio>
          <a-radio value="video">视频问诊</a-radio>
        </a-radio-group>
      </a-card>

      <a-card class="section-card" title="选择接诊医生">
        <a-list :split="true">
          <a-list-item v-for="doc in doctorList" :key="doc.id">
            <a-list-item-meta>
              <template #title>{{ doc.name }} <a-tag color="green" size="small">{{ doc.status }}</a-tag></template>
              <template #description>{{ doc.title }} | {{ doc.department }}</template>
              <template #avatar>
                <a-avatar :size="44" icon="user" />
              </template>
            </a-list-item-meta>
            <template #extra>
              <a-button type="primary" size="small" @click="startConsult">开始问诊</a-button>
            </template>
          </a-list-item>
        </a-list>
      </a-card>

      <a-card class="section-card" title="问诊记录">
        <a-empty description="暂无问诊记录" />
      </a-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { message } from 'ant-design-vue'
import { LeftOutlined } from '@ant-design/icons-vue'

const consultType = ref('text')

const doctorList = [
  { id: 1, name: '张主任', title: '主任医师', department: '心内科', status: '在线' },
  { id: 2, name: '李医生', title: '副主任医师', department: '呼吸内科', status: '在线' },
  { id: 3, name: '王医生', title: '主治医师', department: '消化内科', status: '离线' },
]

const startConsult = () => {
  message.success('正在连接医生...')
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

.section-card {
  border-radius: 8px;
  margin-bottom: 16px;
}
</style>
