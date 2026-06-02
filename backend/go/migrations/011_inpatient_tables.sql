-- 迁移: 住院管理服务表结构
-- 数据库: his_inpatient (Java)

CREATE TABLE IF NOT EXISTS wards (
    id              BIGSERIAL       PRIMARY KEY,
    ward_name       VARCHAR(100)    NOT NULL,
    ward_code       VARCHAR(50)     NOT NULL,
    department_id   BIGINT,
    floor           INT,
    capacity        INT             NOT NULL DEFAULT 0,
    occupied        INT             NOT NULL DEFAULT 0,
    phone           VARCHAR(20),
    status          SMALLINT        NOT NULL DEFAULT 1,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX uk_wards_code ON wards(ward_code);
CREATE INDEX idx_wards_department ON wards(department_id);

CREATE TABLE IF NOT EXISTS rooms (
    id              BIGSERIAL       PRIMARY KEY,
    room_no         VARCHAR(20)     NOT NULL,
    ward_id         BIGINT          NOT NULL,
    room_type       VARCHAR(20),
    floor           INT,
    bed_count       INT             NOT NULL DEFAULT 0,
    status          SMALLINT        NOT NULL DEFAULT 1,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_rooms_ward FOREIGN KEY (ward_id) REFERENCES wards(id)
);

CREATE INDEX idx_rooms_ward ON rooms(ward_id);

CREATE TABLE IF NOT EXISTS beds (
    id              BIGSERIAL       PRIMARY KEY,
    bed_no          VARCHAR(20)     NOT NULL,
    room_id         BIGINT          NOT NULL,
    ward_id         BIGINT          NOT NULL,
    bed_type        VARCHAR(20),
    daily_rate      DECIMAL(10,2),
    status          VARCHAR(20),
    version         INT             NOT NULL DEFAULT 0,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_beds_room FOREIGN KEY (room_id) REFERENCES rooms(id),
    CONSTRAINT fk_beds_ward FOREIGN KEY (ward_id) REFERENCES wards(id)
);

CREATE INDEX idx_beds_room ON beds(room_id);
CREATE INDEX idx_beds_ward ON beds(ward_id);
CREATE INDEX idx_beds_status ON beds(status);

CREATE TABLE IF NOT EXISTS admissions (
    id                  BIGSERIAL       PRIMARY KEY,
    patient_id          BIGINT          NOT NULL,
    bed_id              BIGINT          NOT NULL,
    admission_no        VARCHAR(50),
    admission_type      VARCHAR(20),
    admitting_diagnosis VARCHAR(500),
    doctor_id           BIGINT,
    nurse_id            BIGINT,
    admission_date      TIMESTAMP       NOT NULL,
    status              VARCHAR(20),
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_admissions_bed FOREIGN KEY (bed_id) REFERENCES beds(id)
);

CREATE UNIQUE INDEX uk_admissions_no ON admissions(admission_no);
CREATE INDEX idx_admissions_patient ON admissions(patient_id);
CREATE INDEX idx_admissions_bed ON admissions(bed_id);
CREATE INDEX idx_admissions_status ON admissions(status);

CREATE TABLE IF NOT EXISTS discharges (
    id                  BIGSERIAL       PRIMARY KEY,
    admission_id        BIGINT          NOT NULL,
    patient_id          BIGINT          NOT NULL,
    discharge_date      TIMESTAMP       NOT NULL,
    discharge_type      VARCHAR(20),
    discharge_diagnosis VARCHAR(500),
    discharge_condition VARCHAR(50),
    discharge_summary   TEXT,
    followup_advice     TEXT,
    doctor_id           BIGINT,
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_discharges_admission FOREIGN KEY (admission_id) REFERENCES admissions(id)
);

CREATE INDEX idx_discharges_admission ON discharges(admission_id);
CREATE INDEX idx_discharges_patient ON discharges(patient_id);

CREATE TABLE IF NOT EXISTS medical_orders (
    id              BIGSERIAL       PRIMARY KEY,
    admission_id    BIGINT          NOT NULL,
    order_no        VARCHAR(50),
    order_type      VARCHAR(30),
    content         TEXT            NOT NULL,
    frequency       VARCHAR(30),
    start_time      TIMESTAMP       NOT NULL,
    stop_time       TIMESTAMP,
    doctor_id       BIGINT,
    status          VARCHAR(20),
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_medical_orders_admission FOREIGN KEY (admission_id) REFERENCES admissions(id)
);

CREATE UNIQUE INDEX uk_medical_orders_no ON medical_orders(order_no);
CREATE INDEX idx_medical_orders_admission ON medical_orders(admission_id);
CREATE INDEX idx_medical_orders_status ON medical_orders(status);

CREATE TABLE IF NOT EXISTS nursing_records (
    id              BIGSERIAL       PRIMARY KEY,
    admission_id    BIGINT          NOT NULL,
    patient_id      BIGINT          NOT NULL,
    nurse_id        BIGINT,
    record_type     VARCHAR(30),
    content         TEXT            NOT NULL,
    recorded_at     TIMESTAMP       NOT NULL,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_nursing_records_admission FOREIGN KEY (admission_id) REFERENCES admissions(id)
);

CREATE INDEX idx_nursing_records_admission ON nursing_records(admission_id);
CREATE INDEX idx_nursing_records_patient ON nursing_records(patient_id);

CREATE TABLE IF NOT EXISTS vital_signs (
    id                      BIGSERIAL       PRIMARY KEY,
    admission_id            BIGINT          NOT NULL,
    patient_id              BIGINT          NOT NULL,
    temperature             DECIMAL(4,1),
    heart_rate              INT,
    blood_pressure_systolic INT,
    blood_pressure_diastolic INT,
    respiratory_rate        INT,
    oxygen_saturation       DECIMAL(4,1),
    blood_sugar             DECIMAL(5,1),
    weight                  DECIMAL(5,2),
    height                  DECIMAL(5,1),
    pain_score              INT,
    recorded_at             TIMESTAMP       NOT NULL,
    recorder_id             BIGINT,
    created_at              TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_vital_signs_admission FOREIGN KEY (admission_id) REFERENCES admissions(id)
);

CREATE INDEX idx_vital_signs_admission ON vital_signs(admission_id);
CREATE INDEX idx_vital_signs_patient ON vital_signs(patient_id);
CREATE INDEX idx_vital_signs_recorded ON vital_signs(recorded_at);
