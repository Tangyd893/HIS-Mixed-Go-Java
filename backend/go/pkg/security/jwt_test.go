package security_test

import (
	"crypto/rand"
	"crypto/rsa"
	"testing"
	"time"

	"github.com/golang-jwt/jwt/v5"
	"github.com/his-mixed/go/pkg/security"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/require"
)

func generateRSAKeys() (*rsa.PrivateKey, *rsa.PublicKey) {
	privKey, err := rsa.GenerateKey(rand.Reader, 2048)
	if err != nil {
		panic(err)
	}
	return privKey, &privKey.PublicKey
}

func TestGenerateToken(t *testing.T) {
	privKey, pubKey := generateRSAKeys()
	security.InitWithKeys(pubKey, privKey)

	token, err := security.GenerateToken(1, "admin", "管理员", []string{"admin"}, []string{"*"}, 100)
	require.NoError(t, err)
	assert.NotEmpty(t, token)
}

func TestParseToken(t *testing.T) {
	privKey, pubKey := generateRSAKeys()
	security.InitWithKeys(pubKey, privKey)

	token, err := security.GenerateToken(1, "admin", "管理员", []string{"admin"}, []string{"*"}, 100)
	require.NoError(t, err)

	claims, err := security.ParseToken(token)
	require.NoError(t, err)
	assert.Equal(t, int64(1), claims.UserID)
	assert.Equal(t, "admin", claims.Username)
	assert.Equal(t, "管理员", claims.RealName)
	assert.Equal(t, []string{"admin"}, claims.Roles)
	assert.Equal(t, []string{"*"}, claims.Permissions)
	assert.Equal(t, int64(100), claims.DeptID)
}

func TestExpiredToken(t *testing.T) {
	privKey, pubKey := generateRSAKeys()
	security.InitWithKeys(pubKey, privKey)

	claims := &security.Claims{
		UserID:   1,
		Username: "admin",
		RealName: "管理员",
		RegisteredClaims: jwt.RegisteredClaims{
			ExpiresAt: jwt.NewNumericDate(time.Now().Add(-1 * time.Hour)),
			IssuedAt:  jwt.NewNumericDate(time.Now().Add(-2 * time.Hour)),
		},
	}
	token := jwt.NewWithClaims(jwt.SigningMethodRS256, claims)
	tokenStr, err := token.SignedString(privKey)
	require.NoError(t, err)

	_, err = security.ParseToken(tokenStr)
	assert.Error(t, err)
}

func TestInvalidToken(t *testing.T) {
	privKey, pubKey := generateRSAKeys()
	security.InitWithKeys(pubKey, privKey)

	token, err := security.GenerateToken(1, "admin", "管理员", []string{"admin"}, []string{"*"}, 100)
	require.NoError(t, err)

	tampered := token[:len(token)-3] + "xyz"
	_, err = security.ParseToken(tampered)
	assert.Error(t, err)

	_, err = security.ParseToken("not.a.valid.token")
	assert.Error(t, err)
}
