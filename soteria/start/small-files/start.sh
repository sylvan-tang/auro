#!/usr/bin/env bash

# make sure you have spark installed on server
# make sure you have the jar package on server
# read soteria/README.md for more information
current_path=$(pwd)
# shellcheck disable=SC2046
# shellcheck disable=SC2164
shell_path=$(
  cd $(dirname "${BASH_SOURCE[0]}")
  pwd
)
# shellcheck disable=SC2046
soteria_path=$(dirname $(dirname "$shell_path"))

# shellcheck disable=SC2164
cd "$soteria_path"

sbt soteriaSmallFiles:assembly

# shellcheck disable=SC2034
folder_path="/tmp/smalls/"
log_path="/tmp/smalls.log"
output_path="./smalls.log"

# generate files and test normal reader
# 10000 10 10000 100 10000 1000 10000 10000 200000 100000 1000000 500000
#my_array=(10000 10 10000 100 10000 1000 10000 10000 200000 100000 1000000 500000)
#i=0
#while [[ $i -lt ${#my_array[*]} ]]
#do
#  data_num=${my_array[$i]}
#  let i++
#  file_num=${my_array[$i]}
#  let i++
#
#  mkdir "/tmp/blank"
#  rsync --delete-before -d "/tmp/blank/" "$folder_path"
#  rm -rf "$folder_path"
#
#  spark-submit \
#    --class com.sylvan.soteria.bigdata.files.Main \
#    --executor-memory 2G \
#    --driver-memory 4G \
#    ${shell_path}/target/scala-2.11/soteria-small-files-assembly-0.1.0-SNAPSHOT.jar "$folder_path" "$data_num" "$file_num" -1 > ${log_path} 2>&1
#  grep "cost" ${log_path} >> "$output_path"
#done

#for i in 10000 1000 100 10
#do
#  spark-submit \
#  --class com.sylvan.soteria.bigdata.files.Main \
#  --executor-memory 2G \
#  --driver-memory 4G \
#  ${shell_path}/target/scala-2.11/soteria-small-files-assembly-0.1.0-SNAPSHOT.jar "$folder_path" 200000 100000 "$i" > ${log_path} 2>&1
#  grep "cost" ${log_path} >> "$output_path"
#done

# shellcheck disable=SC2164
cd "$current_path"
