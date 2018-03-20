package de.msg.gbn.dtc.neos.dropwizard;

import io.dropwizard.Application;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class NeosApplication extends Application<NeosConfiguration> {
    public static void main(String[] args) throws Exception {
        new NeosApplication().run(args);
    }

    @Override
    public String getName() {
        return "neos-app";
    }

    @Override
    public void initialize(Bootstrap<NeosConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(
                new ResourceConfigurationSourceProvider());    }

    @Override
    public void run(NeosConfiguration configuration,
                    Environment environment) {

        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());

        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(new TarifeResource(configuration.getMongoDbUri(), configuration.getMongoDbName()));
        environment.jersey().register(new RegionenResource(configuration.getMongoDbUri(), configuration.getMongoDbName()));
    }

}