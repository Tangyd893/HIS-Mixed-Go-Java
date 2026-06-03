#!/bin/bash
# HIS-Mixed 演示环境快速启动脚本

set -e

echo "=========================================="
echo "  HIS-Mixed 演示环境启动"
echo "=========================================="

# 颜色定义
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m'

# 检查Docker是否运行
if ! docker info > /dev/null 2>&1; then
    echo -e "${RED}错误: Docker未运行，请先启动Docker${NC}"
    exit 1
fi

# 检查是否启用完整模式
FULL_MODE=${FULL_MODE:-false}

# 1. 启动基础设施
echo -e "${GREEN}[1/5] 启动基础设施...${NC}"
cd docker
docker-compose up -d postgresql redis rabbitmq
cd ..

# 等待数据库就绪
echo -e "${YELLOW}等待数据库就绪...${NC}"
sleep 5

# 2. 初始化数据库
echo -e "${GREEN}[2/5] 初始化数据库...${NC}"
cd scripts/db
chmod +x db_init.sh
./db_init.sh
cd ../..

# 3. 启动后端服务
echo -e "${GREEN}[3/5] 启动后端服务...${NC}"

# 启动 Go 服务
cd backend/go
echo -e "${YELLOW}启动 Gateway 服务 (:8080)...${NC}"
go run cmd/gateway/main.go &
sleep 2

echo -e "${YELLOW}启动 Registration 服务 (:8083)...${NC}"
go run cmd/registration/main.go &
sleep 1

echo -e "${YELLOW}启动 Schedule 服务 (:8090)...${NC}"
go run cmd/schedule/main.go &
sleep 1

echo -e "${YELLOW}启动 Statistics 服务 (:8095)...${NC}"
go run cmd/statistics/main.go &
sleep 1

# 完整模式下启动更多 Go 服务
if [ "$FULL_MODE" = "true" ]; then
    echo -e "${YELLOW}启动 Pharmacy 服务 (:8087)...${NC}"
    go run cmd/pharmacy/main.go &
    sleep 1

    echo -e "${YELLOW}启动 Examination 服务 (:8088)...${NC}"
    go run cmd/examination/main.go &
    sleep 1

    echo -e "${YELLOW}启动 Outpatient 服务 (:8091)...${NC}"
    go run cmd/outpatient/main.go &
    sleep 1

    echo -e "${YELLOW}启动 Followup 服务 (:8092)...${NC}"
    go run cmd/followup/main.go &
    sleep 1

    echo -e "${YELLOW}启动 Notification 服务 (:8094)...${NC}"
    go run cmd/notification/main.go &
    sleep 1
fi

cd ../..

# 启动 Java 服务
cd backend/java
echo -e "${YELLOW}启动 his-auth 服务 (:8081)...${NC}"
mvn spring-boot:run -pl his-auth -q &
sleep 3

echo -e "${YELLOW}启动 his-user 服务 (:8082)...${NC}"
mvn spring-boot:run -pl his-user -q &
sleep 3

# 完整模式下启动更多 Java 服务
if [ "$FULL_MODE" = "true" ]; then
    echo -e "${YELLOW}启动 his-clinic 服务 (:8084)...${NC}"
    mvn spring-boot:run -pl his-clinic -q &
    sleep 2

    echo -e "${YELLOW}启动 his-prescription 服务 (:8085)...${NC}"
    mvn spring-boot:run -pl his-prescription -q &
    sleep 2

    echo -e "${YELLOW}启动 his-billing 服务 (:8086)...${NC}"
    mvn spring-boot:run -pl his-billing -q &
    sleep 2

    echo -e "${YELLOW}启动 his-emr 服务 (:8097)...${NC}"
    mvn spring-boot:run -pl his-emr -q &
    sleep 2

    echo -e "${YELLOW}启动 his-system 服务 (:8096)...${NC}"
    mvn spring-boot:run -pl his-system -q &
    sleep 2
fi

cd ../..

echo -e "${GREEN}后端服务启动完成，等待服务就绪...${NC}"
sleep 5

# 4. 启动前端
echo -e "${GREEN}[4/5] 启动前端应用...${NC}"
cd frontend/his-web-patient
npm install -q 2>/dev/null || true
npm run dev &
cd ../his-web-admin
npm install -q 2>/dev/null || true
npm run dev &
cd ../..

# 5. 显示启动信息
echo ""
echo "=========================================="
echo -e "${GREEN}  演示环境启动完成！${NC}"
echo "=========================================="
echo ""
echo "访问地址："
echo "  患者端: http://localhost:5174"
echo "  管理端: http://localhost:5175"
echo ""
echo "后端服务："
echo "  Gateway:     http://localhost:8080"
echo "  Auth:        http://localhost:8081"
echo "  User:        http://localhost:8082"
echo "  Registration:http://localhost:8083"
echo "  Schedule:    http://localhost:8090"
echo "  Statistics:  http://localhost:8095"
echo ""
echo "演示账号："
echo "  管理员: admin / admin123"
echo "  医生: doctor01~05 / admin123"
echo "  患者: patient01 / admin123"
echo ""
echo -e "${YELLOW}微信开发者工具演示（可选）：${NC}"
echo "  1. 打开微信开发者工具"
echo "  2. 导入项目: frontend/his-mp-webview"
echo "  3. 详情 → 本地设置 → 勾选「不校验合法域名」"
echo "  4. 编译运行即可在模拟器中查看患者端"
echo ""
echo -e "${YELLOW}提示：使用 FULL_MODE=true ./scripts/start_demo.sh 可启动所有服务${NC}"
echo ""
echo "按 Ctrl+C 停止所有服务"
echo ""

# 等待用户中断
wait
