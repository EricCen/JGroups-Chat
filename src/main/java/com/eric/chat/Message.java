package com.eric.chat;

import java.io.Serializable;

/**
 * Created by ETIACEN on 12/5/2015.
 */
public class Message implements Serializable {
    private String saying;
    private User user;

    public Message(String saying, User user) {
        this.saying = saying;
        this.user = user;
    }

    public String getSaying() {
        return saying;
    }

    public User getUser() {
        return user;
    }

    public String toString(){
        return "Message: " + saying + " said by " + user;
    }
}
