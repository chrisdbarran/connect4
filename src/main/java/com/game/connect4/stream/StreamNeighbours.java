package com.game.connect4.stream;

import java.util.LinkedList;

public class StreamNeighbours<T> {

    private final int queueLength;

    private LinkedList<T> queue = new LinkedList<>();
    
    private boolean fourElementsRead;

    public StreamNeighbours(int queueLength) {
        this.queueLength = queueLength;
    }

    public StreamNeighbours<T> addNext (T nextElem) {
        if (queue.size()  == queueLength )
        {
            queue.remove();
        }

        queue.add(nextElem);

        if(!areFourElementsRead() && queue.size() == queueLength) {
            fourElementsRead = true;
        }

        return this;
    }

    public boolean areFourElementsRead() {
        return fourElementsRead;
    }

    public T getFirst() {
        return queue.getFirst();
    }

    public T getLast() {
        return queue.getLast();
    }

}

