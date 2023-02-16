package domain

import (
	"context"
	"fmt"

	"github.com/gogf/gf/frame/g"
	"github.com/gogf/gf/os/gtime"

	echov1 "github.com/sylvan/auro/apis/echo/v1"
	"github.com/sylvan/auro/pkg/lib/convert"
	mysqllib "github.com/sylvan/auro/pkg/lib/mysql"
	"github.com/sylvan/auro/pkg/service/echo/db/dao"
	"github.com/sylvan/auro/pkg/service/echo/db/model"
)

func CreateGreeting(ctx context.Context, request *echov1.CreateGreetingRequest) (*echov1.Greeting, error) {
	greetingDo := &model.Greeting{}
	err := convert.EoToDo(request.Greeting, greetingDo)
	if err != nil {
		return nil, err
	}
	greetingId, err := dao.Greeting.Ctx(ctx).InsertAndGetId(g.Map{
		dao.Greeting.Columns.Entity: greetingDo.Entity,
	})
	if err != nil {
		return nil, err
	}
	request.Greeting.Id = greetingId
	return request.Greeting, nil
}
func GetGreeting(ctx context.Context, request *echov1.GetGreetingRequest) (*echov1.Greeting, error) {
	greetingDo := &model.Greeting{}
	err := dao.Greeting.Ctx(ctx).Where(dao.Greeting.Columns.Id, request.Id).Struct(greetingDo)
	if err != nil {
		return nil, err
	}
	greetingEo := echov1.Greeting{}
	err = convert.DoToEo(greetingDo, &greetingEo)
	if err != nil {
		return nil, err
	}
	return &greetingEo, nil
}
func UpdateGreeting(ctx context.Context, request *echov1.UpdateGreetingRequest) (*echov1.Greeting, error) {
	greetingDo := &model.Greeting{}
	err := convert.EoToDo(request.Greeting, greetingDo)
	if err != nil {
		return nil, err
	}
	_, err = dao.Greeting.Ctx(ctx).Save(g.Map{
		dao.Greeting.Columns.Id:     greetingDo.Id,
		dao.Greeting.Columns.Entity: greetingDo.Entity,
	})
	if err != nil {
		return nil, err
	}
	return GetGreeting(ctx, &echov1.GetGreetingRequest{Id: request.Greeting.Id})
}
func ListGreetings(ctx context.Context, request *echov1.ListGreetingsRequest) (*echov1.ListGreetingsResponse_Details, error) {
	cm := dao.Greeting.Ctx(ctx)
	cm = mysqllib.CreateQueryConditon(cm, cm, request.GetCommonOption(), request.GetCommonFilter(), dao.Greeting.Columns.Id)
	if len(request.GetCommonOption().GetQuery()) > 0 {
		cm = cm.WhereLike(
			dao.Greeting.Columns.Greeted,
			fmt.Sprintf("%s%s%s", "%", request.GetCommonOption().GetQuery(), "%"),
		)
	}

	var greetingList []model.Greeting
	idToTime := make(map[int64]*gtime.Time)
	cm.Structs(&greetingList)
	var greetingEos []*echov1.Greeting
	for _, greetingDo := range greetingList {
		if request.GetCommonFilter().GetColumnName() == dao.Greeting.Columns.CreateAt {
			idToTime[int64(greetingDo.Id)] = greetingDo.CreateAt
		} else if request.GetCommonFilter().GetColumnName() == dao.Greeting.Columns.UpdateAt {
			idToTime[int64(greetingDo.Id)] = greetingDo.UpdateAt
		}
		greetingEo := echov1.Greeting{}
		err := convert.DoToEo(greetingDo, &greetingEo)
		if err != nil {
			return nil, err
		}
		greetingEos = append(greetingEos, &greetingEo)
	}

	mysqllib.NextCommonOption(request.GetCommonOption())
	mysqllib.NextCommonFilter(request.GetCommonFilter(), idToTime)
	return &echov1.ListGreetingsResponse_Details{
		Items:       greetingEos,
		NextRequest: request,
	}, nil
}
func DeleteGreeting(ctx context.Context, request *echov1.DeleteGreetingRequest) (*echov1.Greeting, error) {
	greetingDo := &model.Greeting{}
	err := dao.Greeting.Ctx(ctx).Where(dao.Greeting.Columns.Id, request.Id).Struct(greetingDo)
	if err != nil {
		return nil, err
	}
	greetingEo := echov1.Greeting{}
	err = convert.DoToEo(greetingDo, &greetingEo)
	if err != nil {
		return nil, err
	}
	_, err = dao.Greeting.Ctx(ctx).Delete(dao.Greeting.Columns.Id, request.Id)
	if err != nil {
		return nil, err
	}
	return &greetingEo, nil
}

func DeleteGreetings(ctx context.Context, request *echov1.DeleteGreetingsRequest) ([]int64, error) {
	cm := mysqllib.CreateQueryConditon(dao.Greeting.Ctx(ctx), nil, nil, request.GetCommonFilter(), dao.Greeting.Columns.Id)
	if cm == nil {
		return []int64{}, nil
	}
	var greetingList []model.Greeting
	cm.Structs(&greetingList)
	ids := []int64{}
	for _, greetingDo := range greetingList {
		ids = append(ids, int64(greetingDo.Id))
	}
	if len(ids) == 0 {
		return ids, nil
	}
	_, err := dao.Greeting.Ctx(ctx).WhereIn(dao.Greeting.Columns.Id, ids).Delete()
	if err != nil {
		return nil, err
	}
	return ids, nil
}
