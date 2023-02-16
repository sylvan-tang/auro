package com.sylvan.auro.common.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtils {

  private static final ObjectMapper OBJECT_MAPPER =
      new ObjectMapper()
          .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
          .configure(JsonParser.Feature.IGNORE_UNDEFINED, true)
          .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
          .findAndRegisterModules();

  public static <T> String marshal(T target) throws IOException {
    Preconditions.checkNotNull(target);
    return OBJECT_MAPPER.writeValueAsString(target);
  }

  public static <T> byte[] marshalToBytes(T target) throws IOException {
    Preconditions.checkNotNull(target);

    return OBJECT_MAPPER.writeValueAsBytes(target);
  }

  public static <T> T unmarshal(String json, Class<T> clazz) throws IOException {
    Preconditions.checkNotNull(json);
    Preconditions.checkNotNull(clazz);

    return OBJECT_MAPPER.readValue(json, clazz);
  }

  public static <T> T unmarshal(String json, TypeReference<T> typeRef) throws IOException {
    Preconditions.checkNotNull(json);
    Preconditions.checkNotNull(typeRef);
    return OBJECT_MAPPER.readValue(json, typeRef);
  }

  public static <T> T unmarshalFromBytes(byte[] json, Class<T> clazz) throws IOException {
    Preconditions.checkNotNull(json);
    Preconditions.checkNotNull(clazz);

    return OBJECT_MAPPER.readValue(json, clazz);
  }

  public static boolean isSimpleObjectNode(JsonNode src) {
    return src.isObject() && getObjectValueStream(src).allMatch(JsonNode::isValueNode);
  }

  public static boolean isObjectOfSimpleObjectNode(JsonNode src) {
    return src.isObject() && getObjectValueStream(src).allMatch(JsonUtils::isSimpleObjectNode);
  }

  public static Stream<JsonNode> getObjectValueStream(JsonNode src) {
    Preconditions.checkState(src.isContainerNode());
    return StreamSupport.stream(Spliterators.spliteratorUnknownSize(src.elements(), 0), false);
  }
}
