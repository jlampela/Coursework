# Phonebook exercise

Tietorakenteet ja algoritmit | Data structures and algorithms 2021.

## The goal

* The goal of this exercise is to implement a faster phonebook using *either* a hash table *or* binary search tree.
* The implementation must pass all the tests included in the exercise.
* When done, deliver your project as instructed in the course.

You can choose which of the two data structures / algorithms to use in this exercise. 

**Note about grading**

**If** you chose to implement the **BooksAndWords** as the **course project**, this is for **you**: If you use a different data structure here than in the course project BooksAndWords, you will get **extra points** from this, worth **one** course exercise. If you decide to make your life easier (a good and honorable choice that is) and use the same data structure here and the same in the course project, you will not get those extra points.

## Prerequisites

You have all the tools installed and working. This was tested in the `00-init` exercise  of the course. If you haven't done that yet, do it now.

## Analysing slow algorithm performance

The project includes a phone book class `SlowPhoneBook` for storing phone numbers and names of persons. A test file is included, and the tests read that file of 100 000 person data into an array of `Person` objects. 

Run the tests for testing the performance of the `SlowPhoneBook` implementation:

```command
mvn -Dtest=PhoneBookTests#slowPhoneBookTests test
```
Note that other tests at this point will fail, so run only the test indicated above!

Run the test *several times* to see the time it takes to:

* Fill the phonebook from the test data file.
* Do *linear search* to find the test persons (four searches done).
* Sort the array in ascending order by the phone number.
* Do a *binary search* to find the same four test persons as in linear search.

Note the comparisons; how much faster binary search usually is, compared to the linear search.

**Study** the code to see how linear search is done. Compare it to the binary search.

**See** that binary search is done using the Java `Arrays` class. Note also that in order to execute the binary search, the array has to be sorted. **See** how the class makes sure that the array *is* sorted before attempting binary search. Also **note** that sorting a large array takes time, usually more than doing a linear search to a sorted array.

> Note also that in this course exercise and course project BooksAndWords, **you must** implement sorting and searching, including binary search, **yourself**. You **must not** use the sorting and searching implemented in Java or any other external libraries.

What if the array is a) large b) often added to and removed from and c) also often searched from? What does it mean for the performance of the binary search? Questions about this might be asked in the course exams, by the way.

## Instructions for implementing a faster algorithm

You will now implement a faster phone book.

Implement this new class in the `FastPhoneBook.java` file. Implement it either using:

* a hash table.
* or a binary search tree,

following the course lectures and materials. You are allowed to draw inspiration from the course demonstration projects too. Copying code from elsewhere is not allowed.

You need to finish implementation of the `Person` class too. The `hashCode`, `equals` and `compareTo` methods need to be implemented.

Your implementation should meet the following **goals**:

* No crashing, exceptions, index out of bounds errors, lost or duplicate data.
* Searching nor adding should not get stuck in a loop running forever.
* All entries from the test file are added to the phonebook.
* The faster algorithm is really significantly faster than the slow implementation.

For testing, add code to `FastPhoneBook.printStatus()` to print out any interesting data from your implementation. For example, if you implemented this using hash tables, use a counter to calculate how many collisions happened when adding persons to the hash table. 

## Testing 

**Run the PhoneBookTests** to make sure your implementation passes the unit tests. From command line, you can execute the tests (in the directory that contains the exercise `pom.xml` file):

```
mvn test
```

If the tests do not pass, you will see errors. Otherwise you will see that the tests succeed. If you have  issues, fix your implementation and try again.

When working with this exercise, **do not**:

* Change the `PhoneBookTests` class in any way.
* Change the input data file `PhoneBook.txt` in any way.

You only need to change the `Person.java` and `FastPhoneBook.java` files in this exercise.

## Delivery

Deliver the exercise as instructed in the course, after your tests pass and your implementation meets the goals.

## Questions or problems?

Participate in the course lectures, exercises and online support group.

## About

* Course material for Tietorakenteet ja algoritmit | Data structures and algorithms 2021.
* Study Program for Information Processing Science, University of Oulu.
* (c) Antti Juustila, INTERACT Research Group.
