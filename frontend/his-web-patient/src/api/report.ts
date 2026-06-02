import request from './request'

export interface ExaminationReport {
  id: number
  reportNo: string
  patientId: number
  examinationId: number
  examinationType: string
  examinationName: string
  doctorId: number
  doctorName: string
  department: string
  result: string
  conclusion: string
  status: string
  reportDate: string
  createdAt: string
}

export const reportApi = {
  // 获取报告列表
  getReports: (params?: { patientId?: number; status?: string; page?: number; size?: number }) => {
    return request.get<any, { content: ExaminationReport[]; totalElements: number }>('/examination/reports', { params })
  },

  // 获取报告详情
  getReport: (id: number) => {
    return request.get<any, ExaminationReport>(`/examination/reports/${id}`)
  },

  // 获取患者报告
  getReportsByPatient: (patientId: number) => {
    return request.get<any, ExaminationReport[]>(`/examination/reports/patient/${patientId}`)
  },
}
