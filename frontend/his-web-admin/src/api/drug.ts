import request from './request'

export interface Drug {
  id: number
  code: string
  name: string
  genericName: string
  category: string
  specification: string
  unit: string
  manufacturer: string
  price: number
  stock: number
  minStock: number
  status: number
  createdAt: string
}

export interface DrugQuery {
  page?: number
  pageSize?: number
  keyword?: string
  category?: string
  status?: number
}

export interface DrugForm {
  code: string
  name: string
  genericName: string
  category: string
  specification: string
  unit: string
  manufacturer: string
  price: number
  stock: number
  minStock: number
}

export const drugApi = {
  getList(params: DrugQuery) {
    return request.get<any, { list: Drug[]; total: number }>('/drugs', { params })
  },

  getById(id: number) {
    return request.get<any, Drug>(`/drugs/${id}`)
  },

  create(data: DrugForm) {
    return request.post('/drugs', data)
  },

  update(id: number, data: Partial<DrugForm>) {
    return request.put(`/drugs/${id}`, data)
  },

  delete(id: number) {
    return request.delete(`/drugs/${id}`)
  },

  updateStatus(id: number, status: number) {
    return request.put(`/drugs/${id}/status`, { status })
  },

  updateStock(id: number, quantity: number, type: 'in' | 'out') {
    return request.put(`/drugs/${id}/stock`, { quantity, type })
  },
}
