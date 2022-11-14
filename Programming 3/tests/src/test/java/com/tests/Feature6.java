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

public class Feature6 {
    
    private static TestClient testClient = null;
    private static TestSettings testSettings = null;


    Feature6(){
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
    @DisplayName("Testing sending coordinates to server")
    void testSendCoordinates() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        System.out.println("Testing sending coordinates to server");
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

        JSONObject comment = new JSONObject();
        comment.put("id", id);
        comment.put("comment", "jokin kommentti");
        comment.put("sent", dateText);

        int result2 = testClient.sendComment(comment);
        System.out.println(result2);
        assertTrue(200 <= result2 && result2 <= 299);

        String commentResponse = testClient.getlatestJSONHTTPSCoordinates();
        JSONArray commentArray = new JSONArray(commentResponse);

        boolean commentFound = false;

        for(int i=0; i<commentArray.length(); i++){
            obj3 = commentArray.getJSONObject(i);

            System.out.println("\n comparing object: " + obj3);
            if(obj3.has("comments"))
            {

                System.out.println("comments found");
            
                JSONArray arr = new JSONArray(obj3.getJSONArray("comments"));
                
                System.out.println("\n" + arr + "\n");

                for(int a=0; a<arr.length(); a++){
                    JSONObject comapr = arr.getJSONObject(a);

                    System.out.println("comparing: " + comapr + " to " + comment);
                    if(comapr.similar(comment)){
                    commentFound = true; break;
                    }
                }
            }
        }

        assertTrue(commentFound);
    }



    @Test 
    @Order(2)
    @DisplayName("Testing rubbish comments to server")
    void testSendinRubbishToComment() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        System.out.println("Testing sending rubbish comments to server");
        testClient.testRegisterUserJSON(testSettings.getNick(), testSettings.getPassword(), testSettings.getEmail());

        JSONObject comment = new JSONObject();
        comment.put("ID", "ölaskdjflaskdfj");
        comment.put("comment", "asdfpasoidfpoasd");
        comment.put("sent", "alöskdfjaslödkfjlaskdf");

        int result2 = testClient.sendComment(comment);
        System.out.println(result2);
        assertFalse(200 <= result2 && result2 <= 299);

    }

    @Test 
    @Order(3)
    @DisplayName("Testing rubbish to server")
    void testSendinRubbishToCommentAgain() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        System.out.println("Testing sending rubbish comments to server");
        testClient.testRegisterUserJSON(testSettings.getNick(), testSettings.getPassword(), testSettings.getEmail());

        JSONObject comment = new JSONObject();
        comment.put("bla", "ösdflaskdjflaskdfj");

        int result2 = testClient.sendComment(comment);
        System.out.println(result2);
        assertFalse(200 <= result2 && result2 <= 299);

    }

    JSONObject getSpecificCoordinate (JSONObject originalObject){

        JSONObject specificObject = new JSONObject();

        specificObject.put("sent", originalObject.getString("sent"));
        specificObject.put("username", originalObject.getString("username"));
        specificObject.put("latitude", originalObject.getDouble("latitude"));
        specificObject.put("longitude", originalObject.getDouble("longitude"));

        return specificObject;
    }

}
