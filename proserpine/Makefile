PROJECT_DIR := $(shell pwd)

.PHONY: base-image
base-image:
	docker build -t sylvan-proserpine:latest -f python.dockerfile .

# use venv as IDE python env path
# use `--add-host host.docker.internal:host-gateway` to enable container for connection to localhost
# link: https://www.cloudsavvyit.com/14114/how-to-connect-to-localhost-within-a-docker-container/
# if can't run `make install-ci` in docker, run `/usr/bin/python3.8 -m pip install --upgrade pip` to fix it

#	    --env PYTHONPATH="/usr/local/lib/python38.zip:/usr/local/lib/python3.8:/usr/local/lib/python3.8/lib-dynload:/usr/local/lib/python3.8/site-packages:/proserpine/__pypackages__/3.8/lib"
.PHONY: bash-docker-mac
bash-docker-mac:
	@printf "🐋 \033[1;32m===> Run command below to bash into docker...\033[0m\n"
	docker run --add-host host.docker.internal:host-gateway -it --rm \
	    --volume $(PROJECT_DIR):/proserpine \
	    sylvan-proserpine:latest sh

