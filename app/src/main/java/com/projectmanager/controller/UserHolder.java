package com.projectmanager.controller;

import org.kohsuke.github.GHMyself;

public class UserHolder {
    private GHMyself user;
    private final static UserHolder INSTANCE = new UserHolder();

    private UserHolder() {}

    public static UserHolder getInstance() {
        return INSTANCE;
    }

    public GHMyself getUser() {
        return user;
    }

    public void setUser(GHMyself user) {
        this.user = user;
    }
}
