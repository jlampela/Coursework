package oy.tol.tra;

public class FastPhoneBook implements PhoneBook {

    static final int MAX_TABLE_SIZE = 50000000;
    private Person [] persons = new Person[MAX_TABLE_SIZE];
    private int sizeOf = 0;
    private int collision = 0;
    private int probe = 0;

    /**
     * Adds a person to the phonebook.
     * 
     * @param person The person to add to the phone book.
     * @return True if managed to add the person, otherwise false.
     */
    @Override
    public boolean add(Person person) throws IllegalArgumentException {
        if(person == null){
            throw new IllegalArgumentException();
        }
        int index = person.hashCode();
        int tmp = 0;
        while(index < persons.length-1){
            if(tmp > probe){probe = tmp;}
            if (persons[index] == null){
                persons[index] = person;
                sizeOf++;
                return true;
            }
            index = index + 1 % MAX_TABLE_SIZE;
            collision++;
            tmp++;
        }
        return false;
    }

    @Override
    public int size() {
        return sizeOf;
    }

    /**
     * Finds a person from the phonebook by the person's phone number.
     *
     * @param number The phone number to search from the phone book.
     * @return Return the Person object, or if not found null.
     */
    @Override
    public Person findPersonByPhone(String number) throws IllegalArgumentException {
        if(number == null){
            throw new IllegalArgumentException();
        }
        Person tmp = new Person(number);
        int index = tmp.hashCode();
        while(index < persons.length -1 && persons[index] != null){
            if(persons[index].getPhoneNumber().equals(number)){
                tmp = persons[index];
                return tmp;
            }
            index++;
        }
        return null;
    }

	@Override
	public Person[] getPersons() {
        // Students: You do not need to implemented this here.
        // Just let it return null.
		return null;
	}

    /**
     * Prints out the statistics of the phone book.
     * Prints size, collision and probes
     */
    @Override
    public void printStatus() {
        System.out.println("Fast phone book has " + sizeOf + " persons");
        System.out.println("Fast phone book has " + collision + " collisions when filling the hash table.");
        System.out.println("Fast phone book had to probe " + probe + " times in the worst case.");
    }
    
}
