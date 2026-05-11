-- 迁移: 电子病历服务表结构
-- 数据库: his_emr (Java)

CREATE TABLE IF NOT EXISTS emr_records (
    id                  BIGSERIAL       PRIMARY KEY,
    encounter_id        BIGINT,
    patient_id          BIGINT          NOT NULL,
    doctor_id           BIGINT          NOT NULL,
    template_code       VARCHAR(50),
    subjective          JSONB,
    objective           JSONB,
    assessment          JSONB,
    plan                JSONB,
    status              VARCHAR(20)     NOT NULL,
    qc_level            SMALLINT        NOT NULL DEFAULT 0,
    fhir_resource       JSONB,
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_emr_records_encounter ON emr_records(encounter_id);
CREATE INDEX idx_emr_records_patient ON emr_records(patient_id);
CREATE INDEX idx_emr_records_doctor ON emr_records(doctor_id);
CREATE INDEX idx_emr_records_status ON emr_records(status);

CREATE TABLE IF NOT EXISTS emr_templates (
    id                  BIGSERIAL       PRIMARY KEY,
    name                VARCHAR(100)    NOT NULL,
    code                VARCHAR(50),
    type                VARCHAR(30),
    content_schema      JSONB,
    is_active           BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX uk_emr_templates_code ON emr_templates(code);
CREATE INDEX idx_emr_templates_type ON emr_templates(type);
CREATE INDEX idx_emr_templates_active ON emr_templates(is_active);

CREATE TABLE IF NOT EXISTS emr_template_fields (
    id                  BIGSERIAL       PRIMARY KEY,
    template_id         BIGINT          NOT NULL,
    field_name          VARCHAR(100)    NOT NULL,
    field_label         VARCHAR(200),
    field_type          VARCHAR(50),
    section             VARCHAR(20),
    sort                INT             NOT NULL DEFAULT 0,
    required            BOOLEAN         NOT NULL DEFAULT FALSE,
    default_value       TEXT,
    options             JSONB,
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_emr_template_fields_template FOREIGN KEY (template_id) REFERENCES emr_templates(id)
);

CREATE INDEX idx_emr_template_fields_tmpl ON emr_template_fields(template_id);

CREATE TABLE IF NOT EXISTS emr_quality_checks (
    id                  BIGSERIAL       PRIMARY KEY,
    emr_record_id       BIGINT          NOT NULL,
    qc_level            SMALLINT        NOT NULL,
    checker_id          BIGINT,
    result              VARCHAR(20),
    comment             TEXT,
    checked_at          TIMESTAMP,
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_emr_quality_checks_record FOREIGN KEY (emr_record_id) REFERENCES emr_records(id)
);

CREATE INDEX idx_emr_quality_checks_record ON emr_quality_checks(emr_record_id);
CREATE INDEX idx_emr_quality_checks_result ON emr_quality_checks(result);

CREATE TABLE IF NOT EXISTS cdss_check_logs (
    id                  BIGSERIAL       PRIMARY KEY,
    emr_record_id       BIGINT,
    prescription_id     BIGINT,
    check_type          VARCHAR(50)     NOT NULL,
    check_result        JSONB           NOT NULL,
    risk_level          SMALLINT,
    suggestion          TEXT,
    checked_at          TIMESTAMP,
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_cdss_check_logs_emr ON cdss_check_logs(emr_record_id);
CREATE INDEX idx_cdss_check_logs_prescription ON cdss_check_logs(prescription_id);
CREATE INDEX idx_cdss_check_logs_type ON cdss_check_logs(check_type);
