-- 迁移: 门诊诊疗服务表结构
-- 数据库: his_clinic (Java)

CREATE TABLE IF NOT EXISTS encounters (
    id                  BIGSERIAL       PRIMARY KEY,
    registration_id     BIGINT,
    patient_id          BIGINT          NOT NULL,
    doctor_id           BIGINT          NOT NULL,
    department_id       BIGINT,
    chief_complaint     VARCHAR(500),
    present_illness     TEXT,
    past_history        TEXT,
    physical_exam       JSONB,
    encounter_type      VARCHAR(20),
    status              VARCHAR(20)     NOT NULL,
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_encounters_patient ON encounters(patient_id);
CREATE INDEX idx_encounters_doctor ON encounters(doctor_id);
CREATE INDEX idx_encounters_registration ON encounters(registration_id);
CREATE INDEX idx_encounters_status ON encounters(status);

CREATE TABLE IF NOT EXISTS diagnoses (
    id                  BIGSERIAL       PRIMARY KEY,
    encounter_id        BIGINT          NOT NULL,
    icd_code            VARCHAR(20)     NOT NULL,
    diagnosis_name      VARCHAR(200)    NOT NULL,
    diagnosis_type      VARCHAR(20),
    certainty           INT             NOT NULL DEFAULT 100,
    sequence            INT             NOT NULL DEFAULT 1,
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_diagnoses_encounter FOREIGN KEY (encounter_id) REFERENCES encounters(id)
);

CREATE INDEX idx_diagnoses_encounter ON diagnoses(encounter_id);
CREATE INDEX idx_diagnoses_icd_code ON diagnoses(icd_code);

CREATE TABLE IF NOT EXISTS referrals (
    id                  BIGSERIAL       PRIMARY KEY,
    encounter_id        BIGINT          NOT NULL,
    patient_id          BIGINT          NOT NULL,
    from_department_id  BIGINT,
    to_department_id    BIGINT,
    from_doctor_id      BIGINT,
    to_doctor_id        BIGINT,
    referral_reason     TEXT,
    diagnosis_summary   VARCHAR(500),
    status              VARCHAR(20)     NOT NULL DEFAULT 'PENDING',
    referred_at         TIMESTAMP,
    received_at         TIMESTAMP,
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_referrals_encounter FOREIGN KEY (encounter_id) REFERENCES encounters(id)
);

CREATE INDEX idx_referrals_encounter ON referrals(encounter_id);
CREATE INDEX idx_referrals_patient ON referrals(patient_id);
CREATE INDEX idx_referrals_status ON referrals(status);
