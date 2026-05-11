package errors_test

import (
	"testing"

	perrors "github.com/his-mixed/go/pkg/errors"
	"github.com/stretchr/testify/assert"
)

func TestGetMessage(t *testing.T) {
	assert.Equal(t, "成功", perrors.GetMessage(perrors.CodeSuccess))
	assert.Equal(t, "内部服务器错误", perrors.GetMessage(perrors.CodeInternal))
	assert.Equal(t, "Token已过期", perrors.GetMessage(perrors.CodeTokenExpired))
	assert.Equal(t, "缺少必填参数", perrors.GetMessage(perrors.CodeParamMissing))
}

func TestNew(t *testing.T) {
	err := perrors.New(perrors.CodeBadRequest)
	assert.NotNil(t, err)
	assert.Equal(t, perrors.CodeBadRequest, err.Code)
	assert.Equal(t, "请求参数错误", err.Message)
	assert.Contains(t, err.Error(), "[400]")
}

func TestNewf(t *testing.T) {
	err := perrors.Newf(perrors.CodeParamInvalid, "参数 %s 的值 %v 无效", "age", -1)
	assert.NotNil(t, err)
	assert.Equal(t, perrors.CodeParamInvalid, err.Code)
	assert.Equal(t, "参数 age 的值 -1 无效", err.Message)
}

func TestUnknownCode(t *testing.T) {
	assert.Equal(t, "未知错误", perrors.GetMessage(99999))
	assert.Equal(t, "未知错误", perrors.GetMessage(0))
}
