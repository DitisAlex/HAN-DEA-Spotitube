package han.oose.dea.dao;

import han.oose.dea.domain.User;

import javax.enterprise.inject.Alternative;

import han.oose.dea.exceptions.UnauthorizedException;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import static com.mongodb.client.model.Filters.*;

@Alternative
public class UserDAOMongo implements IUserDAO {
    public static final String DB_NAME = "spotitube";
    public static final String CO_NAME = "users";

    MongoClient mongoClient = MongoClients.create();
    MongoDatabase database = mongoClient.getDatabase(DB_NAME);
    MongoCollection<Document> usersCollection = database.getCollection(CO_NAME);

    @Override
    public User checkAuthenticated(String username, String password) throws UnauthorizedException {
        try {
            Document result = usersCollection.find(and(eq("username", username), eq("password", password))).first();

            User user = new User();
            user.setUsername((String) result.get("username"));
            user.setToken((String) result.get("token"));

            return user;
        } catch (Exception e){
            throw new UnauthorizedException();
        }
    }
}
