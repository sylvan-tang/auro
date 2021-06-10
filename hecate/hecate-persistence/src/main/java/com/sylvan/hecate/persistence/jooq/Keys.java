/*
 * This file is generated by jOOQ.
 */
package com.sylvan.hecate.persistence.jooq;

import com.sylvan.hecate.persistence.jooq.tables.FlywaySchemaHistory;
import com.sylvan.hecate.persistence.jooq.tables.GlobalLock;
import com.sylvan.hecate.persistence.jooq.tables.records.FlywaySchemaHistoryRecord;
import com.sylvan.hecate.persistence.jooq.tables.records.GlobalLockRecord;
import javax.annotation.Generated;
import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;

/**
 * A class modelling foreign key relationships and constraints of tables of the <code></code>
 * schema.
 */
@Generated(
    value = {"http://www.jooq.org", "jOOQ version:3.11.12"},
    comments = "This class is generated by jOOQ")
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Keys {

  // -------------------------------------------------------------------------
  // IDENTITY definitions
  // -------------------------------------------------------------------------

  public static final Identity<GlobalLockRecord, Integer> IDENTITY_GLOBAL_LOCK =
      Identities0.IDENTITY_GLOBAL_LOCK;

  // -------------------------------------------------------------------------
  // UNIQUE and PRIMARY KEY definitions
  // -------------------------------------------------------------------------

  public static final UniqueKey<FlywaySchemaHistoryRecord> KEY_FLYWAY_SCHEMA_HISTORY_PRIMARY =
      UniqueKeys0.KEY_FLYWAY_SCHEMA_HISTORY_PRIMARY;
  public static final UniqueKey<GlobalLockRecord> KEY_GLOBAL_LOCK_PRIMARY =
      UniqueKeys0.KEY_GLOBAL_LOCK_PRIMARY;
  public static final UniqueKey<GlobalLockRecord> KEY_GLOBAL_LOCK_IDX_KEY =
      UniqueKeys0.KEY_GLOBAL_LOCK_IDX_KEY;

  // -------------------------------------------------------------------------
  // FOREIGN KEY definitions
  // -------------------------------------------------------------------------

  // -------------------------------------------------------------------------
  // [#1459] distribute members to avoid static initialisers > 64kb
  // -------------------------------------------------------------------------

  private static class Identities0 {
    public static Identity<GlobalLockRecord, Integer> IDENTITY_GLOBAL_LOCK =
        Internal.createIdentity(GlobalLock.GLOBAL_LOCK, GlobalLock.GLOBAL_LOCK.ID);
  }

  private static class UniqueKeys0 {
    public static final UniqueKey<FlywaySchemaHistoryRecord> KEY_FLYWAY_SCHEMA_HISTORY_PRIMARY =
        Internal.createUniqueKey(
            FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY,
            "KEY_flyway_schema_history_PRIMARY",
            FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.INSTALLED_RANK);
    public static final UniqueKey<GlobalLockRecord> KEY_GLOBAL_LOCK_PRIMARY =
        Internal.createUniqueKey(
            GlobalLock.GLOBAL_LOCK, "KEY_global_lock_PRIMARY", GlobalLock.GLOBAL_LOCK.ID);
    public static final UniqueKey<GlobalLockRecord> KEY_GLOBAL_LOCK_IDX_KEY =
        Internal.createUniqueKey(
            GlobalLock.GLOBAL_LOCK, "KEY_global_lock_idx_key", GlobalLock.GLOBAL_LOCK.KEY);
  }
}
