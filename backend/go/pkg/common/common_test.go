package common_test

import (
	"testing"

	"github.com/his-mixed/go/pkg/common"
	"github.com/stretchr/testify/assert"
)

func TestNextID(t *testing.T) {
	ids := make(map[int64]bool)
	for i := 0; i < 1000; i++ {
		id := common.NextID()
		assert.False(t, ids[id], "ID 重复: %d", id)
		ids[id] = true
	}
}

func TestNextIDStr(t *testing.T) {
	idStr := common.NextIDStr()
	assert.Greater(t, len(idStr), 0)
}

func TestMaskPhone(t *testing.T) {
	assert.Equal(t, "138****8000", common.MaskPhone("13800138000"))
	assert.Equal(t, "12", common.MaskPhone("12"))
}

func TestMaskIDCard(t *testing.T) {
	assert.Equal(t, "110****1234", common.MaskIDCard("110101199001011234"))
	assert.Equal(t, "123", common.MaskIDCard("123"))
}

func TestValidatePhone(t *testing.T) {
	assert.True(t, common.ValidatePhone("13800138000"))
	assert.True(t, common.ValidatePhone("15912345678"))
	assert.False(t, common.ValidatePhone("12345"))
	assert.False(t, common.ValidatePhone("12012345678"))
	assert.False(t, common.ValidatePhone(""))
}

func TestMD5(t *testing.T) {
	assert.Equal(t, "5d41402abc4b2a76b9719d911017c592", common.MD5("hello"))
	assert.Equal(t, "d41d8cd98f00b204e9800998ecf8427e", common.MD5(""))
}
