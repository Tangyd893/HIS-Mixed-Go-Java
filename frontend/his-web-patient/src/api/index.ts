export { authApi } from './auth'
export type { LoginRequest, LoginResponse, RegisterRequest } from './auth'

export { scheduleApi } from './schedule'
export type { ScheduleSlot, GetSlotsRequest } from './schedule'

export { registrationApi } from './registration'
export type { RegisterRequest as RegistrationRequest, RegisterResponse, Registration } from './registration'

export { pharmacyApi } from './pharmacy'
export type { Drug, Prescription, PrescriptionItem } from './pharmacy'

export { billingApi } from './billing'
export type { BillItem, Payment, CreatePaymentRequest } from './billing'

export { reportApi } from './report'
export type { ExaminationReport } from './report'

export { followupApi } from './followup'
export type { FollowupPlan, FollowupRecord } from './followup'

export { consultationApi } from './consultation'
export type { Consultation, ConsultationMessage } from './consultation'

export { departmentApi } from './department'
export type { Department } from './department'
