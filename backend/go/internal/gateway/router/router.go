package router

import (
	"github.com/gin-gonic/gin"
	"github.com/his-mixed/go/pkg/health"
)

func SetupRouter(r *gin.Engine, proxies map[string]gin.HandlerFunc) {
	r.GET("/api/health", health.Handler)
	r.GET("/api/ready", health.ReadyHandler)
	r.GET("/api/ping", health.PingHandler)

	proxy := func(name string) gin.HandlerFunc {
		if h, ok := proxies[name]; ok {
			return h
		}
		return func(c *gin.Context) {
			c.JSON(503, gin.H{"code": 503, "message": "服务未配置: " + name})
		}
	}

	auth := r.Group("/api/auth")
	{
		auth.POST("/login", proxy("auth"))
		auth.POST("/refresh", proxy("auth"))
		auth.GET("/captcha", proxy("auth"))
	}

	user := r.Group("/api/user")
	{
		user.GET("/patients", proxy("user"))
		user.GET("/patients/:id", proxy("user"))
		user.POST("/patients", proxy("user"))
		user.PUT("/patients/:id", proxy("user"))
		user.GET("/employees", proxy("user"))
		user.GET("/departments", proxy("user"))
	}

	reg := r.Group("/api/registration")
	{
		reg.GET("/schedules", proxy("registration"))
		reg.POST("/appointments", proxy("registration"))
		reg.GET("/appointments", proxy("registration"))
		reg.GET("/queue", proxy("registration"))
	}

	clinic := r.Group("/api/clinic")
	{
		clinic.POST("/encounters", proxy("clinic"))
		clinic.GET("/encounters", proxy("clinic"))
		clinic.POST("/diagnoses", proxy("clinic"))
	}

	pres := r.Group("/api/prescription")
	{
		pres.POST("/prescriptions", proxy("prescription"))
		pres.GET("/prescriptions", proxy("prescription"))
	}

	bill := r.Group("/api/billing")
	{
		bill.POST("/calculate", proxy("billing"))
		bill.POST("/payments", proxy("billing"))
	}

	pharm := r.Group("/api/pharmacy")
	{
		pharm.GET("/drugs", proxy("pharmacy"))
		pharm.POST("/dispense", proxy("pharmacy"))
		pharm.GET("/dispense-queue", proxy("pharmacy"))
	}

	exam := r.Group("/api/examination")
	{
		exam.GET("/requests", proxy("examination"))
		exam.POST("/reports", proxy("examination"))
		exam.GET("/reports/:id", proxy("examination"))
	}

	inp := r.Group("/api/inpatient")
	{
		inp.POST("/admissions", proxy("inpatient"))
		inp.GET("/beds", proxy("inpatient"))
		inp.POST("/orders", proxy("inpatient"))
		inp.POST("/discharges", proxy("inpatient"))
	}

	sched := r.Group("/api/schedule")
	{
		sched.GET("/plans", proxy("schedule"))
		sched.POST("/slots/generate", proxy("schedule"))
		sched.GET("/slots", proxy("schedule"))
	}

	out := r.Group("/api/outpatient")
	{
		out.POST("/consultations", proxy("outpatient"))
		out.GET("/consultations/:id", proxy("outpatient"))
	}

	fu := r.Group("/api/followup")
	{
		fu.GET("/plans", proxy("followup"))
		fu.POST("/records", proxy("followup"))
	}

	hr := r.Group("/api/health-record")
	{
		hr.GET("/patients/:id/overview", proxy("health-record"))
	}

	notify := r.Group("/api/notification")
	{
		notify.POST("/send", proxy("notification"))
		notify.GET("/templates", proxy("notification"))
	}

	stat := r.Group("/api/statistics")
	{
		stat.GET("/dashboard", proxy("statistics"))
		stat.GET("/registration-trend", proxy("statistics"))
	}

	sys := r.Group("/api/system")
	{
		sys.GET("/dict/types", proxy("system"))
		sys.GET("/configs", proxy("system"))
		sys.GET("/audit-logs", proxy("system"))
	}

	emr := r.Group("/api/emr")
	{
		emr.POST("/records", proxy("emr"))
		emr.GET("/records/:id", proxy("emr"))
	}
}
