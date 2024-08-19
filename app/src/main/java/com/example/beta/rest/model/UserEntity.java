package com.example.beta.rest.model;

public class UserEntity {
    private String nom;
    private String numTel;
    private String password;

    // Constructeur, Getters, et  Setters
    public UserEntity(String numTel, String password) {
        this.numTel = numTel;
        this.password = password;

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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
