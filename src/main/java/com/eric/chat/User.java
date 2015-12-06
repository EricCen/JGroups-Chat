package com.eric.chat;

import java.io.Serializable;

/**
 * Created by ETIACEN on 12/5/2015.
 */
public class User implements Serializable{
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }



    public String toString(){
        return "User: " + name;
    }
}
