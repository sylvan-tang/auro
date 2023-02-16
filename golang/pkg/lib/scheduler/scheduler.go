package scheduler

import (
	"context"
	"time"

	"github.com/jasonlvhit/gocron"
)

func SchedulerWithTicker(d time.Duration, exector func(ctx context.Context)) func(ctx context.Context) {
	return func(ctx context.Context) {
		s := gocron.NewScheduler()
		s.Every(uint64(d.Seconds())).Second().Do(exector, ctx)
		<-s.Start()
	}
}
