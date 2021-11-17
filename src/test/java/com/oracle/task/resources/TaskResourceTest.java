
package com.oracle.task.resources;

import com.oracle.task.api.Task;
import com.oracle.task.db.daos.TaskDAO;
import com.oracle.task.resources.TaskResource;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.bson.types.ObjectId;
import org.junit.*;
import org.mockito.Mockito;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


public class TaskResourceTest {

    private static final TaskDAO dao = Mockito.mock(TaskDAO.class);

    private static final ObjectId objectId = new ObjectId("507f1f77bcf86cd799439011");

    public static final String CONTEXT = "/tasks";

    private static List<Task> tasks;

    private static Task task;

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new TaskResource(dao))
            .build();

    @Before
    public void setUp(){
        tasks = new ArrayList<>();
        task = new Task();
        task.setId(new ObjectId("507f1f77bcf86cd799439011"));
        task.setText("Meeting with the client");
        task.setReminder(true);
        task.setDay("Monday 5th 2.30P M");
        tasks.add(task);
    }

    @After
    public void tearDown(){
        Mockito.reset(dao);
    }

    @Test
    public void test_all(){
        Mockito.when(dao.getAll()).thenReturn(tasks);
        final Response responseTest = resources.target(CONTEXT).request().get();
        Assert.assertNotNull(responseTest);
        Assert.assertEquals(Response.Status.OK.getStatusCode() ,responseTest.getStatus());
    }

    @Test
    public void test_getOne(){
        Mockito.when(dao.getOne(objectId)).thenReturn(task);
        final Response responseTest = resources.target(CONTEXT).path("/507f1f77bcf86cd799439011").request().get();
        Assert.assertNotNull(responseTest);
        Assert.assertEquals(Response.Status.OK.getStatusCode() ,responseTest.getStatus());
        Assert.assertEquals(task, responseTest.readEntity(Task.class));
    }

    @Test
    public void test_save(){
        Mockito.doNothing().when(dao).save(task);
        final Response responseTest = resources.target(CONTEXT).request().post(Entity.entity(task, MediaType.APPLICATION_JSON));
        Assert.assertNotNull(responseTest);
        Assert.assertEquals(Response.Status.CREATED.getStatusCode() ,responseTest.getStatus());
        Mockito.verify(dao, Mockito.times(1)).save(task);
    }

    @Test
    public void test_update(){
        Mockito.doNothing().when(dao).update(objectId, task);
        final Response responseTest = resources.target(CONTEXT).path("/507f1f77bcf86cd799439011").request().put(Entity.entity(task, MediaType.APPLICATION_JSON));
        Assert.assertNotNull(responseTest);
        Assert.assertEquals(Response.Status.OK.getStatusCode() ,responseTest.getStatus());
    }

    @Test
    public void test_delete(){
        Mockito.doNothing().when(dao).delete(objectId);
        final Response responseTest = resources.target(CONTEXT).path("/507f1f77bcf86cd799439011").request().delete();
        Assert.assertNotNull(responseTest);
        Assert.assertEquals(Response.Status.OK.getStatusCode() ,responseTest.getStatus());
    }
}
