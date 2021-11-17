
package com.oracle.task.db.daos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.oracle.task.api.Task;
import com.oracle.task.helper.TaskMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.types.ObjectId;


public class TaskDAO {

    final MongoCollection<Document> taskCollection;


    public TaskDAO(final MongoCollection<Document> taskCollection) {
        this.taskCollection = taskCollection;
    }

    public List<Task> getAll() {
        final MongoCursor<Document> tasks = taskCollection.find().iterator();
        final List<Task> tasksFind = new ArrayList<>();
        try {
            while (tasks.hasNext()) {
                final Document task = tasks.next();
                tasksFind.add(TaskMapper.map(task));
            }
        } finally {
            tasks.close();
        }
        return tasksFind;
    }


    public Task getOne(final ObjectId id) {
        final Optional<Document> taskFind = Optional.ofNullable(taskCollection.find(new Document("_id", id)).first());
        return taskFind.isPresent() ? TaskMapper.map(taskFind.get()) : null;
    }

    public void save(final Task task){
        final Document saveTask =new Document("text", task.getText())
                .append("day", task.getDay()).append("reminder", task.isReminder());
        taskCollection.insertOne(saveTask);
    }



    public void update(final ObjectId id, final Task task) {
        taskCollection.updateOne(new Document("_id", id),
                new Document("$set", new Document("text", task.getText())
                         .append("day", task.getDay())
                        .append("reminder", task.isReminder()))

        );
    }


    public void delete(final ObjectId id){
        taskCollection.deleteOne(new Document("_id", id));
    }
}
