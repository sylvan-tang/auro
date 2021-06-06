# Java 相关知识

## 更新 MySQL schema

1. 将代码更新至最新版本，如果要彻底重建MySQL并生成最新的jooq代码，请跳过以下所有步骤直接执行`./bin/init-db.sh`
2. 在模块目录下执行 `mvn -pl hecate-persistence -Dflyway.skip=false flyway:migrate` 应用其他人对数据的更新到本地数据库
3. 如果需要修改数据库schema，在 `hecate-persistence/src/main/resources/db/migration`
   目录下创建新的sql文件，输入具体的DDL，注意文件名必须以`V{版本号}__`开头
4. 执行第2步，flyway组件会根据序号去更新本地数据库
5. 如果需要清除flyway创建的表，执行`mvn -pl hecate-persistence -Dflyway.skip=false flyway:clean`
6. 如果要根据最新的MySQL
   schema生成jooq代码，在项目根目录下执行`mvn -pl hecate-persistence -Djooq.codegen.skip=false jooq-codegen:generate`
