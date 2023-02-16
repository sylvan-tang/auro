package errors

import (
	"fmt"

	commonv1 "github.com/sylvan/auro/apis/common/v1"
)

const (
	// StatusSuccess message.
	StatusSuccess = "Success"
	// Default error message.
	Default = "错误"
	// SQLErrorMessage error message.
	SQLErrorMessage         = "数据库查询错误"
	invalidFieldValueFormat = "field %+v invalid value: %+v"
)

// Error defines a custom error type which extends grpc.Codes.
type Error struct {
	code    commonv1.Code
	message string
}

// Code returns error code.
func (e *Error) Code() int32 {
	return int32(e.code)
}

// Error returns error string.
func (e *Error) Error() string {
	return e.message
}

// String implements strings.String.
func (e *Error) String() string {
	return fmt.Sprintf(`{"code":%d,"message":"%s"}`, e.code, e.message)
}

// New returns an error representing c and msg. If code is OK, returns nil.
func New(c commonv1.Code, msg string) error {
	if c == 0 {
		return nil
	}
	return &Error{
		code:    c,
		message: msg,
	}
}

// Newf returns New(c, fmt.Sprintf(format, args...)).
func Newf(code commonv1.Code, format string, args ...interface{}) error {
	return New(code, fmt.Sprintf(format, args...))
}

// GeneralError returns Error with commonV1.Code_CODE_GENERAL_ERROR.
func GeneralError(msg string) error {
	return New(commonv1.Code_CODE_GENERAL_ERROR, msg)
}

// GeneralErrorf returns Error with commonV1.Code_CODE_GENERAL_ERROR.
func GeneralErrorf(format string, args ...interface{}) error {
	return Newf(commonv1.Code_CODE_GENERAL_ERROR, format, args...)
}

// NotFoundError returns Error with commonV1.Code_CODE_NOT_FOUND.
func NotFoundError(msg string) error {
	return New(commonv1.Code_CODE_NOT_FOUND, msg)
}

// NotFoundErrorf returns Error with commonV1.Code_CODE_NOT_FOUND.
func NotFoundErrorf(format string, args ...interface{}) error {
	return Newf(commonv1.Code_CODE_NOT_FOUND, format, args...)
}

// AlreadyExistsError returns Error with commonV1.Code_CODE_ALREADY_EXISTS.
func AlreadyExistsError(msg string) error {
	return New(commonv1.Code_CODE_ALREADY_EXISTS, msg)
}

// InvalidArgumentError returns Error with commonV1.Code_CODE_INVALID_ARGUMENT.
func InvalidArgumentError(msg string) error {
	return New(commonv1.Code_CODE_INVALID_ARGUMENT, msg)
}

// InvalidArgumentErrorf returns Error with commonV1.Code_CODE_INVALID_ARGUMENT.
func InvalidArgumentErrorf(format string, args ...interface{}) error {
	return Newf(commonv1.Code_CODE_INVALID_ARGUMENT, format, args...)
}

// InvalidFieldValueError returns Error with commonV1.Code_CODE_GENERAL_ERROR.
func InvalidFieldValueError(args ...interface{}) error {
	return InvalidFieldValueErrorf(invalidFieldValueFormat, args...)
}

// InvalidFieldValueErrorf returns Error with commonV1.Code_CODE_GENERAL_ERROR.
func InvalidFieldValueErrorf(format string, args ...interface{}) error {
	return Newf(commonv1.Code_CODE_GENERAL_ERROR, format, args...)
}

// SQLError wraps error with code commonV1.Code_CODE_GENERAL_SQL_ERROR.
func SQLError(err error) error {
	return New(commonv1.Code_CODE_GENERAL_SQL_ERROR, err.Error())
}
