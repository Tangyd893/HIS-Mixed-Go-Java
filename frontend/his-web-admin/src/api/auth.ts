import request from './request'

export interface LoginRequest {
  username: string
  password: string
}

export interface LoginResponse {
  accessToken: string
  refreshToken: string
  tokenType: string
  expiresIn: number
  userId: number
  username: string
  realName: string
  roles: string[]
  permissions: string[]
}

export interface UserInfo {
  id: number
  username: string
  realName: string
  phone: string
  email: string
  roles: string[]
  status: number
}

export const authApi = {
  login(data: LoginRequest) {
    return request.post<any, LoginResponse>('/auth/login', data)
  },

  refreshToken(refreshToken: string) {
    return request.post<any, LoginResponse>('/auth/refresh', { refreshToken })
  },

  getUserInfo(userId: number) {
    return request.get<any, UserInfo>(`/users/${userId}`)
  },

  getUserList(params: { page?: number; pageSize?: number; keyword?: string }) {
    return request.get<any, { list: UserInfo[]; total: number }>('/users', { params })
  },
}
