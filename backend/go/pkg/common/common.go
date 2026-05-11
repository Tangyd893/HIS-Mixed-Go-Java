// Package common 通用工具集 — 雪花算法、校验、加密、响应封装
package common

import (
	"crypto/md5"
	"encoding/hex"
	"math/rand"
	"regexp"
	"strconv"
	"time"

	"github.com/bwmarrin/snowflake"
)

var idGen *snowflake.Node

func init() {
	node, err := snowflake.NewNode(rand.Int63n(1024))
	if err != nil {
		panic("雪花算法初始化失败: " + err.Error())
	}
	idGen = node
}

// NextID 生成全局唯一 ID
func NextID() int64 {
	return idGen.Generate().Int64()
}

// NextIDStr 生成全局唯一 ID 字符串
func NextIDStr() string {
	return strconv.FormatInt(NextID(), 10)
}

// MD5 计算字符串 MD5
func MD5(s string) string {
	h := md5.New()
	h.Write([]byte(s))
	return hex.EncodeToString(h.Sum(nil))
}

// MaskPhone 手机号脱敏：保留前3后4
func MaskPhone(phone string) string {
	if len(phone) < 7 {
		return phone
	}
	return phone[:3] + "****" + phone[len(phone)-4:]
}

// MaskIDCard 身份证号脱敏：保留前3后4
func MaskIDCard(idCard string) string {
	if len(idCard) < 7 {
		return idCard
	}
	return idCard[:3] + "****" + idCard[len(idCard)-4:]
}

var phoneRegex = regexp.MustCompile(`^1[3-9]\d{9}$`)

// ValidatePhone 校验手机号格式
func ValidatePhone(phone string) bool {
	return phoneRegex.MatchString(phone)
}

// NowMillis 当前时间毫秒戳
func NowMillis() int64 {
	return time.Now().UnixMilli()
}
