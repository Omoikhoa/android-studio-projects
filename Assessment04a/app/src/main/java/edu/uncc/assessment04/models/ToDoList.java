package edu.uncc.assessment04.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "toDoList")

public class ToDoList implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int tid;
    String name;

    String uid;

    public ToDoList() {
    }

    public ToDoList(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTid() {
        return String.valueOf(tid);
    }
}
