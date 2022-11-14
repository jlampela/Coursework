package com.project;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Base64;

import javax.xml.parsers.*;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.w3c.dom.Document;




class Weather {

    public Weather(){
    
    }

    private static final int CONNECT_TIMEOUT = 10 * 1000;
	private static final int REQUEST_TIMEOUT = 30 * 1000;
    private static final String apiurl = "https://localhost:4001/weather/";
    private static final String password = "salasana123";
    private String weather;

    public String sendPost(double lat, double lon) {

        try {
            
            URL url = new URL(apiurl);
            byte[] msgBytes;
            String xml = "<coordinates>\n   <latitude>" + lat + "</latitude>\n    <longitude>" + lon + "</longitude>\n</coordinates>";

            HttpURLConnection con = createTrustingConnection(url);
	
            msgBytes = xml.getBytes("UTF-8");
            con.setRequestProperty("Content-Type", "application/xml");

            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Length", String.valueOf(msgBytes.length));

            byte[] encodedAuth = Base64.getEncoder().encode(password.getBytes(StandardCharsets.UTF_8));
            String authHeaderValue = "Basic " + new String(encodedAuth);
            con.setRequestProperty("Authorization", authHeaderValue);

            OutputStream writer = con.getOutputStream();
            writer.write(msgBytes);
            writer.close();
            
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(con.getInputStream());
            doc.getDocumentElement().normalize();
            
            String temp = doc.getElementsByTagName("temperature").item(0).getTextContent().trim();
            String unit = doc.getElementsByTagName("Unit").item(0).getTextContent().trim();

            weather = "Weather: " + temp + " " + unit;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weather;
    }

    private HttpURLConnection createTrustingConnection(URL url) throws KeyStoreException, CertificateException,
            NoSuchAlgorithmException, FileNotFoundException, KeyManagementException, IOException, UnrecoverableKeyException {

            char[] pass = password.toCharArray();
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream("C:/Users/johan/Ohjelmointi3/keystore.jks"), pass);
            //keyStore.setCertificateEntry("localhost", certificate);

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(keyStore, pass);

            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setSSLSocketFactory(sslContext.getSocketFactory());
            // All requests use these common timeouts.
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(REQUEST_TIMEOUT);
            return connection;

    }

}

