// Code generated by protoc-gen-go-grpc. DO NOT EDIT.

package assocv1

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

// AssocServiceClient is the client API for AssocService service.
//
// For semantics around ctx use and closing/ending streaming RPCs, please refer to https://pkg.go.dev/google.golang.org/grpc/?tab=doc#ClientConn.NewStream.
type AssocServiceClient interface {
	// Creates a Assoc.
	CreateAssoc(ctx context.Context, in *CreateAssocRequest, opts ...grpc.CallOption) (*CreateAssocResponse, error)
	// Updates a Assoc.
	UpdateAssoc(ctx context.Context, in *UpdateAssocRequest, opts ...grpc.CallOption) (*UpdateAssocResponse, error)
	// Lists Assocs in a Location.
	ListAssocs(ctx context.Context, in *ListAssocsRequest, opts ...grpc.CallOption) (*ListAssocsResponse, error)
	// Deletes a Assoc.
	DeleteAssoc(ctx context.Context, in *DeleteAssocRequest, opts ...grpc.CallOption) (*DeleteAssocResponse, error)
	// Batch delete Assoc by filter.
	DeleteAssocs(ctx context.Context, in *DeleteAssocsRequest, opts ...grpc.CallOption) (*DeleteAssocsResponse, error)
	CountAssocs(ctx context.Context, in *CountAssocsRequest, opts ...grpc.CallOption) (*CountAssocsResponse, error)
	GetAssoc(ctx context.Context, in *GetAssocRequest, opts ...grpc.CallOption) (*GetAssocResponse, error)
}

type assocServiceClient struct {
	cc grpc.ClientConnInterface
}

func NewAssocServiceClient(cc grpc.ClientConnInterface) AssocServiceClient {
	return &assocServiceClient{cc}
}

func (c *assocServiceClient) CreateAssoc(ctx context.Context, in *CreateAssocRequest, opts ...grpc.CallOption) (*CreateAssocResponse, error) {
	out := new(CreateAssocResponse)
	err := c.cc.Invoke(ctx, "/assoc.v1.AssocService/CreateAssoc", in, out, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *assocServiceClient) UpdateAssoc(ctx context.Context, in *UpdateAssocRequest, opts ...grpc.CallOption) (*UpdateAssocResponse, error) {
	out := new(UpdateAssocResponse)
	err := c.cc.Invoke(ctx, "/assoc.v1.AssocService/UpdateAssoc", in, out, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *assocServiceClient) ListAssocs(ctx context.Context, in *ListAssocsRequest, opts ...grpc.CallOption) (*ListAssocsResponse, error) {
	out := new(ListAssocsResponse)
	err := c.cc.Invoke(ctx, "/assoc.v1.AssocService/ListAssocs", in, out, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *assocServiceClient) DeleteAssoc(ctx context.Context, in *DeleteAssocRequest, opts ...grpc.CallOption) (*DeleteAssocResponse, error) {
	out := new(DeleteAssocResponse)
	err := c.cc.Invoke(ctx, "/assoc.v1.AssocService/DeleteAssoc", in, out, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *assocServiceClient) DeleteAssocs(ctx context.Context, in *DeleteAssocsRequest, opts ...grpc.CallOption) (*DeleteAssocsResponse, error) {
	out := new(DeleteAssocsResponse)
	err := c.cc.Invoke(ctx, "/assoc.v1.AssocService/DeleteAssocs", in, out, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *assocServiceClient) CountAssocs(ctx context.Context, in *CountAssocsRequest, opts ...grpc.CallOption) (*CountAssocsResponse, error) {
	out := new(CountAssocsResponse)
	err := c.cc.Invoke(ctx, "/assoc.v1.AssocService/CountAssocs", in, out, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *assocServiceClient) GetAssoc(ctx context.Context, in *GetAssocRequest, opts ...grpc.CallOption) (*GetAssocResponse, error) {
	out := new(GetAssocResponse)
	err := c.cc.Invoke(ctx, "/assoc.v1.AssocService/GetAssoc", in, out, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

// AssocServiceServer is the server API for AssocService service.
// All implementations must embed UnimplementedAssocServiceServer
// for forward compatibility
type AssocServiceServer interface {
	// Creates a Assoc.
	CreateAssoc(context.Context, *CreateAssocRequest) (*CreateAssocResponse, error)
	// Updates a Assoc.
	UpdateAssoc(context.Context, *UpdateAssocRequest) (*UpdateAssocResponse, error)
	// Lists Assocs in a Location.
	ListAssocs(context.Context, *ListAssocsRequest) (*ListAssocsResponse, error)
	// Deletes a Assoc.
	DeleteAssoc(context.Context, *DeleteAssocRequest) (*DeleteAssocResponse, error)
	// Batch delete Assoc by filter.
	DeleteAssocs(context.Context, *DeleteAssocsRequest) (*DeleteAssocsResponse, error)
	CountAssocs(context.Context, *CountAssocsRequest) (*CountAssocsResponse, error)
	GetAssoc(context.Context, *GetAssocRequest) (*GetAssocResponse, error)
	mustEmbedUnimplementedAssocServiceServer()
}

// UnimplementedAssocServiceServer must be embedded to have forward compatible implementations.
type UnimplementedAssocServiceServer struct {
}

func (UnimplementedAssocServiceServer) CreateAssoc(context.Context, *CreateAssocRequest) (*CreateAssocResponse, error) {
	return nil, status.Errorf(codes.Unimplemented, "method CreateAssoc not implemented")
}
func (UnimplementedAssocServiceServer) UpdateAssoc(context.Context, *UpdateAssocRequest) (*UpdateAssocResponse, error) {
	return nil, status.Errorf(codes.Unimplemented, "method UpdateAssoc not implemented")
}
func (UnimplementedAssocServiceServer) ListAssocs(context.Context, *ListAssocsRequest) (*ListAssocsResponse, error) {
	return nil, status.Errorf(codes.Unimplemented, "method ListAssocs not implemented")
}
func (UnimplementedAssocServiceServer) DeleteAssoc(context.Context, *DeleteAssocRequest) (*DeleteAssocResponse, error) {
	return nil, status.Errorf(codes.Unimplemented, "method DeleteAssoc not implemented")
}
func (UnimplementedAssocServiceServer) DeleteAssocs(context.Context, *DeleteAssocsRequest) (*DeleteAssocsResponse, error) {
	return nil, status.Errorf(codes.Unimplemented, "method DeleteAssocs not implemented")
}
func (UnimplementedAssocServiceServer) CountAssocs(context.Context, *CountAssocsRequest) (*CountAssocsResponse, error) {
	return nil, status.Errorf(codes.Unimplemented, "method CountAssocs not implemented")
}
func (UnimplementedAssocServiceServer) GetAssoc(context.Context, *GetAssocRequest) (*GetAssocResponse, error) {
	return nil, status.Errorf(codes.Unimplemented, "method GetAssoc not implemented")
}
func (UnimplementedAssocServiceServer) mustEmbedUnimplementedAssocServiceServer() {}

// UnsafeAssocServiceServer may be embedded to opt out of forward compatibility for this service.
// Use of this interface is not recommended, as added methods to AssocServiceServer will
// result in compilation errors.
type UnsafeAssocServiceServer interface {
	mustEmbedUnimplementedAssocServiceServer()
}

func RegisterAssocServiceServer(s grpc.ServiceRegistrar, srv AssocServiceServer) {
	s.RegisterService(&AssocService_ServiceDesc, srv)
}

func _AssocService_CreateAssoc_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(CreateAssocRequest)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(AssocServiceServer).CreateAssoc(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/assoc.v1.AssocService/CreateAssoc",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(AssocServiceServer).CreateAssoc(ctx, req.(*CreateAssocRequest))
	}
	return interceptor(ctx, in, info, handler)
}

func _AssocService_UpdateAssoc_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(UpdateAssocRequest)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(AssocServiceServer).UpdateAssoc(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/assoc.v1.AssocService/UpdateAssoc",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(AssocServiceServer).UpdateAssoc(ctx, req.(*UpdateAssocRequest))
	}
	return interceptor(ctx, in, info, handler)
}

func _AssocService_ListAssocs_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(ListAssocsRequest)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(AssocServiceServer).ListAssocs(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/assoc.v1.AssocService/ListAssocs",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(AssocServiceServer).ListAssocs(ctx, req.(*ListAssocsRequest))
	}
	return interceptor(ctx, in, info, handler)
}

func _AssocService_DeleteAssoc_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(DeleteAssocRequest)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(AssocServiceServer).DeleteAssoc(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/assoc.v1.AssocService/DeleteAssoc",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(AssocServiceServer).DeleteAssoc(ctx, req.(*DeleteAssocRequest))
	}
	return interceptor(ctx, in, info, handler)
}

func _AssocService_DeleteAssocs_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(DeleteAssocsRequest)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(AssocServiceServer).DeleteAssocs(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/assoc.v1.AssocService/DeleteAssocs",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(AssocServiceServer).DeleteAssocs(ctx, req.(*DeleteAssocsRequest))
	}
	return interceptor(ctx, in, info, handler)
}

func _AssocService_CountAssocs_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(CountAssocsRequest)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(AssocServiceServer).CountAssocs(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/assoc.v1.AssocService/CountAssocs",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(AssocServiceServer).CountAssocs(ctx, req.(*CountAssocsRequest))
	}
	return interceptor(ctx, in, info, handler)
}

func _AssocService_GetAssoc_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(GetAssocRequest)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(AssocServiceServer).GetAssoc(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/assoc.v1.AssocService/GetAssoc",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(AssocServiceServer).GetAssoc(ctx, req.(*GetAssocRequest))
	}
	return interceptor(ctx, in, info, handler)
}

// AssocService_ServiceDesc is the grpc.ServiceDesc for AssocService service.
// It's only intended for direct use with grpc.RegisterService,
// and not to be introspected or modified (even as a copy)
var AssocService_ServiceDesc = grpc.ServiceDesc{
	ServiceName: "assoc.v1.AssocService",
	HandlerType: (*AssocServiceServer)(nil),
	Methods: []grpc.MethodDesc{
		{
			MethodName: "CreateAssoc",
			Handler:    _AssocService_CreateAssoc_Handler,
		},
		{
			MethodName: "UpdateAssoc",
			Handler:    _AssocService_UpdateAssoc_Handler,
		},
		{
			MethodName: "ListAssocs",
			Handler:    _AssocService_ListAssocs_Handler,
		},
		{
			MethodName: "DeleteAssoc",
			Handler:    _AssocService_DeleteAssoc_Handler,
		},
		{
			MethodName: "DeleteAssocs",
			Handler:    _AssocService_DeleteAssocs_Handler,
		},
		{
			MethodName: "CountAssocs",
			Handler:    _AssocService_CountAssocs_Handler,
		},
		{
			MethodName: "GetAssoc",
			Handler:    _AssocService_GetAssoc_Handler,
		},
	},
	Streams:  []grpc.StreamDesc{},
	Metadata: "assoc/v1/assoc_service.proto",
}