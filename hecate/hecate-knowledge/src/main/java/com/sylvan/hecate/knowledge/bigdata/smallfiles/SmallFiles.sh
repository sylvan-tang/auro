#!/bin/bash

root_path=/tmp/smalls

echo "| 文件个数 | 并发数 | rm 时间 | rsync 时间 |"
echo "| ---- | ---- | ---- | ---- |"

my_array=(1000 1 1000 10 10000 10 20000 20 100000 20 200000 20 1000000 20 2000000 20)
i=1
while [[ $i -le ${#my_array[*]} ]]
do
    file_num=${my_array[$i]}
    let i++
    parallelism=${my_array[$i]}
    let i++
    rm_dir="$root_path/${file_num}/${parallelism}/rm"
    rsync_dir="$root_path/${file_num}/${parallelism}/rsync"
    start_time=$(date +%s)
    rm -rf ${rm_dir}
    # shellcheck disable=SC2006
    rm_time=$((`date '+%s'`-start_time))
    start_time=$(date +%s)
    rsync --delete-before -d /tmp/blank/ "$rsync_dir"
    # shellcheck disable=SC2006
    rsync_time=$((`date '+%s'`-start_time))
    echo "| $file_num | $parallelism | $rm_time | $rsync_time |"
done
