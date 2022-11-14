package com.viikko2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import com.sun.net.httpserver.*;

class RegistrationHandler implements HttpHandler {
    
    public UserAuthenticator user;

    public RegistrationHandler(UserAuthenticator user){
        this.user = user;
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        if (t.getRequestMethod().equalsIgnoreCase("POST")) {
            InputStream stream = t.getRequestBody();
            String text = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
            String[] arr = text.split(":", -2);
            
            if(user.checkCredentials(arr[0], arr[1])){
                OutputStream outputStream = t.getResponseBody();
                String alrdy = "User already registered";
                t.sendResponseHeaders(403, alrdy.length());
                outputStream.write(alrdy.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();
            } else if(!arr[0].isEmpty() || !arr[1].isEmpty()) {
                user.addUser(arr[0], arr[1]);
                t.sendResponseHeaders(200, -1);
            } else {
                t.sendResponseHeaders(400, -1);
            }
            stream.close();
        } else {
            OutputStream outputStream = t.getResponseBody();
            String NotSup = "Not supported";
            t.sendResponseHeaders(400, NotSup.length());
            outputStream.write(NotSup.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
        }
    }
}