# Build controller in a Go builder container
FROM golang:1.18.2 as go-builder

# change timezone in docker with GMT+8.
# to make sure the server start by this image will behave with same in your machine.
RUN ln -sf /usr/share/zoneinfo/Asia/Hong_Kong /etc/localtime

WORKDIR /work

RUN wget -O gf https://github.com/gogf/gf-cli/releases/download/v1.16.2/gf_linux_amd64 && chmod +x gf && ./gf install

WORKDIR /opt
RUN go install golang.org/x/tools/cmd/goimports@latest
RUN apt-get update && apt-get install -y apt-transport-https autoconf build-essential curl git gnupg software-properties-common && \
    curl -fsSL https://bazel.build/bazel-release.pub.gpg | gpg --dearmor >bazel.gpg && \
    mv bazel.gpg /etc/apt/trusted.gpg.d/ && \
    echo "deb [arch=amd64] https://storage.googleapis.com/bazel-apt stable jdk1.8" | tee /etc/apt/sources.list.d/bazel.list && \
    apt-get update && apt-get install -y bazel
RUN apt-get install gcc g++

WORKDIR /tmp/go
RUN go env -w GONOPROXY=http://goproxy.cn && \
    go env -w GONOSUMDB=off && \
    go env -w GOPRIVATE="" && \
    go env -w GOPROXY=http://goproxy.cn,direct && \
    go env -w GOSUMDB=off && \
    go env -w GOTMPDIR=/tmp/go

ENV PATH="/usr/local/go/bin:/go/bin:$PATH"

WORKDIR /auro
