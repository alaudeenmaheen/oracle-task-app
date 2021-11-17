
package com.oracle.task.resources;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;

import com.oracle.task.api.Task;
import com.oracle.task.db.daos.TaskDAO;

import io.swagger.annotations.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Api(value = "tasks",
     description = "Tasks REST API for handle Tasks CRUD operations on tasks collection.",
     tags = {"tasks"})
@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {

    /**
     * Logger class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskResource.class);


    private TaskDAO taskDAO;


    public TaskResource(final TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    /**
     * Get all {@link Task} objects.
     *
     * @return A object {@link Response} with the information of result this method.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success."),
            @ApiResponse(code = 404, message = "Tasks not found")
    })
    @GET
    public Response all() {
        LOGGER.info("List all Tasks.");
        final List<Task> tasksFind = taskDAO.getAll();
        if (tasksFind.isEmpty()) {
            return Response.accepted(new com.oracle.task.api.Response("Tasks not found."))
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
        final GenericEntity<List<Task>> entity
                = new GenericEntity<List<Task>>(tasksFind) {};
        return Response.ok(entity).build();
    }

    /**
     * Get a {@link Task} by identifier.
     *
     * @param id the identifier.
     * @return A object {@link Response} with the information of result this method.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success."),
            @ApiResponse(code = 404, message = "Tasks not found")
    })
    @GET
    @Path("/{id}")
    public Response getOne(@ApiParam(value = "id") @PathParam("id") @NotNull final ObjectId id) {
        LOGGER.info("Find the task by identifier : " + id.toString());
        final Task task = taskDAO.getOne(id);
        if (task != null) {
            return Response.ok(task).build();
        }
        return Response.accepted(new com.oracle.task.api.Response("Task not found.")).build();
    }

    /**
     * Persis a {@link Task} object in a collection.
     *
     * @param task The objecto to persist.
     * @return A object {@link Response} with the information of result this method.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success.")
    })
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(@ApiParam(value = "Task") @NotNull final Task task) {
        LOGGER.info("Persist a task in collection with the information: {}", task);
        taskDAO.save(task);
        return Response.status(Response.Status.CREATED).build();
    }

    /**
     * Update the information of a {@link Task}.
     *
     * @param id    The identifier.
     * @param task the task information.
     * @return A object {@link Response} with the information of result this method.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success.")
    })
    @PUT
    @Path("/{id}")
    public Response update(@ApiParam(value = "id") @PathParam("id") @NotNull final ObjectId id,
                           @ApiParam(value = "Task") @NotNull final Task task) {
        LOGGER.info("Update the information of a task : {} ", task);
        taskDAO.update(id, task);
        return Response.ok().build();
    }

    /**
     * Delete a {@link Task} object.
      * @param id   the identifier.
     * @return  A object {@link Response} with the information of result this method.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation success.")
    })
    @DELETE
    @Path("/{id}")
    public Response delete(@ApiParam(value = "id") @PathParam("id") @NotNull final ObjectId id) {
        LOGGER.info("Delete a task from collection with identifier: " + id.toString());
        taskDAO.delete(id);
        return Response.ok().build();
    }
}
