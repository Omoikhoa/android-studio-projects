// Assignment 09
// File Name: AppDatabase.java
// Full Name: Alex Ilevbare

package edu.uncc.assignment09.models;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
