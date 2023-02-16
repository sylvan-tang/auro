# Build server in a Go builder container
FROM ghcr.io/sylvan-tang/golang-env:latest

WORKDIR /auro
COPY . .

ARG SERVER
ARG DEST_CONF
# Get dependencies - will also be cached if we won't change go.mod/go.sum
WORKDIR /auro/golang
RUN make replace-conf DEST_CONF=${DEST_CONF}
RUN --mount=type=cache,target=/go --mount=type=cache,target=/root/.cache/go-build \
    GOOS=linux GOARCH=amd64 ./build.sh

WORKDIR /app/config
RUN cp /auro/golang/config/config.toml /app/config/
WORKDIR /app
RUN chmod +x /auro/golang/bin/*
RUN cp /auro/golang/bin/* /app/
