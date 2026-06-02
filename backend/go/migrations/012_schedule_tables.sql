-- 迁移: 排班管理服务表结构
-- 数据库: his_schedule (Go)

CREATE TABLE IF NOT EXISTS schedule_plans (
    id              BIGINT          PRIMARY KEY,
    plan_name       VARCHAR(100)    NOT NULL,
    department_id   BIGINT,
    start_date      DATE            NOT NULL,
    end_date        DATE            NOT NULL,
    description     VARCHAR(500),
    status          SMALLINT        NOT NULL DEFAULT 1,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_schedule_plans_department ON schedule_plans(department_id);
CREATE INDEX idx_schedule_plans_date ON schedule_plans(start_date, end_date);

CREATE TABLE IF NOT EXISTS schedule_slots (
    id                  BIGINT          PRIMARY KEY,
    plan_id             BIGINT          NOT NULL,
    doctor_id           BIGINT          NOT NULL,
    department_id       BIGINT,
    room_id             BIGINT,
    schedule_date       DATE            NOT NULL,
    day_of_week         SMALLINT,
    start_time          TIME            NOT NULL,
    end_time            TIME            NOT NULL,
    total_quota         INT             NOT NULL,
    remaining           INT             NOT NULL,
    interval_minutes    INT             NOT NULL DEFAULT 10,
    is_active           BOOLEAN         NOT NULL DEFAULT TRUE,
    version             INT             NOT NULL DEFAULT 0,
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_schedule_slots_plan FOREIGN KEY (plan_id) REFERENCES schedule_plans(id)
);

CREATE INDEX idx_schedule_slots_plan ON schedule_slots(plan_id);
CREATE INDEX idx_schedule_slots_doctor ON schedule_slots(doctor_id);
CREATE INDEX idx_schedule_slots_date ON schedule_slots(schedule_date);
CREATE INDEX idx_schedule_slots_active ON schedule_slots(is_active, schedule_date);
