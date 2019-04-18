package com.sucl.sbjms.security.dev;

import com.sucl.sbjms.security.auth.IUser;
import lombok.Data;

import java.util.Set;

@Data
public class DevUser implements IUser {
    private String username;

    public DevUser(String username){
        this.username = username;
    }

    @Override
    public Set<String> getRoleIds() {
        return null;
    }

    public String toString(){
        return username;
    }
}
