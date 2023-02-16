FROM mysql:5.7.29
COPY ./mysql/create_db.sql /docker-entrypoint-initdb.d/000000.sql
COPY ./mysql/assoc/20221107-180630-init-assoc.up.sql /docker-entrypoint-initdb.d/000001.sql
COPY ./mysql/echo/20221031-093318-init.up.sql /docker-entrypoint-initdb.d/000002.sql
