import request from './request'

export interface FollowupPlan {
  id: number
  patientId: number
  planName: string
  followupType: string
  startDate: string
  endDate: string
  frequency: string
  status: string
  createdAt: string
}

export interface FollowupRecord {
  id: number
  planId: number
  patientId: number
  followupMethod: string
  content: string
  patientCondition: string
  advice: string
  followupDate: string
  createdAt: string
}

export const followupApi = {
  // 获取随访计划列表
  getPlans: (params?: { patientId?: number; status?: string; page?: number; size?: number }) => {
    return request.get<any, { content: FollowupPlan[]; totalElements: number }>('/followup/plans', { params })
  },

  // 获取计划详情
  getPlan: (id: number) => {
    return request.get<any, FollowupPlan>(`/followup/plans/${id}`)
  },

  // 获取患者随访计划
  getPlansByPatient: (patientId: number) => {
    return request.get<any, FollowupPlan[]>(`/followup/plans/patient/${patientId}`)
  },

  // 获取随访记录
  getRecords: (planId: number) => {
    return request.get<any, FollowupRecord[]>(`/followup/records/plan/${planId}`)
  },

  // 确认随访
  confirmFollowup: (recordId: number) => {
    return request.put<any, void>(`/followup/records/${recordId}/confirm`)
  },
}
