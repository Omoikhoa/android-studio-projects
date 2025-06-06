package edu.uncc.assessment04.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ToDoListItemDao {
    @Query("SELECT * FROM toDoListItem WHERE tid = :tid")
    List<ToDoListItem> getAllByTid(String tid);

    @Query("SELECT * FROM toDoListItem")
    List<ToDoListItem> getAll();

    @Insert
    void insertAll(ToDoListItem... toDoListItems);

    @Delete
    void delete(ToDoListItem toDoListItems);

}
