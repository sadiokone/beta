package com.example.beta.rest.model;


import android.widget.EditText;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class UserEntityDB {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String numTel;
    private  String password;
    public UserEntityDB(String numTel, String password){
        this.numTel=numTel;
        this.password=password;
    }

  /*  public UserEntityDB(EditText numTel, EditText password) {

    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
