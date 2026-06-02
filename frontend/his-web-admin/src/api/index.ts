export { authApi } from './auth'
export type { LoginRequest, LoginResponse, UserInfo } from './auth'

export { doctorApi } from './doctor'
export type { Doctor, DoctorQuery, DoctorForm } from './doctor'

export { scheduleApi } from './schedule'
export type { Schedule, ScheduleQuery, ScheduleForm, ScheduleBatchForm } from './schedule'

export { drugApi } from './drug'
export type { Drug, DrugQuery, DrugForm } from './drug'

export { departmentApi } from './department'
export type { Department, DepartmentForm } from './department'

export { statisticsApi } from './statistics'
export type { DashboardStats, DepartmentStat, TrendPoint, Registration } from './statistics'

export { patientApi } from './patient'
export type { Patient, PatientQuery } from './patient'
