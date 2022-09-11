# Linked list exercise part 2

Tietorakenteet ja algoritmit | Data structures and algorithms 2021.

## The goal

The goal of this exercise is to test the linked list data structure you created to perform an actual useful task. You will use the linked list to check to reverse an alphabetically sorted list of strings in a reverse order. A functionality often needed in very many applications. Consider, for example, various user interfaces displaing lists of data items, where user is able to sort the item lists to ascending or descending order -- basically reversing the order of items in the list.

The second goal is to compare the execution times of your linked list implementation with the Java `ArrayList` and `LinkedList` implementations. You should ask yourself, when comparing execution times, **why the differences**? 

**IMPORTANT:** note that the in the **course exam** there may be questions related to these performance differences and this exercise! So really put an effort in conducting and understanding this exercise!

## Prerequisites

You have completed the first task of this exercise and your `LinkedListImplementation` works as tested by the `ListTests`.

## Instructions

In this exercise you will use the `LinkedListImplementation` which handles String data. The test reads a long list of grapes from a data file. The grapes are in alphabetical order. Your task is to **implement** the `LinkedListImplementation.reverse()` method so that the order of the items in the linked list is reversed in place.

"In place" means that you should not create a new list where your put the elements of the old list in reverse order, but instead travese the list and change the direction of the links.

For example, if order was: `A -> B -> C -> D`, it is after reversing, `A <- B <- C <- D`. As you then finally set D as the head of the list, effectively then the list becomes `D -> C -> B -> A`.

**Open** the test `ReorderListTests.java`. You will see that it contains unit tests to read test data and check then if the reverse works in your linked list implementation. 

> Note: it is also possible to implement the list reverse using a Stack. However, this solution consumes more memory since elements from list are removed from the list, pushed to stack and then back to the now empty list in reverse order. So do not do this in this exercise.

In order to explain the differences between Java `ArrayList`, `LinkedList` and your implementation, **take a look** the following source code files:

* Java ArrayList: https://github.com/openjdk/jdk15u-dev/blob/master/src/java.base/share/classes/java/util/ArrayList.java
* Java LinkedList: https://github.com/openjdk/jdk15u-dev/blob/master/src/java.base/share/classes/java/util/LinkedList.java
* Java Collections: https://github.com/openjdk/jdk15u-dev/blob/master/src/java.base/share/classes/java/util/Collections.java

Java `Collections.reverse()` is used in the test to reverse the Java arrays/lists.

**Comparing** how Java implements `add()`, `get(int)` and `indexOf(E)`, for example, to your implementation, explains the speed differences between your and Java code.

## Testing 

**Run the ReorderListTests tests** to make sure your linked list implementation passes the unit tests. From command line, you can  execute the tests (in the directory that contains the exercise `pom.xml` file):

```
mvn -Dtest=ReorderListTests test
```

If the tests do not pass, you will see errors. Otherwise you will see that the tests succeed. 

Note that at this point you can execute *all* the tests:

```
mvn test
```

Especially if you have changed and fixed your `LinkedListImplementation.java`, it is necessary to do *regression testing* (running the already executed tests again).

When working with this exercise, **do not**:

* change the `LinkedListInterface` interface in any way,
* change the unit tests in any way,
* change the test data file  `Grapes.txt` in any way.

## Delivery

After all your tests pass, you should deliver your submission to the exercise as instructed in the course.

## Questions or problems?

Participate in the course lectures, exercises and online support group.

If you have issues building and running the tests, make sure you have the Java 15 installed, environment variables are as they should and Maven is installed.

## About

* Course material for Tietorakenteet ja algoritmit | Data structures and algorithms 2021.
* Study Program for Information Processing Science, University of Oulu.
* Antti Juustila, INTERACT Research Group.