package integration_test

import (
	"testing"

	. "github.com/smartystreets/goconvey/convey"

	echov1 "github.com/sylvan/auro/apis/echo/v1"
	"github.com/sylvan/auro/apis/thirdparty/greeting"
	echoclient "github.com/sylvan/auro/pkg/client/echo"
)

func TestEchoClient(t *testing.T) {
	Convey("Test create echo", t, func(c C) {
		resp, err := echoclient.GetInstance(rootCtx).CreateGreeting(
			rootCtx,
			&echov1.CreateGreetingRequest{
				Greeting: &echov1.Greeting{
					Greeted: "hello,world!",
					Chat:    &greeting.Chat{Hello: &greeting.Hello{Name: "Sylvan"}},
				},
			},
		)
		c.So(err, ShouldBeNil)
		c.So(resp.Code, ShouldEqual, 0)
	})
}
