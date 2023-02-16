package echo

import (
	"context"
	"fmt"
	"os"
	"testing"

	"github.com/leventeliu/goproc"

	echoclient "github.com/sylvan/auro/pkg/client/echo"
	customgrpc "github.com/sylvan/auro/toolkits/pkg/lib/grpc"
)

var (
	rootCtx                                   = context.Background()
	echoCtrl                                  = goproc.NewController(rootCtx, "testing.echo")
	echoCtrl2                                 = goproc.NewController(rootCtx, "testing.echo2")
	echoServerConfig *customgrpc.ServerConfig = &customgrpc.ServerConfig{
		NetworkConfig: customgrpc.NetworkConfig{Name: "grpc-front", Host: "localhost", Port: 60011},
		Network:       "tcp",
	}
	echoCli *echoclient.Client
)

func setup() {
	echoCtrl.WithValue("server", &Service{}).
		Go(RunRPC)
	// make sure two client will start at same time.
	echoCtrl2.WithValue("server", &Service{}).
		WithValue(fmt.Sprintf("%s.%s", customgrpc.ServerKey, "echo"), echoServerConfig).
		Go(RunRPC)
	echoConn := customgrpc.NewServerConn(echoServerConfig)
	echoCli = echoclient.NewClient(echoConn)
}

func tearDown() {
	// 停掉本地运行时启动的服务
	echoCtrl.Shutdown()
	echoCtrl2.Shutdown()
}

func TestMain(m *testing.M) {
	// 跑测试的时候，会默认先运行 TestMain 函数，然后再跑实际的测试用例 m
	os.Exit(func() int {
		setup()
		defer tearDown()
		return m.Run()
	}())
}
