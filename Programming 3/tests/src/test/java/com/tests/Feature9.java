package com.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Timestamp;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javax.swing.RowFilter.ComparisonType;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;

public class Feature9 {
    
    private static TestClient testClient = null;
    private static TestSettings testSettings = null;


    Feature9(){
        testSettings = new TestSettings();
        TestSettings.readSettingsXML("testconfigw5.xml");
        testClient = new TestClient(testSettings.getCertificate(), testSettings.getServerAddress(), testSettings.getNick(), testSettings.getPassword());

    }

    @Test
    @BeforeAll
    @DisplayName("Setting up the test environment")
    public static void initialize() {
        System.out.println("initialized Feature 9 tests");
    }
    
    @Test
    @AfterAll
    public static void teardown() {
        System.out.println("Testing finished.");
    }


    @Test 
    @Order(1)
    @DisplayName("Testing request user coordinates")
    void testRequestUserCoordinates() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        System.out.println("Testing request user coordinates");
        testClient.testRegisterUserJSON(testSettings.getNick(), testSettings.getPassword(), testSettings.getEmail());
        JSONObject obj = new JSONObject();

        ZonedDateTime now =ZonedDateTime.now(ZoneId.of("UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd'T'HH:mm:ss.SSSX");

        String dateText = now.format(formatter);

        double coord1 = ThreadLocalRandom.current().nextDouble(0,180);
        double coord2 = ThreadLocalRandom.current().nextDouble(0,180);

        obj.put("sent", dateText);
        obj.put("username", "Seppo");
        obj.put("latitude", coord1);
        obj.put("longitude", coord2);

        int result = testClient.testJSONHTTPSCoordinates(obj);
        System.out.println(result);
        assertTrue(200 <= result && result <= 299);

        obj.clear();
        dateText = now.format(formatter);

        coord1 = ThreadLocalRandom.current().nextDouble(0,180);
        coord2 = ThreadLocalRandom.current().nextDouble(0,180);

        obj.put("sent", dateText);
        obj.put("username", "Teppo");
        obj.put("latitude", coord1);
        obj.put("longitude", coord2);

        result = testClient.testJSONHTTPSCoordinates(obj);
        System.out.println(result);
        assertTrue(200 <= result && result <= 299);

        obj.clear();
        dateText = now.format(formatter);

        coord1 = ThreadLocalRandom.current().nextDouble(0,180);
        coord2 = ThreadLocalRandom.current().nextDouble(0,180);

        obj.put("sent", dateText);
        obj.put("username", "Matti");
        obj.put("latitude", coord1);
        obj.put("longitude", coord2);

        result = testClient.testJSONHTTPSCoordinates(obj);
        System.out.println(result);
        assertTrue(200 <= result && result <= 299);

        String response = testClient.getUserCoordinates("Teppo");


        JSONArray obj2 = new JSONArray(response);

        boolean onlyOneUser = true;
        JSONObject obj3 = new JSONObject();

        for(int i=0; i<obj2.length(); i++){
            obj3 = obj2.getJSONObject(i);

            if(!obj3.getString("username").equals("Teppo"))
            {onlyOneUser = false; break;}
        }

        assertTrue(onlyOneUser);

    }



    @Test 
    @Order(2)
    @DisplayName("Testing getting coord on time window")
    void testCoordinatesTimeWindow() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException, InterruptedException {
        System.out.println("Testing getting coords on time window");
        testClient.testRegisterUserJSON(testSettings.getNick(), testSettings.getPassword(), testSettings.getEmail());
        JSONObject obj = new JSONObject();

        ZonedDateTime now =ZonedDateTime.now(ZoneId.of("UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd'T'HH:mm:ss.SSSX");


        String dateText = now.format(formatter);

        String timeStart = dateText;

        now = now.plusMinutes(10);

        dateText = now.format(formatter);

        double coord1 = ThreadLocalRandom.current().nextDouble(0,180);
        double coord2 = ThreadLocalRandom.current().nextDouble(0,180);

        obj.put("sent", dateText);
        obj.put("username", "Seppo");
        obj.put("latitude", coord1);
        obj.put("longitude", coord2);

        int result = testClient.testJSONHTTPSCoordinates(obj);
        System.out.println(result);
        assertTrue(200 <= result && result <= 299);

        obj.clear();

        now = now.plusMinutes(10);
        
        dateText = now.format(formatter);

        coord1 = ThreadLocalRandom.current().nextDouble(0,180);
        coord2 = ThreadLocalRandom.current().nextDouble(0,180);

        obj.put("sent", dateText);
        obj.put("username", "Teppo");
        obj.put("latitude", coord1);
        obj.put("longitude", coord2);

        result = testClient.testJSONHTTPSCoordinates(obj);
        System.out.println(result);
        assertTrue(200 <= result && result <= 299);

        obj.clear();
        now = now.plusMinutes(10);

        dateText = now.format(formatter);
        

        coord1 = ThreadLocalRandom.current().nextDouble(0,180);
        coord2 = ThreadLocalRandom.current().nextDouble(0,180);

        obj.put("sent", dateText);
        obj.put("username", "Matti");
        obj.put("latitude", coord1);
        obj.put("longitude", coord2);
        
        now = now.plusMinutes(10);
        dateText = now.format(formatter);
        String timeEnd = dateText;

        result = testClient.testJSONHTTPSCoordinates(obj);
        System.out.println(result);
        assertTrue(200 <= result && result <= 299);

        obj.clear();
        now = now.plusMinutes(10);

        dateText = now.format(formatter);

        coord1 = ThreadLocalRandom.current().nextDouble(0,180);
        coord2 = ThreadLocalRandom.current().nextDouble(0,180);

        obj.put("sent", dateText);
        obj.put("username", "Pekka");
        obj.put("latitude", coord1);
        obj.put("longitude", coord2);

        result = testClient.testJSONHTTPSCoordinates(obj);
        System.out.println(result);
        assertTrue(200 <= result && result <= 299);

        String response = testClient.getTimeCoordinates(timeStart, timeEnd);


        JSONArray obj2 = new JSONArray(response);

        boolean onlySpecificTimeWindow = true;
        JSONObject obj3 = new JSONObject();

        long timeStartLong = toMilli(timeStart);
        long timeEndLong = toMilli(timeEnd);

        for(int i=0; i<obj2.length(); i++){
            obj3 = obj2.getJSONObject(i);
            System.out.println(obj3.toString());

            long coordTime = toMilli(obj3.getString("sent"));

            if(timeStartLong > coordTime || timeEndLong < coordTime)
            {onlySpecificTimeWindow = false; break;}
        }

        assertTrue(onlySpecificTimeWindow);
    }


    @Test 
    @Order(3)
    @DisplayName("Testing false query")
    void testSendingFalseQuery() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException, InterruptedException {
        System.out.println("Testing false query");
        testClient.testRegisterUserJSON(testSettings.getNick(), testSettings.getPassword(), testSettings.getEmail());
  
        int response = testClient.testQuery("garbage");

        assertFalse(200 <= response && response <= 299);
    }
    JSONObject getSpecificCoordinate (JSONObject originalObject){

        JSONObject specificObject = new JSONObject();

        specificObject.put("sent", originalObject.getString("sent"));
        specificObject.put("username", originalObject.getString("username"));
        specificObject.put("latitude", originalObject.getDouble("latitude"));
        specificObject.put("longitude", originalObject.getDouble("longitude"));

        return specificObject;
    }

    private long toMilli (String date){

        OffsetDateTime odt = OffsetDateTime.parse(date);
        LocalDateTime ldt = odt.toLocalDateTime();
        long sent = ldt.toInstant(ZoneOffset.UTC).toEpochMilli();
        return sent;

   }

}
