package mysql

import (
	"time"

	"github.com/gogf/gf/database/gdb"
	"github.com/gogf/gf/os/gtime"

	commonv1 "github.com/sylvan/auro/apis/common/v1"
)

const (
	defaultPage = 1
	defaultSize = 10

	defaultDuration = 5 * time.Minute
)

func CreateQueryConditon(cm, defaultCm *gdb.Model, commonOption *commonv1.CommonOption, commonFilter *commonv1.CommonFilter, priColumnName string) *gdb.Model {
	if commonOption == nil && commonFilter == nil {
		return defaultCm
	}
	// add into query conditon
	if commonFilter != nil {
		if len(commonFilter.GetColumnName()) > 0 {
			if commonFilter.GetHighTime() != "" {
				cm = cm.WhereLTE(commonFilter.GetColumnName(), gtime.New(commonFilter.GetHighTime()))
				defaultCm = cm
			}
			if commonFilter.GetLowTime() != "" {
				cm = cm.WhereGT(commonFilter.GetColumnName(), gtime.New(commonFilter.GetLowTime()))
				defaultCm = cm
			}
		}

		if len(commonFilter.GetExcludeIds()) > 0 {
			cm = cm.WhereNotIn(priColumnName, commonFilter.GetExcludeIds())
			defaultCm = cm
			// page should equal to 1 when ExcludeIds is not empty.
			if commonOption != nil {
				commonOption.Page = 1
			}
		}
		if len(commonFilter.GetIncludeIds()) > 0 {
			cm = cm.WhereIn(priColumnName, commonFilter.GetIncludeIds())
			defaultCm = cm
			// page should equal to 1 when IncludeIds is not empty.
			if commonOption != nil {
				commonOption.Page = 1
			}
		}
	}
	if commonOption != nil {
		if commonOption.GetOrderBy() != "" {
			cm = cm.OrderDesc(commonOption.GetOrderBy())
		}
		if commonOption.GetGroupBy() != "" {
			cm = cm.GroupBy(commonOption.GetGroupBy())
		}
		if commonOption.GetPage() == 0 {
			commonOption.Page = defaultPage
		}
		if commonOption.GetSize() == 0 {
			commonOption.Size = defaultSize
		}
		cm = cm.Page(int(commonOption.GetPage()), int(commonOption.GetSize()))
		defaultCm = cm
	}
	return defaultCm
}

func NextCommonFilter(commonFilter *commonv1.CommonFilter, idToTime map[int64]*gtime.Time) {
	if commonFilter == nil {
		return
	}
	if len(idToTime) == 0 {
		commonFilter.HighTime = commonFilter.LowTime
		commonFilter.LowTime = ""
		return
	}
	highTimestampe := int64(0)
	lowTimestamp := gtime.Now().Timestamp()
	for _, t := range idToTime {
		if t.Timestamp() > highTimestampe {
			highTimestampe = t.Timestamp()
		}
		if t.Timestamp() < lowTimestamp {
			lowTimestamp = t.Timestamp()
		}
	}
	commonFilter.HighTime = gtime.NewFromTimeStamp(highTimestampe).String()
	commonFilter.LowTime = gtime.NewFromTimeStamp(lowTimestamp - (highTimestampe - lowTimestamp) - int64(defaultDuration.Seconds())).String()
	var excludeIds []int64
	for id, t := range idToTime {
		if t.Timestamp() == lowTimestamp {
			excludeIds = append(excludeIds, id)
		}
	}
	commonFilter.ExcludeIds = excludeIds
}

func NextCommonOption(commonOption *commonv1.CommonOption) {
	if commonOption == nil {
		return
	}
	commonOption.Page = commonOption.Page + 1
}
