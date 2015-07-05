package com.maxdemarzi;


import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.neo4j.graphdb.*;

import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Path("/service")
public class Service {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @GET
    @Path("/friends/{user_id}")
    @Produces({"application/json"})
    public Response Friends(
            @PathParam("user_id") final String userId,
            @Context final GraphDatabaseService db) throws IOException {
        List<Map<String, Object>> results = new ArrayList<>();

        try (Transaction tx = db.beginTx()) {
            final Node user = db.findNode(Labels.User, "user_id", userId);

            if (user != null) {
                for (Relationship r : user.getRelationships(Direction.OUTGOING, RelationshipTypes.FRIENDS)) {
                    Node friend = r.getEndNode();
                    HashMap properties = new HashMap();
                    for (String key : friend.getPropertyKeys()) {
                        properties.put(key, friend.getProperty(key));
                    }
                    results.add(properties);
                }
            }
        }

        return Response.ok().entity(objectMapper.writeValueAsString(results)).build();
    }

    @GET
    @Path("/friends2/{user_id}")
    @Produces({"application/json"})
    public Response Friends2(
            @PathParam("user_id") final String userId,
            @Context final GraphDatabaseService db) throws IOException {

        StreamingOutput stream = new StreamingOutput() {
            @Override
            public void write(OutputStream os) throws IOException, WebApplicationException
            {
                JsonGenerator jg = objectMapper.getJsonFactory().createJsonGenerator( os, JsonEncoding.UTF8 );
                jg.writeStartArray();

                try (Transaction tx = db.beginTx()) {
                    final Node user = db.findNode(Labels.User, "user_id", userId);

                    if (user != null) {
                        for (Relationship r : user.getRelationships(Direction.OUTGOING, RelationshipTypes.FRIENDS)) {
                            Node friend = r.getEndNode();
                            jg.writeStartObject();
                            for (String key : friend.getPropertyKeys()) {
                                jg.writeObjectField(key, friend.getProperty(key));
                            }
                            jg.writeEndObject();
                        }
                    }
                }
                    jg.writeEndArray();
                    jg.flush();
                    jg.close();
                }
            };

        return Response.ok().entity(stream).type(MediaType.APPLICATION_JSON).build();
    }

}
