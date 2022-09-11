# Maze

Maze is a course project task in the course Data Structures and Algorithms.

In this project, the Java container classes and algorithms are free to use. So if you need to use a `Stack`, `Queue`, `PriorityQueue`, `Set` and/or `Vector`, just do it. Also any algorithm that you may need from the Java is allowed.

What is **not allowed** is to use an existing Java Graph implementation from *anywhere*. As explained in the course, copying and plagiarizing is not allowed.

If you want to read the code documentation as HTML API documentation, you can create documentation with command `mvn javadoc:javadoc`. The documentation can then be found from project directory `target/site/apidocs/index.html`.

## Project description 

The application implements a graph data structure to be used in maze like applications.

View the course demo videos about the working implementation to get an impression on the functionality.

The application has some code already implemented. Your task as a student is to implement the graph related algorithms and data structures only. The code you should implement is all located in the `com.tol.tira.graph` package.

The project contains the following classes already implemented, you should *not* be editing:

1. `MazeApp`: the main program for a Java app with Swing GUI. Also implements the menu bar of the application. Contains `ControlPanel` and `MazePanel`.
1. `ControlPanel`: a strip of buttons at the botton of the app main window for executing graph related commands.
1. `MazePanel`: an area in the main window where the maze is drawn. Animates maze related events.
1. `MazeObserver`: `ControlPanel` and `MazePanel` are observers of the Maze. Whenever the Maze changes, it notifies the observers. Observers can then update the GUI. This is the famous [Observer](https://en.wikipedia.org/wiki/Observer_pattern) design pattern.
1. `MazeNode`: the graph data structures in the `graph` package are generic. The `Graph` and other associated classes in the `graph` package can handle any data types that fulfill the Java `Comparable` interface. That is, you can use the graph data structure to *contain* application specific elements. In this app, the `MazeNode` is that: graph vertices contain `MazeNode` that is basically just one place in the maze, specified by the x and y coordinates of that "dot" in the maze. If you take a look at the Unit tests, there you can see that instead of `MazeNode`, Java `Integer` class is used as the template parameter.
1. `EdgeInformation`: a small utility class that makes it easier to draw graph vertices and edges to the GUI.
1. `Maze` extends the `graph.Graph` class. `Graph` is a generic class to handle any kinds of graphs for any application area. `Maze` builds on top of the `Graph` to specify how exactly graphs are used to implement mazes containing `MazeNode`s in the graph as template parameters.

Your work to be done is in the `graph` package. It contains the following classes. You should have watched the course lectures and read course materials to understand these concepts. Here we just shortly describe the implementation but do not explain the concepts. So if you do not know what is a vertex etc., check out the course lectures and materials first! All the classes are generic (template) classes. A concept you should be familiar with from earlier programming courses.

1. `Vertex`: implements the vertex of a graph.
1. `Edge`: implements an edge between two vertices.
1. `Visit`: a small utility class needed in implementing the Dijkstra shortest path algorithm.
1. `Graph`: an abstract class inherited by `Maze`, implements the graph data structure as an edge list.

All of the work is done with the `Graph` class. Implement the missing methods of that class following the instructions below.

Some graph related design decision have already been made for you. The graph is build using an **edge list**. You do not have to make this decision and wonder if adjacency list or matrix should be used. So just go with the edge list.

**First**, get to know the structure, the classes, and start implementing the missing pieces. Without these, your obviously cannot build or use the app the way you see it in the course demonstrations.

You will find **TODO comments** on the code. Use the search feature of your IDE to find the items to do. After the small utility classes have an implementation, you can focus on the `Graph` class. 

## First steps

As you can see from the `MazeApp`, the first thing it does is that it calls `maze.createMaze(sizeX, sizeY, true);`. So the very first thing you should implement is the code needed to actually create a maze. As mentioned already, you should not change the `Maze` implementation. See that `Maze.createMaze` calls inherited methods from the `Graph`:

```Java
      for (int y = 0; y < this.height; y++) {
         for (int x = 0; x < this.width; x++) {
            createVertexFor(new MazeNode(x, y));
         }
      }
// ... and later:
      Vertex<MazeNode> startVertex = getVertexFor(new MazeNode(0, 0));
// And
      randomlyCreateEdges(startVertex, visited);
// And in randomlyCreateEdges:
      addEdge(EdgeType.UNDIRECTED, fromVertex, nextVertex, 1.0);
```
You **do not** have to change this code. See how it uses the `Graph` methods to create a maze as a graph.

What you can see here is that in creating the maze, methods implemented in `Graph` are called. `createVertexFor` and `addEdge` are **methods of** `Graph` -- those are not implemented. **You must implement them**. Again following what was taught in the course lectures and demonstrations.

After you have implemented the `Vertex`, `Edge`, `Visit` and the methods needed to create a graph, and you launch the app you should see the graph drawn on the GUI.

At this point you should be able to **execute** the `BasicGraphTests` described below in **Unit Testing** section. If you have issues with this step, the tests are there to help you out finding what is wrong.

If you do not see the graph drawn, make sure you have followed the instructions in the comments to call, when appropriate:

```Java
abstract void addToPath(Vertex<T> vertex);
```

That abstract method is already implementented in `Maze` so that whenever a vertex is added to a path, it will animate it in the GUI. So make sure that you *call* this method in `Graph` whenever you are instructed to do so.

Also, you should be able to load graphs from the graph description files! As you can see, `Maze.createFromFile(File file)` is called when the main menu item "Load maze file..." is selected, and this method reads the maze file and uses the same `createVertexFor` and `addEdge` methods to create a maze from the file.

Any other method at this point does nothing, may return values that lead to crashes, so do not click the other buttons or menu items until you have finished those.

## Next steps

After your code implements the creation of the maze by implementing the relevant `Graph` methods, you can start working on the other functionalities.

One approach to get started is to implement it **one use case a time**. For example, the GUI has a button to execute breadth-first search. You will see that:

1. When the user clicks the BFS button, `ControlPanel` calls `maze.doBreadthFirstSearch()`
1. `Maze.doBreadthFirstSearch()` does some work you should not change at all. This is related e.g. to the animations of the GUI. Let that code be.
1. See that `doBreadthFirstSearch()` calls `breadthFirstSearch(start, null);`. That latter method is inherited from `Graph` and implemented there.
1. `Graph` implementation for `breadthFirstSearch(start, null);` is empty and has a TODO comment.
1. You should implement `Graph.breadthFirstSearch(start, null);` to make that button in the GUI to actually work.

Follow the instructions in the code comments, test out how things work, and when you are done, selecte the next button or menu item functionality and implement that use case. 

See below the section on **testing** on how and when to execute the tests. For example, after implementing breadth-first search, you can execute the `SearchGraphTests.testBreadthFirstSearchUndirected` test -- it executes BFS search tests for undirected graphs. There are other BFS tests to execute, so check those out.

## Detailed requirements

You must implement all the missing methods from the `Graph` class. The method names indicate the functionality you should implement, as you know from the course lectures.

* `Vertex<T> createVertexFor(T dataItem)` -- creating vertices to the graph.
* `void addEdge(Edge.EdgeType type, Vertex<T> source, Vertex<T> destination, double weight)` -- adding edge between two vertices.
* `void addDirectedEdge(Vertex<T> source, Vertex<T> destination, double weight)` -- adding a directed edge between two vertices.
* `Vector<Edge<T>> getEdges(Vertex<T> source)` -- getting the edges from a vertex.
* `Vertex<T> getVertexFor(T node)` -- getting a vertex that has the content in T (vertices data item equals parameter T).
* `Vector<Vertex<T>> breadthFirstSearch(Vertex<T> from, Vertex<T> target)` -- do a breadth-first search on the graph, returning the vertices visited.
* `Vector<Vertex<T>> depthFirstSearch(Vertex<T> from, Vertex<T> target)` -- do a depth-first search on the graph, returning the vertices visited.
* `boolean isDisconnected()` -- returns true if the graph is disconnected.
* `boolean hasCycles(boolean isDirected)` -- returns true if the graph has cycles. Parameter can be used to tell to the algorithm if the graph is directed or not. With Maze, graph is always undirected though, but implement the algorithm so that it works also for directed graphs. You do not have to implement this method to find cycles for *disconnected* graphs in this project.
* `Map<Vertex<T>, Visit<T>> shortestPathsFrom(Vertex<T> start)` -- the first step of Dijkstra's algorith. Finds the shortest paths from a starting vertex.
* `Vector<Edge<T>> route(Vertex<T> toDestination, Map<Vertex<T>, Visit<T>> paths) ` -- the second step of Dijkstra's algorithm. Finds a route to a destination using the shortest paths from the starting vertex. The paths are from calling `shortestPathsFrom(Vertex<T> start)`.

Note that you will need to add to `Graph`some additional private implementation methods and/or data structures to implement all of the above. Implement these in the `Graph` class as **private** class properties. 

You MUST NOT change the public or protected interface of the Graph in implementing these.

As you go about implementing these, find the corresponding Unit tests and use them to find out if you have any issues with your implementation.

## Testing with the GUI

After you have implemented the above required methods, you should be able to successfully use all the GUI commands. Test them out. If you have problems **execute the Unit tests** described below to find out the problem.

Change to **game mode** and start playing. Moving the player symbol has already been implemented for you. Use the arrow keys in the keyboard. 

The game uses your implementation of the Dijkstra's shortest path finding algorithm to try to chase and catch the player symbol. The algorithm should be fast enough; the game should be playable so that you really have to try to avoid the game symbol. Depending on the graph. Try loading different graph files and play the game with them. Obviously graphs with cycles are more interesting to play.

If the game is too slow to play, check out if your Dijkstra implementation needs to be improved.

Check also that there are no exceptions thrown in the console. Or that the GUI does not show any error messages. If any of these happen, there are issues in your code. Make sure you have understood what to implement and the implementations are not buggy.

## Unit Testing

The testing area contains several tests you can execute in steps, as you get your implementation tested. Test in steps, start from basic tests and when those work. Then, when you have implemented breadth-first search, you can execute those tests. When that is working, move to depth-first search and execute those tests. You got the point.

Remember to do **regression testing**. When you add new functionality, it may be that you broke something, and executing those tests that passed earlier is also important.

`BasicGraphTests` -- test adding vertices and edges to a simple graph. Checks that the graph does contain those vertices and edges with correct weights. Tests both directed and undirected graphs. Calls the `Graph` methods `createVertexFor()`, `addEdge()` both with `EdgeType.UNDIRECTED` and `EdgeType.DIRECTED` (in separate graphs, all graphs are either directed or undirected but not mixed), `getEdges()` and `getVertexFor()`.

`SearchGraphTests`-- tests the breadth-first search `Graph.breadthFirstSearch()` and depth-first search `Graph.depthFirstSearch()` with both directed and undirected graphs. All of these are connected graphs.

`CyclesTests` -- tests your implementation of `Graph.hasCycles()` with connected undirected, connected directed and unconnected directed test graphs that may or may not have cycles.

`DisconnectednessTests`-- tests your implementation of `Graph.isDisconnected()` with connected undirected, connected directed and unconnected directed test graphs to find out if your algoritm correctly analyses the graph for connectedness.

`DijkstraSearchTests`-- tests your implementation of the Dijkstra's shortest path algorithm. 

Ask for assistance from the teachers in the course exercise sessions or in course Moodle workspace.

## Delivery

Before delivering your final submission, **create** a new markdown file `REPORT.md` in the project root directory (the same this file is in). 

In that file, report:

1. **Your implementation choices**; describe the algorithms you implemented. Did you use some other data structure(s) and/or algorithms not mentioned in the lectures? Why?
1. What can you say about the **correctness** of your implementation? Any issues, bugs or problems you couldn't solve? Any idea why the problem persists and what could perhaps be the solution?
1. What can you say about the **time complexity** of your implementation? How efficient is the code in executing the various graph algoritms?
1. What did you find the **most difficult things** to understand and implement in this course project? Why?
1. What did you learn doing this?

Don't forget to **add the REPORT.md file to git**. 

Remember to use `git status` to see that all changes have been committed to your local repository. If not, do `git add` and `git commit` as necessary. Then deliver your project to your remote git repository using `git push`. Check using your browser to see the remote repository has all the necessary files with correct versions. Teachers are inspecting the version in your remote git repository, not the one on your personal computer. If the remote repository is missing files or has old versions of files, this will lead to problems.

Good luck!