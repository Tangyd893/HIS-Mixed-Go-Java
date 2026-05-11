-- 迁移: 随访管理服务表结构
-- 数据库: his_followup (Go)

CREATE TABLE IF NOT EXISTS followup_plans (
    id                  BIGINT          PRIMARY KEY,
    patient_id          BIGINT          NOT NULL,
    doctor_id           BIGINT,
    diagnosis           VARCHAR(500),
    followup_type       VARCHAR(30),
    start_date          DATE            NOT NULL,
    end_date            DATE,
    interval_days       INT,
    total_times         INT,
    completed_times     INT             NOT NULL DEFAULT 0,
    status              VARCHAR(20),
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_followup_plans_patient ON followup_plans(patient_id);
CREATE INDEX idx_followup_plans_status ON followup_plans(status);
CREATE INDEX idx_followup_plans_date ON followup_plans(start_date, end_date);

CREATE TABLE IF NOT EXISTS followup_records (
    id                  BIGINT          PRIMARY KEY,
    plan_id             BIGINT          NOT NULL,
    patient_id          BIGINT          NOT NULL,
    executor_id         BIGINT,
    followup_date       DATE            NOT NULL,
    followup_method     VARCHAR(20),
    content             TEXT,
    patient_condition   TEXT,
    advice              TEXT,
    next_followup_date  DATE,
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_followup_records_plan FOREIGN KEY (plan_id) REFERENCES followup_plans(id)
);

CREATE INDEX idx_followup_records_plan ON followup_records(plan_id);
CREATE INDEX idx_followup_records_patient ON followup_records(patient_id);
CREATE INDEX idx_followup_records_date ON followup_records(followup_date);

CREATE TABLE IF NOT EXISTS survey_templates (
    id              BIGINT          PRIMARY KEY,
    template_name   VARCHAR(200)    NOT NULL,
    template_type   VARCHAR(50),
    description     TEXT,
    questions       JSONB           NOT NULL,
    status          SMALLINT        NOT NULL DEFAULT 1,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at      TIMESTAMP
);

CREATE INDEX idx_survey_templates_type ON survey_templates(template_type);
CREATE INDEX idx_survey_templates_status ON survey_templates(status);

CREATE TABLE IF NOT EXISTS survey_answers (
    id                  BIGINT          PRIMARY KEY,
    template_id         BIGINT          NOT NULL,
    patient_id          BIGINT          NOT NULL,
    followup_record_id  BIGINT,
    answers             JSONB           NOT NULL,
    total_score         INT,
    submitted_at        TIMESTAMP,
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_survey_answers_template FOREIGN KEY (template_id) REFERENCES survey_templates(id)
);

CREATE INDEX idx_survey_answers_template ON survey_answers(template_id);
CREATE INDEX idx_survey_answers_patient ON survey_answers(patient_id);
