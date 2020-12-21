package club.rigox.cherry.database;

import club.rigox.cherry.Cherry;
import com.mongodb.*;

import java.util.Objects;
import java.util.UUID;

import static club.rigox.cherry.utils.Logger.info;
import static club.rigox.cherry.utils.Logger.warn;

public class MongoDB {
    private final Cherry cherry;
    private DBCollection playerCollection;
    private MongoClient client;

    public MongoDB (Cherry plugin) {
        this.cherry = plugin;
    }

    public void connect(){
        MongoClientURI uri = new MongoClientURI(String.format("mongodb+srv://%s:%s@%s/admin?retryWrites=true&w=majority",
                cherry.getDatabase().getString("MONGO.AUTH.USERNAME"),
                cherry.getDatabase().getString("MONGO.AUTH.PASSWORD"),
                cherry.getDatabase().getString("MONGO.HOST")));

        client = new MongoClient(uri);

        String databaseString = Objects.requireNonNull(cherry.getDatabase().getString("MONGO.DATABASE"));
        DB database = client.getDB(databaseString);
        playerCollection = database.getCollection("players");
    }

    public void close() {
        client.close();
        info("MongoDB connection has been closed!");
    }

    public double getMongoCredits(UUID uuid) {
        DBObject r = new BasicDBObject("UUID", uuid.toString());
        DBObject found = playerCollection.findOne(r);

        if (found == null) {
            warn("Player has been added to the database");
            storePlayer(uuid, 100.0);
            return 100.0;
        }

        return (double) found.get("credits");
    }

    public void storePlayer(UUID uuid, Double credits) {
        DBObject object = new BasicDBObject("UUID", uuid.toString());
        object.put("credits", credits);
        playerCollection.insert(object);
    }

    public void updateMongoCredits(UUID uuid, Double credits) {
        DBObject r = new BasicDBObject("UUID", uuid.toString());
        DBObject found = playerCollection.findOne(r);

        if (found == null) {
            warn("Player has been added to the database while being checked.");
            storePlayer(uuid, 100.0);
            return;
        }

        BasicDBObject set = new BasicDBObject("$set", r);
        set.append("$set", new BasicDBObject("credits", credits));
        playerCollection.update(found, set);
    }

}
