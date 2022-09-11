package com.tests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;

public class PostHTTPSCoordinates {
    
    private static TestClient testClient = null;
    private static TestSettings testSettings = null;


    PostHTTPSCoordinates(){
        testSettings = new TestSettings();
        TestSettings.readSettingsXML("testconfigw2.xml");
        testClient = new TestClient(testSettings.getCertificate(), testSettings.getServerAddress(), testSettings.getNick(), testSettings.getPassword());

    }

    @Test
    @BeforeAll
    @DisplayName("Setting up the test environment")
    public static void initialize() {
        System.out.println("initialized week 2 tests");
    }
    
    @Test
    @AfterAll
    public static void teardown() {
        System.out.println("Testing finished.");
    }

    @Test 
    @Order(1)
    @DisplayName("Testing server connection")
    void testHTTPServerConnection() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        System.out.println("Testing server connection");
        int result = testClient.testHTTPSConnection();
        assertTrue(result > 1);
    }

    @Test 
    @Order(2)
    @DisplayName("Testing registering an user")
    void testRegisterUser() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        System.out.println("Testing registering an user");
        int result = testClient.testRegisterUser("jokurandom", "jokurandompsw");
        System.out.println(result);
        assertTrue(200 <= result && result <= 299);
    }

    @Test 
    @Order(3)
    @DisplayName("Testing registering same user again - must fail")
    void testRegisterUserAgain() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        System.out.println("Testing registering same user again - must fail");
        testClient.testRegisterUser("randomi1", "randomi1");
        int result = testClient.testRegisterUser("randomi1", "randomi1");
        System.out.println(result);
        assertFalse(200 <= result && result <= 299);
    }

    @Test 
    @Order(4)
    @DisplayName("Testing sending coordinates to server")
    void testSendCoordinates() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        System.out.println("Testing sending coordinates to server");
        String coordinates = "Longitude 1 Latitude 2";
        testClient.testRegisterUser(testSettings.getNick(), testSettings.getPassword());
        int result = testClient.testHTTPSCoordinates(coordinates);
        System.out.println(result);
        assertTrue(200 <= result && result <= 299);
        String response = testClient.getlatestHTTPSCoordinates();
        assertEquals(coordinates, response);
    }

    @Test 
    @Order(5)
    @DisplayName("Testing characters")
    void testCharacters() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        System.out.println("Testing characters");
        String coordinates = "åäö";
        testClient.testRegisterUser(testSettings.getNick(), testSettings.getPassword());
        int result = testClient.testHTTPSCoordinates(coordinates);
        System.out.println(result);
        assertTrue(200 <= result && result <= 299);
        String response = testClient.getlatestHTTPSCoordinates();
        assertEquals(coordinates, response);
    }
/* pieleen mennyt testi - ei ajeta
    @Test 
    @Order(6)
    @DisplayName("Testing empty string")
    void testEmptyString() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        System.out.println("Testing empty string");
        String coordinates = "";
        int result = testClient.testHTTPSCoordinates(coordinates);
        assertTrue(result == 200 || result == 204);

    }
*/

    @Test 
    @Order(7)
    @DisplayName("Sending empty string to registration - must fail")
    void testRegisterRubbish() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        System.out.println("Testing sending empty string to registration");
        int result = testClient.testRegisterUser("", "");
        System.out.println(result);
        assertFalse(200 <= result && result <= 299);

    }

    @Test 
    @Order(7)
    @DisplayName("Sending GET to registration - must fail")
    void testRegisterGet() throws IOException, KeyManagementException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        System.out.println("Sending GET to registration - must fail");
        int result = testClient.testRegisterGet();
        System.out.println(result);
        assertFalse(200 <= result && result <= 299);

    }
}
