FROM sbtscala/scala-sbt:eclipse-temurin-jammy-8u352-b08_1.8.2_3.2.2

RUN apt update
RUN apt install gcc make wget -y

WORKDIR /tmp
COPY ./soteria /tmp/soteria
RUN cd /tmp/soteria && sbt clean assembly
WORKDIR /auro

