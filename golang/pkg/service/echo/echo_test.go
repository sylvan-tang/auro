package echo

import (
	"testing"
	"time"

	"github.com/gogf/gf/os/gtime"
	. "github.com/smartystreets/goconvey/convey"
	"google.golang.org/protobuf/proto"

	commonv1 "github.com/sylvan/auro/apis/common/v1"
	echov1 "github.com/sylvan/auro/apis/echo/v1"
	"github.com/sylvan/auro/apis/thirdparty/greeting"
	echoclient "github.com/sylvan/auro/pkg/client/echo"
)

func TestCRUDEcho(t *testing.T) {
	lowTime := ""
	Convey("Testing CRUD opeartion from GreetingService!", t, func(c C) {
		greetingEo := echov1.Greeting{
			Greeted: "hello,world!",
			Chat:    &greeting.Chat{Hello: &greeting.Hello{Name: "Sylvan"}},
		}
		resp, err := GetInstance().CreateGreeting(rootCtx, &echov1.CreateGreetingRequest{Greeting: &greetingEo})
		c.So(err, ShouldBeNil)
		c.So(resp.Details.Id, ShouldBeGreaterThan, 0)

		// test errors interceptor
		for _, echoClient := range []*echoclient.Client{echoCli, echoclient.GetInstance(rootCtx)} {
			getResp, err := echoClient.GetGreeting(rootCtx, &echov1.GetGreetingRequest{Id: resp.Details.Id + 1})
			c.So(err, ShouldBeNil)
			c.So(getResp.Code, ShouldEqual, 2001)
			c.So(getResp.Message, ShouldEqual, "数据库查询错误")
		}

		getResp, err := GetInstance().GetGreeting(rootCtx, &echov1.GetGreetingRequest{Id: resp.Details.Id})
		c.So(err, ShouldBeNil)
		c.So(getResp.Details.CreateAt, ShouldNotBeNil)
		c.So(getResp.Details.UpdateAt, ShouldNotBeNil)
		lowTime = getResp.Details.CreateAt
		time.Sleep(5 * time.Second)

		resp.Details.CreateAt = getResp.Details.CreateAt
		resp.Details.UpdateAt = getResp.Details.UpdateAt
		c.So(resp.Details.Id, ShouldBeGreaterThan, 0)
		c.So(proto.Equal(resp.Details, getResp.Details), ShouldBeTrue)

		getResp.Details.Greeted = "hello, world!"
		updateResp, err := GetInstance().UpdateGreeting(rootCtx, &echov1.UpdateGreetingRequest{Greeting: getResp.Details})
		c.So(err, ShouldBeNil)
		getResp.Details.UpdateAt = updateResp.Details.UpdateAt
		c.So("hello, world!", ShouldEqual, updateResp.Details.Greeted)
		c.So(proto.Equal(getResp.Details, updateResp.Details), ShouldBeTrue)

		deleteResp, err := GetInstance().DeleteGreeting(rootCtx, &echov1.DeleteGreetingRequest{Id: getResp.Details.Id})
		c.So(err, ShouldBeNil)
		c.So(proto.Equal(deleteResp.Details, updateResp.Details), ShouldBeTrue)

		for i := 0; i < 10; i++ {
			GetInstance().CreateGreeting(rootCtx, &echov1.CreateGreetingRequest{Greeting: &greetingEo})
		}
		listResp, err := GetInstance().ListGreetings(rootCtx, &echov1.ListGreetingsRequest{CommonOption: &commonv1.CommonOption{Page: 1, Size: 10}})
		c.So(err, ShouldBeNil)
		c.So(len(listResp.Details.Items), ShouldBeGreaterThan, 0)
		time.Sleep(2 * time.Second)
		highTime := gtime.Now().String()
		listResp, err = GetInstance().ListGreetings(
			rootCtx, &echov1.ListGreetingsRequest{CommonOption: &commonv1.CommonOption{Page: 1, Size: 5}, CommonFilter: &commonv1.CommonFilter{
				ColumnName: "create_at",
				HighTime:   highTime,
				LowTime:    lowTime,
			},
			},
		)
		c.So(err, ShouldBeNil)
		c.So(len(listResp.Details.Items), ShouldBeGreaterThan, 0)
		c.So(listResp.Details.NextRequest.CommonFilter.HighTime, ShouldNotEqual, highTime)
		c.So(listResp.Details.NextRequest.CommonFilter.LowTime, ShouldNotBeEmpty)
		c.So(listResp.Details.NextRequest.CommonFilter.ExcludeIds, ShouldNotBeEmpty)

		listResp, err = GetInstance().ListGreetings(rootCtx, listResp.Details.NextRequest)
		c.So(err, ShouldBeNil)
		c.So(len(listResp.Details.Items), ShouldBeGreaterThan, 0)
		c.So(listResp.Details.NextRequest.CommonFilter.HighTime, ShouldNotEqual, highTime)
		c.So(listResp.Details.NextRequest.CommonFilter.LowTime, ShouldNotBeEmpty)
		c.So(listResp.Details.NextRequest.CommonFilter.ExcludeIds, ShouldNotBeEmpty)

		deletesResp, err := GetInstance().DeleteGreetings(rootCtx, &echov1.DeleteGreetingsRequest{CommonFilter: &commonv1.CommonFilter{
			ColumnName: "create_at",
			HighTime:   gtime.Now().String(),
			LowTime:    lowTime,
		}})
		c.So(err, ShouldBeNil)
		c.So(len(deletesResp.Details), ShouldBeGreaterThan, 0)
	})
}
