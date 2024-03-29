# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: assoc/v1/assoc_service.proto
"""Generated protocol buffer code."""
from google.protobuf.internal import builder as _builder
from google.protobuf import descriptor as _descriptor
from google.protobuf import descriptor_pool as _descriptor_pool
from google.protobuf import symbol_database as _symbol_database
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()


from ...common.v1 import types_pb2 as common_dot_v1_dot_types__pb2
from google.api import annotations_pb2 as google_dot_api_dot_annotations__pb2
from google.api import client_pb2 as google_dot_api_dot_client__pb2
from google.api import resource_pb2 as google_dot_api_dot_resource__pb2
from google.protobuf import field_mask_pb2 as google_dot_protobuf_dot_field__mask__pb2


DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n\x1c\x61ssoc/v1/assoc_service.proto\x12\x08\x61ssoc.v1\x1a\x15\x63ommon/v1/types.proto\x1a\x1cgoogle/api/annotations.proto\x1a\x17google/api/client.proto\x1a\x19google/api/resource.proto\x1a google/protobuf/field_mask.proto\"S\n\x12\x43reateAssocRequest\x12\x16\n\x06parent\x18\x01 \x01(\tR\x06parent\x12%\n\x05\x61ssoc\x18\x02 \x01(\x0b\x32\x0f.assoc.v1.AssocR\x05\x61ssoc\"n\n\x13\x43reateAssocResponse\x12\x12\n\x04\x63ode\x18\x01 \x01(\x05R\x04\x63ode\x12\x18\n\x07message\x18\x02 \x01(\tR\x07message\x12)\n\x07\x64\x65tails\x18\x03 \x01(\x0b\x32\x0f.assoc.v1.AssocR\x07\x64\x65tails\"\xd2\x01\n\x12UpdateAssocRequest\x12\x17\n\x07\x66rom_id\x18\x01 \x01(\x03R\x06\x66romId\x12\x13\n\x05to_id\x18\x02 \x01(\x03R\x04toId\x12*\n\x06\x61_type\x18\x03 \x01(\x0e\x32\x13.assoc.v1.AssocTypeR\x05\x61Type\x12%\n\x05\x61ssoc\x18\x04 \x01(\x0b\x32\x0f.assoc.v1.AssocR\x05\x61ssoc\x12;\n\x0bupdate_mask\x18\x05 \x01(\x0b\x32\x1a.google.protobuf.FieldMaskR\nupdateMask\"n\n\x13UpdateAssocResponse\x12\x12\n\x04\x63ode\x18\x01 \x01(\x05R\x04\x63ode\x12\x18\n\x07message\x18\x02 \x01(\tR\x07message\x12)\n\x07\x64\x65tails\x18\x03 \x01(\x0b\x32\x0f.assoc.v1.AssocR\x07\x64\x65tails\"\xa2\x02\n\x11ListAssocsRequest\x12\x16\n\x06parent\x18\x01 \x01(\tR\x06parent\x12<\n\rcommon_option\x18\x02 \x01(\x0b\x32\x17.common.v1.CommonOptionR\x0c\x63ommonOption\x12<\n\rcommon_filter\x18\x03 \x01(\x0b\x32\x17.common.v1.CommonFilterR\x0c\x63ommonFilter\x12\x37\n\tread_mask\x18\x04 \x01(\x0b\x32\x1a.google.protobuf.FieldMaskR\x08readMask\x12@\n\roption_filter\x18\x05 \x01(\x0b\x32\x1b.assoc.v1.AssocOptionFilterR\x0coptionFilter\"\x96\x02\n\x12ListAssocsResponse\x12\x12\n\x04\x63ode\x18\x01 \x01(\x05R\x04\x63ode\x12\x18\n\x07message\x18\x02 \x01(\tR\x07message\x12>\n\x07\x64\x65tails\x18\x03 \x01(\x0b\x32$.assoc.v1.ListAssocsResponse.DetailsR\x07\x64\x65tails\x1a\x91\x01\n\x07\x44\x65tails\x12%\n\x05items\x18\x01 \x03(\x0b\x32\x0f.assoc.v1.AssocR\x05items\x12>\n\x0cnext_request\x18\x02 \x01(\x0b\x32\x1b.assoc.v1.ListAssocsRequestR\x0bnextRequest\x12\x1f\n\x0btotal_count\x18\x03 \x01(\x05R\ntotalCount\"n\n\x12\x44\x65leteAssocRequest\x12\x17\n\x07\x66rom_id\x18\x01 \x01(\x03R\x06\x66romId\x12\x13\n\x05to_id\x18\x02 \x01(\x03R\x04toId\x12*\n\x06\x61_type\x18\x03 \x01(\x0e\x32\x13.assoc.v1.AssocTypeR\x05\x61Type\"n\n\x13\x44\x65leteAssocResponse\x12\x12\n\x04\x63ode\x18\x01 \x01(\x05R\x04\x63ode\x12\x18\n\x07message\x18\x02 \x01(\tR\x07message\x12)\n\x07\x64\x65tails\x18\x03 \x01(\x0b\x32\x0f.assoc.v1.AssocR\x07\x64\x65tails\"\xad\x01\n\x13\x44\x65leteAssocsRequest\x12\x16\n\x06parent\x18\x01 \x01(\tR\x06parent\x12<\n\rcommon_filter\x18\x02 \x01(\x0b\x32\x17.common.v1.CommonFilterR\x0c\x63ommonFilter\x12@\n\roption_filter\x18\x03 \x01(\x0b\x32\x1b.assoc.v1.AssocOptionFilterR\x0coptionFilter\"^\n\x14\x44\x65leteAssocsResponse\x12\x12\n\x04\x63ode\x18\x01 \x01(\x05R\x04\x63ode\x12\x18\n\x07message\x18\x02 \x01(\tR\x07message\x12\x18\n\x07\x64\x65tails\x18\x03 \x03(\x03R\x07\x64\x65tails\"Y\n\x12\x43ountAssocsRequest\x12\x17\n\x07\x66rom_id\x18\x01 \x01(\x03R\x06\x66romId\x12*\n\x06\x61_type\x18\x02 \x01(\x0e\x32\x13.assoc.v1.AssocTypeR\x05\x61Type\"]\n\x13\x43ountAssocsResponse\x12\x12\n\x04\x63ode\x18\x01 \x01(\x05R\x04\x63ode\x12\x18\n\x07message\x18\x02 \x01(\tR\x07message\x12\x18\n\x07\x64\x65tails\x18\x03 \x01(\x03R\x07\x64\x65tails\"k\n\x0fGetAssocRequest\x12\x17\n\x07\x66rom_id\x18\x01 \x01(\x03R\x06\x66romId\x12*\n\x06\x61_type\x18\x02 \x01(\x0e\x32\x13.assoc.v1.AssocTypeR\x05\x61Type\x12\x13\n\x05to_id\x18\x03 \x01(\x03R\x04toId\"k\n\x10GetAssocResponse\x12\x12\n\x04\x63ode\x18\x01 \x01(\x05R\x04\x63ode\x12\x18\n\x07message\x18\x02 \x01(\tR\x07message\x12)\n\x07\x64\x65tails\x18\x03 \x01(\x0b\x32\x0f.assoc.v1.AssocR\x07\x64\x65tails\"\xfa\x01\n\x05\x41ssoc\x12\x0e\n\x02id\x18\x01 \x01(\x03R\x02id\x12\x1b\n\tcreate_at\x18\x02 \x01(\tR\x08\x63reateAt\x12\x1b\n\tupdate_at\x18\x03 \x01(\tR\x08updateAt\x12\x17\n\x07\x66rom_id\x18\x04 \x01(\x03R\x06\x66romId\x12\x13\n\x05to_id\x18\x05 \x01(\x03R\x04toId\x12*\n\x06\x61_type\x18\x06 \x01(\x0e\x32\x13.assoc.v1.AssocTypeR\x05\x61Type\x12\x12\n\x04meta\x18\x07 \x01(\tR\x04meta:9\xea\x41\x36\n\'github.com.sylvan.auro.apis/AssocEntity\x12\x0b\x61ssocs/{id}\"o\n\x11\x41ssocOptionFilter\x12\x17\n\x07\x66rom_id\x18\x01 \x01(\x03R\x06\x66romId\x12*\n\x06\x61_type\x18\x02 \x01(\x0e\x32\x13.assoc.v1.AssocTypeR\x05\x61Type\x12\x15\n\x06to_ids\x18\x03 \x03(\x03R\x05toIds*\xfc\x01\n\tAssocType\x12\x1a\n\x16\x41SSOC_TYPE_UNSPECIFIED\x10\x00\x12\x14\n\x10\x41SSOC_TYPE_TAGBY\x10\x01\x12\x14\n\x10\x41SSOC_TYPE_TAGTO\x10\x02\x12\x19\n\x15\x41SSOC_TYPE_CREATED_BY\x10\x03\x12\x1b\n\x17\x41SSOC_TYPE_CREATED_WITH\x10\x04\x12\x1b\n\x17\x41SSOC_TYPE_INHERIT_FROM\x10\x05\x12\x18\n\x14\x41SSOC_TYPE_EXTEND_TO\x10\x06\x12\x1b\n\x17\x41SSOC_TYPE_FLOW_HISTORY\x10\x07\x12\x1b\n\x17\x41SSOC_TYPE_HISTORY_FLOW\x10\x08\x32\xe7\x06\n\x0c\x41ssocService\x12t\n\x0b\x43reateAssoc\x12\x1c.assoc.v1.CreateAssocRequest\x1a\x1d.assoc.v1.CreateAssocResponse\"(\xda\x41\x0cparent,assoc\x82\xd3\xe4\x93\x02\x13\"\n/v1/assocs:\x05\x61ssoc\x12\x82\x01\n\x0bUpdateAssoc\x12\x1c.assoc.v1.UpdateAssocRequest\x1a\x1d.assoc.v1.UpdateAssocResponse\"6\x82\xd3\xe4\x93\x02\x30\x32+/v1/assocs/{from_id=*}/{a_type=*}/{to_id=*}:\x01*\x12h\n\nListAssocs\x12\x1b.assoc.v1.ListAssocsRequest\x1a\x1c.assoc.v1.ListAssocsResponse\"\x1f\x82\xd3\xe4\x93\x02\x19\"\x14/v1/assocs/resources:\x01*\x12}\n\x0b\x44\x65leteAssoc\x12\x1c.assoc.v1.DeleteAssocRequest\x1a\x1d.assoc.v1.DeleteAssocResponse\"1\x82\xd3\xe4\x93\x02+*)/v1/assocs/{from_id=*}/{a_type=*}/{to_id}\x12\x64\n\x0c\x44\x65leteAssocs\x12\x1d.assoc.v1.DeleteAssocsRequest\x1a\x1e.assoc.v1.DeleteAssocsResponse\"\x15\x82\xd3\xe4\x93\x02\x0f\x32\n/v1/assocs:\x01*\x12u\n\x0b\x43ountAssocs\x12\x1c.assoc.v1.CountAssocsRequest\x1a\x1d.assoc.v1.CountAssocsResponse\")\x82\xd3\xe4\x93\x02#\x12!/v1/assocs/{from_id=*}/{a_type=*}\x12v\n\x08GetAssoc\x12\x19.assoc.v1.GetAssocRequest\x1a\x1a.assoc.v1.GetAssocResponse\"3\x82\xd3\xe4\x93\x02-\x12+/v1/assocs/{from_id=*}/{a_type=*}/{to_id=*}\x1a\x1e\xca\x41\x1bgithub.com/sylvan/auro/apisB.Z,github.com/sylvan/auro/apis/assoc/v1;assocv1b\x06proto3')

_builder.BuildMessageAndEnumDescriptors(DESCRIPTOR, globals())
_builder.BuildTopDescriptorsAndMessages(DESCRIPTOR, 'assoc.v1.assoc_service_pb2', globals())
if _descriptor._USE_C_DESCRIPTORS == False:

  DESCRIPTOR._options = None
  DESCRIPTOR._serialized_options = b'Z,github.com/sylvan/auro/apis/assoc/v1;assocv1'
  _ASSOC._options = None
  _ASSOC._serialized_options = b'\352A6\n\'github.com.sylvan.auro.apis/AssocEntity\022\013assocs/{id}'
  _ASSOCSERVICE._options = None
  _ASSOCSERVICE._serialized_options = b'\312A\033github.com/sylvan/auro/apis'
  _ASSOCSERVICE.methods_by_name['CreateAssoc']._options = None
  _ASSOCSERVICE.methods_by_name['CreateAssoc']._serialized_options = b'\332A\014parent,assoc\202\323\344\223\002\023\"\n/v1/assocs:\005assoc'
  _ASSOCSERVICE.methods_by_name['UpdateAssoc']._options = None
  _ASSOCSERVICE.methods_by_name['UpdateAssoc']._serialized_options = b'\202\323\344\223\00202+/v1/assocs/{from_id=*}/{a_type=*}/{to_id=*}:\001*'
  _ASSOCSERVICE.methods_by_name['ListAssocs']._options = None
  _ASSOCSERVICE.methods_by_name['ListAssocs']._serialized_options = b'\202\323\344\223\002\031\"\024/v1/assocs/resources:\001*'
  _ASSOCSERVICE.methods_by_name['DeleteAssoc']._options = None
  _ASSOCSERVICE.methods_by_name['DeleteAssoc']._serialized_options = b'\202\323\344\223\002+*)/v1/assocs/{from_id=*}/{a_type=*}/{to_id}'
  _ASSOCSERVICE.methods_by_name['DeleteAssocs']._options = None
  _ASSOCSERVICE.methods_by_name['DeleteAssocs']._serialized_options = b'\202\323\344\223\002\0172\n/v1/assocs:\001*'
  _ASSOCSERVICE.methods_by_name['CountAssocs']._options = None
  _ASSOCSERVICE.methods_by_name['CountAssocs']._serialized_options = b'\202\323\344\223\002#\022!/v1/assocs/{from_id=*}/{a_type=*}'
  _ASSOCSERVICE.methods_by_name['GetAssoc']._options = None
  _ASSOCSERVICE.methods_by_name['GetAssoc']._serialized_options = b'\202\323\344\223\002-\022+/v1/assocs/{from_id=*}/{a_type=*}/{to_id=*}'
  _ASSOCTYPE._serialized_start=2544
  _ASSOCTYPE._serialized_end=2796
  _CREATEASSOCREQUEST._serialized_start=181
  _CREATEASSOCREQUEST._serialized_end=264
  _CREATEASSOCRESPONSE._serialized_start=266
  _CREATEASSOCRESPONSE._serialized_end=376
  _UPDATEASSOCREQUEST._serialized_start=379
  _UPDATEASSOCREQUEST._serialized_end=589
  _UPDATEASSOCRESPONSE._serialized_start=591
  _UPDATEASSOCRESPONSE._serialized_end=701
  _LISTASSOCSREQUEST._serialized_start=704
  _LISTASSOCSREQUEST._serialized_end=994
  _LISTASSOCSRESPONSE._serialized_start=997
  _LISTASSOCSRESPONSE._serialized_end=1275
  _LISTASSOCSRESPONSE_DETAILS._serialized_start=1130
  _LISTASSOCSRESPONSE_DETAILS._serialized_end=1275
  _DELETEASSOCREQUEST._serialized_start=1277
  _DELETEASSOCREQUEST._serialized_end=1387
  _DELETEASSOCRESPONSE._serialized_start=1389
  _DELETEASSOCRESPONSE._serialized_end=1499
  _DELETEASSOCSREQUEST._serialized_start=1502
  _DELETEASSOCSREQUEST._serialized_end=1675
  _DELETEASSOCSRESPONSE._serialized_start=1677
  _DELETEASSOCSRESPONSE._serialized_end=1771
  _COUNTASSOCSREQUEST._serialized_start=1773
  _COUNTASSOCSREQUEST._serialized_end=1862
  _COUNTASSOCSRESPONSE._serialized_start=1864
  _COUNTASSOCSRESPONSE._serialized_end=1957
  _GETASSOCREQUEST._serialized_start=1959
  _GETASSOCREQUEST._serialized_end=2066
  _GETASSOCRESPONSE._serialized_start=2068
  _GETASSOCRESPONSE._serialized_end=2175
  _ASSOC._serialized_start=2178
  _ASSOC._serialized_end=2428
  _ASSOCOPTIONFILTER._serialized_start=2430
  _ASSOCOPTIONFILTER._serialized_end=2541
  _ASSOCSERVICE._serialized_start=2799
  _ASSOCSERVICE._serialized_end=3670
# @@protoc_insertion_point(module_scope)
