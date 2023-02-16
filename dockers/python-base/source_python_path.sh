#!/bin/bash
PYTHONPATH=/opt/__pypackages__/3.8/lib
for pkg_name in site-packages dist-packages lib-dynload; do
  for pkg_path in $(find / -name ${pkg_name} -type d -print); do
    if [ ! -z "$pkg_path" ]; then
      PYTHONPATH=${PYTHONPATH}:${pkg_path}
    fi
  done
done
echo "export PYTHONPATH=${PYTHONPATH}" >>~/.bashrc
