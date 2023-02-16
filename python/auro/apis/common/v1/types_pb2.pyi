"""
@generated by mypy-protobuf.  Do not edit manually!
isort:skip_file
"""
import builtins
import google.protobuf.any_pb2
import google.protobuf.descriptor
import google.protobuf.internal.containers
import google.protobuf.internal.enum_type_wrapper
import google.protobuf.message
import typing
import typing_extensions

DESCRIPTOR: google.protobuf.descriptor.FileDescriptor

class _Code:
    ValueType = typing.NewType('ValueType', builtins.int)
    V: typing_extensions.TypeAlias = ValueType
class _CodeEnumTypeWrapper(google.protobuf.internal.enum_type_wrapper._EnumTypeWrapper[_Code.ValueType], builtins.type):
    DESCRIPTOR: google.protobuf.descriptor.EnumDescriptor
    CODE_UNSPECIFIED: _Code.ValueType  # 0
    CODE_GENERAL_ERROR: _Code.ValueType  # 1000
    """Should avoid collision with grpc codes (0-16).

    For any unidentified error.
    """

    CODE_INVALID_ARGUMENT: _Code.ValueType  # 1003
    CODE_NOT_FOUND: _Code.ValueType  # 1005
    CODE_ALREADY_EXISTS: _Code.ValueType  # 1006
    CODE_GENERAL_SQL_ERROR: _Code.ValueType  # 2000
    """SQL errors. Should hide any detailed SQL message to prevent data breaches.
    For any unidentified SQL error.
    """

    CODE_SQL_NO_ROWS: _Code.ValueType  # 2001
    """Maps sql.ErrNoRows."""

    CODE_K8S_NOT_ENOUGH_RESOURCE: _Code.ValueType  # 3000
    """k8s errors.
    For experiment  with k8s resource not enough.
    """

class Code(_Code, metaclass=_CodeEnumTypeWrapper):
    """Code extends GRPC's code but maps to status 2XX in grpc-gateway.
    Check:
    definition:  https://github.com/grpc/grpc-go/blob/master/codes/codes.go,
    and mapping: https://github.com/grpc-ecosystem/grpc-gateway/blob/master/runtime/errors.go.
    """
    pass

CODE_UNSPECIFIED: Code.ValueType  # 0
CODE_GENERAL_ERROR: Code.ValueType  # 1000
"""Should avoid collision with grpc codes (0-16).

For any unidentified error.
"""

CODE_INVALID_ARGUMENT: Code.ValueType  # 1003
CODE_NOT_FOUND: Code.ValueType  # 1005
CODE_ALREADY_EXISTS: Code.ValueType  # 1006
CODE_GENERAL_SQL_ERROR: Code.ValueType  # 2000
"""SQL errors. Should hide any detailed SQL message to prevent data breaches.
For any unidentified SQL error.
"""

CODE_SQL_NO_ROWS: Code.ValueType  # 2001
"""Maps sql.ErrNoRows."""

CODE_K8S_NOT_ENOUGH_RESOURCE: Code.ValueType  # 3000
"""k8s errors.
For experiment  with k8s resource not enough.
"""

global___Code = Code


class Response(google.protobuf.message.Message):
    DESCRIPTOR: google.protobuf.descriptor.Descriptor
    CODE_FIELD_NUMBER: builtins.int
    MESSAGE_FIELD_NUMBER: builtins.int
    DETAILS_FIELD_NUMBER: builtins.int
    code: builtins.int
    message: typing.Text
    @property
    def details(self) -> google.protobuf.any_pb2.Any: ...
    def __init__(self,
        *,
        code: builtins.int = ...,
        message: typing.Text = ...,
        details: typing.Optional[google.protobuf.any_pb2.Any] = ...,
        ) -> None: ...
    def HasField(self, field_name: typing_extensions.Literal["details",b"details"]) -> builtins.bool: ...
    def ClearField(self, field_name: typing_extensions.Literal["code",b"code","details",b"details","message",b"message"]) -> None: ...
global___Response = Response

class CommonOption(google.protobuf.message.Message):
    """option struct for list."""
    DESCRIPTOR: google.protobuf.descriptor.Descriptor
    PAGE_FIELD_NUMBER: builtins.int
    SIZE_FIELD_NUMBER: builtins.int
    CURSOR_FIELD_NUMBER: builtins.int
    QUERY_FIELD_NUMBER: builtins.int
    ORDER_BY_FIELD_NUMBER: builtins.int
    GROUP_BY_FIELD_NUMBER: builtins.int
    page: builtins.int
    """The standard page number, start with zero, use cursor or size as page rotation."""

    size: builtins.int
    """The standard list page size."""

    cursor: typing.Text
    """The standard list page cursor."""

    query: typing.Text
    """The search keyword."""

    order_by: typing.Text
    """A comma-separated list of fields to order by, sorted in descending order.
    Supported fields example:
      * `create_at`
      * `update_at`
    """

    group_by: typing.Text
    """A comma-separated list of fields to group by."""

    def __init__(self,
        *,
        page: builtins.int = ...,
        size: builtins.int = ...,
        cursor: typing.Text = ...,
        query: typing.Text = ...,
        order_by: typing.Text = ...,
        group_by: typing.Text = ...,
        ) -> None: ...
    def ClearField(self, field_name: typing_extensions.Literal["cursor",b"cursor","group_by",b"group_by","order_by",b"order_by","page",b"page","query",b"query","size",b"size"]) -> None: ...
global___CommonOption = CommonOption

class CommonFilter(google.protobuf.message.Message):
    """filter struct for list."""
    DESCRIPTOR: google.protobuf.descriptor.Descriptor
    COLUMN_NAME_FIELD_NUMBER: builtins.int
    HIGH_TIME_FIELD_NUMBER: builtins.int
    LOW_TIME_FIELD_NUMBER: builtins.int
    EXCLUDE_IDS_FIELD_NUMBER: builtins.int
    INCLUDE_IDS_FIELD_NUMBER: builtins.int
    column_name: typing.Text
    high_time: typing.Text
    """High time string in local timezone, `${column_name}` <= '${high_time}'."""

    low_time: typing.Text
    """Low time string in local timezone `${column_name}` > '${low_time}'."""

    @property
    def exclude_ids(self) -> google.protobuf.internal.containers.RepeatedScalarFieldContainer[builtins.int]:
        """exclude ids for next filter.
        next filter will reset high_time with the lower time in current respone,
        so, exclude_ids will be setted with the id that has timestamp ot this filter.
        """
        pass
    @property
    def include_ids(self) -> google.protobuf.internal.containers.RepeatedScalarFieldContainer[builtins.int]:
        """list object that id in include in this field."""
        pass
    def __init__(self,
        *,
        column_name: typing.Text = ...,
        high_time: typing.Text = ...,
        low_time: typing.Text = ...,
        exclude_ids: typing.Optional[typing.Iterable[builtins.int]] = ...,
        include_ids: typing.Optional[typing.Iterable[builtins.int]] = ...,
        ) -> None: ...
    def ClearField(self, field_name: typing_extensions.Literal["column_name",b"column_name","exclude_ids",b"exclude_ids","high_time",b"high_time","include_ids",b"include_ids","low_time",b"low_time"]) -> None: ...
global___CommonFilter = CommonFilter
