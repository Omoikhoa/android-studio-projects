// Assignment 12
// ToDoList.java
// Alex Ilevbare

package edu.uncc.assignment11.models;

import java.io.Serializable;

public class ToDoList implements Serializable {
    String name;
    String todoListId;
    String userId;

    public ToDoList() {
    }

    public ToDoList(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getTodoListId() {
        return todoListId;
    }
    public void setTodoListId(String todoListId) {
        this.todoListId = todoListId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
