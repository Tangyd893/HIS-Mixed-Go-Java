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

export interface DepartmentForm {
  name: string
  code: string
  parentId: number
  sort: number
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

  create(data: DepartmentForm) {
    return request.post('/departments', data)
  },

  update(id: number, data: Partial<DepartmentForm>) {
    return request.put(`/departments/${id}`, data)
  },

  delete(id: number) {
    return request.delete(`/departments/${id}`)
  },
}
