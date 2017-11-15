package com.example.tahaali.realmdatabase.model;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Taha Ali on 10/16/2017.
 */

public class Person extends RealmObject {
    @PrimaryKey
   private String  p_key;
    @Required
    private String name;
    private String email;

    public String getP_key() {
        return p_key;
    }

    public void setP_key(String p_key) {
        this.p_key = p_key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
