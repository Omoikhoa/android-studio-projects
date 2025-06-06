// Assignment 12
// ToDoListItem.java
// Alex Ilevbare

package edu.uncc.assignment11.models;

public class ToDoListItem {
    String name;
    String priority;
    String todoListId;
    String todoListItemId;
    String userId;

    public ToDoListItem() {
    }

    public ToDoListItem(String name, String priority) {
        this.name = name;
        this.priority = priority;
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
    public String getTodoListId() {
        return todoListId;
    }
    public void setTodoListId(String todoListId) {
        this.todoListId = todoListId;
    }
    public String getTodoListItemId() {
        return todoListItemId;
    }
    public void setTodoListItemId(String todoListItemId) {
        this.todoListItemId = todoListItemId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
