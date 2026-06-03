-- HIS-Mixed 用户相关演示数据（his_user 库）

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
