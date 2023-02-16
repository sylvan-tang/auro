#!/usr/bin/env bash
set -e

PROJECT_PATH="$(cd "$(dirname "${BASH_SOURCE[0]}")" && cd .. && pwd)"

gen() {
  local i=0
  local n
  # 获取 db name, /migrations directory, then execute sql.
  if [[ -f "$PROJECT_PATH/mysql/create_db.sql" ]]; then
    rm "$PROJECT_PATH/mysql/create_db.sql"
  fi
  for dbname in $(ls -l $PROJECT_PATH/mysql | grep "^d" | awk '{print $NF}'); do
    echo "CREATE DATABASE /*!32312 IF NOT EXISTS*/ \`$dbname\` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;" >>"$PROJECT_PATH/mysql/create_db.sql"
  done
  echo "COPY ./mysql/create_db.sql /docker-entrypoint-initdb.d/$(printf "%06d" $i).sql"
  ((i++))
  cd "$PROJECT_PATH" && find "./mysql" -type f -name '*.up.sql' |
    sort |
    while read -r n; do
      echo "COPY $n /docker-entrypoint-initdb.d/$(printf "%06d" "$i").sql"
      ((i++))
    done
}

main() {
  # Generate dockerfile
  local dockerfile="${PROJECT_PATH}/dockers/mysql.dockerfile"
  cat >"$dockerfile" <<DOCKERFILE
FROM mysql:5.7.29
$(gen)
DOCKERFILE
  echo "[DEBUG] generated dockerfile:"
  cat "$dockerfile"
}

main
