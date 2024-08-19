package com.example.beta.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.beta.dao.LotDao;
import com.example.beta.rest.model.LotEntity;

@Database(entities = {LotEntity.class}, version = 1)
public abstract class LotDatabase extends RoomDatabase {
    public abstract LotDao lotDao();

    private static volatile LotDatabase INSTANCE;

    public static LotDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LotDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    LotDatabase.class, "lot_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
