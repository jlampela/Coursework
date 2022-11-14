package com.viikko1;

import com.sun.net.httpserver.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class Server implements HttpHandler {

    private Server() {
    }

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

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8001),0);
        server.createContext("/coordinates", new Server());
        server.setExecutor(null); 
        server.start(); 
    }
}