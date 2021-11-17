
package com.oracle.task.health;

import org.bson.Document;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.client.MongoClient;

public class DropwizardMongoDBHealthCheck extends HealthCheck {


    private MongoClient mongoClient;

    public DropwizardMongoDBHealthCheck(final MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    protected Result check() {
        try {
            final Document document = mongoClient.getDatabase("tasks").runCommand(new Document("buildInfo", 1));
            if (document == null) {
                return Result.unhealthy("Build Info failed.");
            }
        } catch (final Exception e) {
            return Result.unhealthy("Getting DB operation failed.");
        }
        return Result.healthy();
    }
}
