package convert

import (
	"path"
	"testing"

	"github.com/golang/protobuf/proto"
	. "github.com/smartystreets/goconvey/convey"

	"github.com/sylvan/auro/apis/thirdparty/greeting"
	customruntime "github.com/sylvan/auro/pkg/lib/runtime"
)

type TestEo struct {
	Name string
}

func TestYamlFileConvert(t *testing.T) {
	resoucePath := customruntime.GetResourcesDirForFunction()
	Convey("Testing get yaml conevert", t, func(c C) {
		testCases := []struct {
			src  interface{}
			name string
			cmp  func(a interface{}, name string) bool
		}{
			{
				src: &greeting.Chat{
					Hello: &greeting.Hello{Name: "Sylvan"},
				},
				name: "chat.yaml",
				cmp: func(a interface{}, name string) bool {
					chatEo := a.(*greeting.Chat)
					err := ProtoEoToYamlFile(chatEo, path.Join(resoucePath, name))
					So(err, ShouldBeNil)
					chatEo2 := &greeting.Chat{}
					err = YamlFileToProtoEo(path.Join(resoucePath, name), chatEo2)
					So(err, ShouldBeNil)
					return proto.Equal(chatEo, chatEo2)
				},
			},
			{
				src: TestEo{
					Name: "Sylvan",
				},
				name: "test.yaml",
				cmp: func(a interface{}, name string) bool {
					testEo := a.(TestEo)
					err := EoToYamlFile(&testEo, path.Join(resoucePath, name))
					So(err, ShouldBeNil)
					testEo2 := &TestEo{}
					err = YamlFileToEo(path.Join(resoucePath, name), testEo2)
					So(err, ShouldBeNil)
					So(&testEo, ShouldResemble, testEo2)
					return true
				},
			},
		}
		for i, tc := range testCases {
			_, _ = c.Printf("case #%d: src=%+v dest=%+v\n", i, tc.src, tc.name)
			So(tc.cmp(tc.src, tc.name), ShouldBeTrue)
		}
	})
}
func TestJsonFileConvert(t *testing.T) {
	resoucePath := customruntime.GetResourcesDirForFunction()
	Convey("Testing get json conevert", t, func(c C) {
		testCases := []struct {
			src  interface{}
			name string
			cmp  func(a interface{}, name string) bool
		}{
			{
				src: &greeting.Chat{
					Hello: &greeting.Hello{Name: "Sylvan"},
				},
				name: "chat.json",
				cmp: func(a interface{}, name string) bool {
					chatEo := a.(*greeting.Chat)
					err := ProtoEoToJsonFile(chatEo, path.Join(resoucePath, name))
					So(err, ShouldBeNil)
					chatEo2 := &greeting.Chat{}
					err = JsonFileToProtoEo(path.Join(resoucePath, name), chatEo2)
					So(err, ShouldBeNil)
					return proto.Equal(chatEo, chatEo2)
				},
			},
			{
				src: TestEo{
					Name: "Sylvan",
				},
				name: "test.json",
				cmp: func(a interface{}, name string) bool {
					testEo := a.(TestEo)
					err := EoToJsonFile(&testEo, path.Join(resoucePath, name))
					So(err, ShouldBeNil)
					testEo2 := &TestEo{}
					err = JsonFileToEo(path.Join(resoucePath, name), testEo2)
					So(err, ShouldBeNil)
					So(&testEo, ShouldResemble, testEo2)
					return true
				},
			},
		}
		for i, tc := range testCases {
			_, _ = c.Printf("case #%d: src=%+v dest=%+v\n", i, tc.src, tc.name)
			So(tc.cmp(tc.src, tc.name), ShouldBeTrue)
		}
	})
}
