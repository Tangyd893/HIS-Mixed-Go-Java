import request from './request'

export interface Doctor {
  id: number
  userId: number
  name: string
  gender: string
  title: string
  departmentId: number
  departmentName: string
  specialty: string
  introduction: string
  avatar: string
  status: number
  createdAt: string
}

export interface DoctorQuery {
  page?: number
  pageSize?: number
  keyword?: string
  departmentId?: number
  status?: number
}

export interface DoctorForm {
  userId: number
  name: string
  gender: string
  title: string
  departmentId: number
  specialty: string
  introduction: string
  avatar?: string
}

export const doctorApi = {
  getList(params: DoctorQuery) {
    return request.get<any, { list: Doctor[]; total: number }>('/doctors', { params })
  },

  getById(id: number) {
    return request.get<any, Doctor>(`/doctors/${id}`)
  },

  create(data: DoctorForm) {
    return request.post('/doctors', data)
  },

  update(id: number, data: Partial<DoctorForm>) {
    return request.put(`/doctors/${id}`, data)
  },

  delete(id: number) {
    return request.delete(`/doctors/${id}`)
  },

  updateStatus(id: number, status: number) {
    return request.put(`/doctors/${id}/status`, { status })
  },
}
