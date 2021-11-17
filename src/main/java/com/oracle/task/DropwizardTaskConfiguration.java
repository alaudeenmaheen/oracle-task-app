
package com.oracle.task;

import com.oracle.task.db.configuration.MongoDBConnection;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;


public class DropwizardTaskConfiguration extends Configuration {

    /**
     * The data configuration for MongoDB.
     */

    private MongoDBConnection mongoDBConnection;

    @JsonProperty("swagger")
    private SwaggerBundleConfiguration swaggerBundleConfiguration;

    /**
     * Gets the mongoDBConnection.
     *
     * @return the value mongoDBConnection.
     */
    public MongoDBConnection getMongoDBConnection() {
        return mongoDBConnection;
    }

    /**
     * Sets the mongoDBConnection.
     *
     * @param mongoDBConnection value.
     */
    public void setMongoDBConnection(final MongoDBConnection mongoDBConnection) {
        this.mongoDBConnection = mongoDBConnection;
    }

    /**
     * Gets the swaggerBundleConfiguration.
     *
     * @return the value swaggerBundleConfiguration.
     */
    public SwaggerBundleConfiguration getSwaggerBundleConfiguration() {
        return swaggerBundleConfiguration;
    }

    /**
     * Sets the swaggerBundleConfiguration.
     *
     * @param swaggerBundleConfiguration value.
     */
    public void setSwaggerBundleConfiguration(final SwaggerBundleConfiguration swaggerBundleConfiguration) {
        this.swaggerBundleConfiguration = swaggerBundleConfiguration;
    }
}
