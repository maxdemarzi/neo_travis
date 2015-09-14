# neo_travis
Example Neo4j Extension Testing using Travis-CI

- [![Build Status](https://secure.travis-ci.org/maxdemarzi/neo_travis.png?branch=master)](http://travis-ci.org/maxdemarzi/neo_travis)
- [![Coverage Status](https://coveralls.io/repos/maxdemarzi/neo_travis/badge.svg?branch=master&service=github)](https://coveralls.io/github/maxdemarzi/neo_travis?branch=master)

# Instructions

1. Build it:

        mvn clean package

2. Copy target/travis-1.0.jar to the plugins/ directory of your Neo4j server.

3. Configure Neo4j by adding a line to conf/neo4j-server.properties:

        org.neo4j.server.thirdparty_jaxrs_classes=com.maxdemarzi=/v1
        
4. Start Neo4j server.

5. Create some test data:

        CREATE (user1:User {user_id:'u1', name:'Max'})
        CREATE (friend1:User {user_id:'f1', name:'Michael'})
        CREATE (friend2:User {user_id:'f2', name:'Peter'})
        CREATE (friend3:User {user_id:'f3', name:'David'})
        CREATE (user1)-[:FRIENDS]->(friend1)
        CREATE (user1)-[:FRIENDS]->(friend2)
        CREATE (user1)-[:FRIENDS]->(friend3)
        
6. Try it:
        
        :GET /v1/service/friends/u1

7. Test it.