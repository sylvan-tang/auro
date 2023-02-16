FROM python:3.8.13

RUN pip install --no-cache-dir \
    requests==2.27.1 \
    pymongo==3.7.2 \
    j2y==0.3.0 \
    packaging==21.1 \
    setuptools==56.0.0 \
    setuptools_scm==6.4.2 \
    setuptools-git-versioning==1.8.1 \
    wheel==0.36.2 \
    twine==3.8.0

RUN pip install --no-cache-dir \
    pdm==2.3.2 \
    pdm-pep517==1.0.6 && \
    pdm config -g cache_dir /cache/.pdm
RUN python -m pip install -U pdm

RUN pip install ipython
RUN apt update
RUN apt install vim -y
RUN apt install git

WORKDIR /work
COPY ./dockers/python-base/source_python_path.sh ./
RUN bash /work/source_python_path.sh
WORKDIR /auro
