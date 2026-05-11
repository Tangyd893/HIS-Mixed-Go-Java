package model

import "time"

// StatSnapshot 统计快照
type StatSnapshot struct {
	ID        int64     `gorm:"primaryKey" json:"id"`
	StatType  string    `gorm:"size:50;index;not null" json:"statType"`
	StatDate  time.Time `gorm:"type:date;index;not null" json:"statDate"`
	StatData  string    `gorm:"type:jsonb;not null" json:"statData"`
	CreatedAt time.Time `json:"createdAt"`
}

func (StatSnapshot) TableName() string {
	return "stat_snapshots"
}
