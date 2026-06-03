import request from './request'

export interface Prescription {
  id: number
  prescriptionNo: string
  patientId: number
  doctorId: number
  departmentId?: number
  prescriptionType?: string
  status: string
  diagnosisSummary?: string
  totalAmount?: number
  items: PrescriptionItem[]
  createdAt: string
  updatedAt: string
}

export interface PrescriptionItem {
  id: number
  prescriptionId: number
  drugId: number
  drugName: string
  specification?: string
  quantity: number
  unit?: string
  dosage?: string
  frequency?: string
  usageMethod?: string
  days?: number
  unitPrice?: number
  subtotal?: number
}

export const prescriptionApi = {
  getPrescription(id: number) {
    return request.get<any, Prescription>(`/prescription/prescriptions/${id}`)
  },

  getPrescriptions(patientId: number, page = 1, pageSize = 10) {
    return request.get<any, { list: Prescription[]; total: number }>('/prescription/prescriptions', {
      params: { patientId, page, pageSize },
    })
  },

  getPrescriptionsByPatient(patientId: number) {
    return request.get<any, Prescription[]>(`/prescription/prescriptions/patient/${patientId}`)
  },

  getPrescriptionItems(id: number) {
    return request.get<any, PrescriptionItem[]>(`/prescription/prescriptions/${id}/items`)
  },
}
