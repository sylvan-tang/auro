// Code generated by protoc-gen-validate. DO NOT EDIT.
// source: thirdparty/greeting/chat.proto

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

// Validate checks the field values on Chat with the rules defined in the proto
// definition for this message. If any rules are violated, the first error
// encountered is returned, or nil if there are no violations.
func (m *Chat) Validate() error {
	return m.validate(false)
}

// ValidateAll checks the field values on Chat with the rules defined in the
// proto definition for this message. If any rules are violated, the result is
// a list of violation errors wrapped in ChatMultiError, or nil if none found.
func (m *Chat) ValidateAll() error {
	return m.validate(true)
}

func (m *Chat) validate(all bool) error {
	if m == nil {
		return nil
	}

	var errors []error

	if all {
		switch v := interface{}(m.GetHello()).(type) {
		case interface{ ValidateAll() error }:
			if err := v.ValidateAll(); err != nil {
				errors = append(errors, ChatValidationError{
					field:  "Hello",
					reason: "embedded message failed validation",
					cause:  err,
				})
			}
		case interface{ Validate() error }:
			if err := v.Validate(); err != nil {
				errors = append(errors, ChatValidationError{
					field:  "Hello",
					reason: "embedded message failed validation",
					cause:  err,
				})
			}
		}
	} else if v, ok := interface{}(m.GetHello()).(interface{ Validate() error }); ok {
		if err := v.Validate(); err != nil {
			return ChatValidationError{
				field:  "Hello",
				reason: "embedded message failed validation",
				cause:  err,
			}
		}
	}

	if len(errors) > 0 {
		return ChatMultiError(errors)
	}
	return nil
}

// ChatMultiError is an error wrapping multiple validation errors returned by
// Chat.ValidateAll() if the designated constraints aren't met.
type ChatMultiError []error

// Error returns a concatenation of all the error messages it wraps.
func (m ChatMultiError) Error() string {
	var msgs []string
	for _, err := range m {
		msgs = append(msgs, err.Error())
	}
	return strings.Join(msgs, "; ")
}

// AllErrors returns a list of validation violation errors.
func (m ChatMultiError) AllErrors() []error { return m }

// ChatValidationError is the validation error returned by Chat.Validate if the
// designated constraints aren't met.
type ChatValidationError struct {
	field  string
	reason string
	cause  error
	key    bool
}

// Field function returns field value.
func (e ChatValidationError) Field() string { return e.field }

// Reason function returns reason value.
func (e ChatValidationError) Reason() string { return e.reason }

// Cause function returns cause value.
func (e ChatValidationError) Cause() error { return e.cause }

// Key function returns key value.
func (e ChatValidationError) Key() bool { return e.key }

// ErrorName returns error name.
func (e ChatValidationError) ErrorName() string { return "ChatValidationError" }

// Error satisfies the builtin error interface
func (e ChatValidationError) Error() string {
	cause := ""
	if e.cause != nil {
		cause = fmt.Sprintf(" | caused by: %v", e.cause)
	}

	key := ""
	if e.key {
		key = "key for "
	}

	return fmt.Sprintf(
		"invalid %sChat.%s: %s%s",
		key,
		e.field,
		e.reason,
		cause)
}

var _ error = ChatValidationError{}

var _ interface {
	Field() string
	Reason() string
	Key() bool
	Cause() error
	ErrorName() string
} = ChatValidationError{}
