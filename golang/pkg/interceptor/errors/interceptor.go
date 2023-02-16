package errors

import (
	"context"
	"database/sql"
	"fmt"
	"strings"

	"github.com/gogf/gf/frame/g"
	spb "google.golang.org/genproto/googleapis/rpc/status"
	"google.golang.org/grpc"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/status"
	"google.golang.org/protobuf/encoding/protojson"
	"google.golang.org/protobuf/proto"

	commonv1 "github.com/sylvan/auro/apis/common/v1"
	"github.com/sylvan/auro/pkg/lib/grpc/errors"
	"github.com/sylvan/auro/toolkits/pkg/lib/grpc/interceptor"
)

func init() {
	interceptor.RegisterUnary("errors", UnaryServerInterceptor())
}

func protoLog(m proto.Message) string {
	return protojson.MarshalOptions{}.Format(m)
}

func translateWellKnownErrorMessage(err string) *spb.Status {
	switch {
	// We don't have general pattern for any SQL error, just try our best.
	case strings.Contains(err, sql.ErrNoRows.Error()):
		return &spb.Status{
			Code:    int32(commonv1.Code_CODE_SQL_NO_ROWS),
			Message: errors.SQLErrorMessage,
		}
	case strings.Contains(strings.ToLower(err), "sql"):
		return &spb.Status{
			Code:    int32(commonv1.Code_CODE_GENERAL_SQL_ERROR),
			Message: errors.SQLErrorMessage,
		}
	default:
		return &spb.Status{
			Code:    int32(commonv1.Code_CODE_GENERAL_ERROR),
			Message: fmt.Sprintf("%s: %s", errors.Default, err),
		}
	}
}

func translateWellKnownError(err error) *spb.Status {
	switch {
	case err == sql.ErrNoRows:
		return &spb.Status{
			Code:    int32(commonv1.Code_CODE_SQL_NO_ROWS),
			Message: errors.SQLErrorMessage,
		}
	case err == sql.ErrTxDone, err == sql.ErrConnDone:
		return &spb.Status{
			Code:    int32(commonv1.Code_CODE_GENERAL_SQL_ERROR),
			Message: errors.SQLErrorMessage,
		}
	default:
		return translateWellKnownErrorMessage(err.Error())
	}
}

func translateError(err error) *spb.Status {
	// Bypass custom grpc error.
	if st, ok := err.(*errors.Error); ok {
		// Hide detailed SQL error.
		if st.Code() == int32(commonv1.Code_CODE_GENERAL_SQL_ERROR) ||
			st.Code() == int32(commonv1.Code_CODE_SQL_NO_ROWS) {
			g.Log().Debugf("error interceptor hides detailed sql error: %s", st.String())
			return &spb.Status{
				Code:    st.Code(),
				Message: errors.SQLErrorMessage,
			}
		}
		g.Log().Debugf("error interceptor bypasses custom error: %s", st.String())
		return &spb.Status{
			Code:    st.Code(),
			Message: st.Error(),
		}
	}

	// Bypass grpc produced status, except code.Unknown, which maps to 500 Internal error.
	if statusErr, ok := status.FromError(err); ok {
		if statusErr.Code() == codes.Unknown {
			// Error type info is hidden by grpc status.
			// Try to translate will-known error by message string.
			if trResp := translateWellKnownErrorMessage(statusErr.Message()); trResp != nil {
				g.Log().Debugf("error interceptor translates app status: from=%s to=%s", protoLog(statusErr.Proto()), protoLog(trResp))
				return trResp
			}
		}
		return nil // bypass other defined codes or unidentified errors, which should be aware of.
	}

	// Try to translate will-known error.
	if trResp := translateWellKnownError(err); trResp != nil {
		g.Log().Debugf("error interceptor translates app status: from=\"%+v\" to=%s", err, protoLog(trResp))
		return trResp
	}

	// Default: return directly if cannot translate. Unreachable for current strategy.
	return nil
}

// UnaryServerInterceptor returns a grpc.UnaryServerInterceptor for error manipulation.
func UnaryServerInterceptor() grpc.UnaryServerInterceptor {
	return func(ctx context.Context, req interface{}, info *grpc.UnaryServerInfo, handler grpc.UnaryHandler) (resp interface{}, err error) {
		resp, err = handler(ctx, req)
		// Bypass success app status.
		if err == nil {
			return resp, nil
		}
		if trStatus := translateError(err); trStatus != nil {
			return trStatus, nil
		}
		return resp, err
	}
}
