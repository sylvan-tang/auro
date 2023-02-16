package assoc

import (
	"context"
	"os"
	"testing"
	"time"

	"github.com/leventeliu/goproc"
)

var (
	rootCtx   = context.Background()
	assocCtrl = goproc.NewController(rootCtx, "testing.assoc")
)

func setup() {
	assocCtrl.WithValue("server", &Service{}).
		Go(RunRPC)
	time.Sleep(10 * time.Second)
}

func tearDown() {
	// 停掉本地运行时启动的服务
	assocCtrl.Shutdown()
}

func TestMain(m *testing.M) {
	// 跑测试的时候，会默认先运行 TestMain 函数，然后再跑实际的测试用例 m
	os.Exit(func() int {
		setup()
		defer tearDown()
		return m.Run()
	}())
}
