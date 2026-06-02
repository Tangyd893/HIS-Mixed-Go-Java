import request from './request'

export interface BillItem {
  id: number
  billNo: string
  patientId: number
  billType: string
  itemName: string
  quantity: number
  unitPrice: number
  amount: number
  status: string
  paymentId?: number
  paidAt?: string
  createdAt: string
}

export interface Payment {
  id: number
  paymentNo: string
  patientId: number
  totalAmount: number
  paymentMethod: string
  status: string
  paidAt: string
  createdAt: string
}

export interface CreatePaymentRequest {
  patientId: number
  billItemIds: number[]
  paymentMethod: string
}

export const billingApi = {
  // 获取账单列表
  getBillItems: (params?: { patientId?: number; status?: string; page?: number; size?: number }) => {
    return request.get<any, { content: BillItem[]; totalElements: number }>('/billing/bill-items', { params })
  },

  // 获取支付记录列表
  getPayments: (params?: { patientId?: number; status?: string; page?: number; size?: number }) => {
    return request.get<any, { content: Payment[]; totalElements: number }>('/billing/payments', { params })
  },

  // 获取患者支付记录
  getPaymentsByPatient: (patientId: number) => {
    return request.get<any, Payment[]>(`/billing/payments/patient/${patientId}`)
  },

  // 创建支付
  createPayment: (data: CreatePaymentRequest) => {
    return request.post<any, Payment>('/billing/payments', data.billItemIds, {
      params: { patientId: data.patientId, paymentMethod: data.paymentMethod }
    })
  },

  // 获取未支付金额
  getUnpaidAmount: (patientId: number) => {
    return request.get<any, number>(`/billing/unpaid-amount/${patientId}`)
  },
}
