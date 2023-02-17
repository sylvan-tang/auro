// ==========================================================================
// Code generated by GoFrame CLI tool. DO NOT EDIT.
// ==========================================================================

package internal

import (
	"github.com/gogf/gf/os/gtime"
)

// Greeting is the golang structure for table greeting.
type Greeting struct {
	Id       uint64      `orm:"id,primary" json:"id"`        // 自增ID，主键
	CreateAt *gtime.Time `orm:"create_at"  json:"create_at"` // 创建时间
	UpdateAt *gtime.Time `orm:"update_at"  json:"update_at"` // 更新时间
	Entity   string      `orm:"entity"     json:"entity"`    // greeting proto 结构内数据
	Greeted  string      `orm:"greeted"    json:"greeted"`   // proto 中的字段，直接映射出来做 index 使用
}