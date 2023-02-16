FROM python:3.8.2-alpine

WORKDIR /phoebe

RUN apk update && apk upgrade && apk add --update alpine-sdk && \
    apk add --no-cache bash git openssh make cmake
RUN PIP_INDEX_URL="https://pypi.tuna.tsinghua.edu.cn/simple" \
      pip install \
      --trusted-host pypi.tuna.tsinghua.edu.cn -U \
      pip setuptools wheel "pdm==1.13.3" "pdm-pep517==0.11.2"
RUN PIP_INDEX_URL="https://pypi.tuna.tsinghua.edu.cn/simple" \
      pip install \
      --trusted-host pypi.tuna.tsinghua.edu.cn -U \
      pdm==1.12.8
