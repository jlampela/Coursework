package com.tests;

public class Client {

    private String keystore;
    private String serverAdress = "";
    private String coordinateContext ="coordinates";
    private String registerContext ="registration";
    private String commentContext ="comment";


    public Client(String keystore2, String address) {
        this.keystore = keystore2;

        if (!address.endsWith("/")) {
			serverAdress = address;
            serverAdress +=  "/";
		}
        else{
            serverAdress = address;
        }
    }

    public Client(String address) {

        if (!address.endsWith("/")) {
			serverAdress = address;
            serverAdress +=  "/";
		}
        else{
            serverAdress = address;
        }

    }

    /*
    public void setupClient (String keystorelocation, String inputServerAddress){
        
        this.keystore = keystorelocation;

        if (!inputServerAddress.endsWith("/")) {
			serverAdress = inputServerAddress;
            serverAdress +=  "/";
		}
        else{
            serverAdress = inputServerAddress;
        }

    }

    */

    public String getKeystore() {
        System.out.println(this.keystore);
        return this.keystore;
    }

    public String getServerAddress(){
        return this.serverAdress;
    }

    public String getCoordinatesContext(){
        return this.coordinateContext;
    }

    public String getRegisterContext(){
        return this.registerContext;
    }

    public String getCommentContext(){
        return this.commentContext;
    }
    
}
