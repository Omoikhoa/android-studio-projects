package edu.uncc.assessment04.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ToDoListDao {
    @Query("SELECT * FROM toDoList")
    List<ToDoList> getAll();

    @Query("SELECT * FROM toDoList WHERE uid = :uid")
    List<ToDoList> getAllByUid(String uid);

    @Insert
    void insertAll(ToDoList... toDoLists);

    @Delete
    void delete(ToDoList toDoLists);
}
