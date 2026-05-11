-- 迁移: 收费结算服务表结构
-- 数据库: his_billing (Java)

CREATE TABLE IF NOT EXISTS payments (
    id BIGSERIAL PRIMARY KEY,
    payment_no VARCHAR(50),
    patient_id BIGINT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    payment_method VARCHAR(20),
    status VARCHAR(20),
    third_party_tx_id VARCHAR(100),
    paid_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE UNIQUE INDEX uk_payments_no ON payments(payment_no);
CREATE INDEX idx_payments_patient ON payments(patient_id);
CREATE INDEX idx_payments_status ON payments(status);

CREATE TABLE IF NOT EXISTS bill_items (
    id BIGSERIAL PRIMARY KEY,
    bill_no VARCHAR(50),
    patient_id BIGINT NOT NULL,
    bill_type VARCHAR(30),
    ref_id BIGINT,
    item_name VARCHAR(200),
    quantity INT NOT NULL DEFAULT 1,
    unit_price DECIMAL(10,2),
    amount DECIMAL(10,2),
    insurance_ratio DECIMAL(5,4),
    insurance_amount DECIMAL(10,2),
    self_pay_amount DECIMAL(10,2),
    status VARCHAR(20) NOT NULL,
    payment_id BIGINT,
    paid_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_bill_items_payment FOREIGN KEY (payment_id) REFERENCES payments(id)
);
CREATE UNIQUE INDEX uk_bill_items_no ON bill_items(bill_no);
CREATE INDEX idx_bill_items_patient ON bill_items(patient_id);
CREATE INDEX idx_bill_items_status ON bill_items(status);
CREATE INDEX idx_bill_items_payment ON bill_items(payment_id);

CREATE TABLE IF NOT EXISTS refunds (
    id BIGSERIAL PRIMARY KEY,
    refund_no VARCHAR(50) NOT NULL,
    payment_id BIGINT,
    bill_item_id BIGINT,
    patient_id BIGINT NOT NULL,
    refund_amount DECIMAL(10,2) NOT NULL,
    refund_method VARCHAR(20),
    reason TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'PROCESSING',
    refunded_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_refunds_payment FOREIGN KEY (payment_id) REFERENCES payments(id),
    CONSTRAINT fk_refunds_bill_item FOREIGN KEY (bill_item_id) REFERENCES bill_items(id)
);
CREATE UNIQUE INDEX uk_refunds_no ON refunds(refund_no);
CREATE INDEX idx_refunds_patient ON refunds(patient_id);

CREATE TABLE IF NOT EXISTS daily_reports (
    id BIGSERIAL PRIMARY KEY,
    report_date DATE NOT NULL,
    total_revenue DECIMAL(14,2) NOT NULL DEFAULT 0,
    cash_amount DECIMAL(14,2) NOT NULL DEFAULT 0,
    wechat_amount DECIMAL(14,2) NOT NULL DEFAULT 0,
    alipay_amount DECIMAL(14,2) NOT NULL DEFAULT 0,
    insurance_amount DECIMAL(14,2) NOT NULL DEFAULT 0,
    refund_amount DECIMAL(14,2) NOT NULL DEFAULT 0,
    total_count INT NOT NULL DEFAULT 0,
    report_data JSONB,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE UNIQUE INDEX uk_daily_reports_date ON daily_reports(report_date);
