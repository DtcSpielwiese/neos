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

import java.util.regex.Pattern;


public class MongoDbAccess {

    private String dbUri;
    private String dbName;


    public MongoDbAccess(String dbUri, String dbName) {
        this.dbUri = dbUri;
        this.dbName = dbName;
    }

    public String filterTarife(TarifeFilter tarifeFilter) {

        Bson filter = Filters.and(tarifeFilter.toBsonFilters());
        return doFilter("neo_praemien", filter, "Tarifbezeichnung", "Tarif", "Prämie", "isBaseP", "isBaseF", "Unfalleinschluss", "Altersuntergruppe");
    }

    public String filterRegionen(int plzStartWith) {

        //Pattern pattern = Pattern.compile("^"+Pattern.quote(plzStartWith), Pattern.CASE_INSENSITIVE);
        //Bson filter2 = Filters.regex("PLZ", pattern);

        Bson filter = null;

        if (String.valueOf(plzStartWith).length() == 4) {
            filter = Filters.eq("PLZ", plzStartWith);
        }
        else {
            String plzBis = String.valueOf(plzStartWith);
            String plzVon = String.valueOf(plzStartWith);
            int plzBisLength = plzBis.length();

            for (int i = plzBisLength; i < 4; i++) {
                plzBis+="9";
                plzVon+="0";
            }


            filter = Filters.and(
                    Filters.gte("PLZ", Integer.valueOf(plzVon)),
                    Filters.lte("PLZ", Integer.valueOf(plzBis))
                    );
        }


        return doFilter("neo_regionen", filter, "PLZ", "Ortsbezeichnung", "Kanton", "Region", "BFS-Nr", "Gemeinde", "Bezirk");
    }



    private String doFilter(final String collectionName, final Bson filter, final String... selectFieldNames) {

        MongoClientURI connectionString = new MongoClientURI(this.dbUri);

        try (MongoClient mongoClient = new MongoClient(connectionString))
        {
            MongoDatabase database = mongoClient.getDatabase(this.dbName);

            MongoCollection<Document> collection = database.getCollection(collectionName);

            FindIterable<Document> results = collection.find(filter).projection(Projections.include(selectFieldNames));
            return JSON.serialize(results);
        }

    }


}
