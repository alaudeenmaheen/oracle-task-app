
package com.oracle.task.api;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.FixtureHelpers;

import java.util.ArrayList;
import java.util.List;


public class TaskTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception {
        List<Task> taskList = new ArrayList<>();
        final Task task = new Task();
        task.setId(new ObjectId("507f1f77bcf86cd799439011"));
        task.setText("Meeting with the client");
        task.setReminder(true);
        task.setDay("Monday 5th 2.30 PM");
        taskList.add(task);

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(FixtureHelpers.fixture("fixtures/task.json"), Task[].class));

        Assert.assertEquals(expected, MAPPER.writeValueAsString(taskList));
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        final Task[] task = new Task[1];
        task[0] = new Task();
        task[0].setId(new ObjectId("507f1f77bcf86cd799439011"));
        task[0].setText("Meeting with the client");
        task[0].setReminder(true);
        task[0].setDay("Monday 5th 2.30 PM");
        Assert.assertEquals(MAPPER.readValue(FixtureHelpers.fixture("fixtures/task.json"), Task[].class), task);
    }
}
