package com.sylvan.hecate.persistence.converter;

import java.sql.Timestamp;
import java.time.Duration;
import org.jooq.Converter;

public class TimestampConverter implements Converter<Timestamp, Long> {
  private static final long TIME_ZONE_DRIFT = Duration.ofHours(8).toMillis();

  @Override
  public Long from(Timestamp databaseObject) {
    return databaseObject.getTime() + TIME_ZONE_DRIFT;
  }

  @Override
  public Timestamp to(Long userObject) {
    return new Timestamp(userObject - TIME_ZONE_DRIFT);
  }

  @Override
  public Class<Timestamp> fromType() {
    return Timestamp.class;
  }

  @Override
  public Class<Long> toType() {
    return Long.class;
  }
}
