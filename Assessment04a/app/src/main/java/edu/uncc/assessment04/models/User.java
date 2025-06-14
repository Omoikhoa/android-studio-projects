package edu.uncc.assessment04.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "user")

public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int uid;
    String name;
    String passcode;

    public User() {
    }

    public User(String name, String passcode) {
        this.name = name;
        this.passcode = passcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getUid() {
        return String.valueOf(uid);
    }
}
