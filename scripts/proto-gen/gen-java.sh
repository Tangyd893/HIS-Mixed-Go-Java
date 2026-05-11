#!/usr/bin/env bash
# HIS-Mixed protoc Java 代码生成脚本
set -euo pipefail

PROJECT_ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
PROTO_DIR="$PROJECT_ROOT/backend/proto"
JAVA_OUT_DIR="$PROJECT_ROOT/backend/java/common/src/main/java"

echo "===== 生成 Java gRPC 代码 ====="
echo "Proto 目录: $PROTO_DIR"
echo "输出目录:   $JAVA_OUT_DIR"
echo ""

mkdir -p "$JAVA_OUT_DIR"

find "$PROTO_DIR" -type f -name "*.proto" ! -name "*}" | while read -r proto_file; do
    proto_rel=$(realpath --relative-to="$PROTO_DIR" "$proto_file")
    echo "处理: $proto_rel"

    protoc \
        --proto_path="$PROTO_DIR" \
        --java_out="$JAVA_OUT_DIR" \
        --grpc-java_out="$JAVA_OUT_DIR" \
        "$proto_file"
done

echo ""
echo "===== 生成完成 ====="
