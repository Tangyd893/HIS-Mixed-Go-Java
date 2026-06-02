import request from './request'

export interface Schedule {
  id: number
  doctorId: number
  doctorName: string
  departmentId: number
  departmentName: string
  scheduleDate: string
  timeSlot: string
  maxPatients: number
  currentPatients: number
  status: number
  createdAt: string
}

export interface ScheduleQuery {
  page?: number
  pageSize?: number
  doctorId?: number
  departmentId?: number
  startDate?: string
  endDate?: string
  status?: number
}

export interface ScheduleForm {
  doctorId: number
  scheduleDate: string
  timeSlot: string
  maxPatients: number
}

export interface ScheduleBatchForm {
  doctorId: number
  startDate: string
  endDate: string
  timeSlots: { timeSlot: string; maxPatients: number }[]
}

export const scheduleApi = {
  getList(params: ScheduleQuery) {
    return request.get<any, { list: Schedule[]; total: number }>('/schedules', { params })
  },

  getById(id: number) {
    return request.get<any, Schedule>(`/schedules/${id}`)
  },

  create(data: ScheduleForm) {
    return request.post('/schedules', data)
  },

  batchCreate(data: ScheduleBatchForm) {
    return request.post('/schedules/batch', data)
  },

  update(id: number, data: Partial<ScheduleForm>) {
    return request.put(`/schedules/${id}`, data)
  },

  delete(id: number) {
    return request.delete(`/schedules/${id}`)
  },

  updateStatus(id: number, status: number) {
    return request.put(`/schedules/${id}/status`, { status })
  },
}
