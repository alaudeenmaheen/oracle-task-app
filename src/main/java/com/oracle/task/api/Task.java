
package com.oracle.task.api;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

import org.bson.types.ObjectId;

import com.oracle.task.helper.ObjectIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;



public class Task implements Serializable {


    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;


    @NotNull
    private String text;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @NotNull
    private String day;

    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }

    @NotNull
    private boolean reminder;


    public Task() {
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Task task = (Task) o;
        return  Objects.equals(id, task.id) &&
                Objects.equals(text, task.text) &&
                Objects.equals(day, task.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, day);
    }


    public ObjectId getId() {
        return id;
    }


    public void setId(final ObjectId id) {
        this.id = id;
    }


    public String getText() {
        return text;
    }


    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Task{"
                + "id=" + id
                + ", text='" + text + '\''
                + ", day='" + day + '\''
                + '}';
    }
}
