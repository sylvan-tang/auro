package runtime

import (
	"testing"

	. "github.com/smartystreets/goconvey/convey"
)

func TestGetRuntimeFileName(t *testing.T) {
	Convey("Testing get runtime file name should return caller method's file name", t, func() {
		So(GetRuntimeFileName(), ShouldEqual, "runtime_test.go")
	})
}

func TestGetRuntimePackagePath(t *testing.T) {
	Convey("Testing get runtime file name should return caller method's file name", t, func() {
		So(GetRuntimePackagePath(2), ShouldEndWith, "/lib")
		So(GetRuntimePackagePath(3), ShouldEndWith, "/pkg")
	})
}

func TestGetRuntimeFunctionName(t *testing.T) {
	Convey("Testing get runtime function name should return caller method's name", t, func() {
		So(GetRuntimeFunctionNames(), ShouldResemble, []string{"func1", "TestGetRuntimeFunctionName"})
	})
}
