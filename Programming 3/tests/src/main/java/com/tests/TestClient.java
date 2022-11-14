package com.tests;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.cert.Certificate;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Hello world!
 */
public final class TestClient {

    String username;
    String password;
    String auth;
    String fullAddress;
    static Client client;


    private static final int CONNECT_TIMEOUT = 10 * 1000;
	private static final int REQUEST_TIMEOUT = 30 * 1000;

    private String certificateFile;

    private boolean useHttpsInRequests = true;

    private static final String USER_AGENT = "Mozilla/5.0";

    public TestClient(String address){

        client = new Client(address);
        System.out.println("Test client for week 1 created");

    }

    public TestClient(String keystore, String address, String newUser, String newPassword) {

        client = new Client(keystore, address);
        username = newUser;
        password = newPassword;
        auth = username + ":" + password;

    }


    public synchronized int testConnection() throws IOException{

        int responseCode = 200;

        fullAddress = client.getServerAddress();
        fullAddress += client.getCoordinatesContext();

        URL url = new URL(fullAddress);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		responseCode = con.getResponseCode();

        return responseCode;
    }

    public synchronized int testHTTPSConnection() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException{

        int responseCode = 0;

        fullAddress = client.getServerAddress();
        fullAddress += client.getCoordinatesContext();

        URL url = new URL(fullAddress);

        HttpURLConnection con = createTrustingConnection(url);
		con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "text/plain");

        responseCode = con.getResponseCode();

        return responseCode;
    }

    public synchronized int testCoordinates(String coordinates) throws IOException {
        int responseCode = 400;
        byte[] msgBytes;
        fullAddress = client.getServerAddress();
        fullAddress += client.getCoordinatesContext();

        System.out.println(fullAddress);

        URL url = new URL(fullAddress);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
	
        msgBytes = coordinates.getBytes("UTF-8");
        con.setRequestProperty("Content-Type", "text/plain");

		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestProperty("Content-Length", String.valueOf(msgBytes.length));

		OutputStream writer = con.getOutputStream();
		writer.write(msgBytes);
		writer.close();


		responseCode = con.getResponseCode();

        return responseCode;
    }

    public synchronized int testHTTPSCoordinates(String coordinates) throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        int responseCode = 400;
        byte[] msgBytes;

        fullAddress = client.getServerAddress();
        fullAddress += client.getCoordinatesContext();

        URL url = new URL(fullAddress);

        HttpURLConnection con = createTrustingConnection(url);
	
        msgBytes = coordinates.getBytes("UTF-8");
        con.setRequestProperty("Content-Type", "text/plain");

		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestProperty("Content-Length", String.valueOf(msgBytes.length));

        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
		String authHeaderValue = "Basic " + new String(encodedAuth);
		con.setRequestProperty("Authorization", authHeaderValue);

		OutputStream writer = con.getOutputStream();
		writer.write(msgBytes);
		writer.close();


		responseCode = con.getResponseCode();

        return responseCode;
    }

    public synchronized int testJSONHTTPSCoordinates(JSONObject obj) throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        int responseCode = 400;
        byte[] msgBytes;

        fullAddress = client.getServerAddress();
        fullAddress += client.getCoordinatesContext();

        URL url = new URL(fullAddress);

        HttpURLConnection con = createTrustingConnection(url);
	
        String coordinates = obj.toString();
        msgBytes = coordinates.getBytes("UTF-8");
        con.setRequestProperty("Content-Type", "application/json");

		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestProperty("Content-Length", String.valueOf(msgBytes.length));

        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
		String authHeaderValue = "Basic " + new String(encodedAuth);
		con.setRequestProperty("Authorization", authHeaderValue);
        System.out.println("sending coords");
		OutputStream writer = con.getOutputStream();
		writer.write(msgBytes);
		writer.close();


		responseCode = con.getResponseCode();
        System.out.println("got response code: "+responseCode);

        return responseCode;
    }

    public synchronized int testJSONHTTPSCoordinatesModify(JSONObject obj) throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        int responseCode = 400;
        byte[] msgBytes;

        fullAddress = client.getServerAddress();
        fullAddress += client.getCoordinatesContext();

        URL url = new URL(fullAddress);

        HttpURLConnection con = createTrustingConnection(url);
	
        String coordinates = obj.toString();
        msgBytes = coordinates.getBytes("UTF-8");
        con.setRequestProperty("Content-Type", "application/json");

		con.setRequestMethod("PATCH");
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestProperty("Content-Length", String.valueOf(msgBytes.length));

        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
		String authHeaderValue = "Basic " + new String(encodedAuth);
		con.setRequestProperty("Authorization", authHeaderValue);
        System.out.println("sending coords");
		OutputStream writer = con.getOutputStream();
		writer.write(msgBytes);
		writer.close();


		responseCode = con.getResponseCode();
        System.out.println("got response code: "+responseCode);

        return responseCode;
    }

    public synchronized int deleteCoordinates(int coordid) throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        int responseCode = 400;
        byte[] msgBytes;

        fullAddress = client.getServerAddress();
        fullAddress += client.getCoordinatesContext();

        URL url = new URL(fullAddress);

        HttpURLConnection con = createTrustingConnection(url);

        JSONObject obj = new JSONObject();
        obj.put("id", coordid);
	
        String coordinates = obj.toString();
        msgBytes = coordinates.getBytes("UTF-8");
        con.setRequestProperty("Content-Type", "application/json");

		con.setRequestMethod("DELETE");
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestProperty("Content-Length", String.valueOf(msgBytes.length));

        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
		String authHeaderValue = "Basic " + new String(encodedAuth);
		con.setRequestProperty("Authorization", authHeaderValue);
        System.out.println("sending id for deletion");
		OutputStream writer = con.getOutputStream();
		writer.write(msgBytes);
		writer.close();


		responseCode = con.getResponseCode();
        System.out.println("got response code: "+responseCode);

        return responseCode;
    }

    public synchronized String getCoordinates() throws IOException {
        int responseCode = 400;
        fullAddress = client.getServerAddress();
        fullAddress += client.getCoordinatesContext();

        fullAddress = client.getServerAddress();
        URL url = new URL(fullAddress);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "text/plain");


		responseCode = con.getResponseCode();

        ArrayList<String> coordinates = new ArrayList<String>();
        String input;
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));

        while ((input = in.readLine()) != null) {
            coordinates.add(input);
        }

        String result = coordinates.toString();

        return result;
    }

    public synchronized String getlatestCoordinates() throws IOException {
        int responseCode = 400;

        fullAddress = client.getServerAddress();
        fullAddress += client.getCoordinatesContext();
        
        URL url = new URL(fullAddress);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "text/plain");


		responseCode = con.getResponseCode();

        ArrayList<String> coordinates = new ArrayList<String>();
        String input;
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));

        while ((input = in.readLine()) != null) {
            coordinates.add(input);
        }

        String result = coordinates.get(coordinates.size()-1);

        return result;
    }

    public synchronized String getlatestHTTPSCoordinates() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        int responseCode = 400;

        fullAddress = client.getServerAddress();
        fullAddress += client.getCoordinatesContext();

        URL url = new URL(fullAddress);

        HttpURLConnection con = createTrustingConnection(url);
		con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "text/plain");

        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
		String authHeaderValue = "Basic " + new String(encodedAuth);
		con.setRequestProperty("Authorization", authHeaderValue);

        System.out.println("done setting GET");
        responseCode = con.getResponseCode();
        System.out.println(responseCode);

        ArrayList<String> coordinates = new ArrayList<String>();
        String input;
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));

        while ((input = in.readLine()) != null) {
            coordinates.add(input);
        }

        String result = coordinates.get(coordinates.size()-1);

        return result;
    }

    public synchronized String getlatestJSONHTTPSCoordinates() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        int responseCode = 400;

        fullAddress = client.getServerAddress();
        fullAddress += client.getCoordinatesContext();

        URL url = new URL(fullAddress);

        HttpURLConnection con = createTrustingConnection(url);
		con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");

        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
		String authHeaderValue = "Basic " + new String(encodedAuth);
		con.setRequestProperty("Authorization", authHeaderValue);

        System.out.println("done setting GET");
        responseCode = con.getResponseCode();
        System.out.println(responseCode);

        StringBuilder coordinates = new StringBuilder();
        String input;
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));

        while ((input = in.readLine()) != null) {
            coordinates.append(input);
        }

        
        String result = coordinates.toString();
        System.out.println(result);

        return result;
    }


    public synchronized String getUserCoordinates(String user) throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        int responseCode = 400;
        byte[] msgBytes;

        fullAddress = client.getServerAddress();
        fullAddress += client.getCoordinatesContext();

        URL url = new URL(fullAddress);

        HttpURLConnection con = createTrustingConnection(url);

        JSONObject obj = new JSONObject();
        obj.put("query", "user");
        obj.put("nickname", user);
	
        String coordinates = obj.toString();
        msgBytes = coordinates.getBytes("UTF-8");
        con.setRequestProperty("Content-Type", "application/json");

		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestProperty("Content-Length", String.valueOf(msgBytes.length));

        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
		String authHeaderValue = "Basic " + new String(encodedAuth);
		con.setRequestProperty("Authorization", authHeaderValue);

		OutputStream writer = con.getOutputStream();
		writer.write(msgBytes);
		writer.close();


		responseCode = con.getResponseCode();
        System.out.println("got response code: "+responseCode);

        StringBuilder coordinatesReturned = new StringBuilder();
        String input;
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));

        while ((input = in.readLine()) != null) {
            coordinatesReturned.append(input);
        }

        
        String result = coordinatesReturned.toString();
        System.out.println(result);

        return result;
    }

    public synchronized String getTimeCoordinates(String start, String end) throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        int responseCode = 400;
        byte[] msgBytes;

        fullAddress = client.getServerAddress();
        fullAddress += client.getCoordinatesContext();

        URL url = new URL(fullAddress);

        HttpURLConnection con = createTrustingConnection(url);

        JSONObject obj = new JSONObject();
        obj.put("query", "time");
        obj.put("timestart", start);
        obj.put("timeend", end);
	
        String coordinates = obj.toString();
        msgBytes = coordinates.getBytes("UTF-8");
        con.setRequestProperty("Content-Type", "application/json");

		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestProperty("Content-Length", String.valueOf(msgBytes.length));

        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
		String authHeaderValue = "Basic " + new String(encodedAuth);
		con.setRequestProperty("Authorization", authHeaderValue);

		OutputStream writer = con.getOutputStream();
		writer.write(msgBytes);
		writer.close();


		responseCode = con.getResponseCode();
        System.out.println("got response code: "+responseCode);

        StringBuilder coordinatesReturned = new StringBuilder();
        String input;
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));

        while ((input = in.readLine()) != null) {
            coordinatesReturned.append(input);
        }

        
        String result = coordinatesReturned.toString();
        System.out.println(result);

        return result;
    }

    public synchronized int testQuery(String command) throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        int responseCode = 400;
        byte[] msgBytes;

        fullAddress = client.getServerAddress();
        fullAddress += client.getCoordinatesContext();

        URL url = new URL(fullAddress);

        HttpURLConnection con = createTrustingConnection(url);

        JSONObject obj = new JSONObject();
        obj.put("query", command);
	
        String coordinates = obj.toString();
        msgBytes = coordinates.getBytes("UTF-8");
        con.setRequestProperty("Content-Type", "application/json");

		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestProperty("Content-Length", String.valueOf(msgBytes.length));

        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
		String authHeaderValue = "Basic " + new String(encodedAuth);
		con.setRequestProperty("Authorization", authHeaderValue);

		OutputStream writer = con.getOutputStream();
		writer.write(msgBytes);
		writer.close();


		responseCode = con.getResponseCode();
        System.out.println("got response code: "+responseCode);

        return responseCode;
    }

    public int testRegisterUser(String newUsername, String newPassword) throws KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException, FileNotFoundException, IOException {
        int responseCode = 400;
        byte[] msgBytes;

        fullAddress = client.getServerAddress();
        fullAddress += client.getRegisterContext();

        URL url = new URL(fullAddress);

        HttpURLConnection con = createTrustingConnection(url);
	
        String userPayload = newUsername + ":" + newPassword;
        msgBytes = userPayload.getBytes("UTF-8");
        con.setRequestProperty("Content-Type", "text/plain");

		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestProperty("Content-Length", String.valueOf(msgBytes.length));

        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
		String authHeaderValue = "Basic " + new String(encodedAuth);
		con.setRequestProperty("Authorization", authHeaderValue);

		OutputStream writer = con.getOutputStream();
		writer.write(msgBytes);
		writer.close();


		responseCode = con.getResponseCode();

        return responseCode;
    }


    public int testRegisterUserJSON(String newUsername, String newPassword, String newEmail) throws KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException, FileNotFoundException, IOException {
        int responseCode = 400;
        byte[] msgBytes;

        fullAddress = client.getServerAddress();
        fullAddress += client.getRegisterContext();

        URL url = new URL(fullAddress);

        HttpURLConnection con = createTrustingConnection(url);
	
        JSONObject obj = new JSONObject();
        obj.put("username", newUsername);
        obj.put("password", newPassword);
        obj.put("email", newEmail);
        String userPayload = obj.toString();
        msgBytes = userPayload.getBytes("UTF-8");
        con.setRequestProperty("Content-Type", "application/json");

		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestProperty("Content-Length", String.valueOf(msgBytes.length));

        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
		String authHeaderValue = "Basic " + new String(encodedAuth);
		con.setRequestProperty("Authorization", authHeaderValue);

		OutputStream writer = con.getOutputStream();
		writer.write(msgBytes);
		writer.close();


		responseCode = con.getResponseCode();
        System.out.println(responseCode);

        return responseCode;
    }

    public int testRegisterGet() throws KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException, FileNotFoundException, IOException {
        int responseCode = 400;

        fullAddress = client.getServerAddress();
        fullAddress += client.getRegisterContext();

        URL url = new URL(fullAddress);

        HttpURLConnection con = createTrustingConnection(url);

        con.setRequestProperty("Content-Type", "text/plain");

		con.setRequestMethod("GET");
		con.setDoOutput(true);
		con.setDoInput(true);

        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
		String authHeaderValue = "Basic " + new String(encodedAuth);
		con.setRequestProperty("Authorization", authHeaderValue);

		responseCode = con.getResponseCode();

        return responseCode;
    }

    private HttpURLConnection createTrustingConnection(URL url) throws KeyStoreException, CertificateException,
            NoSuchAlgorithmException, FileNotFoundException, KeyManagementException, IOException {

            Certificate certificate = CertificateFactory.getInstance("X.509").generateCertificate(new FileInputStream(client.getKeystore()));
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(null, null);
            keyStore.setCertificateEntry("localhost", certificate);

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            trustManagerFactory.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setSSLSocketFactory(sslContext.getSocketFactory());
            // All requests use these common timeouts.
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(REQUEST_TIMEOUT);
            return connection;

    }

    /**
     * Main
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {

        
        // client.setupClient(args[0], args[1]);



    }

    public int sendComment(JSONObject obj) throws KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException, FileNotFoundException, IOException {

        int responseCode = 400;
        byte[] msgBytes;

        fullAddress = client.getServerAddress();
        fullAddress += client.getCommentContext();

        System.out.println(fullAddress);
        URL url = new URL(fullAddress);

        HttpURLConnection con = createTrustingConnection(url);
	
        String comment = obj.toString();
        msgBytes = comment.getBytes("UTF-8");
        con.setRequestProperty("Content-Type", "application/json");

		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestProperty("Content-Length", String.valueOf(msgBytes.length));

        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
		String authHeaderValue = "Basic " + new String(encodedAuth);
		con.setRequestProperty("Authorization", authHeaderValue);
        System.out.println("sending comment");
		OutputStream writer = con.getOutputStream();
		writer.write(msgBytes);
		writer.close();


		responseCode = con.getResponseCode();
        System.out.println("got response code: "+responseCode);

        return responseCode;
    }


}
