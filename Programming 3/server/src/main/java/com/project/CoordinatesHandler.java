package com.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.stream.Collectors;

import com.sun.net.httpserver.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class CoordinatesHandler implements HttpHandler {

    int id = 1;

    @Override
    public void handle(HttpExchange t) throws IOException {

        System.out.println("Request handled in thread " + Thread.currentThread().getId());
        final Headers headers = t.getRequestHeaders();
        String contentType = "";
        String response = "";
        int code = 200;
        
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
                        JSONObject obj = new JSONObject(newMsg);
                        //System.out.println(obj);

                        OffsetDateTime x = OffsetDateTime.parse(obj.getString("sent"));
                        
                        UserCoordinate tmp = new UserCoordinate(obj.getString("username"), obj.getDouble("longitude"), obj.getDouble("latitude"), x.toLocalDateTime());

                        
                        try {
                            if(obj.getString("description").length() <= 1024 && !obj.getString("description").isEmpty()){
                                tmp.setdescription(obj.getString("description"));
                            } else {
                                tmp.setdescription("nodata");
                            }
                        } catch (JSONException e) {
                            tmp.setdescription("nodata");
                        }
                        
                        JSONObject j = new JSONObject();
                            j.put("username", tmp.getNick())
                            .put("longitude", tmp.getLongitude())
                            .put("latitude", tmp.getLatitude())
                            .put("sent", tmp.dateAsInt())
                            .put("description", tmp.getDescription())
                            .put("id", id++);

                        try {
                            if(obj.getString("weather").isEmpty()){
                                j.put("weather", "");
                            }
                        } catch (JSONException e) {
                            j.put("weather", "nodata");
                        }
                        CoordinateDB.getInstance().addMessage(j);
                        //System.out.println(j);
                    } catch (JSONException e) {
                        code = 411;
                        response = "Faulty coordinates";
                    }
                }
                byte[] bytes = response.getBytes("UTF-8");
                t.sendResponseHeaders(code, bytes.length);
                OutputStream stream = t.getResponseBody();
                stream.write(bytes);
                stream.close();

            } else if(t.getRequestMethod().equalsIgnoreCase("GET")){
                if(CoordinateDB.getInstance().checkIsEmpty()){
                    code = 204;
                    t.sendResponseHeaders(code, -1);
                } else {
                    JSONArray responseCoordinates = CoordinateDB.getInstance().getMessages();
                    //System.out.println(responseCoordinates);
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
        if(code >= 400) {
            byte[] bytes = response.getBytes("UTF-8");
            t.sendResponseHeaders(code, bytes.length);
            OutputStream stream = t.getResponseBody();
            stream.write(response.getBytes());
            stream.close();
        }
    }
}
