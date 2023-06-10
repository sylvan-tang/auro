SHELL := /bin/bash # Use bash syntax

mkfile_path := $(abspath $(lastword $(MAKEFILE_LIST)))
MODEL_PATH := $(patsubst %/,%,$(dir $(mkfile_path)))
ROOT := $(dir $(MODEL_PATH))
MODEL_NAME := $(shell cat $(MODEL_PATH)/name.txt)
DIFF_TARGETS=$(shell cat $(ROOT)git-diff.txt | grep $(MODEL_NAME))

DOCKER_NETWORK := --add-host host.docker.internal:host-gateway
# $(shell if [ `uname` = "Darwin" ]; then echo "--add-host host.docker.internal:host-gateway"; else echo "--network host"; fi)
GO_ENV := $(shell echo `which go`)
GO_CACHE_PATH := $(shell if [ ! -z "$(GO_ENV)" ]; then go env | grep GOMODCACHE | cut -d= -f2 | tr -d '"' | xargs dirname | xargs dirname | xargs echo; else echo $(ROOT)/.cache; fi)

DOCKER_REGISTRY := ghcr.io
DOCKER_OWNER := sylvan-tang
TAG := latest
PUSH_IMAGE := Y

.PHONY: init
## init command
init:
	@go mod tidy

SRC_CONF ?= mysql:root:my-secret-pw@tcp(localhost:3306)
DEST_CONF ?= mysql:root:my-secret-pw@tcp(host.docker.internal:3306)
# $(shell if [ `uname` = "Darwin" ]; then echo "mysql:root:my-secret-pw@tcp(host.docker.internal:3306)"; else echo "mysql:root:my-secret-pw@tcp(host:3306)"; fi)

.PHONE: replace-conf
## replace config.toml with SRC_CONF -> DEST_CONF
replace-conf:
	@printf "🔥 \033[1;32m===> Replace config...\033[0m\n"
	@find $(MODEL_PATH) -name '*config.toml' -type f -exec sed -i -e "s#$(SRC_CONF)#$(DEST_CONF)#g" {} +

.PHONE: revert-conf
## revert config.toml with DEST_CONF -> SRC_CONF
revert-conf:
	@printf "🔥 \033[1;32m===> Revert config...\033[0m\n"
	@find $(MODEL_PATH) -name '*config.toml' -type f -exec sed -i -e "s#$(DEST_CONF)#$(SRC_CONF)#g" {} +

.PHONY: gen-dao
## generate goframe database dao, eg.: use custom mysql link
## make gen-dao DEST_CONF="mysql:root:my-secret-pw@tcp(host.docker.internal:3306)"
## this command require mysql is up, make sure you had start mysql, check `make local-start-mysql` on ROOT
gen-dao:
	@printf "🔥 \033[1;32m===> Generating dao...\033[0m\n"
	@make replace-conf
	@gf gen dao
	@make revert-conf

GO_BUILD_TARGETS := $(wildcard cmd/*)
.PHONY: $(GO_BUILD_TARGETS)
$(GO_BUILD_TARGETS): 
	@printf "🐹 \033[1;32m===> Building $@ distribution binary...\033[0m\n"
	go build -o ./$(@:cmd/%=bin/%) ./$@

.PHONY: build
## build command 
build:
	@if [ -z "$(DIFF_TARGETS)" ]; then \
	echo $(MODEL_NAME) skip build!; \
	else \
	make init && \
	go generate $(MODEL_PATH)/pkg/generate.go && \
	make $(GO_BUILD_TARGETS) && \
	echo $(MODEL_NAME) build is done!; \
	fi

.PHONY: format
## format command 
format:
	@if [ -z "$(DIFF_TARGETS)" ]; then \
	echo $(MODEL_NAME) skip format!; \
	else \
	gofmt -w -s $(MODEL_PATH) && \
	goimports -local github.com/sylvan/auro -w $(MODEL_PATH) && \
	echo $(MODEL_NAME) format is done!; \
	fi

.PHONY: ut
## unit test command 
ut:
	@if [ -z "$(DIFF_TARGETS)" ]; then \
	echo $(MODEL_NAME) skip ut!; \
	else \
	make replace-conf && \
	go test -timeout 3600s \
		-failfast \
	    -coverpkg=$(shell go list ./pkg/... \
	        | grep -v -e '/dao/internal$$' -e '/model/internal$$' \
	        | tr '\n' ',' | sed 's/,$$//') \
	    -covermode=count \
	    -coverprofile=coverage.txt \
	    -v \
	    -parallel=8 \
	    $(shell go list ./pkg/... \
	        | grep -v -e '/dao/internal$$' -e '/model/internal$$') && \
	make revert-conf && \
	echo $(MODEL_NAME) ut is done!; \
	fi

.PHONY: it
## integration test command 
it:
	@if [ -z "$(DIFF_TARGETS)" ]; then \
	echo $(MODEL_NAME) skip it!; \
	else \
	make replace-conf && \
	go test -tags=integration \
		-failfast \
	    -v \
	    -timeout 14400s \
	    $(MODEL_PATH)/integration_test/... && \
	make revert-conf && \
	echo $(MODEL_NAME) integration test is done!; \
	fi

SERVER_IMAGE_TARGETS := $(patsubst %, %-image,  $(GO_BUILD_TARGETS))

.PHONY: go-build
## build image for server
go-build:
	@printf "🐋 \033[1;32m===> Build $(MODEL_NAME) image...\033[0m\n"
	@if [ -z "$(DIFF_TARGETS)" ]; then \
	echo $(MODEL_NAME) skip go build!; \
	else \
	cd $(ROOT) && $(ROOT)/bin/build-image.sh $(MODEL_PATH)/Dockerfile $(MODEL_NAME); \
	fi;

.PHONY: package
## pacakge command
package:
	if [[ -z "$(DIFF_TARGETS)" ]]; then \
	echo $(MODEL_NAME) skip package!; \
	else \
	make go-build && \
	echo $(MODEL_NAME) package is done!; \
	fi

.PHONY: publish
## publish command
publish:
	@echo $(MODEL_NAME) publish is done.

.PHONY: upgrade
## upgrade command
upgrade:
	@echo $(MODEL_NAME) upgrade is done.

.PHONY: deploy
## deploy command 
deploy:
	@if [ -z "$(DIFF_TARGETS)" ]; then \
	echo $(MODEL_NAME) skip deploy!; \
	else \
	make package publish upgrade && \
	echo $(MODEL_NAME) deploy is done!; \
	fi


###############################################################################
# docker commands                                                             #
###############################################################################
TARGETS := build format ut it gen-dao
DOCKER_PHONIES := $(patsubst %, %-docker, $(TARGETS))

# run with: `make {one name in TARGETS}-docker`
# eg: make build-docker
.PHONY: $(DOCKER_PHONIES)
## run make commands in docker
$(DOCKER_PHONIES): stop-all
	@printf "🐋 \033[1;32m===> Running $(@:%-docker=%) in docker...\033[0m\n"
	make -C $(ROOT) $(MODEL_NAME)-env-all-image
	docker run $(DOCKER_NETWORK) --rm \
	    --volume $(ROOT):/auro \
	    --volume $(GO_CACHE_PATH)/pkg:/go/pkg \
	    $(DOCKER_REGISTRY)/$(DOCKER_OWNER)/$(MODEL_NAME)-env:$(TAG) make -C /auro/$(MODEL_NAME) $(@:%-docker=%) DEST_CONF="$(DEST_CONF)"

.PHONY: debug
## run make commands in docker for debug
debug:
	@printf "🐋 \033[1;32m===> Bash into $(@:%-docker=%) docker...\033[0m\n"
	docker run $(DOCKER_NETWORK) -it --rm \
	    --volume $(ROOT):/auro \
	    --volume $(GO_CACHE_PATH)/pkg:/go/pkg \
	    $(DOCKER_REGISTRY)/$(DOCKER_OWNER)/$(MODEL_NAME)-env:$(TAG) bash

START_SERVER_TARGETS := $(patsubst %, %-start,  $(GO_BUILD_TARGETS))
STOP_SERVER_TARGETS := $(patsubst %, %-stop,  $(GO_BUILD_TARGETS))

.PHONY: $(STOP_SERVER_TARGETS)
## stop local server in docker
$(STOP_SERVER_TARGETS):
	@printf "🐋 \033[1;32m===> Remove $(@:cmd/%-stop=%) server...\033[0m\n"
	$(eval sevice_exists := $(shell docker service ls -f name=$(@:cmd/%-stop=%) -q))
	@if [ ! -z "$(sevice_exists)" ]; then \
	docker service rm $(@:cmd/%-stop=%) && \
	sleep 1; \
	fi

.PHONY: $(START_SERVER_TARGETS)
## start server in docker,  add {server-name} into you /etc/hosts, eg:
## 127.0.0.1       localhost echo-server
$(START_SERVER_TARGETS):
	$(eval SERVER_NAME := $(@:cmd/%-start=%))
	@make cmd/$(SERVER_NAME)-stop
	@printf "🐋 \033[1;32m===> Start $(@:cmd/%-start=%) server...\033[0m\n"
	$(eval DOCKER_PUBLISH := $(shell cat $(MODEL_PATH)/server.publish.txt| grep $(@:cmd/%-start=%) | cut -d# -f 2))
	docker service create --name $(SERVER_NAME) \
		$(DOCKER_PUBLISH) \
		$(DOCKER_REGISTRY)/$(DOCKER_OWNER)/$(MODEL_NAME):$(TAG) /app/$(SERVER_NAME) && sleep 10

.PHONY: start-all
start-all: go-build $(START_SERVER_TARGETS)

.PHONY: stop-all
stop-all: $(STOP_SERVER_TARGETS)
