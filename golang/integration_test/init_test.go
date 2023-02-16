package integration_test

import (
	"context"
	"os"
	"path"
	"testing"
	"time"

	"github.com/gogf/gf/frame/g"
	"github.com/leventeliu/goproc"

	customruntime "github.com/sylvan/auro/pkg/lib/runtime"
	echoservice "github.com/sylvan/auro/pkg/service/echo"
	customgrpc "github.com/sylvan/auro/toolkits/pkg/lib/grpc"
)

var (
	rootCtx  = context.Background()
	echoCtrl = goproc.NewController(rootCtx, "it.echo")
)

func setup() {
	// start server for clients.
	configPath := path.Join(customruntime.GetRuntimePackagePath(1), "config")
	err := g.Cfg().SetPath(configPath)
	customgrpc.PanicWhenError(err)
	echoCtrl.WithValue("server", &echoservice.Service{}).
		Go(echoservice.RunRPC)
	time.Sleep(1 * time.Second)
}

func tearDown() {
	// 停掉本地运行时启动的服务
	echoCtrl.Shutdown()
}

func TestMain(m *testing.M) {
	// 跑测试的时候，会默认先运行 TestMain 函数，然后再跑实际的测试用例 m
	os.Exit(func() int {
		setup()
		defer tearDown()
		return m.Run()
	}())
}
