import request from './request'

export interface DashboardStats {
  totalRegistrations: number
  totalOutpatients: number
  totalInpatients: number
  totalPrescriptions: number
  totalRevenue: number
  deptStats: DepartmentStat[]
}

export interface DepartmentStat {
  departmentId: number
  departmentName: string
  visitCount: number
  revenue: number
}

export interface TrendPoint {
  date: string
  value: number
}

export interface Registration {
  id: number
  patientName: string
  doctorName: string
  departmentName: string
  visitDate: string
  serialNumber: string
  status: string
  createdAt: string
}

export const statisticsApi = {
  // 获取仪表盘数据
  getDashboard: (params?: { period?: string; departmentId?: number }) => {
    return request.get<any, DashboardStats>('/statistics/dashboard', { params })
  },

  // 获取趋势数据
  getTrend: (params: { metric: string; startDate: string; endDate: string; granularity?: string; departmentId?: number }) => {
    return request.get<any, TrendPoint[]>('/statistics/trend', { params })
  },

  // 获取今日挂号列表
  getTodayRegistrations: (params?: { page?: number; size?: number }) => {
    return request.get<any, { content: Registration[]; totalElements: number }>('/registration/list', {
      params: { ...params, visitDate: new Date().toISOString().split('T')[0] }
    })
  },
}
