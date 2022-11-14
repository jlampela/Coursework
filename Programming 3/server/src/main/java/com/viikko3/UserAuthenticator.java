package com.viikko3;

import java.util.ArrayList;
import com.sun.net.httpserver.BasicAuthenticator;

class UserAuthenticator extends BasicAuthenticator {

    private ArrayList<User> users = null;

    public UserAuthenticator() {
        super("coodinates");
        users = new ArrayList<User>();
    }

    @Override
    public boolean checkCredentials(String username, String password) {
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getUsername().equals(username) && users.get(i).getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    public boolean addUser(String username, String password, String email) {
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getUsername().equals(username)){
                return false;
            }
        }
        User registerUser = new User(username, password, email);
        users.add(registerUser);
        return true;
    }
}