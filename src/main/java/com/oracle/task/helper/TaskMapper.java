
package com.oracle.task.helper;

import com.oracle.task.api.Task;
import org.bson.Document;


public class TaskMapper {

    /**
     * Map objects {@link Document} to {@link Task}.
     *
     * @param taskDocument the information document.
     * @return A object {@link Task}.
     */
    public static Task map(final Document taskDocument) {
        final Task task = new Task();
        task.setId(taskDocument.getObjectId("_id"));
        task.setText(taskDocument.getString("text"));
        task.setDay(taskDocument.getString("day"));
        task.setReminder(taskDocument.getBoolean("reminder"));
        return task;
    }
}
