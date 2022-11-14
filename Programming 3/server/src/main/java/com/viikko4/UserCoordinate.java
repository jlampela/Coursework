package com.viikko4;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

class UserCoordinate {

    public String nick;
    public String longitude;
    public String latitude;
    public LocalDateTime sent;

    public UserCoordinate(){
    }

    public UserCoordinate(String nick, String longitude, String latitude, LocalDateTime sent){
            this.nick = nick;
            this.longitude = longitude;
            this.latitude = latitude;
            this.sent = sent;
    }

    long dateAsInt(){
        return sent.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    void setSend(long epoch){
        sent = LocalDateTime.ofInstant(Instant.ofEpochMilli(epoch), ZoneOffset.UTC);
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

    public Instant getTime(){
        return this.sent.toInstant(ZoneOffset.UTC);
    }

}
