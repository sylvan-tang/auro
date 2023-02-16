package impl

import (
	"context"

	echov1 "github.com/sylvan/auro/apis/echo/v1"
	"github.com/sylvan/auro/pkg/service/echo/domain"
)

type GreetingServiceImpl struct {
	echov1.UnimplementedGreetingServiceServer
}

func (s GreetingServiceImpl) CreateGreeting(ctx context.Context, request *echov1.CreateGreetingRequest) (*echov1.CreateGreetingResponse, error) {
	detail, err := domain.CreateGreeting(ctx, request)
	if err == nil {
		return &echov1.CreateGreetingResponse{Details: detail}, nil
	}
	return &echov1.CreateGreetingResponse{Code: 500, Message: err.Error()}, err
}
func (s GreetingServiceImpl) GetGreeting(ctx context.Context, request *echov1.GetGreetingRequest) (*echov1.GetGreetingResponse, error) {
	detail, err := domain.GetGreeting(ctx, request)
	if err == nil {
		return &echov1.GetGreetingResponse{Details: detail}, nil
	}
	return &echov1.GetGreetingResponse{Code: 500, Message: err.Error()}, err
}
func (s GreetingServiceImpl) UpdateGreeting(ctx context.Context, request *echov1.UpdateGreetingRequest) (*echov1.UpdateGreetingResponse, error) {
	detail, err := domain.UpdateGreeting(ctx, request)
	if err == nil {
		return &echov1.UpdateGreetingResponse{Details: detail}, nil
	}
	return &echov1.UpdateGreetingResponse{Code: 500, Message: err.Error()}, err
}
func (s GreetingServiceImpl) ListGreetings(ctx context.Context, request *echov1.ListGreetingsRequest) (*echov1.ListGreetingsResponse, error) {
	detail, err := domain.ListGreetings(ctx, request)
	if err == nil {
		return &echov1.ListGreetingsResponse{Details: detail}, nil
	}
	return &echov1.ListGreetingsResponse{Code: 500, Message: err.Error()}, err

}
func (s GreetingServiceImpl) DeleteGreeting(ctx context.Context, request *echov1.DeleteGreetingRequest) (*echov1.DeleteGreetingResponse, error) {
	detail, err := domain.DeleteGreeting(ctx, request)
	if err == nil {
		return &echov1.DeleteGreetingResponse{Details: detail}, nil
	}
	return &echov1.DeleteGreetingResponse{Code: 500, Message: err.Error()}, err
}
func (s GreetingServiceImpl) DeleteGreetings(ctx context.Context, request *echov1.DeleteGreetingsRequest) (*echov1.DeleteGreetingsResponse, error) {
	detail, err := domain.DeleteGreetings(ctx, request)
	if err == nil {
		return &echov1.DeleteGreetingsResponse{Details: detail}, nil
	}
	return &echov1.DeleteGreetingsResponse{Code: 500, Message: err.Error()}, err

}
