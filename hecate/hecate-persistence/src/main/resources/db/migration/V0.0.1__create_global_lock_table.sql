DROP TABLE IF EXISTS `global_lock`;
CREATE TABLE if not exists `global_lock`
(
    `id`                  int(11)      NOT NULL AUTO_INCREMENT COMMENT '自增 ID',
    `key`                 varchar(255) NOT NULL DEFAULT '' COMMENT '锁',
    `holder`           varchar(255) NOT NULL DEFAULT '' COMMENT '持有者',
    `expire_ms`            int(11)     NOT NULL DEFAULT -1 COMMENT '锁失效时间，单位为毫秒，默认 -1 代表永久有效',
    `created_at`          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_key` (`key`)
    ) ENGINE = InnoDB
    AUTO_INCREMENT = 4
    DEFAULT CHARSET = utf8mb4 COMMENT '全局锁表';