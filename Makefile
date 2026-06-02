.PHONY: build check lint test clean proto

# Go 编译所有服务
build-go:
	cd backend/go && go build ./cmd/...

# Java 编译所有服务
build-java:
	cd backend/java && mvn compile -q

# 全部编译
build: build-go build-java

# Go 测试
test-go:
	cd backend/go && go test ./... -count=1

# Java 测试
test-java:
	cd backend/java && mvn test

# 全部测试
test: test-go test-java

# 代码质量检查
check:
	bash scripts/check.sh

# 代码格式化
fmt:
	cd backend/go && gofmt -w .
	cd backend/java && mvn spotless:apply -q || true

# 生成 proto 代码
proto:
	bash scripts/proto-gen/gen-go.sh
	bash scripts/proto-gen/gen-java.sh

# 清理构建产物
clean:
	rm -rf backend/go/bin/
	cd backend/java && mvn clean -q

# 安装前端依赖
setup-frontend:
	cd frontend/his-web-admin && npm install
	cd frontend/his-web-patient && npm install

# Docker 启动基础设施
docker-infra:
	cd docker && docker compose up -d postgresql redis rabbitmq nacos minio

# Docker 启动全部
docker-up:
	cd docker && docker compose up -d --build

# Docker 停止
docker-down:
	cd docker && docker compose down
