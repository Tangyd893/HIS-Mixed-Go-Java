-- 迁移: 统计报表服务表结构
-- 数据库: his_statistics (Go)

CREATE TABLE IF NOT EXISTS stat_snapshots (
    id              BIGINT          PRIMARY KEY,
    stat_type       VARCHAR(50)     NOT NULL,
    stat_date       DATE            NOT NULL,
    stat_data       JSONB           NOT NULL,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_stat_snapshots_type ON stat_snapshots(stat_type);
CREATE INDEX idx_stat_snapshots_date ON stat_snapshots(stat_date);
CREATE UNIQUE INDEX uk_stat_snapshots_type_date ON stat_snapshots(stat_type, stat_date);

CREATE TABLE IF NOT EXISTS stat_exports (
    id              BIGINT          PRIMARY KEY,
    export_type     VARCHAR(50)     NOT NULL,
    export_params   JSONB,
    file_name       VARCHAR(200),
    file_format     VARCHAR(10)     NOT NULL DEFAULT 'xlsx',
    file_size       BIGINT,
    object_key      VARCHAR(200),
    status          VARCHAR(20)     NOT NULL DEFAULT 'PROCESSING',
    exported_at     TIMESTAMP,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_stat_exports_type ON stat_exports(export_type);
CREATE INDEX idx_stat_exports_status ON stat_exports(status);
