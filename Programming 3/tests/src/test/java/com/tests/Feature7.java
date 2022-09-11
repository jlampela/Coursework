package com.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
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

public class Feature7 {
    
    private static TestClient testClient = null;
    private static TestSettings testSettings = null;


    Feature7(){
        testSettings = new TestSettings();
        TestSettings.readSettingsXML("testconfigw5.xml");
        testClient = new TestClient(testSettings.getCertificate(), testSettings.getServerAddress(), testSettings.getNick(), testSettings.getPassword());

    }

    @Test
    @BeforeAll
    @DisplayName("Setting up the test environment")
    public static void initialize() {
        System.out.println("initialized Feature 7 tests");
    }
    
    @Test
    @AfterAll
    public static void teardown() {
        System.out.println("Testing finished.");
    }


    @Test 
    @Order(1)
    @DisplayName("Testing sending coordinates with weather indicator")
    void testSendCoordinates() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        System.out.println("Testing sending coordinates with weather indicator");
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
        obj.put("weather", "");

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
        for(int i=0; i<obj2.length(); i++){
            obj3 = obj2.getJSONObject(i);
            JSONObject comparison = getSpecificCoordinate(obj3);

            System.out.println(comparison);
            int weatherValue;
            if(comparison.has("weather"))
            {
                    Object whatDataFormat = comparison.get("weather");
                    if (whatDataFormat instanceof Integer){

                        weatherValue = comparison.getInt("weather");
                        
                    }else if (whatDataFormat instanceof Double){

                        Double val = comparison.getDouble("weather");
                        weatherValue = val.intValue();
                        
                    }else if (whatDataFormat instanceof BigDecimal){
                        BigDecimal val = comparison.getBigDecimal("weather");
                        weatherValue = val.intValue();
                        
                    }else{
                        String weath = comparison.getString("weather");
                        weath = weath.replaceAll("\\D+","");
                        
                        weatherValue = Integer.parseInt(weath);
                    }
                
                if(weatherValue > -100 && weatherValue < 100)
                {
                    isSame = true;
                }
            }
        }

        assertTrue(isSame);
    }


    JSONObject getSpecificCoordinate (JSONObject originalObject){

        JSONObject specificObject = new JSONObject();

        specificObject.put("sent", originalObject.getString("sent"));
        specificObject.put("username", originalObject.getString("sent"));
        specificObject.put("latitude", originalObject.getDouble("latitude"));
        specificObject.put("longitude", originalObject.getDouble("longitude"));
        if(originalObject.has("weather")){
            specificObject.put("weather", originalObject.get("weather"));
        }
        return specificObject;
    }

}
