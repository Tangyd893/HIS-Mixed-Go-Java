FROM golang:1.25-alpine AS builder

RUN apk add --no-cache git ca-certificates

WORKDIR /app

COPY go.mod go.sum ./
RUN go mod download

COPY . .

ARG SERVICE_NAME
RUN CGO_ENABLED=0 GOOS=linux go build -ldflags="-s -w" -o /app/server ./cmd/${SERVICE_NAME}

FROM alpine:latest

RUN apk add --no-cache ca-certificates tzdata

ENV TZ=Asia/Shanghai

COPY --from=builder /app/server /server

EXPOSE 8080

ENTRYPOINT ["/server"]
