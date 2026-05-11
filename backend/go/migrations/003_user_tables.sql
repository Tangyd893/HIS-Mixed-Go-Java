-- 迁移: 用户管理服务表结构
-- 数据库: his_user (Java)

CREATE TABLE IF NOT EXISTS departments (
    id              BIGSERIAL       PRIMARY KEY,
    name            VARCHAR(100)    NOT NULL,
    code            VARCHAR(50),
    parent_id       BIGINT,
    sort            INT             NOT NULL DEFAULT 0,
    description     VARCHAR(500),
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_departments_parent FOREIGN KEY (parent_id) REFERENCES departments(id)
);

CREATE UNIQUE INDEX uk_departments_code ON departments(code);
CREATE INDEX idx_departments_parent ON departments(parent_id);

CREATE TABLE IF NOT EXISTS employees (
    id              BIGSERIAL       PRIMARY KEY,
    user_id         BIGINT          NOT NULL,
    employee_no     VARCHAR(50),
    name            VARCHAR(50)     NOT NULL,
    gender          SMALLINT        NOT NULL,
    phone           VARCHAR(20),
    email           VARCHAR(100),
    title           VARCHAR(50),
    job_type        VARCHAR(20),
    department_id   BIGINT,
    specialty       VARCHAR(200),
    introduction    TEXT,
    status          SMALLINT        NOT NULL DEFAULT 1,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_employees_dept FOREIGN KEY (department_id) REFERENCES departments(id)
);

CREATE UNIQUE INDEX uk_employees_employee_no ON employees(employee_no);
CREATE INDEX idx_employees_user_id ON employees(user_id);
CREATE INDEX idx_employees_department ON employees(department_id);
CREATE INDEX idx_employees_status ON employees(status);

CREATE TABLE IF NOT EXISTS patients (
    id                  BIGSERIAL       PRIMARY KEY,
    name                VARCHAR(50)     NOT NULL,
    gender              SMALLINT        NOT NULL,
    birth_date          DATE,
    id_card             VARCHAR(18),
    phone               VARCHAR(20),
    address             VARCHAR(500),
    blood_type          VARCHAR(5),
    allergic_history    TEXT,
    marital_status      SMALLINT,
    occupation          VARCHAR(100),
    emergency_contact   VARCHAR(50),
    emergency_phone     VARCHAR(20),
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at          TIMESTAMP
);

CREATE INDEX idx_patients_phone ON patients(phone);
CREATE INDEX idx_patients_id_card ON patients(id_card);
CREATE INDEX idx_patients_name ON patients(name);
CREATE INDEX idx_patients_deleted ON patients(deleted_at);
