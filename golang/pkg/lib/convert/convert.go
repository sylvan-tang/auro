package convert

import (
	"errors"
	"fmt"
	"io/ioutil"
	"reflect"
	"time"

	"github.com/gogf/gf/frame/g"
	"github.com/gogf/gf/os/gtime"
	"github.com/gogf/gf/util/gconv"
	"google.golang.org/protobuf/proto"
	"google.golang.org/protobuf/types/known/timestamppb"
	"sigs.k8s.io/yaml"

	"github.com/sylvan/auro/pkg/lib/encoding/json"
)

// data field which store the proto json.
const dataFieldName = "Entity"

// auto generate id by mysql.
const idFieldName = "Id"

var (
	timeFieldNames = []string{"CreateAt", "UpdateAt"}
	errValidation  = errors.New("Validation error")
)

func ConvertibleToInt(v reflect.Value) (t int, ok bool) {
	if !v.IsValid() {
		return
	}
	t = gconv.Int(v.Interface())
	ok = true
	return
}

func ConvertibleToBool(v reflect.Value) (t bool, ok bool) {
	if !v.IsValid() {
		return
	}
	t = gconv.Bool(v.Interface())
	ok = true
	return
}

func ConvertibleToString(v reflect.Value) (t string, ok bool) {
	if !v.IsValid() {
		return
	}
	t = gconv.String(v.Interface())
	ok = true
	return
}

// ConvertibleToTime acts as a convert bridge for various custom types of time.
func ConvertibleToTime(v reflect.Value) (t time.Time, ok bool) {
	if !v.IsValid() || v.IsZero() {
		return
	}
	ok = v.Type().ConvertibleTo(reflect.TypeOf(time.Time{}))
	if ok {
		reflect.ValueOf(&t).Elem().Set(v.Convert(reflect.TypeOf(time.Time{})))
		return
	}
	ok = v.Type().ConvertibleTo(reflect.TypeOf(gtime.Time{}))
	if ok {
		var gt gtime.Time
		reflect.ValueOf(&gt).Elem().Set(v.Convert(reflect.TypeOf(gtime.Time{})))
		t = gt.Time
		return
	}
	ok = v.Type().ConvertibleTo(reflect.TypeOf(timestamppb.Timestamp{}))
	if ok {
		var pbt = timestamppb.Timestamp{}
		reflect.ValueOf(&pbt).Elem().Set(v.Convert(reflect.TypeOf(timestamppb.Timestamp{})))
		t = pbt.AsTime()
		return
	}
	// Indirect
	if v.Kind() == reflect.Ptr {
		return ConvertibleToTime(v.Elem())
	}
	return
}

func validation(src, dest interface{}, srcKind, destKind reflect.Kind) (reflect.Value, reflect.Value, error) {
	destPtr := reflect.ValueOf(dest)
	if !destPtr.IsValid() || destPtr.Kind() != reflect.Ptr {
		return reflect.ValueOf(nil), reflect.ValueOf(nil), fmt.Errorf("%w: dest is not *%s", errValidation, destKind.String())
	}
	destValue := destPtr.Elem()
	if destValue.Kind() != destKind {
		return reflect.ValueOf(nil), reflect.ValueOf(nil), fmt.Errorf("%w: dest is not *%s", errValidation, destKind.String())
	}
	if !destValue.CanSet() {
		return reflect.ValueOf(nil), reflect.ValueOf(nil), fmt.Errorf("%w: cannot set dest", errValidation)
	}
	srcValue := reflect.ValueOf(src)
	for srcValue.Kind() == reflect.Ptr {
		srcValue = srcValue.Elem()
	}
	if srcValue.Kind() != srcKind {
		return reflect.ValueOf(nil), reflect.ValueOf(nil), fmt.Errorf("%w: src is not %s or ref of %s", errValidation, srcKind.String(), srcKind.String())
	}
	return srcValue, destValue, nil
}

func convertField(srcFieldValue, destField reflect.Value) {
	// simple structure
	if srcFieldValue.Type().ConvertibleTo(destField.Type()) {
		destField.Set(srcFieldValue.Convert(destField.Type()))
		return
	}
	// add your custom convert rules here, e.g. `gtime`.`Time` <-> `timestamppb`.`Timestamp`
	switch destField.Interface().(type) {
	case timestamppb.Timestamp:
		if t, ok := ConvertibleToTime(srcFieldValue); ok {
			destField.Set(reflect.ValueOf(*timestamppb.New(t)).Convert(destField.Type()))
		}
	case *timestamppb.Timestamp:
		if t, ok := ConvertibleToTime(srcFieldValue); ok {
			destField.Set(reflect.ValueOf(timestamppb.New(t)).Convert(destField.Type()))
			return
		}
		var tmNil *timestamppb.Timestamp
		destField.Set(reflect.ValueOf(tmNil))
	case gtime.Time:
		if t, ok := ConvertibleToTime(srcFieldValue); ok {
			destField.Set(reflect.ValueOf(*gtime.NewFromTime(t)).Convert(destField.Type()))
		}
	case *gtime.Time:
		if t, ok := ConvertibleToTime(srcFieldValue); ok {
			destField.Set(reflect.ValueOf(gtime.NewFromTime(t)).Convert(destField.Type()))
			return
		}
		var gtNil *gtime.Time
		destField.Set(reflect.ValueOf(gtNil))
	case int:
		if t, ok := ConvertibleToInt(srcFieldValue); ok {
			destField.Set(reflect.ValueOf(t).Convert(destField.Type()))
		}
	case bool:
		if t, ok := ConvertibleToBool(srcFieldValue); ok {
			destField.Set(reflect.ValueOf(t).Convert(destField.Type()))
		}
	case string:
		if t, ok := ConvertibleToString(srcFieldValue); ok {
			destField.Set(reflect.ValueOf(t).Convert(destField.Type()))
		}
	default:
		g.Log().Warningf("match field name but lacking convert method: %s of type %s.%s",
			srcFieldValue.Type().Kind().String(), destField.Type().PkgPath(), destField.Type())
	}
}

func TimeConvert(src, dest interface{}) error {
	srcValue, destValue, err := validation(src, dest, reflect.String, reflect.Struct)
	if err == nil {
		v := timestamppb.New(gtime.New(src).Time)
		t := &gtime.Time{}
		TimeConvert(v, t)
		src = t
	}
	srcValue, destValue, err = validation(src, dest, reflect.Struct, reflect.Struct)
	if err != nil {
		return err
	}
	convertField(srcValue, destValue)
	return nil
}

// Data object convert to Entity Object
func DoToEo(src interface{}, dest proto.Message) error {
	srcValue, destValue, err := validation(src, dest, reflect.Struct, reflect.Struct)
	if err != nil {
		return err
	}
	json.MustUnmarshalProtoJSON(srcValue.FieldByName(dataFieldName).String(), dest)
	for _, fieldName := range append(timeFieldNames, idFieldName) {
		if destField := destValue.FieldByName(fieldName); destField.IsValid() && destField.CanSet() {
			convertField(srcValue.FieldByName(fieldName), destField)
		}
	}
	return nil
}

// Entity object convert to data object.
func EoToDo(src proto.Message, dest interface{}) error {
	srcValue, destValue, err := validation(src, dest, reflect.Struct, reflect.Struct)
	if err != nil {
		return err
	}
	convertField(reflect.ValueOf(json.MustMarshalProtoJSON(src, false)), destValue.FieldByName(dataFieldName))
	if destField := destValue.FieldByName(idFieldName); destField.IsValid() && destField.CanSet() {
		convertField(srcValue.FieldByName(idFieldName), destField)
	}
	return nil
}

func YamlFileToEo(src string, dest interface{}) error {
	_, _, err := validation(src, dest, reflect.String, reflect.Struct)
	if err != nil {
		return err
	}
	bytes, err := ioutil.ReadFile(src)
	if err != nil {
		return err
	}
	yaml.Unmarshal(bytes, dest)
	return nil
}

func EoToYamlFile(src interface{}, dest string) error {
	_, _, err := validation(src, &dest, reflect.Struct, reflect.String)
	if err != nil {
		return err
	}
	bytes, err := yaml.Marshal(src)
	if err != nil {
		return err
	}
	return ioutil.WriteFile(dest, bytes, 0644)
}

func YamlFileToProtoEo(src string, dest proto.Message) error {
	_, _, err := validation(src, dest, reflect.String, reflect.Struct)
	if err != nil {
		return err
	}
	bytes, err := ioutil.ReadFile(src)
	if err != nil {
		return err
	}
	jsonBytes, err := yaml.YAMLToJSON(bytes)
	if err != nil {
		return err
	}
	json.MustUnmarshalProtoJSON(string(jsonBytes), dest)
	return nil
}

func ProtoEoToYamlFile(src proto.Message, dest string) error {
	_, _, err := validation(src, &dest, reflect.Struct, reflect.String)
	if err != nil {
		return err
	}
	jsonBytes := []byte(json.MustMarshalProtoJSON(src, false))
	bytes, err := yaml.JSONToYAML(jsonBytes)
	if err != nil {
		return err
	}
	return ioutil.WriteFile(dest, bytes, 0644)
}

func ProtoEoToBytes(src proto.Message) ([]byte, error) {
	jsonBytes, err := json.MarshalProto(src)
	if err != nil {
		return nil, err
	}

	return yaml.JSONToYAML(jsonBytes)
}

func JsonFileToEo(src string, dest interface{}) error {
	_, _, err := validation(src, dest, reflect.String, reflect.Struct)
	if err != nil {
		return err
	}
	bytes, err := ioutil.ReadFile(src)
	if err != nil {
		return err
	}
	json.MustUnmarshalJSON(string(bytes), dest)
	return nil
}

func EoToJsonFile(src interface{}, dest string) error {
	_, _, err := validation(src, &dest, reflect.Struct, reflect.String)
	if err != nil {
		return err
	}
	bytes := []byte(json.MustMarshalJSON(src, true))
	return ioutil.WriteFile(dest, bytes, 0644)
}

func JsonFileToProtoEo(src string, dest proto.Message) error {
	bytes, err := ioutil.ReadFile(src)
	if err != nil {
		return err
	}
	json.MustUnmarshalProtoJSON(string(bytes), dest)
	return nil
}

func ProtoEoToJsonFile(src proto.Message, dest string) error {
	bytes := []byte(json.MustMarshalProtoJSON(src, true))
	return ioutil.WriteFile(dest, bytes, 0644)
}
