package com.anttijuustila.tira;

class Algorithms {
     
    public <T extends Comparable<T>> T[] quicksort(T[] array){
        sort(array, 0, array.length-1);
        return array;
    }

    private <T extends Comparable<T>> void sort(T[] array, int low, int high){
        if (low < high){
            int pivot = partition(array, low, high);
            sort(array, low, pivot-1);
            sort(array, pivot+1, high);
        }
    } 

    private <T extends Comparable<T>> int partition(T[] array, int low, int high){
        T pivot = array[high];

        int i = (low - 1);
        for(int j = low; j<=high-1; j++){
            if(array[j].compareTo(pivot) < 0){
                i++;
                T temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        T temp = array[i+1];
        array[i+1] = array[high];
        array[high] = temp;
        return i+1;
    }
}
