#!/usr/bin/env bash
# HIS-Mixed 数据库初始化脚本
set -euo pipefail

PROJECT_ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
SQL_DIR="$PROJECT_ROOT/backend/go/sql"

DB_HOST="${DB_HOST:-localhost}"
DB_PORT="${DB_PORT:-5432}"
DB_USER="${DB_USER:-his_admin}"
DB_PASSWORD="${DB_PASSWORD:-change_me_123}"

export PGPASSWORD="$DB_PASSWORD"

echo "===== 数据库初始化 ====="
echo "目标: $DB_HOST:$DB_PORT"
echo ""

echo "--- 执行 init_all.sql (创建数据库) ---"
psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d postgres -f "$SQL_DIR/init_all.sql"

echo ""
echo "--- 执行 seed_data.sql (初始化数据) ---"
psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d his_auth -f "$SQL_DIR/seed_data.sql"

echo ""
echo "===== 初始化完成 ====="
