![My analysis](https://gitlab.com/jlampela/tira-2021/-/raw/main/03-permutation/perm.png)

# Exercise: Permutations

Tietorakenteet ja algoritmit | Data structures and algorithms 2021.

## The goals

The goals of this exercise are to:

1. Understand the time complexity of handling large arrays.
1. Implementing a function which randomly permutates contents of an array. 
1. Analysing the time complexity and reporting your results.

## Prerequisites

You have all the tools needed working for the exercise.

## Steps of this exercise

In the class `PermutationArray` there is a function `fillArray()` partially implemented. Your programming goal is to **finish the implementation** of the function so that it results in an array that has the numbers shuffled in a random order.

The pseudocode for the algorithm looks like this:

```
1.     for i = 0 to n - 1 // Initialize the array A so that numbers are in order 1,2,..,n 
2.       A[i] = i + 1
3.     for i = 0 to n-1
4.        x = random number between 0..i
5.        switch values of A[i] and A[x]
```
For getting the random number, you can use `ThreadLocalRandom.nextInt()`. You can have access to `ThreadLocalRandom` by calling `ThreadLocalRandom.current()`.

**Study** the code given to you. In the `main()`:

* information about the execution environment (OS, number of processors, JVM heap size) is printed out,
* the `fillArray()` is called in a loop with increasing array sizes. 

Note that Java arrays use `int` basic data type as the array index type, so arrays cannot be larger than what `int` can address. Java `int` is a 32-bit signed two's complement integer, which has a minimum value of `-2^31` and a maximum value of `2^31-1` (2 147 483 647). Since negative values are useless for addressing array indices, only positive values can be used.

---

After the implementation works, you need to build...:

```command
mvn package
````
...and execute the application. Since the default Java Virtual Machine (JVM) settings give your app limited amount of memory by default, you must do this from the command line:

```command
java -Xms4g -Xmx16g -jar target/03-permutation-1.0-SNAPSHOT-jar-with-dependencies.jar
```
Where:

* `-Xms4g` tells your JVM to allocate a minimum of 4GB of heap memory, and
* `-Xmx16g` tells your JVM to allocate a maximum of 16GB of heap memory.

If your machine cannot handle such large numbers, adjust them down. If you have more RAM, adjust the numbers up. Try what works for you.

Note that in the teacher's M1 Apple Silicon with 16GB of memory, it took about 30 minutes to get to the array size of `1 638 400 000` integers. After that it would have taken about the same time to process the array size of `2 000 000 000` integers which is the final step. If your PC is much slower, you may **halt the process** around `1 638 400 000` integers by pressing `ctrl-c` at the command prompt and report only results gotten so far. See below on what the report should contain.

## Task

Create a report in **PDF format** with the following content:

The **output** from running the application, displaying your machine information and the table of array sizes and execution times in milliseconds, as printed out by the app.

A **spreadsheet table and a line chart** created from the table, showing the execution times graphically. If you do not how to do this, the teacher will show you in the exercise session how to do this. The output of the app is tab separated, so you should be able to easily copy & paste the output from the console to a spreadsheet app (Excel, Numbers, Google Sheets,...) and then selecting the table and inserting a new chart based on the selection.

**Your analysis** of the execution times and the **time complexity** of the algorithm based on course materials and lectures. 

Also **ponder** in the report the following:

* What other things than the limited Java `int` range of values would/could limit the size of arrays?
* If you need to address *more* data elements in Java array than the `int` can support, what other solutions you can think of? Describe at least one.

**Expected length** of the report is 1-2 page A4 with images taking about half of a page maximum.

The implementatin and report together are evaluated and graded on scale 1-5 pts based on your analysis of the results and how you are able to reflect the results against the course materials.

## Delivery

* Add the pdf file (or .md and image files) to git and then commit and push the project to the remote repository.
* Deliver your updated repository as instructed in the course.

## Issues or questions?

Ask in the lectures, exercises or online discussion groups. See Moodle for details.

## About

* Course material for Tietorakenteet ja algoritmit | Data structures and algorithms 2021.
* Study Program for Information Processing Science, University of Oulu.
* Antti Juustila, INTERACT Research Group.
