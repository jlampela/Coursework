# Algorithm correctness

Tietorakenteet ja algoritmit | Data structures and algorithms 2021.

## The goal

* The goal of this exercise task is to analyse algorithm (reverse, sort) correctness.
* When you have analysed and tested that the algorithms are not correct, fix them.

## Prerequisites

You have all the tools installed and working. This was tested in the `00-init` exercise 
of the course. If you haven't done that yet, do it now.

## Instructions

**First** run the tests in the project to see that there are no issues. The algorithms for reversing an array and sorting it seem to work. 

You can run the tests from the command prompt or from VS Code. Running on the command prompt:

```
mvn test
```

Test seem to pass. But this is just an illusion -- the *tests are not adequate* and *fail* to reveal bugs in both `reverse()` and `sort()` methods of the `IntArray`.

**Edit** the test data files in `src/test/resources/arraytoreverse.txt` and `src/test/resources/arraytosort.txt` -- add new numbers, change the order of the numbers and run the tests in between. You should see that the tests actually fail with some inputs.

In changing the data files, make sure to provide valid values, only integers, comma separated, no spaces, on one single line.

### First focus on the reverse method testing

1. Try **different test array sizes** for the test arrays in `arraytoreverse.txt`. Add one integer after a comma. Then test.
1. Let the tests compare if the one returned by `IntArray.reverse()` equals the one reversed by Java library method (which surely is correct).

You should see that the reverse does not always work as expected.

* **Study the code** and try to *reason* what is the issue with the implementation. Draw/write on paper to understand what is happening as you "run" the code in your head, assisted by the pen and paper.
* **Debug** the code with VS Code debugger by setting **breakpoints** and step the lines of code to see what is happening. View variable values.

As you begin to understand what the issue is, **fix it**. Execute the tests again with different data in the test file to see if your fixes actually work or not. Make sure you have many various kinds of arrays and correct expected result arrays to compare to.

### Next focus on testing the sort method

You saw the original sort test pass. Again, **change the order and number of integers in the array** in the `arraytosort.txt` test data file. Make sure you have different kinds of int arrays to test, with differing numbers of ints in different kinds of orders.

You should see that the sort does not always work as expected.

* **Study the code** and try to *reason* what is the issue with the implementation. Draw/write on paper to understand what is happening as you "run" the code in your head, assisted by the pen and paper.
* **Debug** the tests with VS Code debugger by setting **breakpoints** and step the lines of code to see what is happening. View variable values.

As you begin to understand what the issue in the `IntArray.java` is, **fix it**. Execute the test again to see if your fixes actually work or not. Make sure you have many various kinds of arrays and correct expected result arrays to compare to. 

## Delivery

When you have fixed the issues in the `reverse()` and `sort()` methods of IntArray, deliver your submission for evaluation as instructed in the course.

## Questions or problems?

Participate in the course lectures, exercises and online support group.

If you have issues building and running the tests, make sure you have the Java 15 installed, environment variables are as they should and Maven is installed.

## About

* Course material for Tietorakenteet ja algoritmit | Data structures and algorithms 2021.
* Study Program for Information Processing Science, University of Oulu.
* Antti Juustila, INTERACT Research Group.