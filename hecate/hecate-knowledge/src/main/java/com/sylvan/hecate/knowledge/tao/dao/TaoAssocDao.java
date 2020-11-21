package com.sylvan.hecate.knowledge.tao.dao;

import com.sylvan.hecate.knowledge.tao.model.TaoAssoc;
import com.sylvan.hecate.knowledge.tao.model.TaoAssocType;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** 参考：https://www.usenix.org/system/files/conference/atc13/atc13-bronson.pdf */
public interface TaoAssocDao {
  /**
   * 添加关系
   *
   * @param fromId：关系发出方
   * @param assocType：关系类型
   * @param toId：关系接收方
   * @param createdAt：关系创建时间戳
   * @param meta：关系详细信息
   * @return : 操作是否成功
   */
  boolean assocAdd(
      long fromId, TaoAssocType assocType, long toId, long createdAt, Map<String, Object> meta);

  /**
   * 删除关系
   *
   * @param fromId：关系发出方
   * @param assocType：关系类型
   * @param toId：关系接收方
   * @return : 操作是否成功
   */
  boolean assocDelete(long fromId, TaoAssocType assocType, long toId);

  /**
   * 修改关系类型
   *
   * @param fromId：关系发出方
   * @param fromType：原有关系类型
   * @param toId：关系接收方
   * @param toType：目标关系类型
   * @return : 操作是否成功
   */
  boolean assocChangeType(long fromId, TaoAssocType fromType, long toId, TaoAssocType toType);

  /**
   * 查询关系列表
   *
   * @param fromId：关系发出方
   * @param assocType：关系类型
   * @param toIds：关系接收方(多个)
   * @param highTime: createAt <= highTime if highTime is defined
   * @param lowTime: createAt >= lowTime if lowTime is defined
   * @return 符合查询条件的关系对象列表
   */
  List<TaoAssoc> assocGet(
      long fromId, TaoAssocType assocType, Set<Long> toIds, Long highTime, Long lowTime);

  /**
   * 查询关系数量
   *
   * @param fromId：关系发出方
   * @param assocType：关系类型
   * @return 关系发出方发出的关系数量
   */
  long assocCount(long fromId, TaoAssocType assocType);

  /**
   * 翻页查询关系列表
   *
   * @param fromId：关系发出方
   * @param assocType：关系类型
   * @param cursor：游标起点
   * @param limit：每页数量
   * @return [cursor, cursor+limit)
   */
  List<TaoAssoc> assocRange(long fromId, TaoAssocType assocType, String cursor, int limit);

  /**
   * 根据时间查询关系列表
   *
   * @param fromId：关系发出方
   * @param assocType：关系类型
   * @param highTime：查询时间从 <= highTime 的关系开始
   * @param lowTime：createdAt >= lowTime 必须满足
   * @param limit：每页数量
   * @return [highTime, lowTime] 从高到底排序，最多取 limit 个关系
   */
  List<TaoAssoc> assocTimeRange(
      long fromId, TaoAssocType assocType, long highTime, long lowTime, int limit);
}
