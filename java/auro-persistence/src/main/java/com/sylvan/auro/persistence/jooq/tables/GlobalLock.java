/*
 * This file is generated by jOOQ.
 */
package com.sylvan.auro.persistence.jooq.tables;

import com.sylvan.auro.persistence.converter.TimestampConverter;
import com.sylvan.auro.persistence.jooq.DefaultSchema;
import com.sylvan.auro.persistence.jooq.Indexes;
import com.sylvan.auro.persistence.jooq.Keys;
import com.sylvan.auro.persistence.jooq.tables.records.GlobalLockRecord;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

/** 全局锁表 */
@Generated(
    value = {"http://www.jooq.org", "jOOQ version:3.11.12"},
    comments = "This class is generated by jOOQ")
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class GlobalLock extends TableImpl<GlobalLockRecord> {

  private static final long serialVersionUID = 1319643161;

  /** The reference instance of <code>global_lock</code> */
  public static final GlobalLock GLOBAL_LOCK = new GlobalLock();

  /** The class holding records for this type */
  @Override
  public Class<GlobalLockRecord> getRecordType() {
    return GlobalLockRecord.class;
  }

  /** The column <code>global_lock.id</code>. 自增 ID */
  public final TableField<GlobalLockRecord, Integer> ID =
      createField(
          "id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "自增 ID");

  /** The column <code>global_lock.key</code>. 锁 */
  public final TableField<GlobalLockRecord, String> KEY =
      createField(
          "key",
          org.jooq.impl.SQLDataType.VARCHAR(255)
              .nullable(false)
              .defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)),
          this,
          "锁");

  /** The column <code>global_lock.holder</code>. 持有者 */
  public final TableField<GlobalLockRecord, String> HOLDER =
      createField(
          "holder",
          org.jooq.impl.SQLDataType.VARCHAR(255)
              .nullable(false)
              .defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)),
          this,
          "持有者");

  /** The column <code>global_lock.expire_ms</code>. 锁失效时间，单位为毫秒，默认 -1 代表永久有效 */
  public final TableField<GlobalLockRecord, Long> EXPIRE_MS =
      createField(
          "expire_ms",
          org.jooq.impl.SQLDataType.BIGINT
              .nullable(false)
              .defaultValue(org.jooq.impl.DSL.inline("-1", org.jooq.impl.SQLDataType.BIGINT)),
          this,
          "锁失效时间，单位为毫秒，默认 -1 代表永久有效");

  /** The column <code>global_lock.created_at</code>. 创建时间 */
  public final TableField<GlobalLockRecord, Long> CREATED_AT =
      createField(
          "created_at",
          org.jooq.impl.SQLDataType.TIMESTAMP
              .nullable(false)
              .defaultValue(
                  org.jooq.impl.DSL.field(
                      "CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)),
          this,
          "创建时间",
          new TimestampConverter());

  /** The column <code>global_lock.updated_at</code>. 最后修改时间 */
  public final TableField<GlobalLockRecord, Long> UPDATED_AT =
      createField(
          "updated_at",
          org.jooq.impl.SQLDataType.TIMESTAMP
              .nullable(false)
              .defaultValue(
                  org.jooq.impl.DSL.field(
                      "CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)),
          this,
          "最后修改时间",
          new TimestampConverter());

  /** Create a <code>global_lock</code> table reference */
  public GlobalLock() {
    this(DSL.name("global_lock"), null);
  }

  /** Create an aliased <code>global_lock</code> table reference */
  public GlobalLock(String alias) {
    this(DSL.name(alias), GLOBAL_LOCK);
  }

  /** Create an aliased <code>global_lock</code> table reference */
  public GlobalLock(Name alias) {
    this(alias, GLOBAL_LOCK);
  }

  private GlobalLock(Name alias, Table<GlobalLockRecord> aliased) {
    this(alias, aliased, null);
  }

  private GlobalLock(Name alias, Table<GlobalLockRecord> aliased, Field<?>[] parameters) {
    super(alias, null, aliased, parameters, DSL.comment("全局锁表"));
  }

  public <O extends Record> GlobalLock(Table<O> child, ForeignKey<O, GlobalLockRecord> key) {
    super(child, key, GLOBAL_LOCK);
  }

  /** {@inheritDoc} */
  @Override
  public Schema getSchema() {
    return DefaultSchema.DEFAULT_SCHEMA;
  }

  /** {@inheritDoc} */
  @Override
  public List<Index> getIndexes() {
    return Arrays.<Index>asList(Indexes.GLOBAL_LOCK_IDX_KEY, Indexes.GLOBAL_LOCK_PRIMARY);
  }

  /** {@inheritDoc} */
  @Override
  public Identity<GlobalLockRecord, Integer> getIdentity() {
    return Keys.IDENTITY_GLOBAL_LOCK;
  }

  /** {@inheritDoc} */
  @Override
  public UniqueKey<GlobalLockRecord> getPrimaryKey() {
    return Keys.KEY_GLOBAL_LOCK_PRIMARY;
  }

  /** {@inheritDoc} */
  @Override
  public List<UniqueKey<GlobalLockRecord>> getKeys() {
    return Arrays.<UniqueKey<GlobalLockRecord>>asList(
        Keys.KEY_GLOBAL_LOCK_PRIMARY, Keys.KEY_GLOBAL_LOCK_IDX_KEY);
  }

  /** {@inheritDoc} */
  @Override
  public GlobalLock as(String alias) {
    return new GlobalLock(DSL.name(alias), this);
  }

  /** {@inheritDoc} */
  @Override
  public GlobalLock as(Name alias) {
    return new GlobalLock(alias, this);
  }

  /** Rename this table */
  @Override
  public GlobalLock rename(String name) {
    return new GlobalLock(DSL.name(name), null);
  }

  /** Rename this table */
  @Override
  public GlobalLock rename(Name name) {
    return new GlobalLock(name, null);
  }
}
