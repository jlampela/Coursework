package com.viikko5;

import java.sql.SQLException;
import com.sun.net.httpserver.BasicAuthenticator;

import org.json.JSONException;
import org.json.JSONObject;

class UserAuthenticator extends BasicAuthenticator {

    private CoordinateDB db = null;

    public UserAuthenticator() {
        super("coodinates");
        db = CoordinateDB.getInstance();
    }

    @Override
    public boolean checkCredentials(String username, String password) {
        //System.out.println("checking user: " + username + " " + password + "\n");

        boolean validUser;
        try {
            validUser = db.authUser(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return validUser;
    }

    public boolean addUser(String username, String password, String email) throws JSONException, SQLException {
        boolean res = db.addUser(new JSONObject().put("username", username).put("password", password).put("email", email));
        if(res){
            //System.out.println(username + " registered");
            return true;
        }
        //System.out.println("Registration failed");
        return false;
    }
}