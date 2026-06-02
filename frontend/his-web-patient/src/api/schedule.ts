import request from './request'

export interface ScheduleSlot {
  slotId: number
  doctorId: number
  doctorName: string
  departmentId: number
  departmentName: string
  date: string
  timeSlot: string
  cardType: string
  maxCount: number
  bookedCount: number
  status: string
}

export interface GetSlotsRequest {
  departmentId?: number
  doctorId?: number
  date?: string
  startDate?: string
  endDate?: string
  page?: number
  pageSize?: number
}

export const scheduleApi = {
  getSlots(params: GetSlotsRequest) {
    return request.get<any, { slots: ScheduleSlot[]; total: number }>('/schedule/slots', { params })
  },

  getSlotsByDate(departmentId: number, date: string) {
    return request.get<any, ScheduleSlot[]>('/schedule/slots', {
      params: { departmentId, date },
    })
  },
}
