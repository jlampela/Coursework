package oy.tol.tra;

import java.lang.reflect.Array;

public class QueueImplementation<E> implements QueueInterface<E> {

    private E [] itemArray;
    private int maxSize;
    private int head = 0;
    private int tail = 0;
    private int count = 0;

    @Override
    public void init(Class<E> elementClass, int size) throws QueueAllocationException {
        if (size < 2) {
            throw new QueueAllocationException("Stack size should be greater than 1");
        }
        try {
            maxSize = size;
            itemArray = (E []) Array.newInstance(elementClass, maxSize);   
            head = 0;
            tail = 0;
        } catch (Exception e) {
            throw new QueueAllocationException(e.getMessage());
        }
    }

    @Override
    public int capacity() {
        return (maxSize - count);
    }

    @Override
    public void enqueue(E element) throws QueueAllocationException, NullPointerException {
        if (element == null){
            throw new NullPointerException();
        }
        try {
            if (count == maxSize){
                reallocateArray(2*maxSize);
            }
            if (tail >= maxSize && head > 0){
                tail = 0;
            }
            itemArray[tail++] = element;
            count++;
        } catch (Exception e){
            throw new QueueAllocationException(e.getMessage());
        }
    }

    @Override
    public E dequeue() throws QueueIsEmptyException {
        if (count == 0){
            throw new QueueIsEmptyException("Queue is empty.");
        }
        E x = itemArray[head];
        head++;
        count--;
        if(head >= maxSize){
            head = 0;
        }
        return x;
    }

    @Override
    public void reallocateArray(int n){
        E[] tmp = (E[]) new Object[n];
        int tmptail = 0;
        int tmpcount = 0;

        while(count > 1){
            E x = dequeue();
            tmp[tmptail++] = x;
            tmpcount++;
        }
        count = tmpcount;
        tail = tmptail;
        head = 0;
        maxSize = n;
        itemArray = tmp;
    }

    @Override
    public E element() throws QueueIsEmptyException {
        if(count == 0){
            throw new QueueIsEmptyException("Queue is empty.");
        }
        return itemArray[head];
    }

    @Override
    public int count() {
        return count;
    }

    @Override
    public boolean empty() {
        return (count() == 0);
    }

    @Override
    public void reset() {
        for(int i = 0; i<maxSize;i++){
            itemArray[i] = null;
        }
        count = 0;    
        head = 0;
        tail = 0;
    }
    @Override
    public String toString(){
        if (empty()) return "[]";
        String result = "[";
        int tmp = head;
        
        if(count == 1){
            return result + itemArray[tmp] + "]";
        }
        result += itemArray[tmp];
        tmp++;
        while(tmp != tail){
            result += ", " + itemArray[tmp++];
        }
	    return result + "]";
    }
}
