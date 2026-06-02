-- 迁移: 消息通知服务表结构
-- 数据库: his_notification (Go)

CREATE TABLE IF NOT EXISTS notification_templates (
    id              BIGINT          PRIMARY KEY,
    template_code   VARCHAR(50),
    template_name   VARCHAR(100)    NOT NULL,
    channel         VARCHAR(10),
    title           VARCHAR(200),
    content         TEXT            NOT NULL,
    is_active       BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX uk_notification_templates_code ON notification_templates(template_code);
CREATE INDEX idx_notification_templates_channel ON notification_templates(channel);

CREATE TABLE IF NOT EXISTS notification_messages (
    id              BIGINT          PRIMARY KEY,
    template_code   VARCHAR(50),
    channel         VARCHAR(10)     NOT NULL,
    recipient       VARCHAR(200)    NOT NULL,
    user_id         BIGINT,
    title           VARCHAR(200),
    content         TEXT            NOT NULL,
    params          JSONB,
    status          VARCHAR(20),
    is_read         BOOLEAN         NOT NULL DEFAULT FALSE,
    sent_at         TIMESTAMP,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_notification_messages_channel ON notification_messages(channel);
CREATE INDEX idx_notification_messages_user ON notification_messages(user_id);
CREATE INDEX idx_notification_messages_status ON notification_messages(status);
CREATE INDEX idx_notification_messages_read ON notification_messages(is_read);

CREATE TABLE IF NOT EXISTS notification_configs (
    id              BIGINT          PRIMARY KEY,
    config_key      VARCHAR(100)    NOT NULL,
    config_name     VARCHAR(100)    NOT NULL,
    channel         VARCHAR(10)     NOT NULL,
    channel_config  JSONB,
    rate_limit      INT             NOT NULL DEFAULT 100,
    is_active       BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX uk_notification_configs_key ON notification_configs(config_key);
