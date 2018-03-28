package de.msg.gbn.dtc.neos.db;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class MongoDbAccess {

    private String dbUri;
    private String dbName;


    public MongoDbAccess(String dbUri, String dbName) {
        this.dbUri = dbUri;
        this.dbName = dbName;
    }

    public String filterTarife(TarifeFilter tarifeFilter) {

        ArrayList<Bson> filters = new ArrayList<>();
        filters.addAll(tarifeFilter.toBsonAggregateMatches());

        Bson filter = Filters.and(filters);
        Bson lookup = new Document("$lookup", new Document("from", "neo_versicherungen").append("localField", "Versicherer").append("foreignField", "Nummer").append("as", "neo_versicherungen"));
        Bson select = new Document("$project",
                new Document("Tarifbezeichnung", 1)
                        .append("Tarif", 1)
                        .append("Prämie", 1)
                        .append("isBaseP", 1)
                        .append("isBaseF", 1)
                        .append("Unfalleinschluss", 1)
                        .append("Altersuntergruppe", 1)
                        .append("sz", new Document("$size", "$neo_versicherungen"))
                        .append("Versichererbezeichnung", "$neo_versicherungen.Name")
                        .append("Versichererort", "$neo_versicherungen.Ort")
                        /*.append("V", new Document("$concat", "[\"$neo_versicherungen.Name\", \" \", \"$neo_versicherungen.Ort\"]"))*/
        );

        List<Document> results = doFilterWithAggregate("neo_praemien", filter, lookup, select, Aggregates.match(Filters.eq("sz", 1)));
        //results = results.stream().filter(v -> ((List)v.get("Versichererbezeichnung")).size()>0).collect(Collectors.toList());
        return JSON.serialize(results);
        //Bson filter = Filters.and(tarifeFilter.toBsonFilters());
        //return doFilter("neo_praemien", filter, "Tarifbezeichnung", "Tarif", "Prämie", "isBaseP", "isBaseF", "Unfalleinschluss", "Altersuntergruppe");
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

    private List<Document> doFilterWithAggregate(final String collectionName, final Bson filter, final Bson lookupCondition, final Bson project, final Bson filterOnForeignCollection) {

        MongoClientURI connectionString = new MongoClientURI(this.dbUri);

        try (MongoClient mongoClient = new MongoClient(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase(this.dbName);

            MongoCollection<Document> collection = database.getCollection(collectionName);

            AggregateIterable<Document> results = collection.aggregate(Arrays.asList(
                    /*new Document("$unwind", "$neo_versicherungen"),*/
                    filter, lookupCondition, project
                    , filterOnForeignCollection
                    ));


            return StreamSupport.stream(results.spliterator(), false)
                    .collect(Collectors.toList());

        }

    }


}
