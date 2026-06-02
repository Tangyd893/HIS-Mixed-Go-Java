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

export interface RegisterRequest {
  username: string
  password: string
  realName: string
  phone: string
  email?: string
}

export const authApi = {
  login(data: LoginRequest) {
    return request.post<any, LoginResponse>('/auth/login', data)
  },

  refreshToken(refreshToken: string) {
    return request.post<any, LoginResponse>('/auth/refresh', { refreshToken })
  },

  register(data: RegisterRequest) {
    return request.post('/users/register', data)
  },

  getUserInfo(userId: number) {
    return request.get(`/users/${userId}`)
  },
}
