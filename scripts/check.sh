#!/usr/bin/env bash
# HIS-Mixed Go 代码检查脚本
set -euo pipefail

PROJECT_ROOT="$(cd "$(dirname "$0")/.." && pwd)"
GO_DIR="$PROJECT_ROOT/backend/go"

echo "===== Go 代码检查 ====="
echo "目标目录: $GO_DIR"
echo ""

# gofmt 检查
echo "--- gofmt 格式检查 ---"
UNFORMATTED=$(gofmt -l "$GO_DIR" 2>&1)
if [ -n "$UNFORMATTED" ]; then
    echo "[FAIL] 以下文件格式不合规:"
    echo "$UNFORMATTED"
    FAILED=1
else
    echo "[OK] 所有文件格式合规"
fi

# go vet 检查
echo ""
echo "--- go vet 静态分析 ---"
if go vet ./... 2>&1; then
    echo "[OK] go vet 未发现问题"
else
    echo "[FAIL] go vet 发现问题"
    FAILED=1
fi

# go build 编译检查
echo ""
echo "--- go build 编译检查 ---"
if go build ./... 2>&1; then
    echo "[OK] 编译通过"
else
    echo "[FAIL] 编译失败"
    FAILED=1
fi

echo ""
if [ "${FAILED:-0}" -eq 0 ]; then
    echo "===== 全部检查通过 ====="
else
    echo "===== 存在检查未通过 ====="
    exit 1
fi
