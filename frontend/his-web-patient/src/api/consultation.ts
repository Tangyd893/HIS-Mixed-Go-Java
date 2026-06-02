import request from './request'

export interface Consultation {
  id: number
  patientId: number
  doctorId?: number
  doctorName?: string
  departmentId?: number
  departmentName?: string
  complaint: string
  status: string
  startedAt?: string
  closedAt?: string
  createdAt: string
}

export interface ConsultationMessage {
  id: number
  consultationId: number
  senderId: number
  senderName: string
  content: string
  messageType: string
  createdAt: string
}

export const consultationApi = {
  // 创建问诊
  createConsultation: (data: { patientId: number; complaint: string }) => {
    return request.post<any, Consultation>('/outpatient/consultations', data)
  },

  // 获取问诊列表
  getConsultations: (params?: { patientId?: number; status?: string; page?: number; size?: number }) => {
    return request.get<any, { content: Consultation[]; totalElements: number }>('/outpatient/consultations', { params })
  },

  // 获取问诊详情
  getConsultation: (id: number) => {
    return request.get<any, Consultation>(`/outpatient/consultations/${id}`)
  },

  // 获取患者问诊记录
  getConsultationsByPatient: (patientId: number) => {
    return request.get<any, Consultation[]>(`/outpatient/consultations/patient/${patientId}`)
  },

  // 获取问诊消息
  getMessages: (consultationId: number) => {
    return request.get<any, ConsultationMessage[]>(`/outpatient/messages/${consultationId}`)
  },

  // 发送消息
  sendMessage: (consultationId: number, content: string) => {
    return request.post<any, ConsultationMessage>(`/outpatient/messages`, {
      consultationId,
      content,
      messageType: 'TEXT',
    })
  },
}
