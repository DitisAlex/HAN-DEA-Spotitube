package han.oose.ica;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

@Path("/")
public class MongoTestApp {
    MongoClient mongoClient = MongoClients.create();
    MongoDatabase database = mongoClient.getDatabase("test_database");
    MongoCollection<Document> collection = database.getCollection("test_collection");

    // Create document
    @POST
    @Path("/")
    public Response createDocument() {
        Document document = new Document("name", "Alex")
                .append("age", 18);
        collection.insertOne(document);

        return Response.status(201).build();
    }

    // Find document
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFirstDocument() {
        Document document = collection.find(eq("name", "Alex")).first();

        return Response.status(200).entity(document).build();
    }

    // Update document
    @PUT
    @Path("/")
    public Response updateDocument() {
        collection.updateOne(eq("name", "Alex"), set("name", "Alexandra"));

        return Response.status(200).build();
    }

    // Delete document
    @DELETE
    @Path("/")
    public Response deleteDocument() {
        collection.deleteOne(eq("name", "Alexandra"));

        return Response.status(200).build();
    }
}