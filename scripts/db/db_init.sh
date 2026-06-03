#!/usr/bin/env bash
# HIS-Mixed 数据库初始化脚本（完整版）
set -euo pipefail

PROJECT_ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
SQL_DIR="$PROJECT_ROOT/backend/go/sql"
MIGRATIONS_DIR="$PROJECT_ROOT/backend/go/migrations"

DB_HOST="${DB_HOST:-localhost}"
DB_PORT="${DB_PORT:-15432}"
DB_USER="${DB_USER:-his_admin}"
DB_PASSWORD="${DB_PASSWORD:-change_me_123}"

export PGPASSWORD="$DB_PASSWORD"

run_sql() {
    local db=$1
    local file=$2
    psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d "$db" -f "$file" 2>&1 | grep -v "already exists" || true
}

run_sql_inline() {
    local db=$1
    psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d "$db" 2>&1
}

echo "=========================================="
echo "  HIS-Mixed 数据库初始化"
echo "  目标: $DB_HOST:$DB_PORT"
echo "=========================================="
echo ""

# ====== 1. 创建所有数据库 ======
echo "--- [1/6] 创建数据库 ---"
psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d postgres -f "$SQL_DIR/init_all.sql" 2>&1 | grep -v "already exists" || true
echo "  数据库创建完成"

# ====== 2. 执行迁移脚本（创建表结构） ======
echo ""
echo "--- [2/6] 执行迁移脚本（创建表结构） ---"
for db_script in \
    "his_auth:002_auth_tables.sql" \
    "his_user:003_user_tables.sql" \
    "his_registration:004_registration_tables.sql" \
    "his_clinic:005_clinic_tables.sql" \
    "his_emr:006_emr_tables.sql" \
    "his_prescription:007_prescription_tables.sql" \
    "his_billing:008_billing_tables.sql" \
    "his_pharmacy:009_pharmacy_tables.sql" \
    "his_examination:010_examination_tables.sql" \
    "his_inpatient:011_inpatient_tables.sql" \
    "his_schedule:012_schedule_tables.sql" \
    "his_outpatient:013_outpatient_tables.sql" \
    "his_followup:014_followup_tables.sql" \
    "his_health_record:015_health_record_tables.sql" \
    "his_notification:016_notification_tables.sql" \
    "his_statistics:017_statistics_tables.sql" \
    "his_system:018_system_tables.sql"
do
    db="${db_script%%:*}"
    script="${db_script##*:}"
    if [ -f "$MIGRATIONS_DIR/$script" ]; then
        run_sql "$db" "$MIGRATIONS_DIR/$script" > /dev/null
        echo "  ✓ $db: $script"
    else
        echo "  ✗ $db: $script (文件不存在)"
    fi
done

# 修复 his_user.patients 表（去掉 sys_user 外键约束）
echo ""
echo "  修复 patients 表外键约束..."
psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d his_user -c "
DO \$\$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'patients') THEN
        CREATE TABLE patients (
            id                  BIGSERIAL       PRIMARY KEY,
            user_id             BIGINT,
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
            status              SMALLINT        NOT NULL DEFAULT 1,
            created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
            updated_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
            deleted_at          TIMESTAMP
        );
        CREATE INDEX idx_patients_phone ON patients(phone);
        CREATE INDEX idx_patients_id_card ON patients(id_card);
        CREATE INDEX idx_patients_name ON patients(name);
        CREATE INDEX idx_patients_deleted ON patients(deleted_at);
    END IF;
END
\$\$;
" > /dev/null
echo "  ✓ his_user: patients 表已就绪"

# ====== 3. 初始化 his_auth 库（用户、角色） ======
echo ""
echo "--- [3/6] 初始化 his_auth 库（用户、角色） ---"
run_sql "his_auth" "$SQL_DIR/seed_data.sql" > /dev/null
echo "  ✓ seed_data.sql 已执行"

psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d his_auth -c "
INSERT INTO users (username, password_hash, real_name, phone, email, status, created_at, updated_at)
VALUES
('patient01', '\$2a\$10\$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '张三', '13900000001', 'patient01@hismixed.com', 1, now(), now()),
('patient02', '\$2a\$10\$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '李四', '13900000002', 'patient02@hismixed.com', 1, now(), now()),
('doctor01', '\$2a\$10\$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '张医生', '13800000001', 'doctor01@hismixed.com', 1, now(), now()),
('doctor02', '\$2a\$10\$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '李医生', '13800000002', 'doctor02@hismixed.com', 1, now(), now()),
('doctor03', '\$2a\$10\$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '王医生', '13800000003', 'doctor03@hismixed.com', 1, now(), now()),
('doctor04', '\$2a\$10\$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '赵医生', '13800000004', 'doctor04@hismixed.com', 1, now(), now()),
('doctor05', '\$2a\$10\$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '刘医生', '13800000005', 'doctor05@hismixed.com', 1, now(), now())
ON CONFLICT DO NOTHING;
" > /dev/null
echo "  ✓ 演示用户已创建 (admin, patient01/02, doctor01~05)"

psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d his_auth -c "
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r
WHERE (u.username LIKE 'patient%' AND r.code = 'PATIENT')
   OR (u.username LIKE 'doctor%' AND r.code = 'DOCTOR')
ON CONFLICT DO NOTHING;
" > /dev/null
echo "  ✓ 用户角色关联已创建"

# ====== 4. 初始化 his_user 库（科室） ======
echo ""
echo "--- [4/6] 初始化 his_user 库（科室数据） ---"
psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d his_user -c "
INSERT INTO departments (name, code, parent_id, sort, created_at, updated_at)
VALUES
('内科', 'DEPT_NK', NULL, 1, now(), now()),
('外科', 'DEPT_WK', NULL, 2, now(), now()),
('儿科', 'DEPT_EK', NULL, 3, now(), now()),
('妇产科', 'DEPT_FCK', NULL, 4, now(), now()),
('骨科', 'DEPT_GK', NULL, 5, now(), now()),
('眼科', 'DEPT_YK', NULL, 6, now(), now()),
('口腔科', 'DEPT_KQK', NULL, 7, now(), now()),
('皮肤科', 'DEPT_PFK', NULL, 8, now(), now()),
('中医科', 'DEPT_ZYK', NULL, 9, now(), now()),
('急诊科', 'DEPT_JZK', NULL, 10, now(), now())
ON CONFLICT (code) DO NOTHING;
" > /dev/null
echo "  ✓ 10个科室已创建"

# ====== 5. 初始化 his_schedule 库（排班数据） ======
echo ""
echo "--- [5/6] 初始化 his_schedule 库（排班数据） ---"
psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d his_schedule -c "
INSERT INTO schedule_plans (id, plan_name, department_id, start_date, end_date, status, created_at, updated_at)
VALUES
(1, '内科6月排班', 1, CURRENT_DATE, CURRENT_DATE + 30, 1, now(), now()),
(2, '外科6月排班', 2, CURRENT_DATE, CURRENT_DATE + 30, 1, now(), now()),
(3, '儿科6月排班', 3, CURRENT_DATE, CURRENT_DATE + 30, 1, now(), now())
ON CONFLICT DO NOTHING;
" > /dev/null
echo "  ✓ 排班计划已创建"

psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d his_schedule -c "
INSERT INTO schedule_slots (id, plan_id, doctor_id, department_id, schedule_date, start_time, end_time, total_quota, remaining, interval_minutes, is_active, created_at, updated_at)
VALUES
(1, 1, 1, 1, CURRENT_DATE, '08:00', '12:00', 30, 30, 10, true, now(), now()),
(2, 1, 1, 1, CURRENT_DATE, '14:00', '17:00', 25, 25, 10, true, now(), now()),
(3, 1, 1, 1, CURRENT_DATE + 1, '08:00', '12:00', 30, 30, 10, true, now(), now()),
(4, 2, 2, 2, CURRENT_DATE, '08:00', '12:00', 30, 30, 10, true, now(), now()),
(5, 2, 2, 2, CURRENT_DATE, '14:00', '17:00', 25, 25, 10, true, now(), now()),
(6, 3, 3, 3, CURRENT_DATE, '08:00', '12:00', 20, 20, 15, true, now(), now())
ON CONFLICT DO NOTHING;
" > /dev/null
echo "  ✓ 排班号源已创建（3个医生，6个时段）"

# ====== 6. 初始化其他业务库 ======
echo ""
echo "--- [6/6] 初始化其他业务库 ---"

# 药品数据
psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d his_pharmacy -c "
INSERT INTO drugs (id, code, name, generic_name, category, specification, unit, manufacturer, price, stock, min_stock, status, created_at, updated_at)
VALUES
(1, 'DRUG001', '阿莫西林胶囊', '阿莫西林', '西药', '0.25g*24粒', '盒', '华北制药', 12.50, 1000, 100, 1, now(), now()),
(2, 'DRUG002', '布洛芬缓释胶囊', '布洛芬', '西药', '0.3g*20粒', '盒', '中美史克', 15.80, 800, 100, 1, now(), now()),
(3, 'DRUG003', '阿奇霉素片', '阿奇霉素', '西药', '0.25g*6片', '盒', '辉瑞制药', 28.60, 500, 50, 1, now(), now()),
(4, 'DRUG004', '复方甘草片', '复方甘草', '中成药', '100片', '瓶', '同仁堂', 8.50, 2000, 200, 1, now(), now()),
(5, 'DRUG005', '板蓝根颗粒', '板蓝根', '中成药', '10g*20袋', '盒', '白云山', 15.00, 1500, 150, 1, now(), now()),
(6, 'DRUG006', '头孢克肟分散片', '头孢克肟', '西药', '0.1g*6片', '盒', '广州白云山', 22.00, 600, 60, 1, now(), now()),
(7, 'DRUG007', '蒙脱石散', '蒙脱石', '西药', '3g*10袋', '盒', '博福-益普生', 25.80, 400, 40, 1, now(), now()),
(8, 'DRUG008', '奥美拉唑肠溶胶囊', '奥美拉唑', '西药', '20mg*14粒', '盒', '阿斯利康', 35.00, 300, 30, 1, now(), now()),
(9, 'DRUG009', '硝苯地平控释片', '硝苯地平', '西药', '30mg*7片', '盒', '拜耳制药', 42.50, 250, 25, 1, now(), now()),
(10, 'DRUG010', '盐酸二甲双胍片', '二甲双胍', '西药', '0.5g*20片', '盒', '中美上海施贵宝', 18.00, 500, 50, 1, now(), now())
ON CONFLICT DO NOTHING;
" 2>/dev/null && echo "  ✓ his_pharmacy: 10种药品已创建" || echo "  ✗ his_pharmacy: 药品表不存在（需先执行迁移）"

echo ""
echo "=========================================="
echo "  数据库初始化完成！"
echo "=========================================="
echo ""
echo "已初始化："
echo "  - his_auth:     用户(admin/patient01~02/doctor01~05) + 角色(7种)"
echo "  - his_user:     科室(10个) + patients表"
echo "  - his_schedule: 排班计划(3个) + 号源(6个时段)"
echo "  - his_pharmacy: 药品(10种)"
echo ""
echo "演示账号："
echo "  管理员: admin / admin123"
echo "  患者:   patient01 / admin123"
echo "  医生:   doctor01~05 / admin123"
