package de.msg.gbn.dtc.neos.dropwizard;

import com.codahale.metrics.annotation.Timed;
import de.msg.gbn.dtc.neos.db.Altersklasse;
import de.msg.gbn.dtc.neos.db.MongoDbAccess;
import de.msg.gbn.dtc.neos.db.TarifeFilter;
import de.msg.gbn.dtc.neos.db.Tariftyp;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Path("/tarife")
@Produces(MediaType.APPLICATION_JSON)
public class TarifeResource {

    private final String mongoDbUri;
    private final String mongoDbName;

    public TarifeResource(String mongoDbUri, String mongoDbName) {
        this.mongoDbUri = mongoDbUri;
        this.mongoDbName = mongoDbName;
    }

    /**
     *
     * @param tariftyp - Tariftyp, z.B.: HMO
     * @param kanton
     * @param region
     * @param altersklasse
     * @param unfalleinschluss
     * @param franchise
     * @param baseTarif
     * @param baseFranchise
     * @param altersuntergruppe
     * @return
     */
    @GET
    @Path("{tariftyp}/{kanton}/{region}/{altersklasse}/{franchise}")
    public Response filterTarife(
            @PathParam("tariftyp") Tariftyp tariftyp
            , @PathParam("kanton") String kanton
            , @PathParam("region") Integer region
            , @PathParam("altersklasse") Altersklasse altersklasse
            , @PathParam("franchise") Integer franchise

            , @QueryParam("unfalleinschluss") Optional<Integer> unfalleinschluss
            , @QueryParam("baseTarif") Optional<Integer> baseTarif
            , @QueryParam("baseFranchise") Optional<Integer> baseFranchise
            , @QueryParam("altersuntergruppe") Optional<String> altersuntergruppe
            ) {

        try {

            TarifeFilter filter = new TarifeFilter();
            filter.setTariftyp(tariftyp);
            filter.setAltersklasse(altersklasse);


            if (kanton==null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Kanton muss übergeben werden, z.B.: '/AG'").build();
            }
            else{
                filter.setKanton(kanton);
            }

            if (region==null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Region muss übergeben werden, z.B.: '/0'").build();
            }
            else{
                filter.setRegion(region);
            }

            if (franchise ==null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Franchise muss übergeben werden, z.B.: '/200'").build();
            }
            else{
                filter.setFranchise(franchise);
            }

            if (unfalleinschluss.isPresent()) filter.setUnfalleinschluss(1 == unfalleinschluss.get());
            filter.setBaseFranchise(baseFranchise.orElse(null));
            filter.setBaseTarif(baseTarif.orElse(null));

            if (altersuntergruppe.isPresent()) {
                filter.setAltersuntergruppe(altersuntergruppe.get());
            }

            String json = new MongoDbAccess(this.mongoDbUri, this.mongoDbName).filterNeos(filter);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();

        }
        catch (Throwable e) {
            return Response.serverError().entity("Unerwarteter Fehler: " + e.getMessage()).build();
        }
    }
}
