# Linked list exercise

Tietorakenteet ja algoritmit | Data structures and algorithms 2021.

## The goal of task 1

* The goal of this exercise task is to implement a Linked list data structure.
* The implementation must pass all the tests included in the `ListTests.java`.
* After your implementation of linked list passes the tests, continue to the task 2.

Note that this file describes the first task of the exercise, and another readme, TASK-2.md describes the second. First follow instructions here, only then go ahead to the [TASK-2.md](TASK-2.md).

The linked list implemented here will be a **singly-linked** list. List only has links to next element, not previous.

## Prerequisites

You have all the tools installed and working. This was tested in the `00-init` exercise  of the course. If you haven't done that yet, do it now.

## Instructions

You should **implement** the interface `LinkedListInterface` in your own class. The `LinkedListImplementation.java` is already there for you. Implement the methods so that the tests in the `ListTests.java` pass. Do not yet try to execute the `ReorderListTests.java` tests, that is the job in the 2nd task.

Make sure to **read** the `LinkedListInterface` documentation **carefully** so that your implementation follows the interface documentation. Obviously you need to know how linked lists work in general, so **check out** the course lectures and other material on linked list data structures.

After you have implemented your linked list methods, you can see that it is already **instantiated** for you in `ListFactory.createStringLinkedList()`. After this, you are ready to test your implementation.

## Testing 

**Run the ListTests tests** to make sure your linked list implementation passes the unit tests. From command line, you can  execute the tests (in the directory that contains the exercise `pom.xml` file):

```
mvn -Dtest=ListTests test
```

If the tests do not pass, you will see errors. Otherwise you will see that the tests succeed. If you have issues, fix your linked list implementation and try again.

Course demonstrations show how to execute the tests from Visual Studio Code. Note that at this point you need only to execute the tests in `ListTests.java`.

When working with this exercise, **do not**:

* Change the `LinkedListInterface` class in any way.
* Change the unit tests in any way.

Your linked list interface implementation java file is the only file you need to edit in this exercise.

## Delivery

Do not deliver this exercise for evaluation until you have completed the second task of this exercise, described in the [TASK-2.md](TASK-2.md).

## Questions or problems?

Participate in the course lectures, exercises and online support group.

## About

* Course material for Tietorakenteet ja algoritmit | Data structures and algorithms 2021.
* Study Program for Information Processing Science, University of Oulu.
* Antti Juustila, INTERACT Research Group.