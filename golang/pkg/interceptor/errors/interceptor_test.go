package errors

import (
	"context"
	"database/sql"
	"errors"
	"fmt"
	"math/rand"
	"net"
	"testing"
	"time"

	"github.com/gogf/gf/database/gdb"
	"github.com/gogf/gf/frame/g"
	"github.com/leventeliu/goproc"
	. "github.com/smartystreets/goconvey/convey"
	"google.golang.org/grpc"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/credentials/insecure"
	"google.golang.org/grpc/status"
	"google.golang.org/protobuf/proto"

	commonv1 "github.com/sylvan/auro/apis/common/v1"
	echov1 "github.com/sylvan/auro/apis/echo/v1"
	grpcerrors "github.com/sylvan/auro/pkg/lib/grpc/errors"
)

func init() {
	rand.Seed(time.Now().UnixNano())
}

// toy server for testing.
type toy struct {
	mockGetGreetingResp  *echov1.GetGreetingResponse
	mockGetGreetingError error

	echov1.UnimplementedGreetingServiceServer
}

func (t *toy) GetGreeting(_ context.Context, _ *echov1.GetGreetingRequest) (*echov1.GetGreetingResponse, error) {
	return t.mockGetGreetingResp, t.mockGetGreetingError
}

func runToy(ctx context.Context) {
	srv := ctx.Value("server")
	rpc := grpc.NewServer(
		grpc.Creds(insecure.NewCredentials()),
		grpc.ChainUnaryInterceptor(UnaryServerInterceptor()),
	)
	echov1.RegisterGreetingServiceServer(rpc, srv.(echov1.GreetingServiceServer))

	addr := ctx.Value("address").(string)
	listener, err := net.Listen("tcp", addr)
	if err != nil {
		g.Log().Fatalf("failed to listen at: %s, err=%s", addr, err)
	}

	defer goproc.NewController(ctx, "server.rpc").Go(func(ctx context.Context) {
		<-ctx.Done()
		g.Log().Infof("get context notify: name=server.grpc error=%s", ctx.Err())
		rpc.GracefulStop()
	}).Wait()

	err = rpc.Serve(listener)
	if err != nil {
		g.Log().Fatalf("grpc server exiting: error=%s", err)
	}
}

func newTestingClient(addr string) echov1.GreetingServiceClient {
	conn, err := grpc.Dial(addr, grpc.WithBlock(), grpc.WithTransportCredentials(insecure.NewCredentials()))
	if err != nil {
		g.Log().Fatal(err)
	}
	return echov1.NewGreetingServiceClient(conn)
}

func TestInterceptor(t *testing.T) {
	var testCases = []struct {
		resp          *echov1.GetGreetingResponse
		err           error
		expectErr     error
		expectCode    int32
		expectMessage string
	}{{
		resp:          &echov1.GetGreetingResponse{},
		expectCode:    0,
		expectMessage: "",
	}, {
		err:           sql.ErrNoRows,
		expectCode:    int32(commonv1.Code_CODE_SQL_NO_ROWS),
		expectMessage: grpcerrors.SQLErrorMessage,
	}, {
		err:           gdb.ErrNoRows,
		expectCode:    int32(commonv1.Code_CODE_SQL_NO_ROWS),
		expectMessage: grpcerrors.SQLErrorMessage,
	}, {
		err:           sql.ErrTxDone,
		expectCode:    int32(commonv1.Code_CODE_GENERAL_SQL_ERROR),
		expectMessage: grpcerrors.SQLErrorMessage,
	}, {
		err:           sql.ErrConnDone,
		expectCode:    int32(commonv1.Code_CODE_GENERAL_SQL_ERROR),
		expectMessage: grpcerrors.SQLErrorMessage,
	}, {
		err:           errors.New("ERROR 1045 (28000): sql syntax error..."),
		expectCode:    int32(commonv1.Code_CODE_GENERAL_SQL_ERROR),
		expectMessage: grpcerrors.SQLErrorMessage,
	}, {
		err:           grpcerrors.SQLError(errors.New("ERROR 1045 (28000): Access denied for user 'foo'@'::1' (using password: NO)")),
		expectCode:    int32(commonv1.Code_CODE_GENERAL_SQL_ERROR),
		expectMessage: grpcerrors.SQLErrorMessage,
	}, {
		err:           grpcerrors.GeneralError("custom error"),
		expectCode:    int32(commonv1.Code_CODE_GENERAL_ERROR),
		expectMessage: "custom error",
	}, {
		err:           grpcerrors.NotFoundError("not found"),
		expectCode:    int32(commonv1.Code_CODE_NOT_FOUND),
		expectMessage: "not found",
	}, {
		err:           errors.New("no translate"),
		expectCode:    int32(commonv1.Code_CODE_GENERAL_ERROR),
		expectMessage: fmt.Sprintf("%s: no translate", grpcerrors.Default),
	}, {
		err:           status.Error(codes.Unknown, sql.ErrNoRows.Error()),
		expectCode:    int32(commonv1.Code_CODE_SQL_NO_ROWS),
		expectMessage: grpcerrors.SQLErrorMessage,
	}, {
		err:           status.Error(codes.Unknown, "no translate"),
		expectCode:    int32(commonv1.Code_CODE_GENERAL_ERROR),
		expectMessage: fmt.Sprintf("%s: no translate", grpcerrors.Default),
	}, {
		err:       status.Error(codes.Internal, "serious error"),
		expectErr: status.Error(codes.Internal, "serious error"),
	}}

	Convey("Testing interceptor", t, func(c C) {
		for _, tc := range testCases {
			func(c C) {
				ctrl := goproc.NewController(context.Background(), "testing")
				defer ctrl.Shutdown()

				address := fmt.Sprintf("localhost:%d", 10000+rand.Intn(30000))
				ctrl.WithValue("server", &toy{
					mockGetGreetingResp:  tc.resp,
					mockGetGreetingError: tc.err,
				}).WithValue("address", address).Go(runToy)

				client := newTestingClient(address)
				resp, err := client.GetGreeting(context.Background(), &echov1.GetGreetingRequest{})

				if err != nil {
					if st, ok := status.FromError(err); ok {
						expectSt, ok2 := status.FromError(tc.expectErr)
						c.So(ok2, ShouldBeTrue)
						c.So(proto.Equal(st.Proto(), expectSt.Proto()), ShouldBeTrue)
					} else {
						c.So(err, ShouldResemble, tc.expectErr)
					}
				} else {
					So(resp, ShouldNotBeNil)
					So(resp.Code, ShouldEqual, tc.expectCode)
					So(resp.Message, ShouldEqual, tc.expectMessage)
				}
			}(c)
		}
	})
}
