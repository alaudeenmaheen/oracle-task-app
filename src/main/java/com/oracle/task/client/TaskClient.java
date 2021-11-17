
package com.oracle.task.client;

import com.oracle.task.api.Task;

import java.util.Arrays;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class TaskClient {

    /**
     * Client to connect.
     */
    private Client client;

    /**
     * Base of URL to connect.
     */
    private String basePath;

    /**
     * Constructor.
     *
     * @param client   the client jersey.
     * @param basePath the base path.
     */
    public TaskClient(final Client client, final String basePath) {
        this.client = client;
        this.basePath = basePath;
    }

    /**
     * Get all {@link Task} objects.
     *
     * @return A list of {@link Task} in other case null.
     */
    public List<Task> all() {
        final WebTarget webTarget = client.target(basePath);
        final Invocation.Builder builder = webTarget.request();
        final Response response = builder.accept(MediaType.APPLICATION_JSON).get();

        if (Response.Status.OK.getStatusCode() == response.getStatus()) {
            return Arrays.asList(response.readEntity(Task[].class));
        }
        return null;
    }

    /**
     * Get a {@link Task} object.
     *
     * @param id the identifier
     * @return A object {@link Task} or null in case not found.
     */
    public Task getOne(final String id) {
        final WebTarget webTarget = client.target(basePath).path("/").path(id);
        final Invocation.Builder builder = webTarget.request();
        final Response response = builder.accept(MediaType.APPLICATION_JSON).get();

        if (Response.Status.OK.getStatusCode() == response.getStatus()) {
            return response.readEntity(Task.class);
        }
        return null;
    }

    /**
     * Persist a object of type {@link Task}.
     *
     * @param task the task.
     * @throws Exception when can not save.
     */
    public void save(final Task task) throws Exception {
        final WebTarget webTarget = client.target(basePath);
        final Invocation.Builder builder = webTarget.request();
        final Response response = builder.accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(task, MediaType.APPLICATION_JSON));

        if (Response.Status.NO_CONTENT.getStatusCode() != response.getStatus()) {
            throw new Exception("Can not save Task.");
        }
    }

    /**
     * Update the information about a {@link Task}.
     *
     * @param id    the identifier.
     * @param task the task information.
     * @throws Exception when can not update the Task object.
     */
    public void update(final String id, Task task) throws Exception {
        final WebTarget webTarget = client.target(basePath).path("/").path(id);
        final Invocation.Builder builder = webTarget.request();
        final Response response = builder.accept(MediaType.APPLICATION_JSON)
                .put(Entity.entity(task, MediaType.APPLICATION_JSON));

        if (Response.Status.NO_CONTENT.getStatusCode() != response.getStatus()) {
            throw new Exception("Can not save Task.");
        }
    }

    /**
     * Delete a {@link Task} the task object.
     *
     * @param id the identifier of Task.
     */
    public void delete(final String id) throws Exception {
        final WebTarget webTarget = client.target(basePath).path("/").path(id);
        final Invocation.Builder builder = webTarget.request();
        final Response response = builder.accept(MediaType.APPLICATION_JSON)
                .delete();

        if (Response.Status.NO_CONTENT.getStatusCode() != response.getStatus()) {
            throw new Exception("Can not save Task.");
        }
    }

}
