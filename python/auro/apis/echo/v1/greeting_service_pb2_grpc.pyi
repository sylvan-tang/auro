"""
@generated by mypy-protobuf.  Do not edit manually!
isort:skip_file
"""
import abc
import ...echo.v1.greeting_service_pb2
import grpc

class GreetingServiceStub:
    """option (grpc.gateway.protoc_gen_swagger.options.openapiv2_swagger) = {
     info: {
        title: "Greeting Service"
        version: "1.0"
        contact: {
          name: "Greeting Service"
          url: "http://github.com/sylvan/auro/apis"
        }
      }
      host: "github.com/sylvan/auro/apis"
      base_path: "/echo/v1/greeting"
      schemes: HTTP
      schemes: HTTPS
      consumes: "application/json"
      produces: "application/json"
      external_docs: {
        description: "API specification in Markdown",
        url: "http://github.com/sylvan/auro/apis/echo/v1/greeting"
      }
    };

    The service that handles the CRUD of Greeting.
    """
    def __init__(self, channel: grpc.Channel) -> None: ...
    CreateGreeting: grpc.UnaryUnaryMultiCallable[
        echo.v1.greeting_service_pb2.CreateGreetingRequest,
        echo.v1.greeting_service_pb2.CreateGreetingResponse]
    """Creates a Greeting."""

    GetGreeting: grpc.UnaryUnaryMultiCallable[
        echo.v1.greeting_service_pb2.GetGreetingRequest,
        echo.v1.greeting_service_pb2.GetGreetingResponse]
    """Gets a Greeting."""

    UpdateGreeting: grpc.UnaryUnaryMultiCallable[
        echo.v1.greeting_service_pb2.UpdateGreetingRequest,
        echo.v1.greeting_service_pb2.UpdateGreetingResponse]
    """Updates a Greeting."""

    ListGreetings: grpc.UnaryUnaryMultiCallable[
        echo.v1.greeting_service_pb2.ListGreetingsRequest,
        echo.v1.greeting_service_pb2.ListGreetingsResponse]
    """Lists Greetings in a Location."""

    DeleteGreeting: grpc.UnaryUnaryMultiCallable[
        echo.v1.greeting_service_pb2.DeleteGreetingRequest,
        echo.v1.greeting_service_pb2.DeleteGreetingResponse]
    """Deletes a Greeting."""

    DeleteGreetings: grpc.UnaryUnaryMultiCallable[
        echo.v1.greeting_service_pb2.DeleteGreetingsRequest,
        echo.v1.greeting_service_pb2.DeleteGreetingsResponse]
    """Batch delete Greeting by filter."""

    ImportGreeting: grpc.UnaryUnaryMultiCallable[
        echo.v1.greeting_service_pb2.ImportGreetingRequest,
        echo.v1.greeting_service_pb2.ImportGreetingResponse]
    """Imports a Greeting."""

    ExportGreeting: grpc.UnaryUnaryMultiCallable[
        echo.v1.greeting_service_pb2.ExportGreetingRequest,
        echo.v1.greeting_service_pb2.ExportGreetingResponse]
    """Exports a Greeting."""


class GreetingServiceServicer(metaclass=abc.ABCMeta):
    """option (grpc.gateway.protoc_gen_swagger.options.openapiv2_swagger) = {
     info: {
        title: "Greeting Service"
        version: "1.0"
        contact: {
          name: "Greeting Service"
          url: "http://github.com/sylvan/auro/apis"
        }
      }
      host: "github.com/sylvan/auro/apis"
      base_path: "/echo/v1/greeting"
      schemes: HTTP
      schemes: HTTPS
      consumes: "application/json"
      produces: "application/json"
      external_docs: {
        description: "API specification in Markdown",
        url: "http://github.com/sylvan/auro/apis/echo/v1/greeting"
      }
    };

    The service that handles the CRUD of Greeting.
    """
    @abc.abstractmethod
    def CreateGreeting(self,
        request: echo.v1.greeting_service_pb2.CreateGreetingRequest,
        context: grpc.ServicerContext,
    ) -> echo.v1.greeting_service_pb2.CreateGreetingResponse:
        """Creates a Greeting."""
        pass

    @abc.abstractmethod
    def GetGreeting(self,
        request: echo.v1.greeting_service_pb2.GetGreetingRequest,
        context: grpc.ServicerContext,
    ) -> echo.v1.greeting_service_pb2.GetGreetingResponse:
        """Gets a Greeting."""
        pass

    @abc.abstractmethod
    def UpdateGreeting(self,
        request: echo.v1.greeting_service_pb2.UpdateGreetingRequest,
        context: grpc.ServicerContext,
    ) -> echo.v1.greeting_service_pb2.UpdateGreetingResponse:
        """Updates a Greeting."""
        pass

    @abc.abstractmethod
    def ListGreetings(self,
        request: echo.v1.greeting_service_pb2.ListGreetingsRequest,
        context: grpc.ServicerContext,
    ) -> echo.v1.greeting_service_pb2.ListGreetingsResponse:
        """Lists Greetings in a Location."""
        pass

    @abc.abstractmethod
    def DeleteGreeting(self,
        request: echo.v1.greeting_service_pb2.DeleteGreetingRequest,
        context: grpc.ServicerContext,
    ) -> echo.v1.greeting_service_pb2.DeleteGreetingResponse:
        """Deletes a Greeting."""
        pass

    @abc.abstractmethod
    def DeleteGreetings(self,
        request: echo.v1.greeting_service_pb2.DeleteGreetingsRequest,
        context: grpc.ServicerContext,
    ) -> echo.v1.greeting_service_pb2.DeleteGreetingsResponse:
        """Batch delete Greeting by filter."""
        pass

    @abc.abstractmethod
    def ImportGreeting(self,
        request: echo.v1.greeting_service_pb2.ImportGreetingRequest,
        context: grpc.ServicerContext,
    ) -> echo.v1.greeting_service_pb2.ImportGreetingResponse:
        """Imports a Greeting."""
        pass

    @abc.abstractmethod
    def ExportGreeting(self,
        request: echo.v1.greeting_service_pb2.ExportGreetingRequest,
        context: grpc.ServicerContext,
    ) -> echo.v1.greeting_service_pb2.ExportGreetingResponse:
        """Exports a Greeting."""
        pass


def add_GreetingServiceServicer_to_server(servicer: GreetingServiceServicer, server: grpc.Server) -> None: ...
