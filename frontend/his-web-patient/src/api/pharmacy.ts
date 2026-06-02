import request from './request'

export interface Drug {
  id: number
  drugCode: string
  drugName: string
  tradeName: string
  drugType: string
  specification: string
  unit: string
  category: string
  isPrescription: boolean
  retailPrice: number
  manufacturer: string
}

export interface Prescription {
  id: number
  prescriptionNo: string
  patientId: number
  doctorId: number
  doctorName: string
  departmentName: string
  diagnosis: string
  status: string
  items: PrescriptionItem[]
  createdAt: string
}

export interface PrescriptionItem {
  id: number
  drugId: number
  drugName: string
  specification: string
  quantity: number
  unit: string
  usage: string
  frequency: string
  price: number
}

export const pharmacyApi = {
  getDrug(id: number) {
    return request.get<any, Drug>(`/pharmacy/drugs/${id}`)
  },

  searchDrugs(keyword: string, page = 1, pageSize = 10) {
    return request.get<any, { list: Drug[]; total: number }>('/pharmacy/drugs', {
      params: { keyword, page, pageSize },
    })
  },

  getPrescription(id: number) {
    return request.get<any, Prescription>(`/pharmacy/prescriptions/${id}`)
  },

  getPrescriptions(patientId: number, page = 1, pageSize = 10) {
    return request.get<any, { list: Prescription[]; total: number }>('/pharmacy/prescriptions', {
      params: { patientId, page, pageSize },
    })
  },
}
