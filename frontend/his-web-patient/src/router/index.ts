import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('@/views/Home.vue'),
    meta: { title: '健康档案' },
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue'),
    meta: { title: '登录' },
  },
  {
    path: '/registration',
    name: 'Registration',
    component: () => import('@/views/registration/Registration.vue'),
    meta: { title: '在线挂号' },
  },
  {
    path: '/appointments',
    name: 'Appointments',
    component: () => import('@/views/appointment/MyAppointments.vue'),
    meta: { title: '我的挂号' },
  },
  {
    path: '/consultation',
    name: 'Consultation',
    component: () => import('@/views/consultation/Consultation.vue'),
    meta: { title: '在线问诊' },
  },
  {
    path: '/prescriptions',
    name: 'Prescriptions',
    component: () => import('@/views/prescription/MyPrescriptions.vue'),
    meta: { title: '我的处方' },
  },
  {
    path: '/bills',
    name: 'Bills',
    component: () => import('@/views/billing/MyBills.vue'),
    meta: { title: '费用查询' },
  },
  {
    path: '/reports',
    name: 'Reports',
    component: () => import('@/views/report/MyReports.vue'),
    meta: { title: '检查报告' },
  },
  {
    path: '/followup',
    name: 'Followup',
    component: () => import('@/views/followup/MyFollowup.vue'),
    meta: { title: '我的随访' },
  },
  {
    path: '/profile',
    name: 'Profile',
    component: () => import('@/views/profile/Profile.vue'),
    meta: { title: '个人中心' },
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

router.beforeEach((to) => {
  if (to.meta.title) {
    document.title = `${to.meta.title} — HIS 患者端`
  }
})

export default router
