package tool

import (
	"fmt"
	"reflect"
	"strings"

	"github.com/gogf/gf/frame/g"
	"github.com/gogf/gf/os/gtime"

	customfnv "github.com/sylvan/auro/pkg/lib/encoding/fnv"
)

func ReadConfig(configKey string, data interface{}) {
	destPtr := reflect.ValueOf(data)
	if !destPtr.IsValid() || destPtr.Kind() != reflect.Ptr {
		g.Log().Fatalf("data is not a ref")
	}
	destValue := destPtr.Elem()
	if destValue.Kind() != reflect.Struct {
		g.Log().Fatalf("data is not a ref of struct")
	}
	err := g.Cfg().GetJson(configKey).Struct(data)
	if err != nil {
		g.Log().Fatalf("failed to parse key: %s, %s", configKey, err)
	}
}

func GenerateName(namePrefix string, toLower bool) string {
	name := fmt.Sprintf("%s-%v", namePrefix, customfnv.FNV32a([]byte(fmt.Sprintf("%s-%v", namePrefix, gtime.Now().TimestampNano()))))
	if toLower {
		return strings.ToLower(name)
	}
	return name
}
