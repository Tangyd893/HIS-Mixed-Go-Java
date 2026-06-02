-- 迁移: 系统管理服务表结构
-- 数据库: his_system (Java)

CREATE TABLE IF NOT EXISTS dict_types (
    id              BIGSERIAL       PRIMARY KEY,
    dict_name       VARCHAR(100)    NOT NULL,
    dict_type       VARCHAR(100)    NOT NULL,
    status          SMALLINT        NOT NULL DEFAULT 1,
    remark          VARCHAR(500),
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX uk_dict_types_type ON dict_types(dict_type);

CREATE TABLE IF NOT EXISTS dict_items (
    id              BIGSERIAL       PRIMARY KEY,
    dict_type       VARCHAR(100)    NOT NULL,
    label           VARCHAR(100)    NOT NULL,
    value           VARCHAR(100)    NOT NULL,
    sort            INT             NOT NULL DEFAULT 0,
    css_class       VARCHAR(100),
    status          SMALLINT        NOT NULL DEFAULT 1,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_dict_items_type FOREIGN KEY (dict_type) REFERENCES dict_types(dict_type)
);

CREATE INDEX idx_dict_items_type ON dict_items(dict_type);
CREATE INDEX idx_dict_items_value ON dict_items(dict_type, value);

CREATE TABLE IF NOT EXISTS sys_configs (
    id              BIGSERIAL       PRIMARY KEY,
    config_name     VARCHAR(100)    NOT NULL,
    config_key      VARCHAR(100)    NOT NULL,
    config_value    TEXT,
    config_type     VARCHAR(20),
    remark          VARCHAR(500),
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX uk_sys_configs_key ON sys_configs(config_key);

CREATE TABLE IF NOT EXISTS audit_logs (
    id              BIGSERIAL       PRIMARY KEY,
    user_id         BIGINT,
    username        VARCHAR(50),
    module          VARCHAR(50),
    action          VARCHAR(50),
    method          VARCHAR(200),
    request_url     VARCHAR(500),
    request_params  TEXT,
    response_code   INT,
    execute_time    BIGINT,
    ip_address      VARCHAR(50),
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_audit_logs_user ON audit_logs(user_id);
CREATE INDEX idx_audit_logs_module ON audit_logs(module);
CREATE INDEX idx_audit_logs_created ON audit_logs(created_at);

CREATE TABLE IF NOT EXISTS outbox_messages (
    id              BIGSERIAL       PRIMARY KEY,
    msg_id          VARCHAR(64)     NOT NULL,
    topic           VARCHAR(100)    NOT NULL,
    routing_key     VARCHAR(100),
    payload         JSONB           NOT NULL,
    status          SMALLINT        NOT NULL DEFAULT 0,
    retry_count     INT             NOT NULL DEFAULT 0,
    max_retry       INT             NOT NULL DEFAULT 3,
    next_retry_at   TIMESTAMP,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX uk_outbox_messages_msg_id ON outbox_messages(msg_id);
CREATE INDEX idx_outbox_messages_status ON outbox_messages(status);
CREATE INDEX idx_outbox_messages_next_retry ON outbox_messages(next_retry_at);
CREATE INDEX idx_outbox_messages_topic ON outbox_messages(topic);
