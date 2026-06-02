-- 迁移: 挂号预约服务表结构
-- 数据库: his_registration (Go)

CREATE TABLE IF NOT EXISTS registrations (
    id                  BIGINT          PRIMARY KEY,
    patient_id          BIGINT          NOT NULL,
    schedule_id         BIGINT          NOT NULL,
    department_id       BIGINT,
    doctor_id           BIGINT,
    visit_type          VARCHAR(20),
    registration_type   VARCHAR(20),
    status              VARCHAR(20)     NOT NULL,
    queue_number        INT,
    symptom             TEXT,
    register_date       DATE            NOT NULL,
    time_slot           VARCHAR(30),
    fee                 DECIMAL(10,2),
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at          TIMESTAMP
);

CREATE INDEX idx_registrations_patient ON registrations(patient_id);
CREATE INDEX idx_registrations_schedule ON registrations(schedule_id);
CREATE INDEX idx_registrations_date ON registrations(register_date);
CREATE INDEX idx_registrations_status ON registrations(status);
CREATE INDEX idx_registrations_doctor ON registrations(doctor_id);
CREATE INDEX idx_registrations_deleted ON registrations(deleted_at);

CREATE TABLE IF NOT EXISTS queue_items (
    id                  BIGINT          PRIMARY KEY,
    registration_id     BIGINT          NOT NULL,
    department_id       BIGINT,
    doctor_id           BIGINT,
    room_id             BIGINT,
    queue_number        INT             NOT NULL,
    status              VARCHAR(20),
    called_at           TIMESTAMP,
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_queue_items_registration FOREIGN KEY (registration_id) REFERENCES registrations(id)
);

CREATE INDEX idx_queue_items_registration ON queue_items(registration_id);
CREATE INDEX idx_queue_items_department ON queue_items(department_id);
CREATE INDEX idx_queue_items_doctor ON queue_items(doctor_id);
CREATE INDEX idx_queue_items_status ON queue_items(status);

CREATE TABLE IF NOT EXISTS appointment_messages (
    id                  BIGINT          PRIMARY KEY,
    registration_id     BIGINT,
    msg_id              VARCHAR(64)     NOT NULL,
    topic               VARCHAR(100)    NOT NULL,
    routing_key         VARCHAR(100),
    payload             JSONB           NOT NULL,
    status              SMALLINT        NOT NULL DEFAULT 0,
    retry_count         INT             NOT NULL DEFAULT 0,
    max_retry           INT             NOT NULL DEFAULT 3,
    next_retry_at       TIMESTAMP,
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX uk_appointment_messages_msg_id ON appointment_messages(msg_id);
CREATE INDEX idx_appointment_messages_status ON appointment_messages(status);
CREATE INDEX idx_appointment_messages_next_retry ON appointment_messages(next_retry_at);
