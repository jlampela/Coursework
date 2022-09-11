package com.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.RowFilter.ComparisonType;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;

public class Feature8 {
    
    private static TestClient testClient = null;
    private static TestSettings testSettings = null;


    Feature8(){
        testSettings = new TestSettings();
        TestSettings.readSettingsXML("testconfigw5.xml");
        testClient = new TestClient(testSettings.getCertificate(), testSettings.getServerAddress(), testSettings.getNick(), testSettings.getPassword());

    }

    @Test
    @BeforeAll
    @DisplayName("Setting up the test environment")
    public static void initialize() {
        System.out.println("initialized Feature 6 tests");
    }
    
    @Test
    @AfterAll
    public static void teardown() {
        System.out.println("Testing finished.");
    }


    @Test 
    @Order(1)
    @DisplayName("Testing if coordinates can be deleted")
    void testDelete() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        System.out.println("Testing if coordinates can be deleted");
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
        obj.put("description", "smth");

        int result = testClient.testJSONHTTPSCoordinates(obj);
        System.out.println(result);
        assertTrue(200 <= result && result <= 299);
        String response = testClient.getlatestJSONHTTPSCoordinates();
        JSONArray obj2 = new JSONArray(response);
        System.out.println(response);
        System.out.println("object is" + obj2);
        //Jokin joukko jsonobjekteja, joista pitää tunnistaa lähetetty objekti...
        boolean isSame = false;
        JSONObject obj3 = new JSONObject();

        boolean itemFound = false;

        for(int i=0; i<obj2.length(); i++){
            obj3 = obj2.getJSONObject(i);
            JSONObject comparison = getSpecificCoordinate(obj3);

            System.out.println(comparison);
            if(obj.similar(comparison))
            {itemFound = true; break;}
        }

        assertTrue(itemFound);

        int id = obj3.getInt("id");

        int responseDelete = testClient.deleteCoordinates(id);
        assertTrue(200 <= responseDelete && responseDelete <= 299);

        String response2 = testClient.getlatestJSONHTTPSCoordinates();
        JSONArray deleteResponse = new JSONArray(response);
        System.out.println(deleteResponse);


        boolean hasBeenDeleted = true;
        JSONObject obj4 = new JSONObject();
        for(int i=0; i<deleteResponse.length(); i++){
            obj4 = deleteResponse.getJSONObject(i);
            JSONObject comparison = new JSONObject(getSpecificCoordinate(obj4));

            System.out.println(comparison);
            if(obj.similar(comparison))
            {hasBeenDeleted = false;break;}
        }

        assertTrue(hasBeenDeleted);
    }



    @Test 
    @Order(2)
    @DisplayName("Testing if coordinates can be edited")
    void testEditCoordinate() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
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
        obj.put("description", "jotakin");

        int result = testClient.testJSONHTTPSCoordinates(obj);
        System.out.println(result);
        assertTrue(200 <= result && result <= 299);
        String response = testClient.getlatestJSONHTTPSCoordinates();
        JSONArray obj2 = new JSONArray(response);
        System.out.println(response);
        System.out.println("object is" + obj2);
        //Jokin joukko jsonobjekteja, joista pitää tunnistaa lähetetty objekti...


        JSONObject obj3 = new JSONObject();
        boolean hasBeenModified = false;
        for(int i=0; i<obj2.length(); i++){
            obj3 = obj2.getJSONObject(i);
            JSONObject comparison = getSpecificCoordinate(obj3);

            System.out.println(comparison);
            if(obj.similar(comparison))
            {

                int id = obj3.getInt("id");

                JSONObject objModified = new JSONObject();
        
                objModified.put("id", id);
                objModified.put("sent", dateText);
                objModified.put("username", "Seppo");
                objModified.put("latitude", coord1);
                objModified.put("longitude", coord2);
                objModified.put("description", "modified juttu");
        
                int responseDelete = testClient.testJSONHTTPSCoordinates(objModified);
                assertTrue(200 <= responseDelete && responseDelete <= 299);
        
                String response2 = testClient.getlatestJSONHTTPSCoordinates();
                JSONArray modifyResponse = new JSONArray(response2);
                System.out.println(modifyResponse);
        
                objModified.remove("id");

                JSONObject obj4 = new JSONObject();
                for(int ii=0; i<modifyResponse.length(); i++){
                    obj4 = modifyResponse.getJSONObject(i);
                    JSONObject comparison2 = getSpecificCoordinate(obj4);
        
                    System.out.println(comparison2);
                    if(objModified.similar(comparison2))
                    {hasBeenModified = true;break;}
                }

            }
        }

       

        assertTrue(hasBeenModified);
    }

    JSONObject getSpecificCoordinate (JSONObject originalObject){

        JSONObject specificObject = new JSONObject();

        specificObject.put("sent", originalObject.getString("sent"));
        specificObject.put("username", originalObject.getString("username"));
        specificObject.put("latitude", originalObject.getDouble("latitude"));
        specificObject.put("longitude", originalObject.getDouble("longitude"));
        if(originalObject.has("description")){
            specificObject.put("description", originalObject.getString("description"));
        }
        return specificObject;
    }

}
