package com.example.beta.dao;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.beta.rest.model.UserEntityDB;

@Database(entities = {UserEntityDB.class}, version = 1)
public  abstract  class AppDataBase  extends RoomDatabase {
    public   abstract UserDao userDao();
    private  static volatile AppDataBase INSTANCE;
    public static AppDataBase getDatabase(final Context context){
        if (INSTANCE ==null){
            synchronized (AppDataBase.class){
                if (INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class,"app_database").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
