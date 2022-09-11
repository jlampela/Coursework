# Binary search exercise

Tietorakenteet ja algoritmit | Data structures and algorithms 2021.

## The goal

* The goal of this exercise is to implement a binary search algorithm.

The code already includes a linear search function -- it is used to compare the search speed to your binary search algorithm.

The tests in this exercise execute both searches and test if the binary search is faster than the linear search.

Note that *sometimes* the linear search *may* be faster. In those cases the test fails. That is not a problem you must fix. You should understand why this may happen from the course lectures and materials.

But if the binary search is slower *often*, then your binary search code has an issue you must fix.

## Prerequisites

You have all the tools installed and working. This was tested in the `00-init` exercise of the course. If you haven't done that yet, do it now.

## Instructions

**Implement** the `binarySearch` method in the `SearchArray` class.

The binary search algorithm **requires** that the array to search is sorted. The **tests do this for you**, so you do *not* have to sort the array in your implementation. But it is important to know this. Calling binary search funtion with an unsorted container will fail.

There are several alternative ways to implement the search. You can either use recursion or loops.

For more information, see the course lectures.

## Testing 

**Run the BinarySearchTests** to make sure your implementation passes the unit tests. From command line, you can 
execute the tests (in the directory that contains the exercise `pom.xml` file):

```
mvn test
```

If the tests do not pass, you will see errors. Otherwise you will see that the tests succeed. If you have 
issues, fix your implementation and try again.

As mentioned above, *sometimes* the linear search *may* be faster. In those cases the test fails. That is not a problem you must fix. You should understand why this may happen from the course lectures and materials. But if your implementation is slower than linear search *often*, then there is a problem you must fix.

When working with this exercise, **do not** change the unit tests in any way.

## Delivery

When done, commit your changes to the `SearchArray` class and push your changes to the remote repository for testing by course teachers.

## Questions or problems?

Participate in the course lectures, exercises and online support group.

## About

* Course material for Tietorakenteet ja algoritmit | Data structures and algorithms 2021.
* Study Program for Information Processing Science, University of Oulu.
* Antti Juustila, INTERACT Research Group.