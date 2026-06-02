import request from './request'

export interface RegisterRequest {
  patientId: number
  scheduleId: number
  cardType: string
  visitDate: string
  complaint?: string
  doctorId?: number
}

export interface RegisterResponse {
  appointmentId: number
  serialNumber: string
  status: string
  createdAt: string
}

export interface Registration {
  id: number
  patientId: number
  scheduleId: number
  departmentId: number
  doctorId: number
  visitType: string
  registrationType: string
  status: string
  queueNumber: number
  serialNumber: string
  symptom: string
  registerDate: string
  visitDate: string
  timeSlot: string
  fee: number
  departmentName: string
  doctorName: string
  createdAt: string
}

export const registrationApi = {
  register(data: RegisterRequest) {
    return request.post<any, RegisterResponse>('/registration/register', data)
  },

  getRegistration(id: number) {
    return request.get<any, Registration>(`/registration/${id}`)
  },

  getRegistrations(patientId: number, page = 1, pageSize = 10) {
    return request.get<any, { list: Registration[]; total: number }>('/registration/list', {
      params: { patientId, page, pageSize },
    })
  },

  cancelRegistration(id: number) {
    return request.put(`/registration/${id}/cancel`)
  },
}
