package com.viikko2;

import java.util.Hashtable;
import java.util.Map;
import com.sun.net.httpserver.BasicAuthenticator;

class UserAuthenticator extends BasicAuthenticator {

    private Map<String,String> users = null;

    public UserAuthenticator(String realm) {
        super(realm);
        users = new Hashtable<String, String>();
        users.put("dummy", "passwd");
    }

    @Override
    public boolean checkCredentials(String username, String password) {
        try {
            if(users.get(username).equals(password)){
                return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }

    public boolean addUser(String userName, String password) {
        if(!userName.isEmpty() && !password.isEmpty()){
            if(!users.containsKey(userName)){
                users.put(userName, password);
                return true;
            }
        }
        return false;
    }
}