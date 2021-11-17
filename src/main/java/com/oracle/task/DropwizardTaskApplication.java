
package com.oracle.task;

import com.oracle.task.api.Task;
import com.oracle.task.db.MongoDBFactoryConnection;
import com.oracle.task.db.MongoDBManaged;
import com.oracle.task.db.daos.TaskDAO;
import com.oracle.task.health.DropwizardMongoDBHealthCheck;
import com.oracle.task.resources.TaskResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DropwizardTaskApplication extends Application<DropwizardTaskConfiguration> {


    private static final Logger LOGGER = LoggerFactory.getLogger(DropwizardTaskApplication.class);


    public static void main(final String[] args) throws Exception {
        new DropwizardTaskApplication().run(args);
    }

    @Override
    public String getName() {
        return "DropwizardTaskApplication";
    }

    @Override
    public void initialize(final Bootstrap<DropwizardTaskConfiguration> bootstrap) {
        LOGGER.info("initialize application.");
        bootstrap.addBundle(new SwaggerBundle<DropwizardTaskConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(
                        final DropwizardTaskConfiguration dropwizardTaskConfiguration) {
                return dropwizardTaskConfiguration.getSwaggerBundleConfiguration();
            }
        });
    }

    @Override
    public void run(final DropwizardTaskConfiguration configuration,
                    final Environment environment) {

        final MongoDBFactoryConnection mongoDBManagerConn = new MongoDBFactoryConnection(configuration.getMongoDBConnection());

        final MongoDBManaged mongoDBManaged = new MongoDBManaged(mongoDBManagerConn.getClient());

        final TaskDAO taskDAO = new TaskDAO(mongoDBManagerConn.getClient()
                .getDatabase(configuration.getMongoDBConnection().getDatabase())
                .getCollection("tasks"));

        environment.lifecycle().manage(mongoDBManaged);
        environment.jersey().register(new TaskResource(taskDAO));
        environment.healthChecks().register("DropwizardMongoDBHealthCheck",
                new DropwizardMongoDBHealthCheck(mongoDBManagerConn.getClient()));
    }

}
