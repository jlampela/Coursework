package com.viikko3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.sun.net.httpserver.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class CoordinatesHandler implements HttpHandler {

    ArrayList<UserCoordinate> coord = new ArrayList<UserCoordinate>();
    
    @Override
    public void handle(HttpExchange t) throws IOException {

        Headers headers = t.getRequestHeaders();
        String contentType = "";
        String response = "";
        int code = 200;
        JSONObject obj = null;
        
        try {
            if(t.getRequestMethod().equalsIgnoreCase("POST")){
                if(headers.containsKey("Content-Type")){
                    //System.out.println("Contains content-type");
                    contentType = headers.get("Content-Type").get(0);
                } else {
                    //System.out.println("No content type");
                    code = 411;
                    response = "No content type in request";
                }
                if(contentType.equalsIgnoreCase("application/json")){
                    //System.out.println("app/json found");
                    InputStream stream = t.getRequestBody();
                    String newMsg = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
                    stream.close();
                    try {
                        obj = new JSONObject(newMsg); 
                        UserCoordinate tmp = new UserCoordinate(obj.getString("username"), obj.getString("longitude"), obj.getString("latitude"));
                        coord.add(tmp);
                    } catch (JSONException e) {
                        System.out.println("parse error, faulty json user");
                    }
                } 
                    byte[] bytes = response.getBytes("UTF-8");
                    t.sendResponseHeaders(code, bytes.length);
                    OutputStream stream = t.getResponseBody();
                    stream.write(bytes);
                    stream.close();

            } else if(t.getRequestMethod().equalsIgnoreCase("GET")){
                if(coord.isEmpty()){
                    System.out.println("list is empty");
                    code = 204;
                    t.sendResponseHeaders(code, -1);
                } else {
                    //System.out.println("list not empty");
                    JSONArray responseCoordinates = new JSONArray();
                    for(UserCoordinate x : coord){
                        try {
                            JSONObject obj2 = new JSONObject();
                            obj2.put("username", x.nick)
                            .put("longitude", x.longitude)
                            .put("latitude", x.latitude);
                            //System.out.println(obj2.toString());
                            responseCoordinates.put(obj2);
                        } catch (Exception e) {
                            System.out.println("json parse error");
                        }
                        
                    }
                    //System.out.println(responseCoordinates.toString());
                    OutputStream outputStream = t.getResponseBody();
                    byte[] bytes = responseCoordinates.toString().getBytes("UTF-8"); 
                    t.sendResponseHeaders(200, bytes.length);
                    outputStream.write(bytes);
                    outputStream.flush();
                    outputStream.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            code = 500;
            response = "Internal server error";
        }
        /*
        byte[] bytes = response.getBytes("UTF-8");
        t.sendResponseHeaders(code, bytes.length);
        OutputStream x = t.getResponseBody();
        x.write(bytes);
        x.close();*/
    }
}
