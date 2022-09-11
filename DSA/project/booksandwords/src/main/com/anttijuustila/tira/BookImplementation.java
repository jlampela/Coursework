package com.anttijuustila.tira;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BookImplementation implements Book{

    File ignoreBook;
    File bookFile;

    BST puu = new BST();

    WordCount[] wordsCounted;
    Algorithms algo = new Algorithms();

    int wordsToIgnore = 0;
    int wordsIgnored = 0;

    @Override
    public void setSource(String fileName, String ignoreWordsFile) throws FileNotFoundException {
        
        File book = new File(fileName);
        File book1 = new File(ignoreWordsFile);
        if(book.exists() && book1.exists()){
            ignoreBook = book1;
            bookFile = book;
        } else {
            throw new FileNotFoundException();
        }
    }

    @Override
    public void countUniqueWords() throws IOException, OutOfMemoryError {
        String[] sanat = new String[38];
        int temp = 0;

        String x = "";
        int cs;
        int[] a = new int[20];
        int cIndex = 0;
        FileReader r = new FileReader(ignoreBook, StandardCharsets.UTF_8);
        
        while((cs = r.read()) != -1){
            if(Character.isLetter(cs)){
                a[cIndex] = cs;
                cIndex++;
                continue;
            }

            x = new String(a, 0, cIndex);
            x = x.toLowerCase();
            if(x.length() > 0){
                sanat[temp] = x;
                temp++;
                this.wordsToIgnore++;
            }
            x = "";
            cIndex = 0;
            a = new int[20];
        }
        r.close();
        

        //Create BST for book words
        Boolean found = false;
        String word = "";
        int c;
        int[] array = new int[100];
        int currentIndex = 0;
        FileReader ra = new FileReader(bookFile, StandardCharsets.UTF_8);
        
        //Read string
        while((c = ra.read()) != -1){
            if(Character.isLetter(c)){
                array[currentIndex] = c;
                currentIndex++;
                continue;
            }

            word = new String(array, 0, currentIndex);
            word = word.toLowerCase();

             
            for(String i : sanat){
                if(i.equals(word)){
                    found = true;
                    break;
                } else {
                    found = false;
                }
            }

            if(word.length() >= 2 && !found){
                puu.addNode(hashCode(word), word);
            } else {
                this.wordsIgnored++;
                found = false;
            }
            word = "";
            currentIndex = 0;
            array = new int[100];
        }
        ra.close();
    }

    /**
     * Simple hasher for strings
    */
    public int hashCode(String name) {
        int hash = 31;
        for(int i = 0; i < name.length(); i++){
            hash = (hash * 31 + name.charAt(i));
        }
        return hash;
    }

    @Override
    public void report() {
        System.out.println("Total amount of words: " + puu.amountOfWords);  
        System.out.println("Total amount of unique words: "+ puu.uniqueWords);
        System.out.println("Count of words to ignore is: " + this.wordsToIgnore);   
        System.out.println("Count of letters that were ignored: " + this.wordsIgnored);
        System.out.println("BST depth: " + puu.depth(puu.root));
        wordsCounted = puu.inOrder(puu.root);
        wordsCounted = algo.quicksort(wordsCounted);
        printList();
    }

    @Override
    public void close() {
        ignoreBook = null;
        bookFile = null;
        puu = new BST();
        return;
    }

    public void printList(){
        int j = 1;
        for(WordCount i : wordsCounted){
            System.out.println(j + ". -> " + i.sana + ": " + i.laskuri);
            j++;
        }
    }

    @Override
    public int getUniqueWordCount() {
        return puu.uniqueWords;
    }

    @Override
    public int getTotalWordCount() {
        return puu.amountOfWords;
    }

    @Override
    public String getWordInListAt(int position) {
        if(puu.amountOfWords <= 0 || (puu.amountOfWords < 100 && position > puu.uniqueWords) || position < 0){
            return null;
        }
        return puu.tmp[position].sana;
    }

    @Override
    public int getWordCountInListAt(int position) {
        if(puu.amountOfWords < 0 || position >= puu.uniqueWords || position < 0){
            return -1;
        }
        return puu.tmp[position].laskuri;
    }
}
