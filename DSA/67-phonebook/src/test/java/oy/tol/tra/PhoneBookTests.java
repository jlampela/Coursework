package oy.tol.tra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;


@DisplayName("Basic tests for the Phone book.")
public class PhoneBookTests {

    static PhoneBook slowPhoneBook = null;
    static PhoneBook fastPhoneBook = null;
    static List<Person> testPersons = new ArrayList<>();
    static Person[] persons = null; 
    static boolean isDirty = false;

    static boolean didRunFastPhoneBookTests = false;
    static long readingToSlowPhoneBook = 0;
    static long readingToFastPhoneBook = 0;
    static long linearSearchTime = 0;
    static long sortTime = 0;
    static long binarySearchTime = 0;
    static long fastPhoneBookSearchTime = 0;

    @BeforeEach
    void readFromFile() {
        try {
            didRunFastPhoneBookTests = false;
            slowPhoneBook = new SlowPhoneBook();
            fastPhoneBook = new FastPhoneBook();
            long start = System.nanoTime();
            readPersonsFromFile(slowPhoneBook);
            isDirty = true;
            readingToSlowPhoneBook = System.nanoTime() - start;
            start = System.nanoTime();
            readPersonsFromFile(fastPhoneBook);
            readingToFastPhoneBook = System.nanoTime() - start;
            testPersons.clear();
            testPersons.add(new Person("0506544014", "Édouard", "Matisse the Great"));
            testPersons.add(new Person("0509702306","Michelangelo Merisi","Botticelli III"));
            testPersons.add(new Person("0407585548","Frida Angelica","Monet"));
            testPersons.add(new Person("0507807683", "Sandro","del Kahlo"));
            testPersons.add(new Person("0503324861", "Vincent","Da Vinci"));
        } catch (IOException e) {
            fail("Could not read test data from PhoneBook.txt");
        }
    }

    @AfterAll 
    static void printStatistics() {
        System.out.println("\n               ========== Statistics ==========\n");
        System.out.format("%35s\t%10d%n", "Phonebook entries:", fastPhoneBook.size());
        System.out.format("%35s\t%10d ns%n", "Filling slow phonebook took:", readingToSlowPhoneBook);
        if (didRunFastPhoneBookTests) {
            System.out.format("%35s\t%10d ns%n", "Filling fast phonebook took:", readingToFastPhoneBook);
        }
        System.out.format("%35s\t%10d ns%n", "Linear search took: ", linearSearchTime);
        System.out.format("%35s\t%10d ns%n", "Sort took: ", sortTime);
        System.out.format("%35s\t%10d ns%n", "Binary search took: ", binarySearchTime);
        if (didRunFastPhoneBookTests) {
            System.out.format("%35s\t%10d ns%n", "Fast phonebook search took: ", fastPhoneBookSearchTime);
        }
        System.out.format("%35s\t%5d %s%n", "Binary search was: ", linearSearchTime / binarySearchTime, " times faster than linear search");
        if (didRunFastPhoneBookTests) {
            System.out.format("%35s\t%5d %s%n", "Fast phonebook search was: ", binarySearchTime / fastPhoneBookSearchTime, " times faster than binary search");
            System.out.format("%35s\t%5d %s%n", "Fast phonebook search was: ", linearSearchTime / fastPhoneBookSearchTime, " times faster than linear search");
        }
        assertTrue(linearSearchTime > 10 * binarySearchTime, () -> "Binary search from sorted array should be much faster than linear search");
        if (didRunFastPhoneBookTests) {
            assertTrue(binarySearchTime > 5 * fastPhoneBookSearchTime, () -> "Fast search from should be much faster than binary search");
        }
        slowPhoneBook.printStatus();
        if (didRunFastPhoneBookTests) {
            fastPhoneBook.printStatus();
        }
    }

    @Test
    @DisplayName("Tests the slow phonebook")
    void slowPhoneBookTests() {
        try {
            System.out.println(">> Testing slow phonebook with " + slowPhoneBook.size() + " entries");
            for (Person testPerson : testPersons) {
                long start = System.nanoTime();
                Person found = slowPhoneBook.findPersonByPhone(testPerson.getPhoneNumber());
                linearSearchTime += System.nanoTime() - start;
                assertNotNull(found, () -> "Should find a person.");
                assertEquals(testPerson.getPhoneNumber(), found.getPhoneNumber(), () -> "Not the same number that was searched.");
                assertEquals(testPerson.getName(), found.getName(), () -> "Not the same person that was searched.");
                System.out.println("Linear search for number " + testPerson.getPhoneNumber() + " and found " + found.getName());
            }
            long start = System.nanoTime();
            persons = slowPhoneBook.getPersons();
            sort();
            sortTime = System.nanoTime() - start;
            for (Person testPerson : testPersons) {
                start = System.nanoTime();
                Person found = findPersonByPhoneBinarySearch(testPerson.getPhoneNumber());
                binarySearchTime += System.nanoTime() - start;
                assertNotNull(found, () -> "Should find a person.");
                assertEquals(testPerson.getPhoneNumber(), found.getPhoneNumber(), () -> "Not the same number that was searched.");
                assertEquals(testPerson.getName(), found.getName(), () -> "Not the same person that was searched.");
                System.out.println("Binary search for number " + testPerson.getPhoneNumber() + " and found " + found.getName());
            }
        } catch (Exception e) {
            fail("Something went wrong in the test." + e.getMessage());
        }
    }

    @Test
    @DisplayName("Tests the fast phonebook")
    void fastPhoneBookTests() {
        try {
            System.out.println(">> Testing fast phonebook with " + fastPhoneBook.size() + " entries");
            for (Person testPerson : testPersons) {
                long start = System.nanoTime();
                Person found = fastPhoneBook.findPersonByPhone(testPerson.getPhoneNumber());
                fastPhoneBookSearchTime += System.nanoTime() - start;
                assertNotNull(found, () -> "Should find that person.");
                assertEquals(testPerson.getPhoneNumber(), found.getPhoneNumber(), () -> "Not the same number that was searched.");
                assertEquals(testPerson.getName(), found.getName(), () -> "Not the same person that was searched.");
                System.out.println("Searched for number " + testPerson.getPhoneNumber() + " and found " + found.getName());
            }
            didRunFastPhoneBookTests = true;
        } catch (Exception e) {
            fail("Something went wrong in the test." + e.getMessage());
        }
    }

    @Test
    void searchPersonNotInPhoneBook() {
        System.out.println("Searching persons not in the phonebooks...");
        assertNull(slowPhoneBook.findPersonByPhone("1234567890"), () -> "Should return null when person is not in the phonebook");
        assertNull(fastPhoneBook.findPersonByPhone("1234567890"), () -> "Should return null when person is not in the phonebook");
        System.out.println("...Finished searching persons not in the phonebooks.");
    }

    private void readPersonsFromFile(PhoneBook toPhoneBook) throws IOException {
        String entries;
        entries = new String(getClass().getClassLoader().getResourceAsStream("PhoneBook.txt").readAllBytes(), StandardCharsets.UTF_8);
        String[] allEntries = entries.split("\\r?\\n");
        for (String entry : allEntries) {
            if (entry.length() > 0) {
                String personElements[] = entry.split(",");
                if (personElements.length == 3) {
                    Person person = new Person(personElements[0], personElements[1], personElements[2]);
                    toPhoneBook.add(person);
                }
            }
        }
    }

    private void sort() {
        Arrays.sort(persons, 0, slowPhoneBook.size(), Comparator.comparing(Person::getPhoneNumber));
        isDirty = false;
     }
  
     public Person findPersonByPhoneBinarySearch(String number) {
        if (null == number) throw new IllegalArgumentException("Phone number cannot be null");
        if (isDirty) {
           sort();
        }
        Person toSearch = new Person(number);
        int index = Arrays.binarySearch(persons, 0, slowPhoneBook.size(), toSearch, Comparator.comparing(Person::getPhoneNumber));
        if (index >= 0) {
           return persons[index];
        }
        return null;
     }
  
}
