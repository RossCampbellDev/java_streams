import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class LambdaAndGenericInterfaces {
    //boring old class to represent a person
    static class Person {
        private String name;
        private int age;
        private int height;

        //getters and setters used for access to private variables
        String getName() { return name; }
        int getAge() { return age; }
        int getHeight() { return height; }

        Person (String n, int a, int h) {
            this.name=n;
            this.age=a;
            this.height=h;
        }
    }

    //this is a GENERIC interface, one which java supplies
    //however it is modified to work with Person type, instead of 'T' (Predicate<T>)
    //it is not actually required, its just more legible

    /*interface Predicate<Person> {
        boolean test(Person t);
    }*/

    public static void main(String[] args) {
        //create some instances of people
        Person ross = new Person("Ross", 29, 188);
        Person alan = new Person("Alan", 15, 157);
        Person dave = new Person("David", 12, 170);
        Person ben = new Person("Ben", 28, 175);

        //add the peeps to a list which we will then apply our Predicate to
        List<Person> roster = new LinkedList<Person>();
        roster.add(ross);
        roster.add(alan);
        roster.add(dave);
        roster.add(ben);

        //we can now use a lambda function when call this method
        //it means we can define the 'test' we want to apply, as and when we want to
        //and the functionality of the method at the other end (in this case printing Persons who meet the criteria) will remain
        //... the lambda function is passed as a parameter of type Predicate<Person>.  this predicate is then applied via the 'test' method
        printPersonsIn(roster,
                    (Person p) -> p.getAge() >= 20 && p.getAge() <= 29  //this is the part we can change to whatever we want.  this is the implementation of the Predicate interface
                );

        printPersonsIn(roster,
                    p -> p.getName().contains("a") //the type of p is actually inferred so we don't need to give it
                );

        //defining predicates in advance lets us combine them
        //we can use predicate.or, or predicate.and to perform logical operations with multiple predicates
        Predicate<Person> nameLongerThanFour = p -> p.getName().length() > 4;
        Predicate<Person> ageGreaterThanTwenty = p -> p.getAge() > 20;

        //we can pass this logical operation as a predicate of it's own to our printPersonsIn method
        //since the below returns a predicate made from the OR of two predicates
        printPersonsIn(roster,
                    nameLongerThanFour.or(ageGreaterThanTwenty)
                );

        //we can use predicate.negate to run a logical NOT on a predicate
    }

    //a method which tests a Predicate on a collection (list) of Persons
    //then returns a list of Persons who meet the criteria
    public static List<Person> printPersonsIn(List<Person> roster, Predicate<Person> tester) {
        List<Person> res = new LinkedList<Person>();

        for (Person p : roster) {
            //when we call tester.test, we are passing a Person object to the Lambda functions above which expect one as the parameter, in effect
            if (tester.test(p)) {
                res.add(p);
                System.out.println(p.getName() + " " + p.getAge());
            }
        }

        return res;
    }
}
