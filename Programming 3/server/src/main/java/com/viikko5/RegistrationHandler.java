package com.viikko5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import com.sun.net.httpserver.*;

import org.json.JSONException;
import org.json.JSONObject;

class RegistrationHandler implements HttpHandler {
    
    final private UserAuthenticator user;

    public RegistrationHandler(UserAuthenticator user){
        this.user = user;
    }

    @Override
    public void handle(HttpExchange t) throws IOException {

        System.out.println("Request handled in thread " + Thread.currentThread().getId());
        final Headers headers = t.getRequestHeaders();
        String contentType = "";
        String response = "";
        int code = 200;
        

        try {
            JSONObject obj = null;

            if(t.getRequestMethod().equalsIgnoreCase("POST")){
                if(headers.containsKey("Content-Type")){
                    //content type available
                    contentType = headers.get("Content-Type").get(0);
                } else {
                    //No content type
                    code = 411;
                    response = "No content type in request";
                }
                if(contentType.equalsIgnoreCase("application/json")){
                    //content type is application/json
                    InputStream stream = t.getRequestBody();
                    String newUser = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
                    stream.close();

                    if(newUser == null || newUser.length() == 0){
                        code = 412;
                        response = "No user credentials";
                    } else {
                        try {
                            obj = new JSONObject(newUser);
                            if(obj.getString("username").length() < 3 || obj.getString("password").length() < 6){
                                code = 413;
                                response = "No proper user credentials";
                            } else {
                                Boolean result = user.addUser(obj.getString("username"), obj.getString("password"), obj.getString("email"));
                                if(result == false){
                                    code = 405;
                                    response = "User already exists";
                                } else {
                                    code = 200;
                                    response = "User registered";
                                }
                            }
                        } catch (JSONException e) {
                            System.out.println("parse json");
                            //parse error, faulty json user

                        }
                    }    
                    byte[] bytes = response.getBytes("UTF-8");
                    t.sendResponseHeaders(code, bytes.length);
                    OutputStream x = t.getResponseBody();
                    x.write(bytes);
                    x.close();
                } else {
                    code = 407;
                    response = "Content type is not application/json";
                }
            }else {
                //other than post
                code = 401;
                response = "Only POST is accepted";
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            code = 500;
            response = "Internal server error";
        }
        if (code >= 400){
            byte[] bytes = response.getBytes("UTF-8");
            t.sendResponseHeaders(code, bytes.length);
            OutputStream x = t.getResponseBody();
            x.write(bytes);
            x.close();
        }
    }
}