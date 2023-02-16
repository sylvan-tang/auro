package impl

import (
	"context"

	assocv1 "github.com/sylvan/auro/apis/assoc/v1"

	"github.com/sylvan/auro/pkg/service/assoc/domain"
)

type AssocServiceImpl struct {
	assocv1.UnimplementedAssocServiceServer
}

func (s AssocServiceImpl) CreateAssoc(ctx context.Context, request *assocv1.CreateAssocRequest) (*assocv1.CreateAssocResponse, error) {
	detail, err := domain.CreateAssoc(ctx, request)
	if err == nil {
		return &assocv1.CreateAssocResponse{Details: detail}, nil
	}
	return &assocv1.CreateAssocResponse{Code: 500, Message: err.Error()}, err
}

func (s AssocServiceImpl) UpdateAssoc(ctx context.Context, request *assocv1.UpdateAssocRequest) (*assocv1.UpdateAssocResponse, error) {
	detail, err := domain.UpdateAssoc(ctx, request)
	if err == nil {
		return &assocv1.UpdateAssocResponse{Details: detail}, nil
	}
	return &assocv1.UpdateAssocResponse{Code: 500, Message: err.Error()}, err
}

func (s AssocServiceImpl) ListAssocs(ctx context.Context, request *assocv1.ListAssocsRequest) (*assocv1.ListAssocsResponse, error) {
	detail, err := domain.ListAssocs(ctx, request)
	if err == nil {
		return &assocv1.ListAssocsResponse{Details: detail}, nil
	}
	return &assocv1.ListAssocsResponse{Code: 500, Message: err.Error()}, err

}

func (s AssocServiceImpl) DeleteAssoc(ctx context.Context, request *assocv1.DeleteAssocRequest) (*assocv1.DeleteAssocResponse, error) {
	detail, err := domain.DeleteAssoc(ctx, request)
	if err == nil {
		return &assocv1.DeleteAssocResponse{Details: detail}, nil
	}
	return &assocv1.DeleteAssocResponse{Code: 500, Message: err.Error()}, err
}

func (s AssocServiceImpl) DeleteAssocs(ctx context.Context, request *assocv1.DeleteAssocsRequest) (*assocv1.DeleteAssocsResponse, error) {
	detail, err := domain.DeleteAssocs(ctx, request)
	if err == nil {
		return &assocv1.DeleteAssocsResponse{Details: detail}, nil
	}
	return &assocv1.DeleteAssocsResponse{Code: 500, Message: err.Error()}, err
}

func (s AssocServiceImpl) CountAssocs(ctx context.Context, request *assocv1.CountAssocsRequest) (*assocv1.CountAssocsResponse, error) {
	detail, err := domain.CountAssocs(ctx, request)
	if err == nil {
		return &assocv1.CountAssocsResponse{Details: detail}, nil
	}
	return &assocv1.CountAssocsResponse{Code: 500, Message: err.Error()}, err
}

func (s AssocServiceImpl) GetAssoc(ctx context.Context, request *assocv1.GetAssocRequest) (*assocv1.GetAssocResponse, error) {
	detail, err := domain.GetAssoc(ctx, request)
	if err == nil {
		return &assocv1.GetAssocResponse{Details: detail}, nil
	}
	return &assocv1.GetAssocResponse{Code: 500, Message: err.Error()}, err
}
