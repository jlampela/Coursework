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
    BST ignorePuu = new BST();

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
        //Create BST for ignore words
        try {
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
                ignorePuu.addNode(hashCode(x), x);
                ignorePuu.wordsToIgnore++;
                x = "";
                cIndex = 0;
                a = new int[20];
            }
            r.close();
        } catch (OutOfMemoryError e) {
            throw new OutOfMemoryError(e.getMessage());
        } catch (IOException a) {
            throw new IOException(a.getMessage());
        }

        //Create BST for book words
        try {
            String word = "";
            int c;
            int[] array = new int[100];
            int currentIndex = 0;
            FileReader r = new FileReader(bookFile, StandardCharsets.UTF_8);
            
            while((c = r.read()) != -1){
                if(Character.isLetter(c)){
                    array[currentIndex] = c;
                    currentIndex++;
                    continue;
                }
                word = new String(array, 0, currentIndex);
                word = word.toLowerCase();
                if(ignorePuu.etsi(ignorePuu.root, hashCode(word)) && word.length() >= 2){
                    puu.addNode(hashCode(word), word);
                } else {
                    puu.wordsIgnored++;
                }
                word = "";
                currentIndex = 0;
                array = new int[100];
            }
            r.close();
        } catch (OutOfMemoryError e) {
            throw new OutOfMemoryError(e.getMessage());
        } catch (IOException a) {
            throw new IOException(a.getMessage());
        }
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
        System.out.println("Count of words to ignore is: " + ignorePuu.wordsToIgnore);   
        System.out.println("Count of letters that were ignored: " + puu.wordsIgnored);
        System.out.println("BST depth: " + puu.depth(puu.root));
        puu.toArray(puu.root);
        puu.printTop();
    }

    @Override
    public void close() {
        ignoreBook = null;
        bookFile = null;
        puu = new BST();
        ignorePuu = new BST();
        return;
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
        return puu.top100[position].name;
    }

    @Override
    public int getWordCountInListAt(int position) {
        if(puu.amountOfWords < 0 || position >= puu.uniqueWords || position < 0){
            return -1;
        }
        return puu.top100[position].count;
    }
}
