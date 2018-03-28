package de.msg.gbn.dtc.neos.db;

import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;

import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;

public class TarifeFilter {

    private String kanton;
    private Integer region;
    private Altersklasse altersklasse;
    private Boolean unfalleinschluss;
    private Tariftyp tariftyp;
    private String altersuntergruppe;
    private Integer franchise;
    private Integer baseTarif;
    private Integer baseFranchise;

    public String getKanton() {
        return kanton;
    }

    public void setKanton(String kanton) {
        this.kanton = kanton;
    }

    public Integer getRegion() {
        return region;
    }

    public void setRegion(Integer region) {
        this.region = region;
    }

    public Altersklasse getAltersklasse() {
        return altersklasse;
    }

    public void setAltersklasse(Altersklasse altersklasse) {
        this.altersklasse = altersklasse;
    }

    public Boolean getUnfalleinschluss() {
        return unfalleinschluss;
    }

    public void setUnfalleinschluss(Boolean unfalleinschluss) {
        this.unfalleinschluss = unfalleinschluss;
    }

    public Tariftyp getTariftyp() {
        return tariftyp;
    }

    public void setTariftyp(Tariftyp tariftyp) {
        this.tariftyp = tariftyp;
    }

    public String getAltersuntergruppe() {
        return altersuntergruppe;
    }

    public void setAltersuntergruppe(String altersuntergruppe) {
        this.altersuntergruppe = altersuntergruppe;
    }

    public Integer getFranchise() {
        return franchise;
    }

    public void setFranchise(Integer franchise) {
        this.franchise = franchise;
    }

    public Integer getBaseTarif() {
        return baseTarif;
    }

    public void setBaseTarif(Integer baseTarif) {
        this.baseTarif = baseTarif;
    }

    public Integer getBaseFranchise() {
        return baseFranchise;
    }

    public void setBaseFranchise(Integer baseFranchise) {
        this.baseFranchise = baseFranchise;
    }

    public Iterable<Bson> toBsonFilters() {
        ArrayList<Bson> filters = new ArrayList<>();

        if (this.tariftyp!=null) filters.add(Filters.eq("Tariftyp", "TAR-" + tariftyp.toString()));
        if (this.kanton!=null) filters.add(Filters.eq("Kanton", kanton));
        if (this.region!=null) filters.add(Filters.eq("Region", "PR-REG CH" + region));
        if (this.altersklasse!=null) filters.add(Filters.eq("Altersklasse", "AKL-" + altersklasse));
        if (this.unfalleinschluss!=null) filters.add(Filters.eq("Unfalleinschluss", this.unfalleinschluss.booleanValue() ? "MIT-UNF" : "OHN-UNF"));
        if (this.altersuntergruppe!=null) filters.add(Filters.eq("Altersuntergruppe", altersuntergruppe));
        if (this.franchise!=null) filters.add(Filters.eq("Franchise", "FRA-" + franchise));
        if (this.baseTarif!=null) filters.add(Filters.eq("isBaseP", this.baseTarif.intValue()));
        if (this.baseFranchise!=null) filters.add(Filters.eq("isBaseF", this.baseFranchise.intValue()));

        return filters;

    }
    public ArrayList<Bson> toBsonAggregateMatches() {

        Iterable<Bson> filters = toBsonFilters();
        ArrayList<Bson> matches = new ArrayList<>();

        for (Bson filter : filters) {
            matches.add(Aggregates.match(filter));
        }

        return matches;

    }
}
