package com.yunini.api.utils;

import com.yunini.api.model.dto.user.UserAddRequest;

public class UserHolder {
    private static final ThreadLocal<UserAddRequest> tl = new ThreadLocal<>();

    public static void saveUser(UserAddRequest user){
        tl.set(user);
    }

    public static UserAddRequest getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}
