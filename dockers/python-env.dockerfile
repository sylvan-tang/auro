FROM ghcr.io/sylvan-tang/python-base:latest

# NOTE: https://github.com/ValvePython/csgo/issues/8
RUN pip install --no-binary=protobuf protobuf==3.20.1
WORKDIR /auro
