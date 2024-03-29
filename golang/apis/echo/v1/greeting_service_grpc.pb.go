// Code generated by protoc-gen-go-grpc. DO NOT EDIT.

package echov1

import (
	context "context"

	grpc "google.golang.org/grpc"
	codes "google.golang.org/grpc/codes"
	status "google.golang.org/grpc/status"
)

// This is a compile-time assertion to ensure that this generated file
// is compatible with the grpc package it is being compiled against.
// Requires gRPC-Go v1.32.0 or later.
const _ = grpc.SupportPackageIsVersion7

// GreetingServiceClient is the client API for GreetingService service.
//
// For semantics around ctx use and closing/ending streaming RPCs, please refer to https://pkg.go.dev/google.golang.org/grpc/?tab=doc#ClientConn.NewStream.
type GreetingServiceClient interface {
	// Creates a Greeting.
	CreateGreeting(ctx context.Context, in *CreateGreetingRequest, opts ...grpc.CallOption) (*CreateGreetingResponse, error)
	// Gets a Greeting.
	GetGreeting(ctx context.Context, in *GetGreetingRequest, opts ...grpc.CallOption) (*GetGreetingResponse, error)
	// Updates a Greeting.
	UpdateGreeting(ctx context.Context, in *UpdateGreetingRequest, opts ...grpc.CallOption) (*UpdateGreetingResponse, error)
	// Lists Greetings in a Location.
	ListGreetings(ctx context.Context, in *ListGreetingsRequest, opts ...grpc.CallOption) (*ListGreetingsResponse, error)
	// Deletes a Greeting.
	DeleteGreeting(ctx context.Context, in *DeleteGreetingRequest, opts ...grpc.CallOption) (*DeleteGreetingResponse, error)
	// Batch delete Greeting by filter.
	DeleteGreetings(ctx context.Context, in *DeleteGreetingsRequest, opts ...grpc.CallOption) (*DeleteGreetingsResponse, error)
	// Imports a Greeting.
	ImportGreeting(ctx context.Context, in *ImportGreetingRequest, opts ...grpc.CallOption) (*ImportGreetingResponse, error)
	// Exports a Greeting.
	ExportGreeting(ctx context.Context, in *ExportGreetingRequest, opts ...grpc.CallOption) (*ExportGreetingResponse, error)
}

type greetingServiceClient struct {
	cc grpc.ClientConnInterface
}

func NewGreetingServiceClient(cc grpc.ClientConnInterface) GreetingServiceClient {
	return &greetingServiceClient{cc}
}

func (c *greetingServiceClient) CreateGreeting(ctx context.Context, in *CreateGreetingRequest, opts ...grpc.CallOption) (*CreateGreetingResponse, error) {
	out := new(CreateGreetingResponse)
	err := c.cc.Invoke(ctx, "/echo.v1.GreetingService/CreateGreeting", in, out, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *greetingServiceClient) GetGreeting(ctx context.Context, in *GetGreetingRequest, opts ...grpc.CallOption) (*GetGreetingResponse, error) {
	out := new(GetGreetingResponse)
	err := c.cc.Invoke(ctx, "/echo.v1.GreetingService/GetGreeting", in, out, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *greetingServiceClient) UpdateGreeting(ctx context.Context, in *UpdateGreetingRequest, opts ...grpc.CallOption) (*UpdateGreetingResponse, error) {
	out := new(UpdateGreetingResponse)
	err := c.cc.Invoke(ctx, "/echo.v1.GreetingService/UpdateGreeting", in, out, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *greetingServiceClient) ListGreetings(ctx context.Context, in *ListGreetingsRequest, opts ...grpc.CallOption) (*ListGreetingsResponse, error) {
	out := new(ListGreetingsResponse)
	err := c.cc.Invoke(ctx, "/echo.v1.GreetingService/ListGreetings", in, out, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *greetingServiceClient) DeleteGreeting(ctx context.Context, in *DeleteGreetingRequest, opts ...grpc.CallOption) (*DeleteGreetingResponse, error) {
	out := new(DeleteGreetingResponse)
	err := c.cc.Invoke(ctx, "/echo.v1.GreetingService/DeleteGreeting", in, out, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *greetingServiceClient) DeleteGreetings(ctx context.Context, in *DeleteGreetingsRequest, opts ...grpc.CallOption) (*DeleteGreetingsResponse, error) {
	out := new(DeleteGreetingsResponse)
	err := c.cc.Invoke(ctx, "/echo.v1.GreetingService/DeleteGreetings", in, out, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *greetingServiceClient) ImportGreeting(ctx context.Context, in *ImportGreetingRequest, opts ...grpc.CallOption) (*ImportGreetingResponse, error) {
	out := new(ImportGreetingResponse)
	err := c.cc.Invoke(ctx, "/echo.v1.GreetingService/ImportGreeting", in, out, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *greetingServiceClient) ExportGreeting(ctx context.Context, in *ExportGreetingRequest, opts ...grpc.CallOption) (*ExportGreetingResponse, error) {
	out := new(ExportGreetingResponse)
	err := c.cc.Invoke(ctx, "/echo.v1.GreetingService/ExportGreeting", in, out, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

// GreetingServiceServer is the server API for GreetingService service.
// All implementations must embed UnimplementedGreetingServiceServer
// for forward compatibility
type GreetingServiceServer interface {
	// Creates a Greeting.
	CreateGreeting(context.Context, *CreateGreetingRequest) (*CreateGreetingResponse, error)
	// Gets a Greeting.
	GetGreeting(context.Context, *GetGreetingRequest) (*GetGreetingResponse, error)
	// Updates a Greeting.
	UpdateGreeting(context.Context, *UpdateGreetingRequest) (*UpdateGreetingResponse, error)
	// Lists Greetings in a Location.
	ListGreetings(context.Context, *ListGreetingsRequest) (*ListGreetingsResponse, error)
	// Deletes a Greeting.
	DeleteGreeting(context.Context, *DeleteGreetingRequest) (*DeleteGreetingResponse, error)
	// Batch delete Greeting by filter.
	DeleteGreetings(context.Context, *DeleteGreetingsRequest) (*DeleteGreetingsResponse, error)
	// Imports a Greeting.
	ImportGreeting(context.Context, *ImportGreetingRequest) (*ImportGreetingResponse, error)
	// Exports a Greeting.
	ExportGreeting(context.Context, *ExportGreetingRequest) (*ExportGreetingResponse, error)
	mustEmbedUnimplementedGreetingServiceServer()
}

// UnimplementedGreetingServiceServer must be embedded to have forward compatible implementations.
type UnimplementedGreetingServiceServer struct {
}

func (UnimplementedGreetingServiceServer) CreateGreeting(context.Context, *CreateGreetingRequest) (*CreateGreetingResponse, error) {
	return nil, status.Errorf(codes.Unimplemented, "method CreateGreeting not implemented")
}
func (UnimplementedGreetingServiceServer) GetGreeting(context.Context, *GetGreetingRequest) (*GetGreetingResponse, error) {
	return nil, status.Errorf(codes.Unimplemented, "method GetGreeting not implemented")
}
func (UnimplementedGreetingServiceServer) UpdateGreeting(context.Context, *UpdateGreetingRequest) (*UpdateGreetingResponse, error) {
	return nil, status.Errorf(codes.Unimplemented, "method UpdateGreeting not implemented")
}
func (UnimplementedGreetingServiceServer) ListGreetings(context.Context, *ListGreetingsRequest) (*ListGreetingsResponse, error) {
	return nil, status.Errorf(codes.Unimplemented, "method ListGreetings not implemented")
}
func (UnimplementedGreetingServiceServer) DeleteGreeting(context.Context, *DeleteGreetingRequest) (*DeleteGreetingResponse, error) {
	return nil, status.Errorf(codes.Unimplemented, "method DeleteGreeting not implemented")
}
func (UnimplementedGreetingServiceServer) DeleteGreetings(context.Context, *DeleteGreetingsRequest) (*DeleteGreetingsResponse, error) {
	return nil, status.Errorf(codes.Unimplemented, "method DeleteGreetings not implemented")
}
func (UnimplementedGreetingServiceServer) ImportGreeting(context.Context, *ImportGreetingRequest) (*ImportGreetingResponse, error) {
	return nil, status.Errorf(codes.Unimplemented, "method ImportGreeting not implemented")
}
func (UnimplementedGreetingServiceServer) ExportGreeting(context.Context, *ExportGreetingRequest) (*ExportGreetingResponse, error) {
	return nil, status.Errorf(codes.Unimplemented, "method ExportGreeting not implemented")
}
func (UnimplementedGreetingServiceServer) mustEmbedUnimplementedGreetingServiceServer() {}

// UnsafeGreetingServiceServer may be embedded to opt out of forward compatibility for this service.
// Use of this interface is not recommended, as added methods to GreetingServiceServer will
// result in compilation errors.
type UnsafeGreetingServiceServer interface {
	mustEmbedUnimplementedGreetingServiceServer()
}

func RegisterGreetingServiceServer(s grpc.ServiceRegistrar, srv GreetingServiceServer) {
	s.RegisterService(&GreetingService_ServiceDesc, srv)
}

func _GreetingService_CreateGreeting_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(CreateGreetingRequest)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(GreetingServiceServer).CreateGreeting(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/echo.v1.GreetingService/CreateGreeting",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(GreetingServiceServer).CreateGreeting(ctx, req.(*CreateGreetingRequest))
	}
	return interceptor(ctx, in, info, handler)
}

func _GreetingService_GetGreeting_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(GetGreetingRequest)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(GreetingServiceServer).GetGreeting(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/echo.v1.GreetingService/GetGreeting",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(GreetingServiceServer).GetGreeting(ctx, req.(*GetGreetingRequest))
	}
	return interceptor(ctx, in, info, handler)
}

func _GreetingService_UpdateGreeting_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(UpdateGreetingRequest)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(GreetingServiceServer).UpdateGreeting(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/echo.v1.GreetingService/UpdateGreeting",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(GreetingServiceServer).UpdateGreeting(ctx, req.(*UpdateGreetingRequest))
	}
	return interceptor(ctx, in, info, handler)
}

func _GreetingService_ListGreetings_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(ListGreetingsRequest)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(GreetingServiceServer).ListGreetings(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/echo.v1.GreetingService/ListGreetings",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(GreetingServiceServer).ListGreetings(ctx, req.(*ListGreetingsRequest))
	}
	return interceptor(ctx, in, info, handler)
}

func _GreetingService_DeleteGreeting_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(DeleteGreetingRequest)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(GreetingServiceServer).DeleteGreeting(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/echo.v1.GreetingService/DeleteGreeting",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(GreetingServiceServer).DeleteGreeting(ctx, req.(*DeleteGreetingRequest))
	}
	return interceptor(ctx, in, info, handler)
}

func _GreetingService_DeleteGreetings_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(DeleteGreetingsRequest)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(GreetingServiceServer).DeleteGreetings(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/echo.v1.GreetingService/DeleteGreetings",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(GreetingServiceServer).DeleteGreetings(ctx, req.(*DeleteGreetingsRequest))
	}
	return interceptor(ctx, in, info, handler)
}

func _GreetingService_ImportGreeting_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(ImportGreetingRequest)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(GreetingServiceServer).ImportGreeting(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/echo.v1.GreetingService/ImportGreeting",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(GreetingServiceServer).ImportGreeting(ctx, req.(*ImportGreetingRequest))
	}
	return interceptor(ctx, in, info, handler)
}

func _GreetingService_ExportGreeting_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(ExportGreetingRequest)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(GreetingServiceServer).ExportGreeting(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/echo.v1.GreetingService/ExportGreeting",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(GreetingServiceServer).ExportGreeting(ctx, req.(*ExportGreetingRequest))
	}
	return interceptor(ctx, in, info, handler)
}

// GreetingService_ServiceDesc is the grpc.ServiceDesc for GreetingService service.
// It's only intended for direct use with grpc.RegisterService,
// and not to be introspected or modified (even as a copy)
var GreetingService_ServiceDesc = grpc.ServiceDesc{
	ServiceName: "echo.v1.GreetingService",
	HandlerType: (*GreetingServiceServer)(nil),
	Methods: []grpc.MethodDesc{
		{
			MethodName: "CreateGreeting",
			Handler:    _GreetingService_CreateGreeting_Handler,
		},
		{
			MethodName: "GetGreeting",
			Handler:    _GreetingService_GetGreeting_Handler,
		},
		{
			MethodName: "UpdateGreeting",
			Handler:    _GreetingService_UpdateGreeting_Handler,
		},
		{
			MethodName: "ListGreetings",
			Handler:    _GreetingService_ListGreetings_Handler,
		},
		{
			MethodName: "DeleteGreeting",
			Handler:    _GreetingService_DeleteGreeting_Handler,
		},
		{
			MethodName: "DeleteGreetings",
			Handler:    _GreetingService_DeleteGreetings_Handler,
		},
		{
			MethodName: "ImportGreeting",
			Handler:    _GreetingService_ImportGreeting_Handler,
		},
		{
			MethodName: "ExportGreeting",
			Handler:    _GreetingService_ExportGreeting_Handler,
		},
	},
	Streams:  []grpc.StreamDesc{},
	Metadata: "echo/v1/greeting_service.proto",
}
