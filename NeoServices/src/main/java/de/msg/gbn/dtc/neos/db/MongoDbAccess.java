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


    public static String filterNeos(TarifeFilter tarifeFilter) {
        MongoClientURI connectionString = new MongoClientURI("mongodb://admin:admin@localhost:27020");

        try (MongoClient mongoClient = new MongoClient(connectionString))
        {
            MongoDatabase database = mongoClient.getDatabase("lotto-mongo-db");

            MongoCollection<Document> collection = database.getCollection("neos");

            Bson filter = Filters.and(tarifeFilter.toBsonFilters());

            FindIterable<Document> results = collection.find(filter).projection(Projections.include("Tarifbezeichnung", "Prämie", "isBaseP", "isBaseF", "Unfalleinschluss", "Altersuntergruppe"));
            return JSON.serialize(results);
        }
    }


}
