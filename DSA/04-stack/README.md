# Stack exercise

Tietorakenteet ja algoritmit | Data structures and algorithms 2021.

## The goal of task 1

* The goal of this exercise is to implement a Stack data structure.
* The implementation must pass all the tests included in the exercise.

Note that this file describes the first task of the exercise, and another readme, [TASK-2.md](TASK-2.md) describes the second. First follow instructions here, only then go ahead to the [TASK-2.md](TASK-2.md).

## Prerequisites

You have all the tools installed and working. This was tested in the `00-init` exercise of the course. If you haven't done that yet, do it now.

## Instructions

You should **implement** the interface `StackInterface` in your own class. The `StackImplementation.java` is already created for you and located in this project's `src/main/java/oy/tol/tra/` directory!

Note that the StackImplementation uses `E` template parameter for the `StackInterface`:

```Java
public class StackImplementation<E> implements StackInterface<E> {
```
So the stack is a generic (template) class.

Make sure to **read** the `StackInterface` documentation **carefully** so that your implementation follows the interface documentation. Obviously you need to know how stacks work in general, so **check out** the course lectures and other material on stack data structures.

In this exercise, you use the Java plain array as the internal data structure for holding the elements:

```Java
private E [] itemArray;
```

In the `StackImplemention.init()`, **allocate** the array of elements, in addition to other things you need to implement:

```Java
   itemArray = (E []) Array.newInstance(ourClass, maxSize);
```

Make sure to implement *reallocating more room* in the StackImplementation internal array if array is already full, when push() is called!

After you have implemented your stack class methods, you can see that it is already **instantiated** for you in `StackFactory.createIntegerStack()`. After this, you are ready to test your implementation.

## Testing 

**Run the StackTests tests** to make sure your stack implementation passes the unit tests. From command line, you can execute the tests (in the directory that contains the exercise `pom.xml` file):

```
mvn -Dtest=StackTests test
```

If the tests do not pass, you will see errors. Otherwise you will see that the tests succeed. If you have issues, fix your stack implementation and try again.

Course demonstrations show how to execute the tests from Visual Studio Code. Note that at this point you need only to execute the tests in `StackTests.java`.

When working with this exercise, **do not**:

* Change the `StackInterface` class in any way.
* Change the unit tests in any way.

The stack interface implementation java file is the only file you need to edit in this exercise.

## Delivery

Do not deliver this exercise for evaluation until you have completed the second task of this exercise, described in the [TASK-2.md](TASK-2.md).

## Questions or problems?

Participate in the course lectures, exercises and online support group.

If you have issues building and running the tests, make sure you have the tools working. This was done in the first exercise.

## About

* Course material for Tietorakenteet ja algoritmit | Data structures and algorithms 2021.
* Study Program for Information Processing Science, University of Oulu.
* Antti Juustila, INTERACT Research Group.