package grpc

import (
	"context"

	"github.com/gogf/gf/frame/g"
	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials/insecure"
)

func New(ctx context.Context, server string) (*grpc.ClientConn, error) {
	receiveMsgSize, sendMsgSize := GetReceiveAndSendMsgSize()
	return grpc.DialContext(ctx, server,
		grpc.WithTransportCredentials(insecure.NewCredentials()),
		grpc.WithDefaultCallOptions(
			grpc.MaxCallRecvMsgSize(receiveMsgSize),
			grpc.MaxCallSendMsgSize(sendMsgSize),
		),
	)
}

func NewConn(addr string) *grpc.ClientConn {
	conn, err := grpc.Dial(addr, grpc.WithTransportCredentials(insecure.NewCredentials()))
	PanicWhenError(err)
	return conn
}

func NewServerConn(serverConfig *ServerConfig) *grpc.ClientConn {
	g.Log().Infof("Start server: %s", serverConfig.Address())
	conn, err := grpc.Dial(serverConfig.Address(), grpc.WithBlock(), grpc.WithTransportCredentials(insecure.NewCredentials()))
	PanicWhenError(err)
	return conn
}
