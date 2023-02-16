package main

import (
	"context"
	"os"
	"os/signal"
	"time"

	"github.com/gogf/gf/frame/g"
	"github.com/leventeliu/goproc"

	schedulerlib "github.com/sylvan/auro/pkg/lib/scheduler"
	"github.com/sylvan/auro/pkg/service/echo"
	"github.com/sylvan/auro/pkg/service/echo/adapter/scheduler"
)

func main() {
	ctx, stop := signal.NotifyContext(context.Background(), os.Interrupt)
	defer stop()
	g.Log().Infof("starting echo server...")
	goproc.NewController(ctx, "main").
		WithValue("server", &echo.Service{}).
		Go(echo.RunRPC).
		Go(echo.RunGateway).
		Go(schedulerlib.SchedulerWithTicker(5*time.Minute, scheduler.Scheduler)).
		Wait()
}
