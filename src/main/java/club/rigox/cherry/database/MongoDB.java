package club.rigox.cherry.database;

import club.rigox.cherry.Cherry;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static club.rigox.cherry.utils.Logger.info;

public class MongoDB {
    private final Cherry cherry;
    private MongoCollection playerCollection;
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

        MongoDatabase database = client.getDatabase(cherry.getDatabase().getString("MONGO.DATABASE"));
        playerCollection = database.getCollection("players");
    }

    public void close() {
        client.close();
        info("MongoDB connection has been closed!");
    }
}
