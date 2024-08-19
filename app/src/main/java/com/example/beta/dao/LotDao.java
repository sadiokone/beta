package com.example.beta.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.beta.rest.model.LotEntity;

import java.util.List;

@Dao
public interface LotDao {


@Insert
    void insert(LotEntity lotentity);

@Query("SELECT * FROM lot_table")
    List<LotEntity> getAllLots();

  // void insertAll(List<LotEntity> lots);

    @Query("DELETE FROM lot_table")
    void deleteAll();


}
