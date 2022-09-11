package oy.tol.tra;

public interface PhoneBook {
    /**
     * Adds a person to the phonebook.
     * @param person The person to add.
     * @return Returns true if the person was added, false otherwise.
     * @throws IllegalArgumentException if the person object is null.
     */
    boolean add(Person person) throws IllegalArgumentException;
    /**
     * Tells how many persons have been added to the phonebook.
     * @return Returns the number of persons in the phonebook.
     */
    int size();
    /**
     * Finds a person from the phonebook by the person's phone number.
     * @param number The phone number of the person.
     * @return The person object, or null if no person was found with that phone number.
     * @throws IllegalArgumentException if the person object is null.
     */
    Person findPersonByPhone(String number) throws IllegalArgumentException;
    /**
     * Prints status information about the phonebook.
     * In the fast implementation, print out statistics about how your data structure works.
     * Read more from the README.md file.
     */
    void printStatus();
    /**
     * Returns the persons as a plain Java array.
     * NB: You DO NOT need to implement this in the fast phonebook!
     * @return Returns the persons in the phone book as an array.
     */
    Person[] getPersons();
}
