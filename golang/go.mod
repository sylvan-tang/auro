module github.com/sylvan/auro

go 1.18

require (
	github.com/gogf/gf v1.16.3
	github.com/golang/protobuf v1.5.2
	github.com/grpc-ecosystem/grpc-gateway v1.16.0
	github.com/jasonlvhit/gocron v0.0.1
	github.com/leventeliu/goproc v1.0.2-0.20210901044106-d6f0d77b9ce6
	github.com/smartystreets/goconvey v1.7.2
	github.com/sylvan/auro/toolkits v0.0.0-00010101000000-000000000000
	google.golang.org/genproto v0.0.0-20221114212237-e4508ebdbee1
	google.golang.org/grpc v1.50.1
	google.golang.org/protobuf v1.28.1
	sigs.k8s.io/yaml v1.3.0
)

require (
	github.com/BurntSushi/toml v1.2.0 // indirect
	github.com/clbanning/mxj v1.8.5-0.20200714211355-ff02cfb8ea28 // indirect
	github.com/dustin/go-humanize v1.0.0 // indirect
	github.com/fsnotify/fsnotify v1.5.1 // indirect
	github.com/gogf/mysql v1.6.1-0.20210603073548-16164ae25579 // indirect
	github.com/gomodule/redigo v2.0.0+incompatible // indirect
	github.com/gopherjs/gopherjs v0.0.0-20220104163920-15ed2e8cf2bd // indirect
	github.com/gorilla/websocket v1.5.0 // indirect
	github.com/grokify/html-strip-tags-go v0.0.0-20190921062105-daaa06bf1aaf // indirect
	github.com/jessevdk/go-flags v1.5.0 // indirect
	github.com/jtolds/gls v4.20.0+incompatible // indirect
	github.com/kr/text v0.2.0 // indirect
	github.com/mattn/go-runewidth v0.0.14 // indirect
	github.com/olekukonko/tablewriter v0.0.5 // indirect
	github.com/rivo/uniseg v0.4.2 // indirect
	github.com/smartystreets/assertions v1.2.0 // indirect
	go.opentelemetry.io/otel v0.20.0 // indirect
	go.opentelemetry.io/otel/metric v0.20.0 // indirect
	go.opentelemetry.io/otel/trace v0.20.0 // indirect
	golang.org/x/net v0.2.0 // indirect
	golang.org/x/sys v0.2.0 // indirect
	golang.org/x/text v0.4.0 // indirect
	gopkg.in/yaml.v2 v2.4.0 // indirect
	gopkg.in/yaml.v3 v3.0.1 // indirect
)

replace (
	github.com/kubeflow/pipelines => github.com/kubeflow/pipelines v0.0.0-20220713181733-f60440623888
	github.com/sylvan/auro/protobuf => ../protobuf
	github.com/sylvan/auro/toolkits => ../toolkits
	google.golang.org/grpc => google.golang.org/grpc v1.44.0
	google.golang.org/protobuf => google.golang.org/protobuf v1.27.1
	k8s.io/kubernetes => k8s.io/kubernetes v1.11.1
)
