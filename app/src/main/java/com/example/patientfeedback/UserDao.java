package com.example.patientfeedback;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAllData();

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM user")
    void deleteAllData();

    @Update
    void update(User user);
}
