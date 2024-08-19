package com.example.beta.rest.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lot_table")
public class LotEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nomLot;
    private String dateDebut;
    private String dateFin;

    // Getters et Setters...

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomLot() {
        return nomLot;
    }

    public void setNomLot(String nomLot) {
        this.nomLot = nomLot;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }
}
