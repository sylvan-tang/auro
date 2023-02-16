from typing import Final

from ._core import Error
from ._core import GrpcClient
from ._core import RPCResponseError
from ._core import try_request_grpc
from ._grpc_clients.assoc import AssocClient
from ._grpc_clients.echo import EchoClient

echo_client: Final[EchoClient] = EchoClient()
assoc_client: Final[AssocClient] = AssocClient()
__all__ = [
    "GrpcClient",
    "Error",
    "RPCResponseError",
    "try_request_grpc",
    "echo_client",
    "assoc_client",
]
