package com.sylvan.jasper.common.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

  private JsonUtils() {}

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

  /** Deprecated, use {@code marshal} */
  @Deprecated(forRemoval = true)
  public static Optional<String> toJSON(Object object) {
    try {
      return Optional.of(marshal(object));
    } catch (IOException e) {
      log.error("toJson error, object: {}", object, e);
    }

    return Optional.empty();
  }

  /** Deprecated, use {@code unmarshal} */
  @Deprecated(forRemoval = true)
  public static <T> Optional<T> toObject(String jsonString, Class<T> clazz) {
    Preconditions.checkNotNull(jsonString);

    try {
      return Optional.of(OBJECT_MAPPER.readValue(jsonString, clazz));
    } catch (Exception e) {
      log.error("toObject error, jsonString: {}", jsonString, e);
    }

    return Optional.empty();
  }

  /** Deprecated, use {@code unmarshal} */
  @Deprecated(forRemoval = true)
  public static <T> List<T> toList(String jsonString, Class<T> clazz) {

    Preconditions.checkNotNull(jsonString);

    try {
      return OBJECT_MAPPER.readValue(
          jsonString, OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
    } catch (Exception e) {
      log.error("toList error, jsonString: {}", jsonString, e);
    }

    return Collections.emptyList();
  }
}
