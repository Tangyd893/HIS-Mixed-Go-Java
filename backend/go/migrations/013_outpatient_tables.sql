-- 迁移: 院外服务表结构
-- 数据库: his_outpatient (Go)

CREATE TABLE IF NOT EXISTS consultations (
    id              BIGINT          PRIMARY KEY,
    patient_id      BIGINT          NOT NULL,
    doctor_id       BIGINT,
    department_id   BIGINT,
    complaint       TEXT,
    status          VARCHAR(20),
    started_at      TIMESTAMP,
    closed_at       TIMESTAMP,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_consultations_patient ON consultations(patient_id);
CREATE INDEX idx_consultations_doctor ON consultations(doctor_id);
CREATE INDEX idx_consultations_status ON consultations(status);

CREATE TABLE IF NOT EXISTS consultation_messages (
    id                  BIGINT          PRIMARY KEY,
    consultation_id     BIGINT          NOT NULL,
    sender_id           BIGINT,
    sender_type         VARCHAR(10),
    message_type        VARCHAR(20),
    content             TEXT,
    attachment_url      VARCHAR(500),
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_consultation_messages_consultation FOREIGN KEY (consultation_id) REFERENCES consultations(id)
);

CREATE INDEX idx_consultation_messages_consultation ON consultation_messages(consultation_id);

CREATE TABLE IF NOT EXISTS chronic_sign_records (
    id              BIGINT          PRIMARY KEY,
    patient_id      BIGINT          NOT NULL,
    doctor_id       BIGINT,
    disease_type    VARCHAR(50),
    sign_date       DATE            NOT NULL,
    expire_date     DATE,
    status          VARCHAR(20),
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_chronic_sign_records_patient ON chronic_sign_records(patient_id);
CREATE INDEX idx_chronic_sign_records_status ON chronic_sign_records(status);
CREATE INDEX idx_chronic_sign_records_expire ON chronic_sign_records(expire_date);

CREATE TABLE IF NOT EXISTS health_reports (
    id              BIGINT          PRIMARY KEY,
    patient_id      BIGINT          NOT NULL,
    report_type     VARCHAR(50)     NOT NULL,
    report_title    VARCHAR(200),
    result_data     JSONB           NOT NULL,
    summary         TEXT,
    risk_level      VARCHAR(20),
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_health_reports_patient ON health_reports(patient_id);
CREATE INDEX idx_health_reports_type ON health_reports(report_type);
