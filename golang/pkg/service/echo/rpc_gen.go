// Code generated by github.com/sylvan/auro/toolkits/cmd/gen-grpc. DO NOT EDIT.
package echo

import (
	"context"
	"fmt"
	"net"

	"github.com/gogf/gf/frame/g"
	"github.com/leventeliu/goproc"
	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials/insecure"

	echov1 "github.com/sylvan/auro/apis/echo/v1"

	// Import all known interceptors.
	_ "github.com/sylvan/auro/pkg/interceptor"
	_ "github.com/sylvan/auro/pkg/interceptor/errors"

	customgrpc "github.com/sylvan/auro/toolkits/pkg/lib/grpc"
	"github.com/sylvan/auro/toolkits/pkg/lib/grpc/interceptor"
)

func loadUnaryInterceptors(ctx context.Context) (its []grpc.UnaryServerInterceptor) {
	var names []string
	names, ok := ctx.Value(fmt.Sprintf("%s.%s", customgrpc.UnaryServerInterceptorsKey, getPkgName())).([]string)
	if !ok {
		names = g.Cfg().GetStrings(fmt.Sprintf("%s.%s", customgrpc.UnaryServerInterceptorsKey, getPkgName()))
	}
	for _, name := range names {
		it, ok := interceptor.GetUnary(name)
		if !ok {
			g.Log().Fatalf("failed to load interceptor %s, did you register it first?", name)
		}
		g.Log().Debugf("loading an unary interceptor: %s", name)
		its = append(its, it)
	}
	return
}

func loadStreamInterceptors(ctx context.Context) (its []grpc.StreamServerInterceptor) {
	var names []string
	names, ok := ctx.Value(fmt.Sprintf("%s.%s", customgrpc.StreamServerInterceptorsKey, getPkgName())).([]string)
	if !ok {
		names = g.Cfg().GetStrings(fmt.Sprintf("%s.%s", customgrpc.StreamServerInterceptorsKey, getPkgName()))
	}
	for _, name := range names {
		it, ok := interceptor.GetStream(name)
		if !ok {
			g.Log().Fatalf("failed to load interceptor %s, did you register it first?", name)
		}
		g.Log().Debugf("loading a stream interceptor: %s", name)
		its = append(its, it)
	}
	return
}

// RunRPC loads config and runs an RPC server to serve endpoints.
func RunRPC(ctx context.Context) {
	srv := ctx.Value("server")
	defaultReceiveSize, defaultSendSize := customgrpc.GetReceiveAndSendMsgSize()
	g.Log().Infof("defaultReceiveSize = %d, defaultSendSize = %d", defaultReceiveSize, defaultSendSize)

	rpc := grpc.NewServer(
		grpc.Creds(insecure.NewCredentials()),
		grpc.ChainUnaryInterceptor(loadUnaryInterceptors(ctx)...),
		grpc.ChainStreamInterceptor(loadStreamInterceptors(ctx)...),
		grpc.MaxSendMsgSize(defaultReceiveSize),
		grpc.MaxRecvMsgSize(defaultSendSize),
	)
	echov1.RegisterGreetingServiceServer(rpc, srv.(echov1.GreetingServiceServer))

	serverConfig := customgrpc.GetServerConfig(ctx, getPkgName())
	ctrl := goproc.NewController(ctx, "server.grpc.assoc")
	defer ctrl.Wait()
	ctrl.WithValue("config", serverConfig).
		WithValue("server", rpc).
		Go(serveRPC)
	ctrl.Go(func(ctx context.Context) {
		<-ctx.Done()
		g.Log().Infof("get context notify: name=server.grpc error=%s", ctx.Err())
		rpc.GracefulStop()
	})
}

func serveRPC(ctx context.Context) {
	rpc := ctx.Value("server").(*grpc.Server)
	cfg := ctx.Value("config").(*customgrpc.ServerConfig)

	g.Log().Infof("starting grpc server: name=%s address=%s@%s", cfg.Name, cfg.Network, cfg.Address())
	listener, err := net.Listen(cfg.Network, cfg.Address())
	if err != nil {
		g.Log().Fatalf("failed to listen %s@%s: %s", cfg.Network, cfg.Address(), err)
	}

	err = rpc.Serve(listener)
	if err != nil {
		g.Log().Fatalf("grpc server exiting: name=%s, error=%s", cfg.Name, err)
	}
	g.Log().Infof("grpc server exited: name=%s", cfg.Name)
}
