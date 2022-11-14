package com.project;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Comment {

    public int id;
    public String comment;
    public LocalDateTime sent;
    
    public Comment(int id, String comment, LocalDateTime sent){
        this.id = id;
        this.comment = comment;
        this.sent = sent;
    }

    long dateAsInt(){
        return sent.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    void setSend(long epoch){
        sent = LocalDateTime.ofInstant(Instant.ofEpochMilli(epoch), ZoneOffset.UTC);
    }

    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public String getComment(){
        return this.comment;
    }
}
