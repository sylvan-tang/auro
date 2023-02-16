SHELL := /bin/bash # Use bash syntax

mkfile_path := $(abspath $(lastword $(MAKEFILE_LIST)))
ROOT := $(patsubst %/,%,$(dir $(mkfile_path)))
GO_ENV := $(shell echo `which go`)
GO_CACHE_PATH := $(shell if [ ! -z "$(GO_ENV)" ]; then go env | grep GOMODCACHE | cut -d= -f2 | tr -d '"' | xargs dirname | xargs dirname | xargs echo; else echo $(ROOT)/.cache; fi)

DOCKER_REGISTRY := ghcr.io
DOCKER_OWNER := sylvan-tang
TAG := latest
SUBMODULE_TEMPLATE_NAME := make-template

#################################################################
# CICD processes                                                #
#################################################################

# all dir that contain Makefile will be considered as a target.
MAKEFILE_PHONIES := $(wildcard */Makefile)
PHONIES=$(dir $(MAKEFILE_PHONIES))
DEPLOY_PHONIES=$(patsubst %, %deploy, $(PHONIES))
IMAGE_DIFF=$(shell cat $(ROOT)/images-diff.txt)
IMAGE_PHONIES=$(patsubst %, %/image, $(IMAGE_DIFF))

DIFF_PHONIES=$(shell cat $(ROOT)/git-diff.txt)
BUILD_PHONIES=$(patsubst %, %/build, $(DIFF_PHONIES))
FORMAT_PHONIES=$(patsubst %, %/format, $(DIFF_PHONIES))
UT_PHONIES=$(patsubst %, %/ut, $(DIFF_PHONIES))
IT_PHONIES=$(patsubst %, %/it, $(DIFF_PHONIES))
SONAR_SCAN_PHONIES=$(patsubst %, %/sonar-scan, $(DIFF_PHONIES))

.PHONY: $(BUILD_PHONIES)
## build commands for each Makefile
$(BUILD_PHONIES):
	@printf "🐋 \033[1;32m===> Running $(@:%=%) ..\033[0m\n"
	@if [ "$(@:%/build=%)" = "$(SUBMODULE_TEMPLATE_NAME)" ]; then \
	echo skip $(@:%=%); \
	else \
	make -C $(dir $(ROOT)/$(@:%=%)) build-docker; \
	fi

.PHONY: $(FORMAT_PHONIES)
## format commands for each Makefile
$(FORMAT_PHONIES):
	@printf "🐋 \033[1;32m===> Running $(@:%=%) ..\033[0m\n"
	@if [ "$(@:%/format=%)" = "$(SUBMODULE_TEMPLATE_NAME)" ]; then \
	echo skip $(@:%=%); \
	else \
	make -C $(dir $(ROOT)/$(@:%=%)) format-docker; \
	fi

.PHONY: $(UT_PHONIES)
## unit test commands for each Makefile
$(UT_PHONIES):
	@printf "🐋 \033[1;32m===> Running $(@:%=%) ..\033[0m\n"
	@if [ "$(@:%/ut=%)" = "$(SUBMODULE_TEMPLATE_NAME)" ]; then \
	echo skip $(@:%=%); \
	else \
	make -C $(dir $(ROOT)/$(@:%=%)) ut-docker; \
	fi

.PHONY: $(IT_PHONIES)
## integration test commands for each Makefile
$(IT_PHONIES):
	@printf "🐋 \033[1;32m===> Running $(@:%=%) ..\033[0m\n"
	@if [ "$(@:%/it=%)" = "$(SUBMODULE_TEMPLATE_NAME)" ]; then \
	echo skip $(@:%=%); \
	else \
	make -C $(dir $(ROOT)/$(@:%=%)) it-docker; \
	fi

.PHONY: $(SONAR_SCAN_PHONIES)
## sonar scan commands for each Makefile
$(SONAR_SCAN_PHONIES):
	@printf "🐋 \033[1;32m===> Running $(@:%=%) ..\033[0m\n"
	@if [ "$(@:%/sonar-scan=%)" = "$(SUBMODULE_TEMPLATE_NAME)" ]; then \
	echo skip $(@:%=%); \
	else \
	make -C $(dir $(ROOT)/$(@:%=%)) sonar-scan; \
	fi

.PHONY: $(DEPLOY_PHONIES)
## deploy commands for each Makefile
$(DEPLOY_PHONIES):
	@printf "🐋 \033[1;32m===> Running $(@:%=%) ..\033[0m\n"
	@if [ "$(@:%/deploy=%)" = "$(SUBMODULE_TEMPLATE_NAME)" ]; then \
	echo skip $(@:%=%); \
	else \
	make -C $(dir $(ROOT)/$(@:%=%)) deploy; \
	fi

.PHONY: $(IMAGE_PHONIES)
## build image commands for each Makefile
$(IMAGE_PHONIES):
	@printf "🐋 \033[1;32m===> Running $(@:%=%) ..\033[0m\n"
	$(eval DOCKER_FILE_NAME := $(subst /,,$(dir $(@:%/=%)))-env)
	@if [ "$(@:%-env/image=%)" = "$(SUBMODULE_TEMPLATE_NAME)" ]; then \
	echo skip $(@:%=%); \
	else \
	$(ROOT)/bin/build-image.sh $(ROOT)/dockers/$(DOCKER_FILE_NAME).dockerfile $(DOCKER_FILE_NAME); \
	fi


.PHONY: build
## build command for PHONIES
build:
	@make $(BUILD_PHONIES)

.PHONY: format
## format command for PHONIES
format:
	@make $(FORMAT_PHONIES)

.PHONY: ut
## unit test command for PHONIES
ut:
	@make $(UT_PHONIES)

.PHONY: it
## integration test command for PHONIES
it:
	@make $(IT_PHONIES)

.PHONY: sonar-scan
## sonar scan command for PHONIES
sonar-scan:
	@make $(SONAR_SCAN_PHONIES)

.PHONY: image
## build docker image command for PHONIES
image:
	@make $(IMAGE_PHONIES)

.PHONY: deploy
## deploy command for PHONIES
deploy:
	@make $(DEPLOY_PHONIES)

#################################################################
# custom processes                                              #
#################################################################

BASE_IMAGES := $(shell cat $(ROOT)/base-images-diff.txt)
BASE_IMAGE_PHONIES := $(patsubst %, %-image, $(BASE_IMAGES))
.PHONY: $(BASE_IMAGE_PHONIES)
## build image commands for each Makefile
$(BASE_IMAGE_PHONIES):
	@printf "🐋 \033[1;32m===> Running $(@:%=%) for dockers/$(@:%-image=%).dockerfile ..\033[0m\n"
	@if [ "$(@:%-env-image=%)" = "$(SUBMODULE_TEMPLATE_NAME)" ]; then \
	echo skip build $(@:%=%); \
	else \
	$(ROOT)/bin/build-image.sh $(ROOT)/dockers/$(@:%-image=%).dockerfile $(@:%-image=%); \
	fi;

.PHONY: base-image
## build base docker image command for BASE_IMAGE_PHONIES
base-image:
	@make $(BASE_IMAGE_PHONIES)

ALL_IMAGES := $(shell cat $(ROOT)/all-images.txt)
ALL_IMAGE_PHONIES := $(patsubst %, %-all-image, $(ALL_IMAGES))
.PHONY: $(ALL_IMAGE_PHONIES)
## build image commands for each Makefile
$(ALL_IMAGE_PHONIES):
	@printf "🐋 \033[1;32m===> Running $(@:%=%) for dockers/$(@:%-all-image=%).dockerfile ..\033[0m\n"
	$(ROOT)/bin/build-image.sh $(ROOT)/dockers/$(@:%-all-image=%).dockerfile $(@:%-all-image=%)

.PHONY: all-image
## build all docker image command for BASE_IMAGE_PHONIES
all-image:
	@make $(ALL_IMAGE_PHONIES)

.PHONY: clean
## clean unused files
clean:
	@printf "💧 \033[1;32m===> Cleaning unused files...\033[0m\n"
	find ./ -name '*DS_Store' -type f -print -delete
	find ./ -name '*.pyc' -type f -print -delete

.PHONY: scm-version
## generate scm version for all python pkg
scm-version:
	@make python-base-all-image PUSH_IMAGE=N
	@printf "🐍 \033[1;32m===> Building scm_version.toml...\033[0m\n"
	docker run --rm \
		--volume $(ROOT):/auro \
	    $(DOCKER_REGISTRY)/$(DOCKER_OWNER)/python-base:$(TAG) bash -c "\
		cd /auro && \
		python3 scm_version_sync.py"
	@printf "🐍 \033[1;32m===> scm-version:\033[0m\n"
	@cat scm_version.toml
	@find $(ROOT) -name 'pyproject.toml' -type f -print | xargs dirname | xargs -I{} cp $(ROOT)/scm_version.toml {}/scm_version.toml

.PHONY: go-work-sync
## run go work sync
go-work-sync:
	@printf "🐍 \033[1;32m===> Running go work sync ...\033[0m\n"
	docker run --rm \
	    --volume $(ROOT):/auro \
	    --volume $(GO_CACHE_PATH)/pkg:/go/pkg \
	    $(DOCKER_REGISTRY)/$(DOCKER_OWNER)/protobuf-env:$(TAG) bash -c "\
		cd /auro && go work sync"

.PHONY: local-stop-mysql
## stop local mysql in docker
mysql_server := $(shell docker service ls -f name=auro-mysql -q)
local-stop-mysql:
	@if [ ! -z "$(mysql_server)" ]; then \
	docker service rm auro-mysql && \
	sleep 20 && \
	docker volume rm auro-mysql-data; \
	fi

.PHONY: local-start-mysql
## start local mysql in docker,  add auro-mysql into you /etc/hosts, eg:
## 127.0.0.1       localhost auro-mysql
local-start-mysql:
	@make local-stop-mysql
	@bash $(ROOT)/bin/gen-mysql-dockerfile.sh
	@make mysql-all-image PUSH_IMAGE=N
	docker service create --name auro-mysql --publish 3306:3306 \
	    --mount type=volume,source=auro-mysql-data,destination=/var/lib/mysql \
	    --env MYSQL_ROOT_PASSWORD=my-secret-pw $(DOCKER_REGISTRY)/$(DOCKER_OWNER)/mysql:latest && sleep 10
