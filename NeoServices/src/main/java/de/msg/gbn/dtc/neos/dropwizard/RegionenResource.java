package de.msg.gbn.dtc.neos.dropwizard;

import de.msg.gbn.dtc.neos.db.MongoDbAccess;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/regionen")
@Produces(MediaType.APPLICATION_JSON)
public class RegionenResource {

    private final String mongoDbUri;
    private final String mongoDbName;

    public RegionenResource(String mongoDbUri, String mongoDbName) {
        this.mongoDbUri = mongoDbUri;
        this.mongoDbName = mongoDbName;
    }

    @GET
    @Path("{plzStartWith}")
    public Response filterTarife(@PathParam("plzStartWith") int plz) {

        try {

            String json = new MongoDbAccess(this.mongoDbUri, this.mongoDbName).filterRegionen(plz);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();

        }
        catch (Throwable e) {
            return Response.serverError().entity("Unerwarteter Fehler: " + e.getMessage()).build();
        }
    }
}
