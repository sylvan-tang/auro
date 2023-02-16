package domain

import (
	"context"
	"fmt"

	"github.com/gogf/gf/database/gdb"
	"github.com/gogf/gf/frame/g"
	"github.com/gogf/gf/os/gtime"

	assocv1 "github.com/sylvan/auro/apis/assoc/v1"
	commonv1 "github.com/sylvan/auro/apis/common/v1"
	"github.com/sylvan/auro/pkg/lib/convert"
	"github.com/sylvan/auro/pkg/lib/encoding/json"
	grpcerrors "github.com/sylvan/auro/pkg/lib/grpc/errors"
	mysqllib "github.com/sylvan/auro/pkg/lib/mysql"
	"github.com/sylvan/auro/pkg/service/assoc/db/dao"
	"github.com/sylvan/auro/pkg/service/assoc/db/model"
)

func CreateAssoc(ctx context.Context, request *assocv1.CreateAssocRequest) (*assocv1.Assoc, error) {
	if request.GetAssoc().GetFromId() == 0 {
		return nil, grpcerrors.InvalidFieldValueError("assoc.from_id", request.GetAssoc().GetFromId())
	}
	if request.GetAssoc().GetAType() == 0 {
		return nil, grpcerrors.InvalidFieldValueError("assoc.a_type", request.GetAssoc().GetAType())
	}
	if request.GetAssoc().GetToId() == 0 {
		return nil, grpcerrors.InvalidFieldValueError("assoc.to_id", request.GetAssoc().GetToId())
	}
	assocDo := &model.Assoc{}
	err := convert.EoToDo(request.Assoc, assocDo)
	if err != nil {
		return nil, err
	}
	revertAssocEo := &assocv1.Assoc{
		FromId: request.Assoc.ToId,
		ToId:   request.Assoc.FromId,
		AType:  GetRevertType(request.Assoc.AType),
		Meta:   request.Assoc.Meta,
	}
	revertAssocEoEneity := json.MustMarshalProtoJSON(revertAssocEo, false)

	assocId, err := dao.Assoc.Ctx(ctx).InsertAndGetId(g.List{
		{
			dao.Assoc.Columns.Entity: assocDo.Entity,
		},
		{
			dao.Assoc.Columns.Entity: revertAssocEoEneity,
		},
	})

	if err != nil {
		return nil, err
	}
	request.Assoc.Id = assocId

	return request.Assoc, nil
}

func UpdateAssoc(ctx context.Context, request *assocv1.UpdateAssocRequest) (*assocv1.Assoc, error) {
	assocDo := &model.Assoc{}
	err := dao.Assoc.Ctx(ctx).Where(dao.Assoc.Columns.FromId, request.FromId).And(dao.Assoc.Columns.ToId, request.ToId).And(dao.Assoc.Columns.AType, request.AType.String()).Struct(assocDo)
	if err != nil {
		return nil, err
	}
	id := assocDo.Id
	err = convert.EoToDo(request.Assoc, assocDo)
	if err != nil {
		return nil, err
	}

	//update revert assoc record
	revertAssocDo := &model.Assoc{}
	err = dao.Assoc.Ctx(ctx).Where(dao.Assoc.Columns.FromId, request.ToId).And(dao.Assoc.Columns.ToId, request.FromId).And(dao.Assoc.Columns.AType, GetRevertType(request.AType).String()).Struct(revertAssocDo)
	if err != nil {
		return nil, err
	}
	revertId := revertAssocDo.Id
	revertAssocEo := &assocv1.Assoc{
		FromId: request.Assoc.ToId,
		ToId:   request.Assoc.FromId,
		AType:  GetRevertType(request.Assoc.AType),
		Meta:   request.Assoc.Meta,
	}
	if err != nil {
		return nil, err
	}

	revertAssocEoEneity := json.MustMarshalProtoJSON(revertAssocEo, false)
	//batch save
	_, err = dao.Assoc.Ctx(ctx).Save(g.List{{
		dao.Assoc.Columns.Id:     id,
		dao.Assoc.Columns.Entity: assocDo.Entity,
	}, {
		dao.Assoc.Columns.Id:     revertId,
		dao.Assoc.Columns.Entity: revertAssocEoEneity,
	}})

	if err != nil {
		return nil, err
	}

	//select updated record
	err = dao.Assoc.Ctx(ctx).Where(dao.Assoc.Columns.Id, id).Struct(assocDo)
	if err != nil {
		return nil, err
	}
	assocEo := assocv1.Assoc{}
	err = convert.DoToEo(assocDo, &assocEo)
	if err != nil {
		return nil, err
	}
	return &assocEo, nil
}

func getQueryContextByFilter(ctx context.Context, commonOption *commonv1.CommonOption, commonFilter *commonv1.CommonFilter, optionFilter *assocv1.AssocOptionFilter, defaultCm *gdb.Model) *gdb.Model {
	cm := mysqllib.CreateQueryConditon(dao.Assoc.Ctx(ctx), defaultCm, commonOption, commonFilter, dao.Assoc.Columns.Id)
	defaultCm = cm
	if cm == nil {
		cm = dao.Assoc.Ctx(ctx)
	}
	if optionFilter.GetFromId() > 0 {
		cm = cm.Where(dao.Assoc.Columns.FromId, optionFilter.GetFromId())
		defaultCm = cm
	}
	if optionFilter.GetAType() != assocv1.AssocType_ASSOC_TYPE_UNSPECIFIED {
		cm = cm.Where(dao.Assoc.Columns.AType, optionFilter.GetAType().String())
		defaultCm = cm
	}
	if len(optionFilter.GetToIds()) > 0 {
		cm = cm.WhereIn(dao.Assoc.Columns.ToId, optionFilter.GetToIds())
		defaultCm = cm
	}
	return defaultCm
}

func ListAssocs(ctx context.Context, request *assocv1.ListAssocsRequest) (*assocv1.ListAssocsResponse_Details, error) {
	cm := getQueryContextByFilter(ctx, request.GetCommonOption(), request.GetCommonFilter(), request.GetOptionFilter(), dao.Assoc.Ctx(ctx))
	if len(request.GetCommonOption().GetQuery()) > 0 {
		cm = cm.WhereLike(dao.Assoc.Columns.Entity, fmt.Sprintf("%s%s%s", "%", request.GetCommonOption().GetQuery(), "%"))
	}
	var assocList []model.Assoc
	idToTime := make(map[int64]*gtime.Time)
	cm.Structs(&assocList)
	var AssocEos []*assocv1.Assoc
	for _, assocDo := range assocList {
		if request.GetCommonFilter().GetColumnName() == dao.Assoc.Columns.CreateAt {
			idToTime[int64(assocDo.Id)] = assocDo.CreateAt
		} else if request.GetCommonFilter().GetColumnName() == dao.Assoc.Columns.UpdateAt {
			idToTime[int64(assocDo.Id)] = assocDo.UpdateAt
		}
		AssocEo := assocv1.Assoc{}
		err := convert.DoToEo(assocDo, &AssocEo)
		if err != nil {
			return nil, err
		}
		AssocEos = append(AssocEos, &AssocEo)
	}
	mysqllib.NextCommonOption(request.GetCommonOption())
	mysqllib.NextCommonFilter(request.GetCommonFilter(), idToTime)
	return &assocv1.ListAssocsResponse_Details{
		Items:       AssocEos,
		NextRequest: request,
	}, nil
}

func createOrConditions(ctx context.Context, defaultCm *gdb.Model, assocList []model.Assoc) *gdb.Model {
	if len(assocList) == 0 {
		return defaultCm
	}
	cm := dao.Assoc.Ctx(ctx)
	for _, assocDo := range assocList {
		cm = cm.WhereOr(dao.Assoc.Columns.FromId, assocDo.FromId).
			And(dao.Assoc.Columns.AType, assocDo.AType).
			And(dao.Assoc.Columns.ToId, assocDo.ToId)
		aType, _ := assocv1.AssocType_value[assocDo.AType]
		cm = cm.WhereOr(dao.Assoc.Columns.FromId, assocDo.ToId).
			And(dao.Assoc.Columns.AType, GetRevertType(assocv1.AssocType(aType)).String()).
			And(dao.Assoc.Columns.ToId, assocDo.FromId)
		defaultCm = cm
	}
	return defaultCm
}

func DeleteAssoc(ctx context.Context, request *assocv1.DeleteAssocRequest) (*assocv1.Assoc, error) {
	assocDo := &model.Assoc{}
	err := dao.Assoc.Ctx(ctx).Where(dao.Assoc.Columns.FromId, request.FromId).And(dao.Assoc.Columns.ToId, request.ToId).And(dao.Assoc.Columns.AType, request.AType.String()).Struct(assocDo)
	if err != nil {
		return nil, err
	}
	assocEo := assocv1.Assoc{}
	err = convert.DoToEo(assocDo, &assocEo)
	if err != nil {
		return nil, err
	}

	//batch delete assoc record
	cm := createOrConditions(ctx, nil, []model.Assoc{*assocDo})
	if cm != nil {
		_, err := cm.Delete()
		if err != nil {
			return nil, err
		}
	}
	return &assocEo, nil
}

func DeleteAssocs(ctx context.Context, request *assocv1.DeleteAssocsRequest) ([]int64, error) {
	cm := getQueryContextByFilter(ctx, nil, request.GetCommonFilter(), request.GetOptionFilter(), nil)
	if cm == nil {
		return []int64{}, nil
	}
	var assocList []model.Assoc
	cm.Structs(&assocList)
	ids := []int64{}
	for _, assocDo := range assocList {
		ids = append(ids, int64(assocDo.Id))
	}
	if len(ids) == 0 {
		return ids, nil
	}
	cm = createOrConditions(ctx, nil, assocList)
	if cm != nil {
		_, err := cm.Delete()
		if err != nil {
			return nil, err
		}
	}
	return ids, nil
}

func CountAssocs(ctx context.Context, request *assocv1.CountAssocsRequest) (int64, error) {
	if request.GetFromId() == 0 {
		return 0, grpcerrors.InvalidFieldValueError("from_id", request.GetFromId())
	}
	cm := dao.Assoc.Ctx(ctx).Where(dao.Assoc.Columns.FromId, request.FromId)
	if request.AType > 0 {
		cm = cm.And(dao.Assoc.Columns.AType, request.AType.String())
	}
	count, err := cm.Count()
	if err != nil {
		return 0, err
	}
	return int64(count), err
}

func GetAssoc(ctx context.Context, request *assocv1.GetAssocRequest) (*assocv1.Assoc, error) {
	assocDo := &model.Assoc{}
	err := dao.Assoc.Ctx(ctx).Where(dao.Assoc.Columns.FromId, request.FromId).And(dao.Assoc.Columns.ToId, request.ToId).And(dao.Assoc.Columns.AType, request.AType.String()).Struct(assocDo)
	if err != nil {
		return nil, err
	}
	assocEo := &assocv1.Assoc{}
	err = convert.DoToEo(assocDo, assocEo)
	if err != nil {
		return nil, err
	}
	return assocEo, nil
}
