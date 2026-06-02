#!/usr/bin/env bash
set -e

BASE_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$BASE_DIR"

HIS_BASE_URL="${HIS_BASE_URL:-http://localhost:8080}"

echo "========================================="
echo "  HIS-Mixed 集成测试"
echo "  目标地址: $HIS_BASE_URL"
echo "========================================="

export HIS_BASE_URL
export HIS_INTEGRATION_TEST=true

go mod tidy 2>/dev/null || true

echo ""
echo "运行集成测试..."
go test -v -count=1 ./api/ 2>&1

echo ""
echo "========================================="
echo "  集成测试完成"
echo "========================================="
