-- HIS-Mixed 默认账号和基础字典数据

-- 默认管理员账号
INSERT INTO users (username, password_hash, real_name, phone, email, status, created_at, updated_at)
VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '系统管理员', '13800000000', 'admin@hismixed.com', 1, now(), now())
ON CONFLICT (username) DO NOTHING;

-- 默认角色
INSERT INTO roles (code, name, description, sort, status, created_at, updated_at)
VALUES
('ADMIN', '超级管理员', '系统超级管理员，拥有全部权限', 1, 1, now(), now()),
('DOCTOR', '医生', '门诊/住院医生', 2, 1, now(), now()),
('NURSE', '护士', '护理人员', 3, 1, now(), now()),
('PHARMACIST', '药剂师', '药房工作人员', 4, 1, now(), now()),
('CASHIER', '收费员', '挂号/收费窗口人员', 5, 1, now(), now()),
('TECHNICIAN', '医技人员', '检查/检验科室人员', 6, 1, now(), now()),
('PATIENT', '患者', '门诊/住院患者', 7, 1, now(), now())
ON CONFLICT (code) DO NOTHING;

-- 管理员角色关联
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r
WHERE u.username = 'admin' AND r.code = 'ADMIN'
ON CONFLICT DO NOTHING;

-- 基础字典: 挂号类型
INSERT INTO sys_dict (dict_type, dict_code, dict_value, sort, status, create_time, update_time)
VALUES
('REGISTRATION_TYPE', 'NORMAL', '普通号', 1, 1, now(), now()),
('REGISTRATION_TYPE', 'EXPERT', '专家号', 2, 1, now(), now()),
('REGISTRATION_TYPE', 'EMERGENCY', '急诊', 3, 1, now(), now())
ON CONFLICT (dict_type, dict_code) DO NOTHING;

-- 基础字典: 性别
INSERT INTO sys_dict (dict_type, dict_code, dict_value, sort, status, create_time, update_time)
VALUES
('GENDER', 'M', '男', 1, 1, now(), now()),
('GENDER', 'F', '女', 2, 1, now(), now()),
('GENDER', 'U', '未知', 3, 1, now(), now())
ON CONFLICT (dict_type, dict_code) DO NOTHING;

-- 基础字典: 处方状态
INSERT INTO sys_dict (dict_type, dict_code, dict_value, sort, status, create_time, update_time)
VALUES
('PRESCRIPTION_STATUS', 'PENDING', '待审核', 1, 1, now(), now()),
('PRESCRIPTION_STATUS', 'APPROVED', '已审核', 2, 1, now(), now()),
('PRESCRIPTION_STATUS', 'DISPENSED', '已发药', 3, 1, now(), now()),
('PRESCRIPTION_STATUS', 'REJECTED', '已退回', 4, 1, now(), now())
ON CONFLICT (dict_type, dict_code) DO NOTHING;

-- 基础字典: 结算状态
INSERT INTO sys_dict (dict_type, dict_code, dict_value, sort, status, create_time, update_time)
VALUES
('BILLING_STATUS', 'UNPAID', '未结算', 1, 1, now(), now()),
('BILLING_STATUS', 'PAID', '已结算', 2, 1, now(), now()),
('BILLING_STATUS', 'REFUNDED', '已退费', 3, 1, now(), now())
ON CONFLICT (dict_type, dict_code) DO NOTHING;
