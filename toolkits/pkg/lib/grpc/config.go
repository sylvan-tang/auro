package grpc

import (
	"context"
	"fmt"

	"github.com/gogf/gf/frame/g"
)

const ServerKey = "server.grpc"
const GatewayServerKey = "server.gateway"
const UnaryServerInterceptorsKey = "server.interceptors.unary"
const StreamServerInterceptorsKey = "server.interceptors.stream"

type NetworkConfig struct {
	Name     string `json:"name"`
	Host     string `json:"host"`
	Port     int    `json:"port"`
	GrpcPort int    `json:"grpcPort"`
}

func (n *NetworkConfig) Address() string {
	return fmt.Sprintf(":%+v", n.Port)
}

func (n *NetworkConfig) GrpcAddress() string {
	return fmt.Sprintf("%s:%+v", n.Host, n.GrpcPort)
}

// GatewayServerConfig setting for http gateway.
type GatewayServerConfig struct {
	NetworkConfig
	Scheme string `json:"scheme"`
}

// ServerConfig setting for grpc server.
type ServerConfig struct {
	NetworkConfig
	Network string `json:"network"`
}

func GetGatewayServerConfig(ctx context.Context, pkgName string) *GatewayServerConfig {
	var serverConfig *GatewayServerConfig
	serverConfig, ok := ctx.Value(fmt.Sprintf("%s.%s", GatewayServerKey, pkgName)).(*GatewayServerConfig)
	if !ok {
		serverConfig = &GatewayServerConfig{}
		err := g.Cfg().GetJson(fmt.Sprintf("%s.%s", GatewayServerKey, pkgName)).Struct(serverConfig)
		if err != nil {
			g.Log().Fatalf("failed to parse rpc gateway server configs: %s", err)
		}
	}
	g.Log().Infof("gatewayServerConfig: %+v", serverConfig)
	return serverConfig
}

func GetServerConfig(ctx context.Context, pkgName string) *ServerConfig {
	var serverConfig *ServerConfig
	serverConfig, ok := ctx.Value(fmt.Sprintf("%s.%s", ServerKey, pkgName)).(*ServerConfig)
	if !ok {
		serverConfig = &ServerConfig{}
		err := g.Cfg().GetJson(fmt.Sprintf("%s.%s", ServerKey, pkgName)).Struct(serverConfig)
		if err != nil {
			g.Log().Fatalf("failed to parse rpc server configs: %s", err)
		}
	}
	g.Log().Infof("serverConfig: %+v", serverConfig)
	return serverConfig
}
