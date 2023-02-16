package json

import (
	"encoding/json"
	"reflect"
)

var (
	// MarshalJSON re-exports json.Marshal.
	MarshalJSON = json.Marshal
	// UnmarshalJSON re-exports json.Unmarshal.
	UnmarshalJSON = json.Unmarshal
	indentStr     = " "
)

// MustMarshalJSON marshals an object to JSON data or panic.
// The result is guaranteed to be compatible for MySQL JSON storage.
func MustMarshalJSON(v interface{}, multiline bool) string {
	var enc []byte
	var err error
	if multiline {
		enc, err = json.MarshalIndent(v, "", indentStr)
	} else {
		enc, err = MarshalJSON(v)
	}
	if err != nil {
		panic(err)
	}
	if len(enc) == 0 {
		// NOTE(leventeliu):
		// Empty string fails JSON validation in MySQL, use a JSON-ish null instead.
		return "null"
	}
	return string(enc)
}

// MustUnmarshalJSON unmarshalls JSON data from MySQL or panic.
// The data is checked special values from MySQL JSON storage.
func MustUnmarshalJSON(data string, v interface{}) {
	if data == "" || data == "null" {
		destValue := reflect.ValueOf(v)
		if !destValue.IsValid() && destValue.Kind() != reflect.Ptr {
			panic("dest is not pointer")
		}
		destElem := destValue.Elem()
		if !destElem.CanSet() {
			panic("cannot set dest")
		}
		destElem.Set(reflect.Zero(destElem.Type()))
		return
	}
	err := UnmarshalJSON([]byte(data), v)
	if err != nil {
		panic(err)
	}
}

// Any is a convenient method to format any object in JSON format and ignores any error.
// You should only use it in logging scenario.
func Any(v interface{}) string {
	enc, _ := MarshalJSON(v)
	return string(enc)
}
