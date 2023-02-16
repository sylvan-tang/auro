package main

import (
	"context"
	"os"
	"os/signal"

	"github.com/gogf/gf/frame/g"
	"github.com/leventeliu/goproc"

	assoc "github.com/sylvan/auro/pkg/service/assoc"
)

func main() {
	ctx, stop := signal.NotifyContext(context.Background(), os.Interrupt)
	defer stop()

	g.Log().Info("starting controller server...")
	goproc.NewController(ctx, "main").
		WithValue("server", &assoc.Service{}).
		Go(assoc.RunRPC).
		Go(assoc.RunGateway).
		Wait()
}
