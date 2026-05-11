-- 迁移: 药房管理服务表结构
-- 数据库: his_pharmacy (Go)

CREATE TABLE IF NOT EXISTS drugs (
    id BIGINT PRIMARY KEY,
    drug_code VARCHAR(50),
    drug_name VARCHAR(200) NOT NULL,
    trade_name VARCHAR(200),
    drug_type VARCHAR(20),
    specification VARCHAR(100),
    unit VARCHAR(20),
    category VARCHAR(50),
    is_prescription BOOLEAN NOT NULL DEFAULT TRUE,
    is_narcotic BOOLEAN NOT NULL DEFAULT FALSE,
    retail_price DECIMAL(10,2),
    manufacturer VARCHAR(200),
    approval_no VARCHAR(100),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);
CREATE UNIQUE INDEX uk_drugs_code ON drugs(drug_code) WHERE deleted_at IS NULL;
CREATE INDEX idx_drugs_category ON drugs(category);
CREATE INDEX idx_drugs_drug_type ON drugs(drug_type);

CREATE TABLE IF NOT EXISTS drug_inventory (
    id BIGINT PRIMARY KEY,
    drug_id BIGINT NOT NULL,
    batch_no VARCHAR(50),
    quantity INT NOT NULL,
    unit VARCHAR(20),
    purchase_price DECIMAL(10,2),
    production_date DATE,
    expiry_date DATE NOT NULL,
    supplier VARCHAR(200),
    status VARCHAR(20),
    version INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_drug_inventory_drug FOREIGN KEY (drug_id) REFERENCES drugs(id)
);
CREATE INDEX idx_drug_inventory_drug ON drug_inventory(drug_id);
CREATE INDEX idx_drug_inventory_batch ON drug_inventory(batch_no);
CREATE INDEX idx_drug_inventory_expiry ON drug_inventory(expiry_date);
CREATE INDEX idx_drug_inventory_status ON drug_inventory(status);

CREATE TABLE IF NOT EXISTS inbound_records (
    id BIGINT PRIMARY KEY,
    inbound_no VARCHAR(50) NOT NULL,
    drug_id BIGINT NOT NULL,
    batch_no VARCHAR(50) NOT NULL,
    quantity INT NOT NULL,
    purchase_price DECIMAL(10,2),
    production_date DATE,
    expiry_date DATE NOT NULL,
    supplier VARCHAR(200),
    operator_id BIGINT,
    inbound_date DATE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_inbound_records_drug FOREIGN KEY (drug_id) REFERENCES drugs(id)
);
CREATE UNIQUE INDEX uk_inbound_records_no ON inbound_records(inbound_no);
CREATE INDEX idx_inbound_records_drug ON inbound_records(drug_id);
CREATE INDEX idx_inbound_records_date ON inbound_records(inbound_date);

CREATE TABLE IF NOT EXISTS dispense_records (
    id BIGINT PRIMARY KEY,
    prescription_id BIGINT,
    patient_id BIGINT NOT NULL,
    drug_id BIGINT NOT NULL,
    inventory_id BIGINT,
    quantity INT NOT NULL,
    dispenser_id BIGINT,
    dispensed_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_dispense_records_drug FOREIGN KEY (drug_id) REFERENCES drugs(id),
    CONSTRAINT fk_dispense_records_inventory FOREIGN KEY (inventory_id) REFERENCES drug_inventory(id)
);
CREATE INDEX idx_dispense_records_prescription ON dispense_records(prescription_id);
CREATE INDEX idx_dispense_records_patient ON dispense_records(patient_id);
CREATE INDEX idx_dispense_records_drug ON dispense_records(drug_id);

CREATE TABLE IF NOT EXISTS inventory_checks (
    id BIGINT PRIMARY KEY,
    drug_id BIGINT NOT NULL,
    inventory_id BIGINT,
    book_quantity INT NOT NULL,
    actual_quantity INT NOT NULL,
    difference INT NOT NULL DEFAULT 0,
    check_date DATE NOT NULL,
    checker_id BIGINT,
    remark TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_inventory_checks_drug FOREIGN KEY (drug_id) REFERENCES drugs(id),
    CONSTRAINT fk_inventory_checks_inventory FOREIGN KEY (inventory_id) REFERENCES drug_inventory(id)
);
CREATE INDEX idx_inventory_checks_drug ON inventory_checks(drug_id);
CREATE INDEX idx_inventory_checks_date ON inventory_checks(check_date);
