// Code generated by protoc-gen-validate. DO NOT EDIT.
// source: thirdparty/greeting/hello.proto

package greeting

import (
	"bytes"
	"errors"
	"fmt"
	"net"
	"net/mail"
	"net/url"
	"regexp"
	"strings"
	"time"
	"unicode/utf8"

	"google.golang.org/protobuf/types/known/anypb"
)

// ensure the imports are used
var (
	_ = bytes.MinRead
	_ = errors.New("")
	_ = fmt.Print
	_ = utf8.UTFMax
	_ = (*regexp.Regexp)(nil)
	_ = (*strings.Reader)(nil)
	_ = net.IPv4len
	_ = time.Duration(0)
	_ = (*url.URL)(nil)
	_ = (*mail.Address)(nil)
	_ = anypb.Any{}
)

// Validate checks the field values on Hello with the rules defined in the
// proto definition for this message. If any rules are violated, the first
// error encountered is returned, or nil if there are no violations.
func (m *Hello) Validate() error {
	return m.validate(false)
}

// ValidateAll checks the field values on Hello with the rules defined in the
// proto definition for this message. If any rules are violated, the result is
// a list of violation errors wrapped in HelloMultiError, or nil if none found.
func (m *Hello) ValidateAll() error {
	return m.validate(true)
}

func (m *Hello) validate(all bool) error {
	if m == nil {
		return nil
	}

	var errors []error

	// no validation rules for Name

	if len(errors) > 0 {
		return HelloMultiError(errors)
	}
	return nil
}

// HelloMultiError is an error wrapping multiple validation errors returned by
// Hello.ValidateAll() if the designated constraints aren't met.
type HelloMultiError []error

// Error returns a concatenation of all the error messages it wraps.
func (m HelloMultiError) Error() string {
	var msgs []string
	for _, err := range m {
		msgs = append(msgs, err.Error())
	}
	return strings.Join(msgs, "; ")
}

// AllErrors returns a list of validation violation errors.
func (m HelloMultiError) AllErrors() []error { return m }

// HelloValidationError is the validation error returned by Hello.Validate if
// the designated constraints aren't met.
type HelloValidationError struct {
	field  string
	reason string
	cause  error
	key    bool
}

// Field function returns field value.
func (e HelloValidationError) Field() string { return e.field }

// Reason function returns reason value.
func (e HelloValidationError) Reason() string { return e.reason }

// Cause function returns cause value.
func (e HelloValidationError) Cause() error { return e.cause }

// Key function returns key value.
func (e HelloValidationError) Key() bool { return e.key }

// ErrorName returns error name.
func (e HelloValidationError) ErrorName() string { return "HelloValidationError" }

// Error satisfies the builtin error interface
func (e HelloValidationError) Error() string {
	cause := ""
	if e.cause != nil {
		cause = fmt.Sprintf(" | caused by: %v", e.cause)
	}

	key := ""
	if e.key {
		key = "key for "
	}

	return fmt.Sprintf(
		"invalid %sHello.%s: %s%s",
		key,
		e.field,
		e.reason,
		cause)
}

var _ error = HelloValidationError{}

var _ interface {
	Field() string
	Reason() string
	Key() bool
	Cause() error
	ErrorName() string
} = HelloValidationError{}
