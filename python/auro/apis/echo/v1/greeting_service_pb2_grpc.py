# Generated by the gRPC Python protocol compiler plugin. DO NOT EDIT!
"""Client and server classes corresponding to protobuf-defined services."""
import grpc

from ...echo.v1 import greeting_service_pb2 as echo_dot_v1_dot_greeting__service__pb2


class GreetingServiceStub(object):
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

    def __init__(self, channel):
        """Constructor.

        Args:
            channel: A grpc.Channel.
        """
        self.CreateGreeting = channel.unary_unary(
                '/echo.v1.GreetingService/CreateGreeting',
                request_serializer=echo_dot_v1_dot_greeting__service__pb2.CreateGreetingRequest.SerializeToString,
                response_deserializer=echo_dot_v1_dot_greeting__service__pb2.CreateGreetingResponse.FromString,
                )
        self.GetGreeting = channel.unary_unary(
                '/echo.v1.GreetingService/GetGreeting',
                request_serializer=echo_dot_v1_dot_greeting__service__pb2.GetGreetingRequest.SerializeToString,
                response_deserializer=echo_dot_v1_dot_greeting__service__pb2.GetGreetingResponse.FromString,
                )
        self.UpdateGreeting = channel.unary_unary(
                '/echo.v1.GreetingService/UpdateGreeting',
                request_serializer=echo_dot_v1_dot_greeting__service__pb2.UpdateGreetingRequest.SerializeToString,
                response_deserializer=echo_dot_v1_dot_greeting__service__pb2.UpdateGreetingResponse.FromString,
                )
        self.ListGreetings = channel.unary_unary(
                '/echo.v1.GreetingService/ListGreetings',
                request_serializer=echo_dot_v1_dot_greeting__service__pb2.ListGreetingsRequest.SerializeToString,
                response_deserializer=echo_dot_v1_dot_greeting__service__pb2.ListGreetingsResponse.FromString,
                )
        self.DeleteGreeting = channel.unary_unary(
                '/echo.v1.GreetingService/DeleteGreeting',
                request_serializer=echo_dot_v1_dot_greeting__service__pb2.DeleteGreetingRequest.SerializeToString,
                response_deserializer=echo_dot_v1_dot_greeting__service__pb2.DeleteGreetingResponse.FromString,
                )
        self.DeleteGreetings = channel.unary_unary(
                '/echo.v1.GreetingService/DeleteGreetings',
                request_serializer=echo_dot_v1_dot_greeting__service__pb2.DeleteGreetingsRequest.SerializeToString,
                response_deserializer=echo_dot_v1_dot_greeting__service__pb2.DeleteGreetingsResponse.FromString,
                )
        self.ImportGreeting = channel.unary_unary(
                '/echo.v1.GreetingService/ImportGreeting',
                request_serializer=echo_dot_v1_dot_greeting__service__pb2.ImportGreetingRequest.SerializeToString,
                response_deserializer=echo_dot_v1_dot_greeting__service__pb2.ImportGreetingResponse.FromString,
                )
        self.ExportGreeting = channel.unary_unary(
                '/echo.v1.GreetingService/ExportGreeting',
                request_serializer=echo_dot_v1_dot_greeting__service__pb2.ExportGreetingRequest.SerializeToString,
                response_deserializer=echo_dot_v1_dot_greeting__service__pb2.ExportGreetingResponse.FromString,
                )


class GreetingServiceServicer(object):
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

    def CreateGreeting(self, request, context):
        """Creates a Greeting.
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def GetGreeting(self, request, context):
        """Gets a Greeting.
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def UpdateGreeting(self, request, context):
        """Updates a Greeting.
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def ListGreetings(self, request, context):
        """Lists Greetings in a Location.
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def DeleteGreeting(self, request, context):
        """Deletes a Greeting.
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def DeleteGreetings(self, request, context):
        """Batch delete Greeting by filter.
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def ImportGreeting(self, request, context):
        """Imports a Greeting.
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def ExportGreeting(self, request, context):
        """Exports a Greeting.
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')


def add_GreetingServiceServicer_to_server(servicer, server):
    rpc_method_handlers = {
            'CreateGreeting': grpc.unary_unary_rpc_method_handler(
                    servicer.CreateGreeting,
                    request_deserializer=echo_dot_v1_dot_greeting__service__pb2.CreateGreetingRequest.FromString,
                    response_serializer=echo_dot_v1_dot_greeting__service__pb2.CreateGreetingResponse.SerializeToString,
            ),
            'GetGreeting': grpc.unary_unary_rpc_method_handler(
                    servicer.GetGreeting,
                    request_deserializer=echo_dot_v1_dot_greeting__service__pb2.GetGreetingRequest.FromString,
                    response_serializer=echo_dot_v1_dot_greeting__service__pb2.GetGreetingResponse.SerializeToString,
            ),
            'UpdateGreeting': grpc.unary_unary_rpc_method_handler(
                    servicer.UpdateGreeting,
                    request_deserializer=echo_dot_v1_dot_greeting__service__pb2.UpdateGreetingRequest.FromString,
                    response_serializer=echo_dot_v1_dot_greeting__service__pb2.UpdateGreetingResponse.SerializeToString,
            ),
            'ListGreetings': grpc.unary_unary_rpc_method_handler(
                    servicer.ListGreetings,
                    request_deserializer=echo_dot_v1_dot_greeting__service__pb2.ListGreetingsRequest.FromString,
                    response_serializer=echo_dot_v1_dot_greeting__service__pb2.ListGreetingsResponse.SerializeToString,
            ),
            'DeleteGreeting': grpc.unary_unary_rpc_method_handler(
                    servicer.DeleteGreeting,
                    request_deserializer=echo_dot_v1_dot_greeting__service__pb2.DeleteGreetingRequest.FromString,
                    response_serializer=echo_dot_v1_dot_greeting__service__pb2.DeleteGreetingResponse.SerializeToString,
            ),
            'DeleteGreetings': grpc.unary_unary_rpc_method_handler(
                    servicer.DeleteGreetings,
                    request_deserializer=echo_dot_v1_dot_greeting__service__pb2.DeleteGreetingsRequest.FromString,
                    response_serializer=echo_dot_v1_dot_greeting__service__pb2.DeleteGreetingsResponse.SerializeToString,
            ),
            'ImportGreeting': grpc.unary_unary_rpc_method_handler(
                    servicer.ImportGreeting,
                    request_deserializer=echo_dot_v1_dot_greeting__service__pb2.ImportGreetingRequest.FromString,
                    response_serializer=echo_dot_v1_dot_greeting__service__pb2.ImportGreetingResponse.SerializeToString,
            ),
            'ExportGreeting': grpc.unary_unary_rpc_method_handler(
                    servicer.ExportGreeting,
                    request_deserializer=echo_dot_v1_dot_greeting__service__pb2.ExportGreetingRequest.FromString,
                    response_serializer=echo_dot_v1_dot_greeting__service__pb2.ExportGreetingResponse.SerializeToString,
            ),
    }
    generic_handler = grpc.method_handlers_generic_handler(
            'echo.v1.GreetingService', rpc_method_handlers)
    server.add_generic_rpc_handlers((generic_handler,))


 # This class is part of an EXPERIMENTAL API.
class GreetingService(object):
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

    @staticmethod
    def CreateGreeting(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/echo.v1.GreetingService/CreateGreeting',
            echo_dot_v1_dot_greeting__service__pb2.CreateGreetingRequest.SerializeToString,
            echo_dot_v1_dot_greeting__service__pb2.CreateGreetingResponse.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def GetGreeting(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/echo.v1.GreetingService/GetGreeting',
            echo_dot_v1_dot_greeting__service__pb2.GetGreetingRequest.SerializeToString,
            echo_dot_v1_dot_greeting__service__pb2.GetGreetingResponse.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def UpdateGreeting(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/echo.v1.GreetingService/UpdateGreeting',
            echo_dot_v1_dot_greeting__service__pb2.UpdateGreetingRequest.SerializeToString,
            echo_dot_v1_dot_greeting__service__pb2.UpdateGreetingResponse.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def ListGreetings(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/echo.v1.GreetingService/ListGreetings',
            echo_dot_v1_dot_greeting__service__pb2.ListGreetingsRequest.SerializeToString,
            echo_dot_v1_dot_greeting__service__pb2.ListGreetingsResponse.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def DeleteGreeting(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/echo.v1.GreetingService/DeleteGreeting',
            echo_dot_v1_dot_greeting__service__pb2.DeleteGreetingRequest.SerializeToString,
            echo_dot_v1_dot_greeting__service__pb2.DeleteGreetingResponse.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def DeleteGreetings(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/echo.v1.GreetingService/DeleteGreetings',
            echo_dot_v1_dot_greeting__service__pb2.DeleteGreetingsRequest.SerializeToString,
            echo_dot_v1_dot_greeting__service__pb2.DeleteGreetingsResponse.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def ImportGreeting(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/echo.v1.GreetingService/ImportGreeting',
            echo_dot_v1_dot_greeting__service__pb2.ImportGreetingRequest.SerializeToString,
            echo_dot_v1_dot_greeting__service__pb2.ImportGreetingResponse.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def ExportGreeting(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/echo.v1.GreetingService/ExportGreeting',
            echo_dot_v1_dot_greeting__service__pb2.ExportGreetingRequest.SerializeToString,
            echo_dot_v1_dot_greeting__service__pb2.ExportGreetingResponse.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)