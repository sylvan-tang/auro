package com.sylvan.hecate.knowledge.tao.dao;

import com.sylvan.hecate.knowledge.tao.model.TaoObject;
import com.sylvan.hecate.knowledge.tao.model.TaoObjectType;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/** @author sylvan */
public interface TaoObjectDao {
  /**
   * 添加对象
   *
   * @param oId：对象 ID
   * @param objectType：对象类型
   * @param values：具体数据
   * @return 操作是否成功
   */
  boolean add(long oId, TaoObjectType objectType, Map<String, Object> values);

  /**
   * 查询对象
   *
   * @param oId：对象 ID
   * @return ：对象
   */
  Optional<TaoObject> get(long oId);

  /**
   * 批量查询对象
   *
   * @param ids：对象集合
   * @return ：对象数据
   */
  Map<Long, TaoObject> batchGet(Set<Long> ids);

  /**
   * 更新对象数据
   *
   * @param oId：对象 ID
   * @param objectType：对象需要更新的类型
   * @param values：对象需要更新的数据
   * @return ：操作是否成功
   */
  boolean update(long oId, TaoObjectType objectType, Map<String, Object> values);

  /**
   * 删除对象
   *
   * @param oId：对象 ID
   * @return ：操作是否成功
   */
  boolean delete(long oId);
}
