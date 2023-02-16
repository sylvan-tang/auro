# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: echo/v1/greeting_service.proto
"""Generated protocol buffer code."""
from google.protobuf.internal import builder as _builder
from google.protobuf import descriptor as _descriptor
from google.protobuf import descriptor_pool as _descriptor_pool
from google.protobuf import symbol_database as _symbol_database
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()


from ...common.v1 import types_pb2 as common_dot_v1_dot_types__pb2
from ...echo.v1 import greeting_pb2 as echo_dot_v1_dot_greeting__pb2
from google.api import annotations_pb2 as google_dot_api_dot_annotations__pb2
from google.api import client_pb2 as google_dot_api_dot_client__pb2
from google.protobuf import field_mask_pb2 as google_dot_protobuf_dot_field__mask__pb2


DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n\x1e\x65\x63ho/v1/greeting_service.proto\x12\x07\x65\x63ho.v1\x1a\x15\x63ommon/v1/types.proto\x1a\x16\x65\x63ho/v1/greeting.proto\x1a\x1cgoogle/api/annotations.proto\x1a\x17google/api/client.proto\x1a google/protobuf/field_mask.proto\"^\n\x15\x43reateGreetingRequest\x12\x16\n\x06parent\x18\x01 \x01(\tR\x06parent\x12-\n\x08greeting\x18\x02 \x01(\x0b\x32\x11.echo.v1.GreetingR\x08greeting\"s\n\x16\x43reateGreetingResponse\x12\x12\n\x04\x63ode\x18\x01 \x01(\x05R\x04\x63ode\x12\x18\n\x07message\x18\x02 \x01(\tR\x07message\x12+\n\x07\x64\x65tails\x18\x03 \x01(\x0b\x32\x11.echo.v1.GreetingR\x07\x64\x65tails\"u\n\x12GetGreetingRequest\x12\x16\n\x06parent\x18\x01 \x01(\tR\x06parent\x12\x0e\n\x02id\x18\x02 \x01(\x03R\x02id\x12\x37\n\tread_mask\x18\x03 \x01(\x0b\x32\x1a.google.protobuf.FieldMaskR\x08readMask\"p\n\x13GetGreetingResponse\x12\x12\n\x04\x63ode\x18\x01 \x01(\x05R\x04\x63ode\x12\x18\n\x07message\x18\x02 \x01(\tR\x07message\x12+\n\x07\x64\x65tails\x18\x03 \x01(\x0b\x32\x11.echo.v1.GreetingR\x07\x64\x65tails\"\x9b\x01\n\x15UpdateGreetingRequest\x12\x16\n\x06parent\x18\x01 \x01(\tR\x06parent\x12-\n\x08greeting\x18\x02 \x01(\x0b\x32\x11.echo.v1.GreetingR\x08greeting\x12;\n\x0bupdate_mask\x18\x03 \x01(\x0b\x32\x1a.google.protobuf.FieldMaskR\nupdateMask\"s\n\x16UpdateGreetingResponse\x12\x12\n\x04\x63ode\x18\x01 \x01(\x05R\x04\x63ode\x12\x18\n\x07message\x18\x02 \x01(\tR\x07message\x12+\n\x07\x64\x65tails\x18\x03 \x01(\x0b\x32\x11.echo.v1.GreetingR\x07\x64\x65tails\"\xa7\x02\n\x14ListGreetingsRequest\x12\x16\n\x06parent\x18\x01 \x01(\tR\x06parent\x12<\n\rcommon_option\x18\x02 \x01(\x0b\x32\x17.common.v1.CommonOptionR\x0c\x63ommonOption\x12<\n\rcommon_filter\x18\x03 \x01(\x0b\x32\x17.common.v1.CommonFilterR\x0c\x63ommonFilter\x12\x37\n\tread_mask\x18\x04 \x01(\x0b\x32\x1a.google.protobuf.FieldMaskR\x08readMask\x12\x42\n\roption_filter\x18\x05 \x01(\x0b\x32\x1d.echo.v1.GreetingOptionFilterR\x0coptionFilter\"\x9f\x02\n\x15ListGreetingsResponse\x12\x12\n\x04\x63ode\x18\x01 \x01(\x05R\x04\x63ode\x12\x18\n\x07message\x18\x02 \x01(\tR\x07message\x12@\n\x07\x64\x65tails\x18\x03 \x01(\x0b\x32&.echo.v1.ListGreetingsResponse.DetailsR\x07\x64\x65tails\x1a\x95\x01\n\x07\x44\x65tails\x12\'\n\x05items\x18\x01 \x03(\x0b\x32\x11.echo.v1.GreetingR\x05items\x12@\n\x0cnext_request\x18\x02 \x01(\x0b\x32\x1d.echo.v1.ListGreetingsRequestR\x0bnextRequest\x12\x1f\n\x0btotal_count\x18\x03 \x01(\x05R\ntotalCount\"?\n\x15\x44\x65leteGreetingRequest\x12\x16\n\x06parent\x18\x01 \x01(\tR\x06parent\x12\x0e\n\x02id\x18\x02 \x01(\x03R\x02id\"s\n\x16\x44\x65leteGreetingResponse\x12\x12\n\x04\x63ode\x18\x01 \x01(\x05R\x04\x63ode\x12\x18\n\x07message\x18\x02 \x01(\tR\x07message\x12+\n\x07\x64\x65tails\x18\x03 \x01(\x0b\x32\x11.echo.v1.GreetingR\x07\x64\x65tails\"\xb2\x01\n\x16\x44\x65leteGreetingsRequest\x12\x16\n\x06parent\x18\x01 \x01(\tR\x06parent\x12<\n\rcommon_filter\x18\x02 \x01(\x0b\x32\x17.common.v1.CommonFilterR\x0c\x63ommonFilter\x12\x42\n\roption_filter\x18\x03 \x01(\x0b\x32\x1d.echo.v1.GreetingOptionFilterR\x0coptionFilter\"a\n\x17\x44\x65leteGreetingsResponse\x12\x12\n\x04\x63ode\x18\x01 \x01(\x05R\x04\x63ode\x12\x18\n\x07message\x18\x02 \x01(\tR\x07message\x12\x18\n\x07\x64\x65tails\x18\x03 \x03(\x03R\x07\x64\x65tails\"s\n\x15ImportGreetingRequest\x12\x16\n\x06parent\x18\x01 \x01(\tR\x06parent\x12\x42\n\rimport_config\x18\x02 \x01(\x0b\x32\x1d.echo.v1.ImportGreetingConfigR\x0cimportConfig\"`\n\x16ImportGreetingResponse\x12\x12\n\x04\x63ode\x18\x01 \x01(\x05R\x04\x63ode\x12\x18\n\x07message\x18\x02 \x01(\tR\x07message\x12\x18\n\x07\x64\x65tails\x18\x03 \x01(\x08R\x07\x64\x65tails\"s\n\x15\x45xportGreetingRequest\x12\x16\n\x06parent\x18\x01 \x01(\tR\x06parent\x12\x42\n\rexport_config\x18\x02 \x01(\x0b\x32\x1d.echo.v1.ExportGreetingConfigR\x0c\x65xportConfig\"`\n\x16\x45xportGreetingResponse\x12\x12\n\x04\x63ode\x18\x01 \x01(\x05R\x04\x63ode\x12\x18\n\x07message\x18\x02 \x01(\tR\x07message\x12\x18\n\x07\x64\x65tails\x18\x03 \x03(\tR\x07\x64\x65tails2\xdb\t\n\x0fGreetingService\x12\x93\x01\n\x0e\x43reateGreeting\x12\x1e.echo.v1.CreateGreetingRequest\x1a\x1f.echo.v1.CreateGreetingResponse\"@\xda\x41\x0fparent,greeting\x82\xd3\xe4\x93\x02(\"\x1c/v1/{parent=echos}/greetings:\x08greeting\x12\x81\x01\n\x0bGetGreeting\x12\x1b.echo.v1.GetGreetingRequest\x1a\x1c.echo.v1.GetGreetingResponse\"7\xda\x41\tparent,id\x82\xd3\xe4\x93\x02%\x12#/v1/{parent=echos}/greetings/{id=*}\x12\xaf\x01\n\x0eUpdateGreeting\x12\x1e.echo.v1.UpdateGreetingRequest\x1a\x1f.echo.v1.UpdateGreetingResponse\"\\\xda\x41\x1bparent,greeting,update_mask\x82\xd3\xe4\x93\x02\x38\x32,/v1/{parent=echos}/greetings/{greeting.id=*}:\x08greeting\x12\x81\x01\n\rListGreetings\x12\x1d.echo.v1.ListGreetingsRequest\x1a\x1e.echo.v1.ListGreetingsResponse\"1\x82\xd3\xe4\x93\x02+\"&/v1/{parent=echos}/greetings/resources:\x01*\x12\x8a\x01\n\x0e\x44\x65leteGreeting\x12\x1e.echo.v1.DeleteGreetingRequest\x1a\x1f.echo.v1.DeleteGreetingResponse\"7\xda\x41\tparent,id\x82\xd3\xe4\x93\x02%*#/v1/{parent=echos}/greetings/{id=*}\x12}\n\x0f\x44\x65leteGreetings\x12\x1f.echo.v1.DeleteGreetingsRequest\x1a .echo.v1.DeleteGreetingsResponse\"\'\x82\xd3\xe4\x93\x02!2\x1c/v1/{parent=echos}/greetings:\x01*\x12\xa4\x01\n\x0eImportGreeting\x12\x1e.echo.v1.ImportGreetingRequest\x1a\x1f.echo.v1.ImportGreetingResponse\"Q\xda\x41\x14parent,import_config\x82\xd3\xe4\x93\x02\x34\"#/v1/{parent=echos}/greetings:import:\rimport_config\x12\xa4\x01\n\x0e\x45xportGreeting\x12\x1e.echo.v1.ExportGreetingRequest\x1a\x1f.echo.v1.ExportGreetingResponse\"Q\xda\x41\x14parent,export_config\x82\xd3\xe4\x93\x02\x34\"#/v1/{parent=echos}/greetings:export:\rexport_config\x1a\x1e\xca\x41\x1bgithub.com/sylvan/auro/apisB,Z*github.com/sylvan/auro/apis/echo/v1;echov1b\x06proto3')

_builder.BuildMessageAndEnumDescriptors(DESCRIPTOR, globals())
_builder.BuildTopDescriptorsAndMessages(DESCRIPTOR, 'echo.v1.greeting_service_pb2', globals())
if _descriptor._USE_C_DESCRIPTORS == False:

  DESCRIPTOR._options = None
  DESCRIPTOR._serialized_options = b'Z*github.com/sylvan/auro/apis/echo/v1;echov1'
  _GREETINGSERVICE._options = None
  _GREETINGSERVICE._serialized_options = b'\312A\033github.com/sylvan/auro/apis'
  _GREETINGSERVICE.methods_by_name['CreateGreeting']._options = None
  _GREETINGSERVICE.methods_by_name['CreateGreeting']._serialized_options = b'\332A\017parent,greeting\202\323\344\223\002(\"\034/v1/{parent=echos}/greetings:\010greeting'
  _GREETINGSERVICE.methods_by_name['GetGreeting']._options = None
  _GREETINGSERVICE.methods_by_name['GetGreeting']._serialized_options = b'\332A\tparent,id\202\323\344\223\002%\022#/v1/{parent=echos}/greetings/{id=*}'
  _GREETINGSERVICE.methods_by_name['UpdateGreeting']._options = None
  _GREETINGSERVICE.methods_by_name['UpdateGreeting']._serialized_options = b'\332A\033parent,greeting,update_mask\202\323\344\223\00282,/v1/{parent=echos}/greetings/{greeting.id=*}:\010greeting'
  _GREETINGSERVICE.methods_by_name['ListGreetings']._options = None
  _GREETINGSERVICE.methods_by_name['ListGreetings']._serialized_options = b'\202\323\344\223\002+\"&/v1/{parent=echos}/greetings/resources:\001*'
  _GREETINGSERVICE.methods_by_name['DeleteGreeting']._options = None
  _GREETINGSERVICE.methods_by_name['DeleteGreeting']._serialized_options = b'\332A\tparent,id\202\323\344\223\002%*#/v1/{parent=echos}/greetings/{id=*}'
  _GREETINGSERVICE.methods_by_name['DeleteGreetings']._options = None
  _GREETINGSERVICE.methods_by_name['DeleteGreetings']._serialized_options = b'\202\323\344\223\002!2\034/v1/{parent=echos}/greetings:\001*'
  _GREETINGSERVICE.methods_by_name['ImportGreeting']._options = None
  _GREETINGSERVICE.methods_by_name['ImportGreeting']._serialized_options = b'\332A\024parent,import_config\202\323\344\223\0024\"#/v1/{parent=echos}/greetings:import:\rimport_config'
  _GREETINGSERVICE.methods_by_name['ExportGreeting']._options = None
  _GREETINGSERVICE.methods_by_name['ExportGreeting']._serialized_options = b'\332A\024parent,export_config\202\323\344\223\0024\"#/v1/{parent=echos}/greetings:export:\rexport_config'
  _CREATEGREETINGREQUEST._serialized_start=179
  _CREATEGREETINGREQUEST._serialized_end=273
  _CREATEGREETINGRESPONSE._serialized_start=275
  _CREATEGREETINGRESPONSE._serialized_end=390
  _GETGREETINGREQUEST._serialized_start=392
  _GETGREETINGREQUEST._serialized_end=509
  _GETGREETINGRESPONSE._serialized_start=511
  _GETGREETINGRESPONSE._serialized_end=623
  _UPDATEGREETINGREQUEST._serialized_start=626
  _UPDATEGREETINGREQUEST._serialized_end=781
  _UPDATEGREETINGRESPONSE._serialized_start=783
  _UPDATEGREETINGRESPONSE._serialized_end=898
  _LISTGREETINGSREQUEST._serialized_start=901
  _LISTGREETINGSREQUEST._serialized_end=1196
  _LISTGREETINGSRESPONSE._serialized_start=1199
  _LISTGREETINGSRESPONSE._serialized_end=1486
  _LISTGREETINGSRESPONSE_DETAILS._serialized_start=1337
  _LISTGREETINGSRESPONSE_DETAILS._serialized_end=1486
  _DELETEGREETINGREQUEST._serialized_start=1488
  _DELETEGREETINGREQUEST._serialized_end=1551
  _DELETEGREETINGRESPONSE._serialized_start=1553
  _DELETEGREETINGRESPONSE._serialized_end=1668
  _DELETEGREETINGSREQUEST._serialized_start=1671
  _DELETEGREETINGSREQUEST._serialized_end=1849
  _DELETEGREETINGSRESPONSE._serialized_start=1851
  _DELETEGREETINGSRESPONSE._serialized_end=1948
  _IMPORTGREETINGREQUEST._serialized_start=1950
  _IMPORTGREETINGREQUEST._serialized_end=2065
  _IMPORTGREETINGRESPONSE._serialized_start=2067
  _IMPORTGREETINGRESPONSE._serialized_end=2163
  _EXPORTGREETINGREQUEST._serialized_start=2165
  _EXPORTGREETINGREQUEST._serialized_end=2280
  _EXPORTGREETINGRESPONSE._serialized_start=2282
  _EXPORTGREETINGRESPONSE._serialized_end=2378
  _GREETINGSERVICE._serialized_start=2381
  _GREETINGSERVICE._serialized_end=3624
# @@protoc_insertion_point(module_scope)
