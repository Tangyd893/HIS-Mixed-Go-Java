#!/bin/bash
# HIS-Mixed 演示环境验证脚本

set -e

# 颜色定义
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m'

echo "=========================================="
echo "  HIS-Mixed 演示环境验证"
echo "=========================================="
echo ""

PASSED=0
FAILED=0

check_service() {
    local name=$1
    local url=$2
    local expected_code=${3:-200}
    
    if curl -s -o /dev/null -w "%{http_code}" "$url" | grep -q "$expected_code"; then
        echo -e "${GREEN}✓${NC} $name: 运行正常"
        ((PASSED++))
    else
        echo -e "${RED}✗${NC} $name: 未响应或异常"
        ((FAILED++))
    fi
}

echo "--- 后端服务检查 ---"
check_service "Gateway (8080)" "http://localhost:8080/health" 200
check_service "Auth (8081)" "http://localhost:8081/actuator/health" 200
check_service "User (8082)" "http://localhost:8082/actuator/health" 200
check_service "Registration (8083)" "http://localhost:8083/health" 200
check_service "Schedule (8090)" "http://localhost:8090/health" 200
check_service "Statistics (8095)" "http://localhost:8095/health" 200

echo ""
echo "--- 可选服务检查（FULL_MODE） ---"
check_service "Pharmacy (8087)" "http://localhost:8087/health" 200 || true
check_service "Billing (8086)" "http://localhost:8086/actuator/health" 200 || true
check_service "Examination (8088)" "http://localhost:8088/health" 200 || true
check_service "Followup (8092)" "http://localhost:8092/health" 200 || true
check_service "Outpatient (8091)" "http://localhost:8091/health" 200 || true

echo ""
echo "--- 前端服务检查 ---"
check_service "患者端 (5174)" "http://localhost:5174" 200
check_service "管理端 (5175)" "http://localhost:5175" 200

echo ""
echo "--- API 代理检查 ---"
check_service "Gateway 代理" "http://localhost:8080/api/auth/login" 405

echo ""
echo "=========================================="
echo -e "验证结果: ${GREEN}$PASSED 通过${NC}, ${RED}$FAILED 失败${NC}"
echo "=========================================="

if [ $FAILED -eq 0 ]; then
    echo ""
    echo -e "${GREEN}所有服务运行正常！${NC}"
    echo ""
    echo "演示账号："
    echo "  管理员: admin / admin123"
    echo "  患者: patient01 / admin123"
    echo ""
    echo "访问地址："
    echo "  患者端: http://localhost:5174"
    echo "  管理端: http://localhost:5175"
    exit 0
else
    echo ""
    echo -e "${YELLOW}部分服务未正常启动，请检查日志${NC}"
    exit 1
fi
