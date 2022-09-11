# Mode exercise

Tietorakenteet ja algoritmit | Data structures and algorithms 2021.

## The goal

The goal of this exercise is to implement an algorithm to find a *mode* from an array of numbers.

The mode of a sequence of numbers is the number that occurs most often in the sequence. For example, the mode of the sequence `{3,1,2,5,3,3,4,1,4,4,3,5}` is `3`. 

Design and implement an algorithm that finds the mode from an input array. Algorithm shall perform this task in `O(n·lg(n))` time, when the size of the input array is n. 

Tip: sorting may help; you can use (exceptionally, in this exercise) sorting support in Java. In Java, what you want to use with plain arrays is `Arrays.sort()`.

## Prerequisites

You have all the tools installed and working. This was tested in the `00-init` exercise of the course. If you haven't done that yet, do it now.

## Instructions

Implement `Mode.findMode(int[] array)` to find the mode. 

The method must return a `Result` object, containing the value of the mode number, as well as the count of such numbers found in the array.

The tests print out tab separated lines of measurements using different array sizes.

**Copy** the output to a spreadsheet application such as Excel, Google Sheets, OpenOffice spreadsheet app or Numbers on macOS.

**Paste** the output to an empty spreadsheet and insert graphs comparing the different measurements to each other.

**Analyse** the runtime of your mode implementation against the measurements and graphs drawn in the spreadsheet app. Did you meet the goal for the time complexity of the exercise?

## Testing 

**Run the ModeTests tests** to make sure your implementation passes the unit tests. From command line, you can  execute the tests (in the directory that contains the exercise `pom.xml` file):

```
mvn test
```

If the tests do not pass, you will see errors. Otherwise you will see that the tests succeed. If you have issues, fix your implementation and try again.

When working with this exercise, **do not** change the unit tests in any way.

## Delivery

Deliver the finished exercise as instructed in the course. Use git to push the committed code to your private remote repository.

## Questions or problems?

Participate in the course lectures, exercises and online support group.

## About

* Course material for Tietorakenteet ja algoritmit | Data structures and algorithms 2021.
* Study Program for Information Processing Science, University of Oulu.
* Antti Juustila, INTERACT Research Group.