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

import org.json.JSONException;
import org.json.JSONObject;

class CommentHandler implements HttpHandler{

    @Override
    public void handle(HttpExchange t) throws IOException{

        final Headers headers = t.getRequestHeaders();
        String contentType = "";
        String response = "";
        int code = 200;

        try {
            if(t.getRequestMethod().equalsIgnoreCase("POST")){
                if(headers.containsKey("Content-Type")){
                    contentType = headers.get("Content-Type").get(0);
                } else {
                    code = 411;
                    response = "No content type in response";
                }
                if(contentType.equalsIgnoreCase("application/json")){
                    InputStream stream = t.getRequestBody();
                    String newMsg = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
                    stream.close();
                    try {
                        JSONObject obj = new JSONObject(newMsg);

                        OffsetDateTime x = OffsetDateTime.parse(obj.getString("sent"));
                        //System.out.println(obj);

                        Comment tmp = new Comment(obj.getInt("id"), obj.getString("comment"), x.toLocalDateTime());

                        JSONObject j = new JSONObject();
                        j.put("id", tmp.getID())
                        .put("comment", tmp.getComment())
                        .put("sent", tmp.dateAsInt());

                        //System.out.println(j);
                        CoordinateDB.getInstance().addComment(j);

                    } catch (JSONException e) {
                        code = 411;
                        response = "Faulty comment";
                    }
                }
                    byte[] bytes = response.getBytes("UTF-8");
                    t.sendResponseHeaders(code, bytes.length);
                    OutputStream stream = t.getResponseBody();
                    stream.write(bytes);
                    stream.close();
            } else {
                code = 405;
                response = "No other than POST allowed";
            }
        } catch (Exception e) {
            e.getStackTrace();
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
