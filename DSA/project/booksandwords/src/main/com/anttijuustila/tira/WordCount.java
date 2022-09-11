package com.anttijuustila.tira;

public class WordCount implements Comparable<WordCount> {
    
    String sana;
    int laskuri;

    WordCount(String sana, int laskuri){
        this.sana = sana;
        this.laskuri = laskuri;
    }

    public String getSana(){
        return sana;
    }

    public int getLaskuri(){
        return laskuri;
    }

    public int compareTo(WordCount wc){
        int compare = ((WordCount) wc).getLaskuri();
        return compare - this.laskuri;
    }
}
