import request from './request'

export interface Department {
  id: number
  name: string
  code: string
  parentId: number
  level: number
  sort: number
  status: number
  createdAt: string
}

export const departmentApi = {
  getList() {
    return request.get<any, Department[]>('/departments')
  },

  getTree() {
    return request.get<any, Department[]>('/departments/tree')
  },

  getById(id: number) {
    return request.get<any, Department>(`/departments/${id}`)
  },
}
