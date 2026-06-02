-- 迁移: 处方管理服务表结构
-- 数据库: his_prescription (Java)

CREATE TABLE IF NOT EXISTS prescriptions (
    id                  BIGSERIAL       PRIMARY KEY,
    prescription_no     VARCHAR(50),
    encounter_id        BIGINT,
    patient_id          BIGINT          NOT NULL,
    doctor_id           BIGINT          NOT NULL,
    department_id       BIGINT,
    prescription_type   VARCHAR(20),
    status              VARCHAR(20)     NOT NULL,
    reviewer_id         BIGINT,
    review_comment      TEXT,
    diagnosis_summary   VARCHAR(500),
    total_amount        DECIMAL(10,2),
    version             INT             NOT NULL DEFAULT 0,
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX uk_prescriptions_no ON prescriptions(prescription_no);
CREATE INDEX idx_prescriptions_patient ON prescriptions(patient_id);
CREATE INDEX idx_prescriptions_doctor ON prescriptions(doctor_id);
CREATE INDEX idx_prescriptions_encounter ON prescriptions(encounter_id);
CREATE INDEX idx_prescriptions_status ON prescriptions(status);

CREATE TABLE IF NOT EXISTS prescription_items (
    id                  BIGSERIAL       PRIMARY KEY,
    prescription_id     BIGINT          NOT NULL,
    drug_id             BIGINT          NOT NULL,
    drug_name           VARCHAR(200),
    specification       VARCHAR(100),
    quantity            DECIMAL(10,2)   NOT NULL,
    unit                VARCHAR(20),
    dosage              VARCHAR(50),
    frequency           VARCHAR(20),
    usage_method        VARCHAR(50),
    days                INT,
    unit_price          DECIMAL(10,2),
    subtotal            DECIMAL(10,2),
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_prescription_items_prescription FOREIGN KEY (prescription_id) REFERENCES prescriptions(id)
);

CREATE INDEX idx_prescription_items_prescription ON prescription_items(prescription_id);
CREATE INDEX idx_prescription_items_drug ON prescription_items(drug_id);

CREATE TABLE IF NOT EXISTS prescription_reviews (
    id                  BIGSERIAL       PRIMARY KEY,
    prescription_id     BIGINT          NOT NULL,
    reviewer_id         BIGINT          NOT NULL,
    review_type         VARCHAR(20)     NOT NULL,
    result              VARCHAR(20)     NOT NULL,
    comment             TEXT,
    reviewed_at         TIMESTAMP       NOT NULL,
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_prescription_reviews_prescription FOREIGN KEY (prescription_id) REFERENCES prescriptions(id)
);

CREATE INDEX idx_prescription_reviews_prescription ON prescription_reviews(prescription_id);
CREATE INDEX idx_prescription_reviews_reviewer ON prescription_reviews(reviewer_id);
