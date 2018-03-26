package de.msg.gbn.dtc.neos.dropwizard;

import io.dropwizard.Application;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

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
        addCorsFilter(environment);
        environment.jersey().register(new TarifeResource(configuration.getMongoDbUri(), configuration.getMongoDbName()));
        environment.jersey().register(new RegionenResource(configuration.getMongoDbUri(), configuration.getMongoDbName()));
    }

    private static void addCorsFilter(Environment environment) {
        // CORS configuration
        final FilterRegistration.Dynamic corsFilter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        corsFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        corsFilter.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM,
                "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        corsFilter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        corsFilter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");

    }

}