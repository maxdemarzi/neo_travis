package com.maxdemarzi;

import org.junit.Rule;
import org.junit.Test;
import org.neo4j.harness.junit.Neo4jRule;
import org.neo4j.test.server.HTTP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.assertTrue;

public class FriendsTest {
    
    @Rule
    public Neo4jRule neo4j = new Neo4jRule()
            .withFixture( CYPHER_STATEMENT )
            .withExtension("/v1", Service.class);

    @Test
    public void shouldRespondToFriends() throws IOException {
        HTTP.Response response = HTTP.GET(neo4j.httpURI().resolve("/v1/service/friends/u1").toString());
        ArrayList actual = response.content();

        HashSet expectedSet = new HashSet<>(expected);
        HashSet actualSet = new HashSet<>(actual);

        assertTrue(actualSet.equals(expectedSet));
    }

    @Test
    public void shouldRespondToFriends2() throws IOException {
        HTTP.Response response = HTTP.GET(neo4j.httpURI().resolve("/v1/service/friends2/u1").toString());
        ArrayList actual = response.content();

        HashSet expectedSet = new HashSet<>(expected);
        HashSet actualSet = new HashSet<>(actual);

        assertTrue(actualSet.equals(expectedSet));
    }

    private static final String CYPHER_STATEMENT =
            new StringBuilder()
                    .append("CREATE (user1:User {user_id:'u1', name:'Max'})")
                    .append("CREATE (friend1:User {user_id:'f1', name:'Michael'})")
                    .append("CREATE (friend2:User {user_id:'f2', name:'Peter'})")
                    .append("CREATE (friend3:User {user_id:'f3', name:'David'})")
                    .append("CREATE (user1)-[:FRIENDS]->(friend1)")
                    .append("CREATE (user1)-[:FRIENDS]->(friend2)")
                    .append("CREATE (user1)-[:FRIENDS]->(friend3)")
                    .toString();

    private static final ArrayList expected = new ArrayList() {{
        add(new HashMap<String, Object>() {{
            put("user_id", "f1");
            put("name", "Michael");
        }});
        add(new HashMap<String, Object>() {{
            put("user_id", "f2");
            put("name", "Peter");
        }});
        add(new HashMap<String, Object>() {{
            put("user_id", "f3");
            put("name", "David");
        }});
    }};
}

