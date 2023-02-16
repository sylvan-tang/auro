// Code generated by protoc-gen-validate. DO NOT EDIT.
// source: echo/v1/greeting.proto

package echov1

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

// Validate checks the field values on Greeting with the rules defined in the
// proto definition for this message. If any rules are violated, the first
// error encountered is returned, or nil if there are no violations.
func (m *Greeting) Validate() error {
	return m.validate(false)
}

// ValidateAll checks the field values on Greeting with the rules defined in
// the proto definition for this message. If any rules are violated, the
// result is a list of violation errors wrapped in GreetingMultiError, or nil
// if none found.
func (m *Greeting) ValidateAll() error {
	return m.validate(true)
}

func (m *Greeting) validate(all bool) error {
	if m == nil {
		return nil
	}

	var errors []error

	// no validation rules for Id

	// no validation rules for CreateAt

	// no validation rules for UpdateAt

	// no validation rules for Greeted

	if all {
		switch v := interface{}(m.GetChat()).(type) {
		case interface{ ValidateAll() error }:
			if err := v.ValidateAll(); err != nil {
				errors = append(errors, GreetingValidationError{
					field:  "Chat",
					reason: "embedded message failed validation",
					cause:  err,
				})
			}
		case interface{ Validate() error }:
			if err := v.Validate(); err != nil {
				errors = append(errors, GreetingValidationError{
					field:  "Chat",
					reason: "embedded message failed validation",
					cause:  err,
				})
			}
		}
	} else if v, ok := interface{}(m.GetChat()).(interface{ Validate() error }); ok {
		if err := v.Validate(); err != nil {
			return GreetingValidationError{
				field:  "Chat",
				reason: "embedded message failed validation",
				cause:  err,
			}
		}
	}

	if len(errors) > 0 {
		return GreetingMultiError(errors)
	}
	return nil
}

// GreetingMultiError is an error wrapping multiple validation errors returned
// by Greeting.ValidateAll() if the designated constraints aren't met.
type GreetingMultiError []error

// Error returns a concatenation of all the error messages it wraps.
func (m GreetingMultiError) Error() string {
	var msgs []string
	for _, err := range m {
		msgs = append(msgs, err.Error())
	}
	return strings.Join(msgs, "; ")
}

// AllErrors returns a list of validation violation errors.
func (m GreetingMultiError) AllErrors() []error { return m }

// GreetingValidationError is the validation error returned by
// Greeting.Validate if the designated constraints aren't met.
type GreetingValidationError struct {
	field  string
	reason string
	cause  error
	key    bool
}

// Field function returns field value.
func (e GreetingValidationError) Field() string { return e.field }

// Reason function returns reason value.
func (e GreetingValidationError) Reason() string { return e.reason }

// Cause function returns cause value.
func (e GreetingValidationError) Cause() error { return e.cause }

// Key function returns key value.
func (e GreetingValidationError) Key() bool { return e.key }

// ErrorName returns error name.
func (e GreetingValidationError) ErrorName() string { return "GreetingValidationError" }

// Error satisfies the builtin error interface
func (e GreetingValidationError) Error() string {
	cause := ""
	if e.cause != nil {
		cause = fmt.Sprintf(" | caused by: %v", e.cause)
	}

	key := ""
	if e.key {
		key = "key for "
	}

	return fmt.Sprintf(
		"invalid %sGreeting.%s: %s%s",
		key,
		e.field,
		e.reason,
		cause)
}

var _ error = GreetingValidationError{}

var _ interface {
	Field() string
	Reason() string
	Key() bool
	Cause() error
	ErrorName() string
} = GreetingValidationError{}

// Validate checks the field values on GreetingOptionFilter with the rules
// defined in the proto definition for this message. If any rules are
// violated, the first error encountered is returned, or nil if there are no violations.
func (m *GreetingOptionFilter) Validate() error {
	return m.validate(false)
}

// ValidateAll checks the field values on GreetingOptionFilter with the rules
// defined in the proto definition for this message. If any rules are
// violated, the result is a list of violation errors wrapped in
// GreetingOptionFilterMultiError, or nil if none found.
func (m *GreetingOptionFilter) ValidateAll() error {
	return m.validate(true)
}

func (m *GreetingOptionFilter) validate(all bool) error {
	if m == nil {
		return nil
	}

	var errors []error

	if len(errors) > 0 {
		return GreetingOptionFilterMultiError(errors)
	}
	return nil
}

// GreetingOptionFilterMultiError is an error wrapping multiple validation
// errors returned by GreetingOptionFilter.ValidateAll() if the designated
// constraints aren't met.
type GreetingOptionFilterMultiError []error

// Error returns a concatenation of all the error messages it wraps.
func (m GreetingOptionFilterMultiError) Error() string {
	var msgs []string
	for _, err := range m {
		msgs = append(msgs, err.Error())
	}
	return strings.Join(msgs, "; ")
}

// AllErrors returns a list of validation violation errors.
func (m GreetingOptionFilterMultiError) AllErrors() []error { return m }

// GreetingOptionFilterValidationError is the validation error returned by
// GreetingOptionFilter.Validate if the designated constraints aren't met.
type GreetingOptionFilterValidationError struct {
	field  string
	reason string
	cause  error
	key    bool
}

// Field function returns field value.
func (e GreetingOptionFilterValidationError) Field() string { return e.field }

// Reason function returns reason value.
func (e GreetingOptionFilterValidationError) Reason() string { return e.reason }

// Cause function returns cause value.
func (e GreetingOptionFilterValidationError) Cause() error { return e.cause }

// Key function returns key value.
func (e GreetingOptionFilterValidationError) Key() bool { return e.key }

// ErrorName returns error name.
func (e GreetingOptionFilterValidationError) ErrorName() string {
	return "GreetingOptionFilterValidationError"
}

// Error satisfies the builtin error interface
func (e GreetingOptionFilterValidationError) Error() string {
	cause := ""
	if e.cause != nil {
		cause = fmt.Sprintf(" | caused by: %v", e.cause)
	}

	key := ""
	if e.key {
		key = "key for "
	}

	return fmt.Sprintf(
		"invalid %sGreetingOptionFilter.%s: %s%s",
		key,
		e.field,
		e.reason,
		cause)
}

var _ error = GreetingOptionFilterValidationError{}

var _ interface {
	Field() string
	Reason() string
	Key() bool
	Cause() error
	ErrorName() string
} = GreetingOptionFilterValidationError{}

// Validate checks the field values on ImportGreetingConfig with the rules
// defined in the proto definition for this message. If any rules are
// violated, the first error encountered is returned, or nil if there are no violations.
func (m *ImportGreetingConfig) Validate() error {
	return m.validate(false)
}

// ValidateAll checks the field values on ImportGreetingConfig with the rules
// defined in the proto definition for this message. If any rules are
// violated, the result is a list of violation errors wrapped in
// ImportGreetingConfigMultiError, or nil if none found.
func (m *ImportGreetingConfig) ValidateAll() error {
	return m.validate(true)
}

func (m *ImportGreetingConfig) validate(all bool) error {
	if m == nil {
		return nil
	}

	var errors []error

	// no validation rules for FilePath

	if len(errors) > 0 {
		return ImportGreetingConfigMultiError(errors)
	}
	return nil
}

// ImportGreetingConfigMultiError is an error wrapping multiple validation
// errors returned by ImportGreetingConfig.ValidateAll() if the designated
// constraints aren't met.
type ImportGreetingConfigMultiError []error

// Error returns a concatenation of all the error messages it wraps.
func (m ImportGreetingConfigMultiError) Error() string {
	var msgs []string
	for _, err := range m {
		msgs = append(msgs, err.Error())
	}
	return strings.Join(msgs, "; ")
}

// AllErrors returns a list of validation violation errors.
func (m ImportGreetingConfigMultiError) AllErrors() []error { return m }

// ImportGreetingConfigValidationError is the validation error returned by
// ImportGreetingConfig.Validate if the designated constraints aren't met.
type ImportGreetingConfigValidationError struct {
	field  string
	reason string
	cause  error
	key    bool
}

// Field function returns field value.
func (e ImportGreetingConfigValidationError) Field() string { return e.field }

// Reason function returns reason value.
func (e ImportGreetingConfigValidationError) Reason() string { return e.reason }

// Cause function returns cause value.
func (e ImportGreetingConfigValidationError) Cause() error { return e.cause }

// Key function returns key value.
func (e ImportGreetingConfigValidationError) Key() bool { return e.key }

// ErrorName returns error name.
func (e ImportGreetingConfigValidationError) ErrorName() string {
	return "ImportGreetingConfigValidationError"
}

// Error satisfies the builtin error interface
func (e ImportGreetingConfigValidationError) Error() string {
	cause := ""
	if e.cause != nil {
		cause = fmt.Sprintf(" | caused by: %v", e.cause)
	}

	key := ""
	if e.key {
		key = "key for "
	}

	return fmt.Sprintf(
		"invalid %sImportGreetingConfig.%s: %s%s",
		key,
		e.field,
		e.reason,
		cause)
}

var _ error = ImportGreetingConfigValidationError{}

var _ interface {
	Field() string
	Reason() string
	Key() bool
	Cause() error
	ErrorName() string
} = ImportGreetingConfigValidationError{}

// Validate checks the field values on ExportGreetingConfig with the rules
// defined in the proto definition for this message. If any rules are
// violated, the first error encountered is returned, or nil if there are no violations.
func (m *ExportGreetingConfig) Validate() error {
	return m.validate(false)
}

// ValidateAll checks the field values on ExportGreetingConfig with the rules
// defined in the proto definition for this message. If any rules are
// violated, the result is a list of violation errors wrapped in
// ExportGreetingConfigMultiError, or nil if none found.
func (m *ExportGreetingConfig) ValidateAll() error {
	return m.validate(true)
}

func (m *ExportGreetingConfig) validate(all bool) error {
	if m == nil {
		return nil
	}

	var errors []error

	// no validation rules for FilePath

	if len(errors) > 0 {
		return ExportGreetingConfigMultiError(errors)
	}
	return nil
}

// ExportGreetingConfigMultiError is an error wrapping multiple validation
// errors returned by ExportGreetingConfig.ValidateAll() if the designated
// constraints aren't met.
type ExportGreetingConfigMultiError []error

// Error returns a concatenation of all the error messages it wraps.
func (m ExportGreetingConfigMultiError) Error() string {
	var msgs []string
	for _, err := range m {
		msgs = append(msgs, err.Error())
	}
	return strings.Join(msgs, "; ")
}

// AllErrors returns a list of validation violation errors.
func (m ExportGreetingConfigMultiError) AllErrors() []error { return m }

// ExportGreetingConfigValidationError is the validation error returned by
// ExportGreetingConfig.Validate if the designated constraints aren't met.
type ExportGreetingConfigValidationError struct {
	field  string
	reason string
	cause  error
	key    bool
}

// Field function returns field value.
func (e ExportGreetingConfigValidationError) Field() string { return e.field }

// Reason function returns reason value.
func (e ExportGreetingConfigValidationError) Reason() string { return e.reason }

// Cause function returns cause value.
func (e ExportGreetingConfigValidationError) Cause() error { return e.cause }

// Key function returns key value.
func (e ExportGreetingConfigValidationError) Key() bool { return e.key }

// ErrorName returns error name.
func (e ExportGreetingConfigValidationError) ErrorName() string {
	return "ExportGreetingConfigValidationError"
}

// Error satisfies the builtin error interface
func (e ExportGreetingConfigValidationError) Error() string {
	cause := ""
	if e.cause != nil {
		cause = fmt.Sprintf(" | caused by: %v", e.cause)
	}

	key := ""
	if e.key {
		key = "key for "
	}

	return fmt.Sprintf(
		"invalid %sExportGreetingConfig.%s: %s%s",
		key,
		e.field,
		e.reason,
		cause)
}

var _ error = ExportGreetingConfigValidationError{}

var _ interface {
	Field() string
	Reason() string
	Key() bool
	Cause() error
	ErrorName() string
} = ExportGreetingConfigValidationError{}
