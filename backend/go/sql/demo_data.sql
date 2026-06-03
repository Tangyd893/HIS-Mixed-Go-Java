-- HIS-Mixed 演示数据脚本
-- 用于MVP演示，包含完整的业务数据

-- ============================================================
-- 1. 科室数据
-- ============================================================
INSERT INTO department (name, code, parent_id, level, sort, status, create_time, update_time)
VALUES
('内科', 'DEPT_NK', 0, 1, 1, 1, now(), now()),
('外科', 'DEPT_WK', 0, 1, 2, 1, now(), now()),
('儿科', 'DEPT_EK', 0, 1, 3, 1, now(), now()),
('妇产科', 'DEPT_FCK', 0, 1, 4, 1, now(), now()),
('骨科', 'DEPT_GK', 0, 1, 5, 1, now(), now()),
('眼科', 'DEPT_YK', 0, 1, 6, 1, now(), now()),
('口腔科', 'DEPT_KQK', 0, 1, 7, 1, now(), now()),
('皮肤科', 'DEPT_PFK', 0, 1, 8, 1, now(), now()),
('中医科', 'DEPT_ZYK', 0, 1, 9, 1, now(), now()),
('急诊科', 'DEPT_JZK', 0, 1, 10, 1, now(), now())
ON CONFLICT (code) DO NOTHING;

-- ============================================================
-- 2. 患者用户账号
-- ============================================================
INSERT INTO users (username, password_hash, real_name, phone, email, status, created_at, updated_at)
VALUES
('patient01', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '张三', '13900000001', 'patient01@hismixed.com', 1, now(), now()),
('patient02', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '李四', '13900000002', 'patient02@hismixed.com', 1, now(), now())
ON CONFLICT (username) DO NOTHING;

-- 患者角色关联
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r
WHERE u.username LIKE 'patient%' AND r.code = 'PATIENT'
ON CONFLICT DO NOTHING;

-- ============================================================
-- 3. 医生用户账号
-- ============================================================
INSERT INTO users (username, password_hash, real_name, phone, email, status, created_at, updated_at)
VALUES
('doctor01', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '张医生', '13800000001', 'doctor01@hismixed.com', 1, now(), now()),
('doctor02', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '李医生', '13800000002', 'doctor02@hismixed.com', 1, now(), now()),
('doctor03', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '王医生', '13800000003', 'doctor03@hismixed.com', 1, now(), now()),
('doctor04', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '赵医生', '13800000004', 'doctor04@hismixed.com', 1, now(), now()),
('doctor05', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '刘医生', '13800000005', 'doctor05@hismixed.com', 1, now(), now())
ON CONFLICT (username) DO NOTHING;

-- 医生角色关联
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r
WHERE u.username LIKE 'doctor%' AND r.code = 'DOCTOR'
ON CONFLICT DO NOTHING;

-- ============================================================
-- 3. 医生信息
-- ============================================================
INSERT INTO doctor (user_id, name, gender, title, department_id, specialty, introduction, status, create_time, update_time)
SELECT u.id, u.real_name, 'M', '主任医师', d.id, '心血管疾病诊治', '从事心血管内科工作20余年，擅长冠心病、高血压、心力衰竭等疾病的诊治。', 1, now(), now()
FROM users u, department d WHERE u.username = 'doctor01' AND d.code = 'DEPT_NK'
ON CONFLICT DO NOTHING;

INSERT INTO doctor (user_id, name, gender, title, department_id, specialty, introduction, status, create_time, update_time)
SELECT u.id, u.real_name, 'M', '副主任医师', d.id, '普外科手术', '擅长胃肠道肿瘤、肝胆疾病的微创手术治疗。', 1, now(), now()
FROM users u, department d WHERE u.username = 'doctor02' AND d.code = 'DEPT_WK'
ON CONFLICT DO NOTHING;

INSERT INTO doctor (user_id, name, gender, title, department_id, specialty, introduction, status, create_time, update_time)
SELECT u.id, u.real_name, 'F', '主治医师', d.id, '小儿呼吸系统疾病', '擅长小儿肺炎、哮喘、支气管炎等呼吸系统疾病的诊治。', 1, now(), now()
FROM users u, department d WHERE u.username = 'doctor03' AND d.code = 'DEPT_EK'
ON CONFLICT DO NOTHING;

INSERT INTO doctor (user_id, name, gender, title, department_id, specialty, introduction, status, create_time, update_time)
SELECT u.id, u.real_name, 'F', '副主任医师', d.id, '产科高危妊娠', '擅长高危妊娠管理、产前诊断、难产处理。', 1, now(), now()
FROM users u, department d WHERE u.username = 'doctor04' AND d.code = 'DEPT_FCK'
ON CONFLICT DO NOTHING;

INSERT INTO doctor (user_id, name, gender, title, department_id, specialty, introduction, status, create_time, update_time)
SELECT u.id, u.real_name, 'M', '主任医师', d.id, '关节外科', '擅长人工关节置换、运动损伤、骨折微创治疗。', 1, now(), now()
FROM users u, department d WHERE u.username = 'doctor05' AND d.code = 'DEPT_GK'
ON CONFLICT DO NOTHING;

-- ============================================================
-- 4. 排班数据（未来7天）
-- ============================================================
INSERT INTO schedule (doctor_id, schedule_date, time_slot, max_patients, current_patients, status, create_time, update_time)
SELECT d.id, CURRENT_DATE + i, 'morning', 30, 0, 1, now(), now()
FROM doctor d, generate_series(0, 6) AS i
WHERE d.status = 1
ON CONFLICT DO NOTHING;

INSERT INTO schedule (doctor_id, schedule_date, time_slot, max_patients, current_patients, status, create_time, update_time)
SELECT d.id, CURRENT_DATE + i, 'afternoon', 25, 0, 1, now(), now()
FROM doctor d, generate_series(0, 6) AS i
WHERE d.status = 1
ON CONFLICT DO NOTHING;

-- ============================================================
-- 5. 药品数据
-- ============================================================
INSERT INTO drug (code, name, generic_name, category, specification, unit, manufacturer, price, stock, min_stock, status, create_time, update_time)
VALUES
('DRUG001', '阿莫西林胶囊', '阿莫西林', '西药', '0.25g*24粒', '盒', '华北制药', 12.50, 1000, 100, 1, now(), now()),
('DRUG002', '布洛芬缓释胶囊', '布洛芬', '西药', '0.3g*20粒', '盒', '中美史克', 15.80, 800, 100, 1, now(), now()),
('DRUG003', '阿奇霉素片', '阿奇霉素', '西药', '0.25g*6片', '盒', '辉瑞制药', 28.60, 500, 50, 1, now(), now()),
('DRUG004', '复方甘草片', '复方甘草', '中成药', '100片', '瓶', '同仁堂', 8.50, 2000, 200, 1, now(), now()),
('DRUG005', '板蓝根颗粒', '板蓝根', '中成药', '10g*20袋', '盒', '白云山', 15.00, 1500, 150, 1, now(), now()),
('DRUG006', '头孢克肟分散片', '头孢克肟', '西药', '0.1g*6片', '盒', '广州白云山', 22.00, 600, 60, 1, now(), now()),
('DRUG007', '蒙脱石散', '蒙脱石', '西药', '3g*10袋', '盒', '博福-益普生', 25.80, 400, 40, 1, now(), now()),
('DRUG008', '奥美拉唑肠溶胶囊', '奥美拉唑', '西药', '20mg*14粒', '盒', '阿斯利康', 35.00, 300, 30, 1, now(), now()),
('DRUG009', '硝苯地平控释片', '硝苯地平', '西药', '30mg*7片', '盒', '拜耳制药', 42.50, 250, 25, 1, now(), now()),
('DRUG010', '盐酸二甲双胍片', '二甲双胍', '西药', '0.5g*20片', '盒', '中美上海施贵宝', 18.00, 500, 50, 1, now(), now())
ON CONFLICT (code) DO NOTHING;

-- ============================================================
-- 6. 患者数据
-- ============================================================
INSERT INTO patient (user_id, name, gender, birth_date, phone, id_card, address, status, create_time, update_time)
SELECT u.id, '张三', 'M', '1990-01-15', '13900000001', '110101199001150011', '北京市东城区XX小区1号楼101', 1, now(), now()
FROM users u WHERE u.username = 'patient01'
ON CONFLICT DO NOTHING;

INSERT INTO patient (user_id, name, gender, birth_date, phone, id_card, address, status, create_time, update_time)
SELECT u.id, '李四', 'F', '1985-06-20', '13900000002', '110101198506200022', '北京市西城区XX路2号', 1, now(), now()
FROM users u WHERE u.username = 'patient02'
ON CONFLICT DO NOTHING;

INSERT INTO patient (user_id, name, gender, birth_date, phone, id_card, address, status, create_time, update_time)
VALUES
(NULL, '王五', 'M', '1978-03-08', '13900000003', '110101197803080033', '北京市朝阳区XX街3号', 1, now(), now()),
(NULL, '赵六', 'F', '1995-11-25', '13900000004', '110101199511250044', '北京市海淀区XX路4号', 1, now(), now()),
(NULL, '钱七', 'M', '2000-08-10', '13900000005', '110101200008100055', '北京市丰台区XX小区5号楼', 1, now(), now())
ON CONFLICT DO NOTHING;

-- ============================================================
-- 7. 挂号数据（今天的挂号）
-- ============================================================
INSERT INTO registration (patient_id, doctor_id, schedule_id, registration_type, queue_number, status, create_time, update_time)
SELECT p.id, d.id, s.id, 'NORMAL', 1, 'COMPLETED', now() - interval '2 hours', now()
FROM patient p, doctor d, schedule s
WHERE p.name = '张三' AND d.name = '张医生' AND s.doctor_id = d.id AND s.schedule_date = CURRENT_DATE AND s.time_slot = 'morning'
LIMIT 1
ON CONFLICT DO NOTHING;

INSERT INTO registration (patient_id, doctor_id, schedule_id, registration_type, queue_number, status, create_time, update_time)
SELECT p.id, d.id, s.id, 'NORMAL', 2, 'WAITING', now() - interval '1 hour', now()
FROM patient p, doctor d, schedule s
WHERE p.name = '李四' AND d.name = '王医生' AND s.doctor_id = d.id AND s.schedule_date = CURRENT_DATE AND s.time_slot = 'morning'
LIMIT 1
ON CONFLICT DO NOTHING;

INSERT INTO registration (patient_id, doctor_id, schedule_id, registration_type, queue_number, status, create_time, update_time)
SELECT p.id, d.id, s.id, 'EXPERT', 3, 'WAITING', now() - interval '30 minutes', now()
FROM patient p, doctor d, schedule s
WHERE p.name = '王五' AND d.name = '刘医生' AND s.doctor_id = d.id AND s.schedule_date = CURRENT_DATE AND s.time_slot = 'morning'
LIMIT 1
ON CONFLICT DO NOTHING;

-- 更新排班当前挂号数
UPDATE schedule SET current_patients = (
    SELECT COUNT(*) FROM registration r WHERE r.schedule_id = schedule.id AND r.status IN ('WAITING', 'COMPLETED')
);

-- ============================================================
-- 完成提示
-- ============================================================
-- 演示数据插入完成
-- 默认管理员账号: admin / admin123
-- 默认医生账号: doctor01-doctor05 / admin123
