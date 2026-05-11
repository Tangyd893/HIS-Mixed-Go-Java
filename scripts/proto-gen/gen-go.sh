#!/usr/bin/env bash
# HIS-Mixed protoc Go 代码生成脚本
set -euo pipefail

PROJECT_ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
PROTO_DIR="$PROJECT_ROOT/backend/proto"
GO_OUT_DIR="$PROJECT_ROOT/backend/go/pkg/grpc"

echo "===== 生成 Go gRPC 代码 ====="
echo "Proto 目录: $PROTO_DIR"
echo "输出目录:   $GO_OUT_DIR"
echo ""

mkdir -p "$GO_OUT_DIR"

find "$PROTO_DIR" -type f -name "*.proto" ! -name "*}" | while read -r proto_file; do
    proto_name=$(basename "$proto_file")
    proto_dir=$(dirname "$proto_file")
    proto_rel=$(realpath --relative-to="$PROTO_DIR" "$proto_file")
    echo "处理: $proto_rel"

    protoc \
        --proto_path="$PROTO_DIR" \
        --go_out="$GO_OUT_DIR" \
        --go_opt=paths=source_relative \
        --go-grpc_out="$GO_OUT_DIR" \
        --go-grpc_opt=paths=source_relative \
        "$proto_file"
done

echo ""
echo "===== 生成完成 ====="
