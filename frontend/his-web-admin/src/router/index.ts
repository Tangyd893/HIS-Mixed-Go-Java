import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue'),
    meta: { title: '登录', public: true },
  },
  {
    path: '/',
    component: () => import('@/layouts/MainLayout.vue'),
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/Dashboard.vue'),
        meta: { title: '工作台' },
      },
      {
        path: 'doctors',
        name: 'Doctors',
        component: () => import('@/views/doctor/DoctorList.vue'),
        meta: { title: '医生管理' },
      },
      {
        path: 'schedules',
        name: 'Schedules',
        component: () => import('@/views/schedule/ScheduleList.vue'),
        meta: { title: '排班管理' },
      },
      {
        path: 'drugs',
        name: 'Drugs',
        component: () => import('@/views/drug/DrugList.vue'),
        meta: { title: '药品管理' },
      },
      {
        path: 'patients',
        name: 'Patients',
        component: () => import('@/views/patient/PatientList.vue'),
        meta: { title: '患者管理' },
      },
      {
        path: 'system',
        name: 'System',
        redirect: '/system/departments',
        children: [
          {
            path: 'departments',
            name: 'Departments',
            component: () => import('@/views/system/DepartmentList.vue'),
            meta: { title: '科室管理' },
          },
          {
            path: 'users',
            name: 'Users',
            component: () => import('@/views/system/UserList.vue'),
            meta: { title: '用户管理' },
          },
        ],
      },
    ],
  },
]

const router = createRouter({
  history: createWebHistory('/admin'),
  routes,
})

router.beforeEach((to, _from, next) => {
  if (to.meta.title) {
    document.title = `${to.meta.title} — HIS 管理端`
  }

  const authStore = useAuthStore()
  authStore.initFromStorage()

  if (to.meta.public) {
    next()
  } else if (!authStore.isLoggedIn) {
    next('/login')
  } else {
    next()
  }
})

export default router
