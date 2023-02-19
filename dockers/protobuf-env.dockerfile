FROM golang:1.18.2 AS go-builder

WORKDIR /opt
RUN go install github.com/envoyproxy/protoc-gen-validate@v0.6.2-0.20210610191444-4f41f10dde19
RUN go install github.com/golang/mock/mockgen@v1.6.0
RUN go install github.com/grpc-ecosystem/grpc-gateway/protoc-gen-grpc-gateway@v1.16.0
RUN go install github.com/grpc-ecosystem/grpc-gateway/protoc-gen-swagger@v1.16.0
RUN go install google.golang.org/grpc/cmd/protoc-gen-go-grpc@v1.1.0
RUN go install google.golang.org/protobuf/cmd/protoc-gen-go@v1.27.1
RUN go install golang.org/x/tools/cmd/goimports@latest

FROM debian:buster AS bazel-builder

WORKDIR /opt
RUN apt-get update && apt-get install -y apt-transport-https autoconf build-essential curl git gnupg software-properties-common \
    && curl -fsSL https://bazel.build/bazel-release.pub.gpg | gpg --dearmor >bazel.gpg \
    && mv bazel.gpg /etc/apt/trusted.gpg.d/ \
    && echo "deb [arch=amd64] https://storage.googleapis.com/bazel-apt stable jdk1.8" | tee /etc/apt/sources.list.d/bazel.list \
    && apt-get update && apt-get install -y bazel
RUN git clone --recurse-submodules https://github.com/grpc/grpc.git

WORKDIR /opt/grpc
RUN git checkout v1.46.0
RUN git submodule update --init --recursive
RUN CC=gcc bazel build //src/compiler:grpc_python_plugin

FROM python:3.8-buster
RUN apt-get update && apt-get install -y autoconf build-essential software-properties-common git jq unzip wget
RUN wget https://bootstrap.pypa.io/get-pip.py && python get-pip.py && rm -fv get-pip.py

COPY --from=go-builder /go/bin/* /usr/bin/

COPY --from=bazel-builder /opt/grpc/bazel-bin/src/compiler/grpc_python_plugin /usr/bin/

### Install releases.
RUN wget -O/usr/bin/buf -cqt5 https://github.com/bufbuild/buf/releases/download/v1.4.0/buf-Linux-x86_64 \
    && chmod +x /usr/bin/buf
RUN wget -O/usr/bin/protoc-gen-buf-breaking -cqt5 https://github.com/bufbuild/buf/releases/download/v1.4.0/protoc-gen-buf-breaking-Linux-x86_64 \
    && chmod +x /usr/bin/protoc-gen-buf-breaking
RUN wget -O/usr/bin/protoc-gen-buf-lint -cqt5 https://github.com/bufbuild/buf/releases/download/v1.4.0/protoc-gen-buf-lint-Linux-x86_64 \
    && chmod +x /usr/bin/protoc-gen-buf-lint
RUN wget -O/usr/bin/swagger -cqt5 https://github.com/go-swagger/go-swagger/releases/download/v0.29.0/swagger_linux_amd64 \
    && chmod +x /usr/bin/swagger

### Install protoc 3.20.1.
WORKDIR /opt
RUN wget -O- -cqt5 https://ftp.gnu.org/gnu/automake/automake-1.16.tar.gz | tar -vxz \
    && cd automake-1.16 && ./configure && make && make install && ldconfig \
    && cd .. && rm -rf automake-1.16

WORKDIR /opt
RUN wget -O- -cqt5 https://github.com/protocolbuffers/protobuf/releases/download/v3.20.1/protobuf-all-3.20.1.tar.gz | tar -vxz \
    && cd protobuf-3.20.1 && ./configure && make && make install && ldconfig \
    && cd .. && rm -rf protobuf-3.20.1

### Install protoc plugins.
WORKDIR /opt
COPY dockers/protobuf-env/requirements.txt .
RUN python -m pip install --disable-pip-version-check -r requirements.txt
# NOTE: https://github.com/ValvePython/csgo/issues/8
RUN pip install --no-binary=protobuf protobuf==3.20.1

### Install buf cache locally.
WORKDIR /opt
RUN mkdir -pv /.cache/buf && chmod -R 777 /.cache

### Install Go.
WORKDIR /usr/local
RUN wget -O- -cqt5 https://go.dev/dl/go1.18.2.linux-amd64.tar.gz | tar -xz
ENV GOPATH=/go
ENV PATH=/go/bin:/usr/local/go/bin:$PATH

# install rust and cargo
WORKDIR /tmp
RUN curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs > /tmp/rustup.sh \
    && chmod +x /tmp/rustup.sh \
    && /tmp/rustup.sh -y
ENV PATH=/root/.cargo/bin:$PATH

WORKDIR /auro
