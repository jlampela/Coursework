package com.tol.tira;

/**
 * MazeObserver implements the Observer role of the Observer design pattern.
 * 
 * @author Antti Juustila
 * @version 1.0
 */
public interface MazeObserver {
    /**
     * The event subject is notifying the observers about.
     */
    enum Event {
        /** Some operation is now done. */
        DONE,
        /** Operation is now started. */
        BEGIN_PROCESSING,
        /** Maze changed so need to redraw it and reset things. */
        MAZE_CHANGED,
        /** The path created by algorithm changed, so do redraw. */
        PATH_CHANGED,
        /** Game mode was switched on. */
        GAME_ON,
        /** Game is over or game mode switched off. */
        GAME_OFF,
        /** Some error happened, tell the user. */
        ERROR,
    }

    /** Subject calls this to notify observers of an event. */
    void update(Event event);
    /** Subject calls this to notify observers of an event with a message to the user. */
    void update(Event event, String message);
}
