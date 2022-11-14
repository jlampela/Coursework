package com.project;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

class UserCoordinate {

    public String nick;
    public double longitude;
    public double latitude;
    public LocalDateTime sent;
    public String description;

    public UserCoordinate(){
    }

    public UserCoordinate(String nick, double longitude, double latitude, LocalDateTime sent){
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

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Instant getTime(){
        return this.sent.toInstant(ZoneOffset.UTC);
    }

    public void setdescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }
}
