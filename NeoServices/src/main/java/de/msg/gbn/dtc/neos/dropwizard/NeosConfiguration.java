package de.msg.gbn.dtc.neos.dropwizard;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class NeosConfiguration extends Configuration {

    @NotEmpty
    private String mongoDbUri;

    @NotEmpty
    private String mongoDbName;

    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "Stranger";

    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String name) {
        this.defaultName = name;
    }

    @JsonProperty
    public String getMongoDbUri() {
        return mongoDbUri;
    }

    @JsonProperty
    public void setMongoDbUri(String mongoDbUri) {
        this.mongoDbUri = mongoDbUri;
    }

    @JsonProperty
    public String getMongoDbName() {
        return mongoDbName;
    }

    @JsonProperty
    public void setMongoDbName(String mongoDbName) {
        this.mongoDbName = mongoDbName;
    }
}