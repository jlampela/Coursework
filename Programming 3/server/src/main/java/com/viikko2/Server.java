package com.viikko2;

import com.sun.net.httpserver.*;

import java.io.*;
import java.net.InetSocketAddress;

import javax.net.ssl.*;
import java.security.*;


class Server{

    private Server() {
    }

    private static SSLContext coordinateServerSSLContext(String file, String password) throws Exception{
        char[] passphrase = password.toCharArray();
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream(file), passphrase);

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, passphrase);

        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(ks);

        SSLContext ssl = SSLContext.getInstance("TLS");
        ssl.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        
        return ssl;
    }

    public static void main(String[] args) throws Exception {
        try {
            UserAuthenticator ua = new UserAuthenticator("coordinates");

            HttpsServer server = HttpsServer.create(new InetSocketAddress(8001),0);
            SSLContext sslContext = coordinateServerSSLContext(args[0], args[1]);

            server.setHttpsConfigurator (new HttpsConfigurator(sslContext) {
                public void configure (HttpsParameters params) {
                InetSocketAddress remote = params.getClientAddress();
                SSLContext c = getSSLContext();
                SSLParameters sslparams = c.getDefaultSSLParameters();
                params.setSSLParameters(sslparams);
                }
            });
            final HttpContext httpContext = server.createContext("/coordinates", new CoordinatesHandler());
            final HttpContext register = server.createContext("/registration", new RegistrationHandler(ua));

            httpContext.setAuthenticator(ua);

            server.setExecutor(null); 
            server.start(); 
        } catch (FileNotFoundException e) {
            // Certificate file not found!
            System.out.println("Certificate not found!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}