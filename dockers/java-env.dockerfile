FROM adoptopenjdk/openjdk11

RUN apt update
RUN apt install gcc make maven -y

WORKDIR /auro
