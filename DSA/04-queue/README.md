# Queue exercise

Tietorakenteet ja algoritmit | Data structures and algorithms 2021.

## The goal

* The goal of this exercise is to implement a Queue data structure.
* The implementation must pass all the tests included in the exercise.
* After your implementation of queue passes the tests, implemen the missing code in `DailyTasks.java` to see an applied usage of the queue.
* When done, deliver your project as instructed in the course.

## Prerequisites

You have all the tools installed and working. This was tested in the `00-init` exercise of the course. If you haven't done that yet, do it now.

## Instructions

You should **implement** the interface `QueueInterface` in your own class. **Create** a new `QueueImplementation.java` file where you do this, inside the `oy.tol.tra` Java package. 

Note that Java packages must be located in the corresponding directory. So `QueueImplementation.java` must be located in this project's `src/main/java/oy/tol/tra/` directory!

Remember to **add** the new java file **to git** version control!:

```console
git add src/main/java/oy/tol/tra/QueueImplementation.java 
```
Otherwise it will be missing from your remote repository and thus also from the teachers when they inspect your work!

Implement the queue so that you use `E` template parameter for the `QueueInterface`:

```Java
public class QueueImplementation<E> implements QueueInterface<E> {
```

Make sure to **read** the `QueueInterface` documentation **carefully** so that your implementation follows the interface documentation. Obviously you need to know how queues work in general, so **check out** the course lectures and other material on queue data structures.

If you have trouble getting started writing the code for the `QueueImplementation.java`, check out the previous Stack exercise and do this one in a similar fashion. Use a plain Java array as the internal data structure. Make sure you allocate a larger array if the is an attempt to add more elements to the array than it currently fits. 

**Make sure** all overridden methods are tagged with `@Override`:

```Java
@Override
   public int capacity() {
```
As mentioned, you should use the Java array as the internal data structure for holding the elements:

```Java
private E [] itemArray;
```

And in the `QueueImplemention.init()`, allocate the array of elements, in addition to other things you need to implement:

```Java
   itemArray = (E []) Array.newInstance(ourClass, maxSize);
```

Make sure also to implement reallocating a bigger array when enqueueing an element and the array is already full!

This is done in a similar way than in the Stack exercise. Now just take a look at the lecture material and take care to notice that values in the array are not necessarily in indexes 0...tail, but e.g. from 14....7!:

```console
values: 6 1 0 9 9 1 8 2 . . .  .  .  .  9  2  6 
index:  0 1 2 3 4 5 6 7 8 9 0 11 12 13 14 15 16
                        ^               ^      
                        tail            head
```

So you cannot just copy the elements from the old table to the new table, leave a gap of empty elements in between, and then some more empty space at the end of the new array. 

So make sure that the new array has the queue elements a) in the same order as before and b) without any empty gaps in between.

After you have implemented your queue class, you can see that it is already **instantiated** for you in `QueueFactory.createIntegerQueue()`. After this, you are ready to test your implementation.

## Testing 

**Run the QueueTests tests** to make sure your queue implementation passes the unit tests. From command line, you can execute the tests (in the directory that contains the exercise `pom.xml` file):

```
mvn test
```

If the tests do not pass, you will see errors. Otherwise you will see that the tests succeed. If you have issues, fix your implementation and try again.

When working with this exercise, **do not**:

* Change the `QueueInterface` class in any way.
* Change the unit tests in any way.

Your queue implementation java file is the only file you need to edit in this exercise.

## Implement the DailyTasks app

The `DailyTasks.java` includes code which uses the queue to present your daily tasks using a Timer. In real life, timer would be much slower, or use actual times (hours:minutes) to schedule the timer, not fixed intervals. But this is just a demonstration of using queues.

**Implement** the missing code following the comments in the methods. After implementing, run the main either from VS Code (you should see `Run|Debug` just above the `main()` function) or from the command line:

```command
mvn package
java -jar target/03-queue-1.0-SNAPSHOT-jar-with-dependencies.jar
```

See how it works. If the tests passed, the app should also work.

## Delivery

Deliver your updated repository as instructed in the course.

## Questions or problems?

Participate in the course lectures, exercises and online support group.

If you have issues building and running the tests, make sure you have the Java 15 installed, environment variables are as they should and Maven is installed.

## About

* Course material for Tietorakenteet ja algoritmit | Data structures and algorithms 2021.
* Study Program for Information Processing Science, University of Oulu.
* Antti Juustila, INTERACT Research Group.