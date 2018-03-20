package de.msg.gbn.dtc.neos.db;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.bson.conversions.Bson;


public class MongoDbAccess {

    private String dbUri;
    private String dbName;


    public MongoDbAccess(String dbUri, String dbName) {
        this.dbUri = dbUri;
        this.dbName = dbName;
    }

    public String filterNeos(TarifeFilter tarifeFilter) {
        MongoClientURI connectionString = new MongoClientURI(this.dbUri);

        try (MongoClient mongoClient = new MongoClient(connectionString))
        {
            MongoDatabase database = mongoClient.getDatabase(this.dbName);

            MongoCollection<Document> collection = database.getCollection("neo_praemien");

            Bson filter = Filters.and(tarifeFilter.toBsonFilters());

            FindIterable<Document> results = collection.find(filter).projection(Projections.include("Tarifbezeichnung", "Prämie", "isBaseP", "isBaseF", "Unfalleinschluss", "Altersuntergruppe"));
            return JSON.serialize(results);
        }
    }


}
