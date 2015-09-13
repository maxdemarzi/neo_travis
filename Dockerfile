FROM neo4j:neo4j

COPY target/travis-1.0.jar /var/lib/neo4j/plugins

RUN bash -c 'echo "org.neo4j.server.thirdparty_jaxrs_classes=com.maxdemarzi=/v1" >> /var/lib/neo4j/conf/neo4j-server.properties'


