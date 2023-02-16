package json

import (
	"google.golang.org/protobuf/encoding/protojson"
	"google.golang.org/protobuf/proto"
)

var (
	// MarshalProto re-exports protojson.Marshal.
	MultilineMarshalProto = protojson.MarshalOptions{UseProtoNames: true, Multiline: true, Indent: " "}.Marshal
	MarshalProto          = protojson.MarshalOptions{UseProtoNames: true}.Marshal
	// UnmarshalProto re-exports protojson.Unmarshal.
	UnmarshalProto = protojson.Unmarshal
)

// MustMarshalProtoJSON marshals an object to JSON data for MySQL or panic.
// The result is guaranteed to be compatible for MySQL JSON storage.
func MustMarshalProtoJSON(v proto.Message, multiline bool) string {
	var enc []byte
	var err error
	if multiline {
		enc, err = MultilineMarshalProto(v)
	} else {
		enc, err = MarshalProto(v)
	}
	if err != nil {
		panic(err)
	}
	if len(enc) == 0 {
		// NOTE(leventeliu):
		// Empty string fails JSON validation in MySQL, use a JSON-ish {} instead.
		return "{}"
	}
	return string(enc)
}

// MustUnmarshalProtoJSON unmarshalls JSON data from MySQL or panic.
// The data is checked special values from MySQL JSON storage.
func MustUnmarshalProtoJSON(data string, v proto.Message) {
	if data == "" || data == "{}" || data == "null" {
		proto.Reset(v)
		return
	}
	err := UnmarshalProto([]byte(data), v)
	if err != nil {
		panic(err)
	}
}

var formatter = protojson.MarshalOptions{}

// Proto is a convenient method to format any proto message in JSON format and ignores any error.
// You should only use it in logging scenario.
func Proto(m proto.Message) string {
	return formatter.Format(m)
}
