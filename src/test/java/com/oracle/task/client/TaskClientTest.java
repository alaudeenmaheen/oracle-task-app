
package com.oracle.task.client;

import com.oracle.task.api.Task;
import io.dropwizard.testing.junit.DropwizardClientRule;
import org.bson.types.ObjectId;
import org.junit.*;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;


public class TaskClientTest {

    private static final Task task;

    public static final String TASK_ID = "507f1f77bcf86cd799439011";

    static {
        task = new Task();
        task.setId(new ObjectId(TASK_ID));
        task.setText("Pay utility bill");
        task.setDay("Monday 5th 2.30 PM");
        task.setReminder(true);
    }


    @Path("/tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public static class TaskResource {

        @GET
        public List<Task> all(){
            return Arrays.asList(task);
        }

        @GET
        @Path("/{id}")
        public Task getOne(@PathParam("id") @NotNull final String id) {
            if (id.equals(TASK_ID)) {
                final Task task = new Task();
                task.setId(new ObjectId(TASK_ID));
                task.setText("Pay utility bill");
                task.setDay("Monday 5th 2.30 PM");
                task.setReminder(true);
                return task;
            } else {
                return null;
            }
        }

        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        public void save(final Task task) {
            if (task == null) {
                throw new IllegalArgumentException("Information task not valid.");
            }
        }

        @PUT
        @Path("/{id}")
        public void update(@PathParam("id") @NotNull final ObjectId id, @NotNull final Task task) {
            if (id != null) {

            } else {
                throw new IllegalArgumentException("The information for update can not be null.");
            }
        }

        @DELETE
        @Path("/{id}")
        public void delete(@PathParam("id") @NotNull final String id) {
            if (id == null) {
                throw new IllegalArgumentException("Information task not valid.");
            }
        }
    }

    @ClassRule
    public static final DropwizardClientRule DROPWIZARD_CLIENT_RULE = new DropwizardClientRule(new TaskResource());

    @Test
    public void test_all(){
        final TaskClient taskClient = new TaskClient(ClientBuilder.newClient(),DROPWIZARD_CLIENT_RULE.baseUri()+"/tasks");
        final List<Task> tasks = taskClient.all();
        Assert.assertNotNull(tasks);
        Assert.assertFalse(tasks.isEmpty());
    }

    @Test
    public void test_getOne() {
        final TaskClient taskClient = new TaskClient(ClientBuilder.newClient(),DROPWIZARD_CLIENT_RULE.baseUri()+"/tasks");
        final Task task = taskClient.getOne(TASK_ID);
        Assert.assertNotNull(task);
    }

    @Test
    public void test_save() {
        final TaskClient taskClient = new TaskClient(ClientBuilder.newClient(),DROPWIZARD_CLIENT_RULE.baseUri()+"/tasks");
        try {
            taskClient.save(task);
        } catch (final Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void update() {
        final TaskClient taskClient = new TaskClient(ClientBuilder.newClient(),DROPWIZARD_CLIENT_RULE.baseUri()+"/tasks");
        try {
            taskClient.update(TASK_ID, task);
        } catch (final Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void delete() {
        final TaskClient taskClient = new TaskClient(ClientBuilder.newClient(),DROPWIZARD_CLIENT_RULE.baseUri()+"/tasks");
        try {
            taskClient.delete(TASK_ID);
        } catch (final Exception e) {
            Assert.fail();
        }

    }
}
