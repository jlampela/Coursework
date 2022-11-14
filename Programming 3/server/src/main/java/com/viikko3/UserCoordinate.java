package com.viikko3;

class UserCoordinate {

    public String nick;
    public String longitude;
    public String latitude;
    
    public UserCoordinate(){

    }

    public UserCoordinate(String nick, String longitude, String latitude){
        this.nick = nick;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    

}
