#!/bin/bash
if [[ "$#" -le 0 ]];then
    echo "input your app name with: $0 {app_name}"
    exit 1
fi
app_name=$1
docker run --rm \
	    --volume $(pwd):/mnt \
	    node:slim bash -c "cd /mnt && npx create-react-app $app_name -y "
