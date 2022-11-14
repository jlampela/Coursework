package com.viikko2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.sun.net.httpserver.*;

class CoordinatesHandler implements HttpHandler {

    ArrayList<String> coord = new ArrayList<String>();
    
    @Override
    public void handle(HttpExchange t) throws IOException {

        if (t.getRequestMethod().equalsIgnoreCase("POST")) {
            InputStream stream = t.getRequestBody();
            String text = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
            coord.add(text.toString() + "\n");
            stream.close();
            t.sendResponseHeaders(200, -1);
        } else if (t.getRequestMethod().equalsIgnoreCase("GET")) {
            String responseCoordinates = null;
            if(coord.size() > 0){
                OutputStream outputStream = t.getResponseBody();
                responseCoordinates = coord.get(0).toString();
                coord.remove(0);
                byte [] bytes = responseCoordinates.getBytes("UTF-8"); 
                t.sendResponseHeaders(200, bytes.length);
                outputStream.write(responseCoordinates.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();
                return;
            }
            t.sendResponseHeaders(200, -1);
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
