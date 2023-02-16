SHELL := /bin/bash # Use bash syntax

mkfile_path := $(abspath $(lastword $(MAKEFILE_LIST)))
MODEL_PATH := $(patsubst %/,%,$(dir $(mkfile_path)))
ROOT := $(dir $(MODEL_PATH))
MODEL_NAME := $(shell cat $(MODEL_PATH)/name.txt)
DIFF_TARGETS=$(shell cat $(ROOT)git-diff.txt | grep $(MODEL_NAME))

PYPKG_PATH := $(ROOT)python/auro

DOCKER_NETWORK := --add-host host.docker.internal:host-gateway
GO_ENV := $(shell echo `which go`)
GO_CACHE_PATH := $(shell if [ ! -z "$(GO_ENV)" ]; then go env | grep GOMODCACHE | cut -d= -f2 | tr -d '"' | xargs dirname | xargs dirname | xargs echo; else echo $(ROOT)/.cache; fi)

DOCKER_REGISTRY := ghcr.io
DOCKER_OWNER := sylvan-tang
TAG := latest

.PHONY: init
## init command
init:
	@go mod tidy

.PHONY: clean
## clean go generated files
clean:
	@printf "🐋 \033[1;32m===> Cleaning generated files...\033[0m\n"
	rm -rf $(MODEL_PATH)/apis-doc $(MODEL_PATH)/go $(PYPKG_PATH)/apis
	
.PHONY: generate
## go genrate proto
generate: init
	@printf "🐋 \033[1;32m===> Genarete proto services files...\033[0m\n"
	@cd $(MODEL_PATH) && go generate apis/generate.go

.PHONY: lint-apis
## check proto apis before generate.
lint-apis: init
	@printf "🐋 \033[1;32m===> Linting proto files...\033[0m\n"
	buf format -w
	buf lint

.PHONY: gen-apis
## generate python and golang apis.
gen-apis: init
	@printf "🐋 \033[1;32m===> Linting proto files...\033[0m\n"
	buf generate -v -o ./
	touch $(PYPKG_PATH)/apis/__init__.py
	find $(PYPKG_PATH)/apis -mindepth 1 -type d -exec touch {}/__init__.py {}/py.typed \;
	find $(MODEL_PATH)/apis/thirdparty -maxdepth 1 -mindepth 1 -type d  -print | xargs -I '{}' basename '{}' | grep -v greeting | xargs  -I '{}' rm -rf $(MODEL_PATH)/go/thirdparty/'{}'
	@bash $(MODEL_PATH)/bin/gen-python.sh $(PYPKG_PATH)

.PHONY: build
## build command 
build:
	@if [ -z "$(DIFF_TARGETS)" ]; then \
	echo $(MODEL_NAME) skip build; \
	else \
	make clean generate lint-apis gen-apis && \
	echo $(MODEL_NAME) build is done; \
	fi

.PHONY: format
## format command 
format:
	@if [ -z "$(DIFF_TARGETS)" ]; then \
	echo $(MODEL_NAME) skip format!; \
	else \
	make init && \
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
	echo $(MODEL_NAME) ut is done!; \
	fi

.PHONY: it
## integration test command 
it:
	@if [ -z "$(DIFF_TARGETS)" ]; then \
	echo $(MODEL_NAME) skip it!; \
	else \
	echo $(MODEL_NAME) it is done!; \
	fi

###############################################################################
# docker commands                                                             #
###############################################################################
TARGETS := build format ut it
DOCKER_PHONIES := $(patsubst %, %-docker, $(TARGETS))

# run with: `make {one name in TARGETS}-docker`
# eg: make build-docker
.PHONY: $(DOCKER_PHONIES)
## run make commands in docker
$(DOCKER_PHONIES):
	@printf "🐋 \033[1;32m===> Running $(@:%-docker=%) in docker...\033[0m\n"
	docker run $(DOCKER_NETWORK) --rm \
	    --volume $(ROOT):/auro \
	    --volume $(GO_CACHE_PATH)/pkg:/go/pkg \
	    $(DOCKER_REGISTRY)/$(DOCKER_OWNER)/$(MODEL_NAME)-env:$(TAG) make -C /auro/$(MODEL_NAME) $(@:%-docker=%)

.PHONY: debug
## run make commands in docker for debug
debug:
	@printf "🐋 \033[1;32m===> Bash into $(@:%-docker=%) docker...\033[0m\n"
	docker run $(DOCKER_NETWORK) -it --rm \
	    --volume $(ROOT):/auro \
	    --volume $(GO_CACHE_PATH)/pkg:/go/pkg \
	    $(DOCKER_REGISTRY)/$(DOCKER_OWNER)/$(MODEL_NAME)-env:$(TAG) bash

.PHONY: package
## pacakge command
package:
	@make build-docker
	@printf "🐍 \033[1;32m===> Build python package...\033[0m\n"
	@echo $(MODEL_NAME) package is done.

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
