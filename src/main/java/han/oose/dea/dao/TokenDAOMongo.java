package han.oose.dea.dao;

import javax.enterprise.inject.Alternative;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import han.oose.dea.domain.User;
import han.oose.dea.exceptions.ForbiddenException;
import org.bson.Document;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

@Alternative
public class TokenDAOMongo implements ITokenDAO{
    public static final String DB_NAME = "spotitube";
    public static final String CO_NAME = "users";

    MongoClient mongoClient = MongoClients.create();
    MongoDatabase database = mongoClient.getDatabase(DB_NAME);
    MongoCollection<Document> tokenCollection = database.getCollection(CO_NAME);

    @Override
    public User addTokenToDB(String token, String username) throws ForbiddenException {
        try {
            tokenCollection.updateOne(eq("username", username), combine(set("token", token)));

        } catch (Exception e) {
            throw new ForbiddenException();
        }
        return null;
    }

    @Override
    public String verifyToken(String token) throws ForbiddenException {
        try {
            Document result = tokenCollection.find(and(eq("token", token))).first();

            return (String) result.get("username");
        } catch (Exception e) {
            throw new ForbiddenException();
        }
    }
}
