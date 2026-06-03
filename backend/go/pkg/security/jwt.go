// Package security JWT 解析、鉴权中间件、用户上下文
package security

import (
	"crypto/rsa"
	"crypto/x509"
	"encoding/pem"
	"errors"
	"fmt"
	"os"
	"time"

	"github.com/golang-jwt/jwt/v5"
)

var (
	verifyKey       *rsa.PublicKey
	signKey         *rsa.PrivateKey
	hmacSecret      []byte // HMAC-SHA 密钥（用于与 Java 服务互通）
	ErrInvalidToken = errors.New("无效的Token")
	ErrExpiredToken = errors.New("Token已过期")
)

// Claims JWT 声明
type Claims struct {
	UserID      int64    `json:"userId"`
	Username    string   `json:"username"`
	RealName    string   `json:"realName"`
	Roles       []string `json:"roles"`
	Permissions []string `json:"permissions"`
	DeptID      int64    `json:"deptId"`
	jwt.RegisteredClaims
}

// InitWithHMAC 使用 HMAC-SHA 密钥初始化（与 Java 服务互通）
func InitWithHMAC(secret string) {
	hmacSecret = []byte(secret)
}

// Init 初始化 JWT 密钥
func Init(publicKeyPath, privateKeyPath string) error {
	if publicKeyPath == "" {
		publicKeyPath = os.Getenv("JWT_PUBLIC_KEY_PATH")
	}
	if privateKeyPath == "" {
		privateKeyPath = os.Getenv("JWT_PRIVATE_KEY_PATH")
	}

	if publicKeyPath != "" {
		pubData, err := os.ReadFile(publicKeyPath)
		if err != nil {
			return err
		}
		block, _ := pem.Decode(pubData)
		if block == nil {
			return errors.New("无法解码公钥 PEM")
		}
		pub, err := x509.ParsePKIXPublicKey(block.Bytes)
		if err != nil {
			return err
		}
		verifyKey = pub.(*rsa.PublicKey)
	}

	if privateKeyPath != "" {
		privData, err := os.ReadFile(privateKeyPath)
		if err != nil {
			return err
		}
		block, _ := pem.Decode(privData)
		if block == nil {
			return errors.New("无法解码私钥 PEM")
		}
		priv, err := x509.ParsePKCS1PrivateKey(block.Bytes)
		if err != nil {
			privAny, err2 := x509.ParsePKCS8PrivateKey(block.Bytes)
			if err2 != nil {
				return err2
			}
			var ok bool
			signKey, ok = privAny.(*rsa.PrivateKey)
			if !ok {
				return errors.New("无法转换为 RSA 私钥")
			}
		} else {
			signKey = priv
		}
	}

	return nil
}

// InitWithKeys 直接传入密钥初始化
func InitWithKeys(pubKey *rsa.PublicKey, privKey *rsa.PrivateKey) {
	verifyKey = pubKey
	signKey = privKey
}

// GenerateToken 签发 Access Token
func GenerateToken(userID int64, username, realName string, roles, permissions []string, deptID int64) (string, error) {
	if signKey == nil && hmacSecret == nil {
		return "", errors.New("JWT 密钥未配置")
	}
	claims := &Claims{
		UserID:      userID,
		Username:    username,
		RealName:    realName,
		Roles:       roles,
		Permissions: permissions,
		DeptID:      deptID,
		RegisteredClaims: jwt.RegisteredClaims{
			ExpiresAt: jwt.NewNumericDate(time.Now().Add(2 * time.Hour)),
			IssuedAt:  jwt.NewNumericDate(time.Now()),
			Issuer:    "his-mixed",
		},
	}
	token := jwt.NewWithClaims(jwt.SigningMethodRS256, claims)
	if signKey != nil {
		return token.SignedString(signKey)
	}
	// 使用 HMAC 签名
	token = jwt.NewWithClaims(jwt.SigningMethodHS256, claims)
	return token.SignedString(hmacSecret)
}

// GenerateRefreshToken 签发 Refresh Token
func GenerateRefreshToken(userID int64, username string) (string, error) {
	if signKey == nil && hmacSecret == nil {
		return "", errors.New("JWT 密钥未配置")
	}
	claims := &jwt.RegisteredClaims{
		Subject:   username,
		ID:        fmt.Sprintf("%d", userID),
		ExpiresAt: jwt.NewNumericDate(time.Now().Add(7 * 24 * time.Hour)),
		IssuedAt:  jwt.NewNumericDate(time.Now()),
		Issuer:    "his-mixed",
	}
	token := jwt.NewWithClaims(jwt.SigningMethodRS256, claims)
	if signKey != nil {
		return token.SignedString(signKey)
	}
	token = jwt.NewWithClaims(jwt.SigningMethodHS256, claims)
	return token.SignedString(hmacSecret)
}

// ParseToken 解析 Token
func ParseToken(tokenStr string) (*Claims, error) {
	// 优先使用 HMAC 验证（与 Java 服务互通）
	if hmacSecret != nil {
		token, err := jwt.ParseWithClaims(tokenStr, &Claims{}, func(t *jwt.Token) (interface{}, error) {
			if _, ok := t.Method.(*jwt.SigningMethodHMAC); !ok {
				return nil, fmt.Errorf("不支持的签名方法: %v", t.Header["alg"])
			}
			return hmacSecret, nil
		})
		if err != nil {
			return nil, err
		}
		if claims, ok := token.Claims.(*Claims); ok && token.Valid {
			return claims, nil
		}
		return nil, ErrInvalidToken
	}

	// 回退到 RSA 验证
	if verifyKey == nil {
		return nil, errors.New("JWT 公钥未配置")
	}
	token, err := jwt.ParseWithClaims(tokenStr, &Claims{}, func(t *jwt.Token) (interface{}, error) {
		return verifyKey, nil
	})
	if err != nil {
		return nil, err
	}
	if claims, ok := token.Claims.(*Claims); ok && token.Valid {
		return claims, nil
	}
	return nil, ErrInvalidToken
}
