// Package router 网关路由注册
package router

import (
	"github.com/gin-gonic/gin"
	"github.com/his-mixed/go/internal/gateway/handler"
	"github.com/his-mixed/go/pkg/health"
)

// SetupRouter 为每个下游服务注册 API 路由分组
func SetupRouter(r *gin.Engine) {
	r.GET("/api/health", health.Handler)
	r.GET("/api/ready", health.ReadyHandler)
	r.GET("/api/ping", health.PingHandler)

	auth := r.Group("/api/auth")
	{
		auth.POST("/login", handler.ProxyAuth)
		auth.POST("/refresh", handler.ProxyAuth)
		auth.GET("/captcha", handler.ProxyAuth)
	}

	user := r.Group("/api/user")
	{
		user.GET("/patients", handler.ProxyUser)
		user.GET("/patients/:id", handler.ProxyUser)
		user.POST("/patients", handler.ProxyUser)
		user.PUT("/patients/:id", handler.ProxyUser)
		user.GET("/employees", handler.ProxyUser)
		user.GET("/departments", handler.ProxyUser)
	}

	reg := r.Group("/api/registration")
	{
		reg.GET("/schedules", handler.ProxyRegistration)
		reg.POST("/appointments", handler.ProxyRegistration)
		reg.GET("/appointments", handler.ProxyRegistration)
		reg.GET("/queue", handler.ProxyRegistration)
	}

	clinic := r.Group("/api/clinic")
	{
		clinic.POST("/encounters", handler.ProxyClinic)
		clinic.GET("/encounters", handler.ProxyClinic)
		clinic.POST("/diagnoses", handler.ProxyClinic)
	}

	pres := r.Group("/api/prescription")
	{
		pres.POST("/prescriptions", handler.ProxyPrescription)
		pres.GET("/prescriptions", handler.ProxyPrescription)
	}

	bill := r.Group("/api/billing")
	{
		bill.POST("/calculate", handler.ProxyBilling)
		bill.POST("/payments", handler.ProxyBilling)
	}

	pharm := r.Group("/api/pharmacy")
	{
		pharm.GET("/drugs", handler.ProxyPharmacy)
		pharm.POST("/dispense", handler.ProxyPharmacy)
		pharm.GET("/dispense-queue", handler.ProxyPharmacy)
	}

	exam := r.Group("/api/examination")
	{
		exam.GET("/requests", handler.ProxyExamination)
		exam.POST("/reports", handler.ProxyExamination)
		exam.GET("/reports/:id", handler.ProxyExamination)
	}

	inp := r.Group("/api/inpatient")
	{
		inp.POST("/admissions", handler.ProxyInpatient)
		inp.GET("/beds", handler.ProxyInpatient)
		inp.POST("/orders", handler.ProxyInpatient)
		inp.POST("/discharges", handler.ProxyInpatient)
	}

	sched := r.Group("/api/schedule")
	{
		sched.GET("/plans", handler.ProxySchedule)
		sched.POST("/slots/generate", handler.ProxySchedule)
		sched.GET("/slots", handler.ProxySchedule)
	}

	out := r.Group("/api/outpatient")
	{
		out.POST("/consultations", handler.ProxyOutpatient)
		out.GET("/consultations/:id", handler.ProxyOutpatient)
	}

	fu := r.Group("/api/followup")
	{
		fu.GET("/plans", handler.ProxyFollowup)
		fu.POST("/records", handler.ProxyFollowup)
	}

	hr := r.Group("/api/health-record")
	{
		hr.GET("/patients/:id/overview", handler.ProxyHealthRecord)
	}

	notify := r.Group("/api/notification")
	{
		notify.POST("/send", handler.ProxyNotification)
		notify.GET("/templates", handler.ProxyNotification)
	}

	stat := r.Group("/api/statistics")
	{
		stat.GET("/dashboard", handler.ProxyStatistics)
		stat.GET("/registration-trend", handler.ProxyStatistics)
	}

	sys := r.Group("/api/system")
	{
		sys.GET("/dict/types", handler.ProxySystem)
		sys.GET("/configs", handler.ProxySystem)
		sys.GET("/audit-logs", handler.ProxySystem)
	}

	emr := r.Group("/api/emr")
	{
		emr.POST("/records", handler.ProxyEMR)
		emr.GET("/records/:id", handler.ProxyEMR)
	}
}
