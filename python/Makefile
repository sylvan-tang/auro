SHELL := /bin/bash # Use bash syntax

mkfile_path := $(abspath $(lastword $(MAKEFILE_LIST)))
MODEL_PATH := $(patsubst %/,%,$(dir $(mkfile_path)))
ROOT := $(dir $(MODEL_PATH))
MODEL_NAME := $(shell cat $(MODEL_PATH)/name.txt)
DIFF_TARGETS=$(shell cat $(ROOT)git-diff.txt | grep $(MODEL_NAME))

DOCKER_NETWORK := --add-host host.docker.internal:host-gateway
GO_ENV := $(shell echo `which go`)
GO_CACHE_PATH := $(shell if [ ! -z "$(GO_ENV)" ]; then go env | grep GOMODCACHE | cut -d= -f2 | tr -d '"' | xargs dirname | xargs dirname | xargs echo; else echo $(ROOT)/.cache; fi)

DOCKER_REGISTRY := ghcr.io
DOCKER_OWNER := sylvan-tang
TAG := latest


UNDERLINE := _
DASH := -
PROJECT_NAME=auro
REPO=$(subst $(UNDERLINE),$(DASH),$(MODEL_NAME))


GIT_COMMIT = $(shell git describe --match= --always)
GIT_VERSION = $(shell git describe --tags --always)


###############################################################################
# COMMANDS                                                                    #
###############################################################################
.PHONY: clean
## Clean python cache file.
clean:
	find . -name '*.pyo' -delete
	find . -name '*.pyc' -delete
	find . -type d -name __pycache__  -exec rm -rf {} +
	find . -name '*~' -delete
	find . -name .coverage -delete
	find . -name '.coverage.*' -delete
	find . -name 'codeclimate.*' -delete
	find . -name 'report.html' -delete
	find . -name cov.xml -delete
	find . -type d -name .pytest_cache -exec rm -rf {} +
	find . -type d -name .mypy_cache -exec rm -rf {} +

.PHONY: init
## init project with pdm
init:
	@printf "🐍 \033[1;32m===> Init $(PROJECT_NAME)...\033[0m\n"
	@if [ -d dist ]; then rm -rf dist; fi && \
	if [ -d .venv ]; then rm -rf .venv; fi && \
	if [ ! -f pdm.lock ]; then pdm lock && \
	pdm add -dG $(PROJECT_NAME) pytest autoflake isort yapf "coverage[toml]<6.4" "setuptools-scm>=6.4.2" "pytest-cov<3.0.0,>=2.12.1" && \
	cat $(MODEL_PATH)/requirements.init.txt | grep -v "\#" | awk '{print $1}' | xargs -I{} pdm add -dG $(PROJECT_NAME) {}; fi
	@printf "🐍 \033[1;32m===> Init $(PROJECT_NAME) done.\033[0m\n"

.PHONY: install
## deploy CI environment
install:
	@printf "🐍 \033[1;32m===> Install $(PROJECT_NAME)...\033[0m\n"
	pip uninstall j2y --yes
	# Note: https://github.com/ValvePython/csgo/issues/8
	PDM_NO_BINARY=protobuf pdm sync -dG $(PROJECT_NAME) --clean
	pdm export -dG $(PROJECT_NAME) --without-hashes --output requirements.txt
	# Note: https://github.com/ValvePython/csgo/issues/8
	pip install -r requirements.txt --force-reinstall --no-binary=protobuf
	@printf "🐍 \033[1;32m===> Install $(PROJECT_NAME) done.\033[0m\n"


.PHONY: pkg-build
## build and push whl to nexus
pkg-build:
	@printf "🐍 \033[1;32m===> Building $(PROJECT_NAME) package...\033[0m\n"
	@cd $(MODEL_PATH)
	@rm -rf dist;
	@pdm build --dest $(MODEL_PATH)/dist;

.PHONY: pkg-publish
## publish package to our pypi
pkg-publish:
	@printf "🐍 \033[1;32m===> Publishing $(PROJECT_NAME) package...\033[0m\n"
	@cd ${MODEL_PATH}
	twine upload --repository-url https://upload.pypi.org/legacy/ --username sylvan-tang --password sylvan-20230213 dist/*

.PHONY: build-image
## build image
build-image:
	REGISTRY=$(DOCKER_REGISTRY)/$(DOCKER_OWNER) IMAGE_NAME=${REPO} PROJECT_NAME=${PROJECT_NAME} ./build_image.sh .

.PHONY: build
## build command 
build: init install
	@echo $(MODEL_NAME) build is done.

.PHONY: format
## format command 
format:
	@printf "🐍 \033[1;32m===> Format $(PROJECT_NAME)...\033[0m\n"
	pdm run autoflake --in-place --remove-all-unused-imports --remove-unused-variables -r $(PROJECT_NAME) tests --exclude $(PROJECT_NAME)/apis
	pdm run isort $(PROJECT_NAME) tests --skip $(PROJECT_NAME)/apis
	pdm run yapf -i -r --parallel  --exclude=$(PROJECT_NAME)/apis $(PROJECT_NAME) tests
	@printf "🐍 \033[1;32m===> Format $(PROJECT_NAME) done.\033[0m\n"

.PHONY: ut
## unit test command 
ut: build
	@printf "🐍 \033[1;32m===> Running unit testing for $(PROJECT_NAME)...\033[0m\n"
	python -m pytest -s -v --cov-config=$(MODEL_PATH)/pyproject.toml $(MODEL_PATH)/tests/unit_test <&-
	# pdm run coverage xml
	@printf "🐍 \033[1;32m===> Running unit testing for $(PROJECT_NAME) done.\033[0m\n"

.PHONY: it
## integration test command
it: build
	@printf "🐍 \033[1;32m===> Running integration testing for $(PROJECT_NAME)...\033[0m\n"
	python -m pytest -s -v --cov-config=$(MODEL_PATH)/pyproject.toml $(MODEL_PATH)/tests/integration_test <&-
	# pdm run coverage xml
	@printf "🐍 \033[1;32m===> Running integration testing for $(PROJECT_NAME) done.\033[0m\n"

.PHONY: package
## pacakge command
package:
	@printf "🐍 \033[1;32m===> Build python package...\033[0m\n"
	docker run --rm \
	    --volume $(ROOT):/auro \
	    $(DOCKER_REGISTRY)/$(DOCKER_OWNER)/$(MODEL_NAME)-env:$(TAG) sh -c "\
	    cd /auro/$(MODEL_NAME) && \
	    if [ -d dist ]; then rm -rf dist; fi && \
	    if [ ! -f pdm.lock ]; then make init; fi && \
	    make install format ut pkg-build"
	@echo $(MODEL_NAME) package is done.

.PHONY: publish
## publish command
publish:
	@printf "🐍 \033[1;32m===> Build python package...\033[0m\n"
	docker run --rm \
	    --volume $(ROOT):/auro \
	    $(DOCKER_REGISTRY)/$(DOCKER_OWNER)/$(MODEL_NAME)-env:$(TAG) sh -c "\
	    cd /auro/$(MODEL_NAME) && \
	    make pkg-publish"
	@echo $(MODEL_NAME) publish is done.

.PHONY: upgrade
## upgrade command
upgrade:
	@echo $(MODEL_NAME) upgrade is done.

.PHONY: deploy
## deploy command 
deploy:
	@if [ -f "$(ROOT)/python-diff.txt" ]; then \
		make package publish upgrade; \
	fi;
	@echo $(MODEL_NAME) deploy is done.

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
