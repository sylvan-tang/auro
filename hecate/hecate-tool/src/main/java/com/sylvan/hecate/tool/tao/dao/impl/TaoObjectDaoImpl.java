package com.sylvan.hecate.tool.tao.dao.impl;

import com.sylvan.hecate.tool.tao.dao.TaoObjectDao;
import com.sylvan.hecate.tool.tao.model.TaoObject;
import com.sylvan.hecate.tool.tao.model.TaoObjectType;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class TaoObjectDaoImpl implements TaoObjectDao {
  @Override
  public boolean add(long oId, TaoObjectType objectType, Map<String, Object> values) {
    return false;
  }

  @Override
  public Optional<TaoObject> get(long oId) {
    return Optional.empty();
  }

  @Override
  public Map<Long, TaoObject> batchGet(Set<Long> ids) {
    return null;
  }

  @Override
  public boolean update(long oId, TaoObjectType objectType, Map<String, Object> values) {
    return false;
  }

  @Override
  public boolean delete(long oId) {
    return false;
  }
}
