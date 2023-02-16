package scheduler

import (
	"context"
	"time"

	"github.com/gogf/gf/frame/g"
)

func Scheduler(_ context.Context) {
	g.Log().Info("scheduler", time.Now())
}
