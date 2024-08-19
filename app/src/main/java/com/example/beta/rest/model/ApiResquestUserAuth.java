package com.example.beta.rest.model;

public class ApiResquestUserAuth {

    private String access_token;
    private String refresh_token;
    private RoleEntity[] roles;
    private String permissions;
private String numTel;
private String nomlot;
    private String datedebut;
    private String datefin;
    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public RoleEntity[] getRoles() {
        return roles;
    }

    public void setRoles(RoleEntity[] roles) {
        this.roles = roles;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }


    public String getNumTel() {
        return  numTel;
    }


}
