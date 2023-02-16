package config

import (
	"path"
	"reflect"
	"testing"

	"github.com/gogf/gf/frame/g"
	. "github.com/smartystreets/goconvey/convey"
)

type empty struct {
}

func TestConfig(t *testing.T) {
	Convey("Testing ctx.Value function...", t, func(c C) {
		testCases := []struct {
			key string
			val any
		}{
			{
				key: "server.namespace",
				val: "auro",
			},
			{
				key: "server.namespace.echo",
				val: "",
			},
		}
		for i, tc := range testCases {
			_, _ = c.Printf("case #%d: key=%+v expect-val=%+v\n", i, tc.key, tc.val)
			So(tc.val, ShouldResemble, g.Cfg().GetString(tc.key))
		}
		c.So(path.Base(reflect.TypeOf(empty{}).PkgPath()), ShouldEqual, "config")
	})
}
