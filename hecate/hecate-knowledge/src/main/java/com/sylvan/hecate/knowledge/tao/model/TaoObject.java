package com.sylvan.hecate.knowledge.tao.model;

import java.util.Map;

public class TaoObject {
  private long id;
  private TaoObjectType objectType;
  private Map<String, Object> values;
  private long createdAt;
  private long updatedAt;
}
