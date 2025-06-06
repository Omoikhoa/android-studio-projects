package edu.uncc.assessment04.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "toDoListItem")
public class ToDoListItem {

    @PrimaryKey(autoGenerate = true)
    public int lid;

    String tid;
    String name;
    String priority;

    public ToDoListItem() {
    }

    public ToDoListItem(String tid, String name, String priority) {
        this.tid = tid;
        this.name = name;
        this.priority = priority;
    }
    public String getTid() {
        return tid;
    }
    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
