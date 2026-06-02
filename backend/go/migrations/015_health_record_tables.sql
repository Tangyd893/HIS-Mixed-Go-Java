-- 迁移: 健康档案服务表结构
-- 数据库: his_health_record (Java)

CREATE TABLE IF NOT EXISTS health_records (
    id                  BIGSERIAL       PRIMARY KEY,
    patient_id          BIGINT          NOT NULL,
    height              DECIMAL(5,1),
    weight              DECIMAL(5,2),
    blood_type          VARCHAR(5),
    blood_pressure      VARCHAR(20),
    blood_sugar         DECIMAL(5,1),
    cholesterol         DECIMAL(5,2),
    smoking_status      VARCHAR(20),
    alcohol_status      VARCHAR(20),
    exercise_frequency  VARCHAR(20),
    dietary_habit       VARCHAR(100),
    family_history      JSONB,
    immunization_history JSONB,
    allergy_list        JSONB,
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at          TIMESTAMP
);

CREATE UNIQUE INDEX uk_health_records_patient ON health_records(patient_id) WHERE deleted_at IS NULL;

CREATE TABLE IF NOT EXISTS record_timeline (
    id              BIGSERIAL       PRIMARY KEY,
    patient_id      BIGINT          NOT NULL,
    event_type      VARCHAR(30),
    event_title     VARCHAR(200),
    event_summary   TEXT,
    event_date      DATE            NOT NULL,
    ref_id          BIGINT,
    department_name VARCHAR(100),
    doctor_name     VARCHAR(50),
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_record_timeline_patient ON record_timeline(patient_id);
CREATE INDEX idx_record_timeline_date ON record_timeline(event_date);
CREATE INDEX idx_record_timeline_type ON record_timeline(event_type);
