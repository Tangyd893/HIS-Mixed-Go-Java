-- 迁移: 检查检验服务表结构
-- 数据库: his_examination (Go)

CREATE TABLE IF NOT EXISTS exam_items (
    id              BIGINT          PRIMARY KEY,
    item_code       VARCHAR(50)     NOT NULL,
    item_name       VARCHAR(200)    NOT NULL,
    exam_type       VARCHAR(30)     NOT NULL,
    category        VARCHAR(50),
    unit_price      DECIMAL(10,2),
    reference_range TEXT,
    unit            VARCHAR(20),
    description     TEXT,
    status          SMALLINT        NOT NULL DEFAULT 1,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at      TIMESTAMP
);

CREATE UNIQUE INDEX uk_exam_items_code ON exam_items(item_code) WHERE deleted_at IS NULL;
CREATE INDEX idx_exam_items_type ON exam_items(exam_type);

CREATE TABLE IF NOT EXISTS exam_requests (
    id              BIGINT          PRIMARY KEY,
    encounter_id    BIGINT,
    patient_id      BIGINT          NOT NULL,
    doctor_id       BIGINT          NOT NULL,
    exam_type       VARCHAR(30),
    exam_item_id    BIGINT          NOT NULL,
    urgency         VARCHAR(20),
    clinical_info   TEXT,
    status          VARCHAR(20),
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_exam_requests_item FOREIGN KEY (exam_item_id) REFERENCES exam_items(id)
);

CREATE INDEX idx_exam_requests_patient ON exam_requests(patient_id);
CREATE INDEX idx_exam_requests_doctor ON exam_requests(doctor_id);
CREATE INDEX idx_exam_requests_status ON exam_requests(status);
CREATE INDEX idx_exam_requests_encounter ON exam_requests(encounter_id);

CREATE TABLE IF NOT EXISTS exam_reports (
    id              BIGINT          PRIMARY KEY,
    request_id      BIGINT          NOT NULL,
    report_no       VARCHAR(50),
    findings        TEXT,
    impression      TEXT,
    conclusion      TEXT,
    reference_range TEXT,
    is_abnormal     BOOLEAN         NOT NULL DEFAULT FALSE,
    technician_id   BIGINT,
    reviewer_id     BIGINT,
    status          VARCHAR(20),
    executed_at     TIMESTAMP,
    reviewed_at     TIMESTAMP,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_exam_reports_request FOREIGN KEY (request_id) REFERENCES exam_requests(id)
);

CREATE UNIQUE INDEX uk_exam_reports_no ON exam_reports(report_no);
CREATE INDEX idx_exam_reports_request ON exam_reports(request_id);
CREATE INDEX idx_exam_reports_status ON exam_reports(status);

CREATE TABLE IF NOT EXISTS exam_attachments (
    id              BIGINT          PRIMARY KEY,
    report_id       BIGINT          NOT NULL,
    file_name       VARCHAR(200)    NOT NULL,
    file_path       VARCHAR(500)    NOT NULL,
    file_type       VARCHAR(50),
    file_size       BIGINT,
    object_key      VARCHAR(200)    NOT NULL,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_exam_attachments_report FOREIGN KEY (report_id) REFERENCES exam_reports(id)
);

CREATE INDEX idx_exam_attachments_report ON exam_attachments(report_id);
