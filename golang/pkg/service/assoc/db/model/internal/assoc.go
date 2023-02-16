// ==========================================================================
// Code generated by GoFrame CLI tool. DO NOT EDIT.
// ==========================================================================

package internal

import (
	"github.com/gogf/gf/os/gtime"
)

// Assoc is the golang structure for table assoc.
type Assoc struct {
	Id       uint64      `orm:"id,primary" json:"id"`        // 自增ID，主键
	CreateAt *gtime.Time `orm:"create_at"  json:"create_at"` // 创建时间
	UpdateAt *gtime.Time `orm:"update_at"  json:"update_at"` // 更新时间
	Entity   string      `orm:"entity"     json:"entity"`    // assoc proto 结构内数据
	FromId   uint64      `orm:"from_id"    json:"from_id"`   // proto 中的字段，直接映射出来做联合主建使用,source_id
	ToId     uint64      `orm:"to_id"      json:"to_id"`     // proto 中的字段，直接映射出来做联合主建使用,destination_id
	AType    string      `orm:"a_type"     json:"a_type"`    // proto 中的字段，直接映射出来做联合主建使用
}
