"""
@generated by mypy-protobuf.  Do not edit manually!
isort:skip_file
"""
import builtins
import google.protobuf.descriptor
import google.protobuf.message
import ...thirdparty.greeting.hello_pb2
import typing
import typing_extensions

DESCRIPTOR: google.protobuf.descriptor.FileDescriptor

class Chat(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor
    HELLO_FIELD_NUMBER: builtins.int
    @property
    def hello(self) -> thirdparty.greeting.hello_pb2.Hello: ...
    def __init__(self,
        *,
        hello: typing.Optional[thirdparty.greeting.hello_pb2.Hello] = ...,
        ) -> None: ...
    def HasField(self, field_name: typing_extensions.Literal["hello",b"hello"]) -> builtins.bool: ...
    def ClearField(self, field_name: typing_extensions.Literal["hello",b"hello"]) -> None: ...
global___Chat = Chat
