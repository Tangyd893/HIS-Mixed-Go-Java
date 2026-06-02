export { authApi } from './auth'
export type { LoginRequest, LoginResponse, RegisterRequest } from './auth'

export { scheduleApi } from './schedule'
export type { ScheduleSlot, GetSlotsRequest } from './schedule'

export { registrationApi } from './registration'
export type { RegisterRequest as RegistrationRequest, RegisterResponse, Registration } from './registration'

export { pharmacyApi } from './pharmacy'
export type { Drug, Prescription, PrescriptionItem } from './pharmacy'
