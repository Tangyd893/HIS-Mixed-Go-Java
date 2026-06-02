#!/bin/bash
# HIS-Mixed 演示环境快速启动脚本

set -e

echo "=========================================="
echo "  HIS-Mixed 演示环境启动"
echo "=========================================="

# 颜色定义
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

# 检查Docker是否运行
if ! docker info > /dev/null 2>&1; then
    echo -e "${YELLOW}警告: Docker未运行，请先启动Docker${NC}"
    exit 1
fi

# 1. 启动基础设施
echo -e "${GREEN}[1/5] 启动基础设施...${NC}"
cd docker
docker-compose up -d postgres redis rabbitmq
cd ..

# 等待数据库就绪
echo -e "${YELLOW}等待数据库就绪...${NC}"
sleep 5

# 2. 初始化数据库
echo -e "${GREEN}[2/5] 初始化数据库...${NC}"
cd backend/go
if [ -f "scripts/db_init.sh" ]; then
    ./scripts/db_init.sh
fi

# 3. 插入演示数据
echo -e "${GREEN}[3/5] 插入演示数据...${NC}"
if [ -f "sql/demo_data.sql" ]; then
    psql -U his -d his_auth -f sql/seed_data.sql 2>/dev/null || true
    psql -U his -d his_registration -f sql/demo_data.sql 2>/dev/null || true
fi
cd ../..

# 4. 启动后端服务
echo -e "${GREEN}[4/5] 启动后端服务...${cd backend/go"
# 这里可以添加实际的服务启动命令
# go run cmd/gateway/main.go &
# go run cmd/registration/main.go &
# go run cmd/schedule/main.go &
# go run cmd/pharmacy/main.go &
cd ../..

# 5. 启动前端
echo -e "${GREEN}[5/5] 启动前端应用...${NC}"
cd frontend/his-web-patient
npm run dev &
cd ../his-web-admin
npm run dev &
cd ../..

echo ""
echo "=========================================="
echo -e "${GREEN}  演示环境启动完成！${NC}"
echo "=========================================="
echo ""
echo "访问地址："
echo "  患者端: http://localhost:5174"
echo "  管理端: http://localhost:5175"
echo ""
echo "演示账号："
echo "  管理员: admin / admin123"
echo "  医生: doctor01 / admin123"
echo ""
echo "按 Ctrl+C 停止所有服务"
echo ""

# 等待用户中断
wait
