package com.example.beta.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.beta.rest.model.UserEntityDB;

@Dao
public interface UserDao {
    @Insert
    void insert(UserEntityDB user);

    // @Query("SELECT * FROM user_table WHERE phoneNumber = :phoneNumber AND password = :password LIMIT 1")
    @Query("SELECT * FROM user_table WHERE numTel = :numphone AND password = :motpass LIMIT 1")
    UserEntityDB getUser(String numphone, String motpass);
}
