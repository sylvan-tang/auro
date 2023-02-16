//go:build tools
// +build tools

package tools

import (
	_ "github.com/envoyproxy/protoc-gen-validate"
	_ "github.com/golang/mock/gomock"
	_ "github.com/grpc-ecosystem/grpc-gateway/protoc-gen-grpc-gateway"
	_ "github.com/grpc-ecosystem/grpc-gateway/protoc-gen-swagger"
	_ "github.com/minio/mc"
	_ "github.com/plexsystems/pacmod"
	_ "golang.org/x/tools/gopls"
	_ "google.golang.org/grpc/cmd/protoc-gen-go-grpc"
	_ "google.golang.org/protobuf/cmd/protoc-gen-go"

	_ "github.com/kubeflow/pipelines/api/v2alpha1/go/cachekey"

	_ "github.com/sylvan/auro/toolkits/cmd/gen-proto"
	_ "github.com/sylvan/auro/toolkits/cmd/gen-pypkg"
)
