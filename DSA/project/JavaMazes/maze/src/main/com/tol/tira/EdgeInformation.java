package com.tol.tira;

import com.tol.tira.MazeNode.Direction;

/**
 * A small utility class to make drawing mazes easier.
 * 
 * @author Antti Juustila
 * @version 1.0
 */
class EdgeInformation {
    EdgeInformation(MazeNode.Direction direction, float weight) {
        this.direction = direction;
        this.weight = weight;
    }
    MazeNode.Direction direction = Direction.NONE;
    float weight = 1.0f;
}
