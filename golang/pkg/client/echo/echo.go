package echo

import (
	"context"
	"sync"

	"github.com/gogf/gf/frame/g"
	"google.golang.org/grpc"

	echov1 "github.com/sylvan/auro/apis/echo/v1"
	"github.com/sylvan/auro/pkg/lib/tool"
	customgrpc "github.com/sylvan/auro/toolkits/pkg/lib/grpc"
)

var (
	client *Client
	once   sync.Once
)

func GetInstance(ctx context.Context) *Client {
	var err error
	once.Do(func() {
		client, err = New(ctx)
	})
	if err != nil {
		panic(err)
	}
	return client
}

// Client is a EchoServiceClient.
type Client struct {
	echov1.GreetingServiceClient
}

// New creates a new Client.
func New(ctx context.Context) (*Client, error) {
	return NewWithOption(ctx, "")
}

// NewWithOption creates a new Client with options.
func NewWithOption(ctx context.Context, server string) (*Client, error) {
	if len(server) == 0 {
		serverConfig := &customgrpc.GatewayServerConfig{}
		tool.ReadConfig("server.gateway.echo", serverConfig)
		server = serverConfig.GrpcAddress()
	}
	g.Log().Infof("echo-server: %s", server)
	conn, err := customgrpc.New(ctx, server)
	if err != nil {
		return nil, err
	}
	return NewClient(conn), nil
}

// NewClient create new client using conn.
func NewClient(conn *grpc.ClientConn) *Client {
	return &Client{echov1.NewGreetingServiceClient(conn)}
}
