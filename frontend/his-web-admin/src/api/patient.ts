import request from './request'

export interface Patient {
  id: number
  name: string
  gender: string
  age: number
  phone: string
  idCard: string
  address: string
  bloodType?: string
  allergies?: string
  status: number
  createdAt: string
  updatedAt: string
}

export interface PatientQuery {
  keyword?: string
  status?: number
  page?: number
  size?: number
}

export const patientApi = {
  // 获取患者列表
  getList: (params?: PatientQuery) => {
    return request.get<any, { content: Patient[]; totalElements: number }>('/user/patients', { params })
  },

  // 获取患者详情
  getById: (id: number) => {
    return request.get<any, Patient>(`/user/patients/${id}`)
  },

  // 创建患者
  create: (data: Partial<Patient>) => {
    return request.post<any, Patient>('/user/patients', data)
  },

  // 更新患者
  update: (id: number, data: Partial<Patient>) => {
    return request.put<any, Patient>(`/user/patients/${id}`, data)
  },

  // 更新患者状态
  updateStatus: (id: number, status: number) => {
    return request.put<any, void>(`/user/patients/${id}/status`, { status })
  },
}
