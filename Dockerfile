FROM neo4j/neo4j:2.2.5
MAINTAINER Max De Marzi<max@neo4j.com>

ENV MAVEN_VERSION 3.2.3

RUN apt-get update \
    && apt-get install -y curl

RUN bash -c 'echo "org.neo4j.server.thirdparty_jaxrs_classes=com.maxdemarzi=/v1" >> /var/lib/neo4j/conf/neo4j-server.properties'


