import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MorePractice {
    interface Person {
        void greet();
        int getAge();
        String getName();
    }

    interface msg {
        void say();
    }

    static class Mate implements Person {
        private int age;
        private String name;

        Mate(int age, String name) {
            this.age=age;
            this.name=name;
        }

        @Override
        public void greet() {
            System.out.println("Alright mate");
        }

        @Override
        public int getAge() {
            return age;
        }

        @Override
        public String getName() {
            return name;
        }

        public void customMessage(msg m) {
            m.say();
        }
    }

    static class Girl implements Person {
        private int age;
        private String name;

        Girl(int age, String name) {
            this.age=age;
            this.name=name;
        }
        @Override
        public void greet() {
            System.out.println("Hey hot stuff");
        }

        @Override
        public int getAge() {
            return age;
        }

        @Override
        public String getName() {
            return name;
        }

        public void customMessage(msg m) {
            m.say();
        }
    }

    public static void main(String[] args){
        Mate Charlie = new Mate(27, "Charlie");
        Mate Trab = new Mate(30, "Trab");
        Girl Danae = new Girl(29, "Danae");

        //we can use the interface as the type - this way we maintain the functionality of the different implementations in 1 list
        List<Person> peeps = new ArrayList<>();

        peeps.add(Charlie);
        peeps.add(Trab);
        peeps.add(Danae);

        Predicate<Person> ageGreaterThanTwenty = p -> p.getAge() > 27;

        //stream through the people and collect the result into a new list
        // replaced "p -> p.getAge() > 27" with a predicate
        // this can be simplified to a method reference "ageGreaterThanTwenty::test"
        List<Person> newPeeps = peeps.stream()
                .filter(p -> ageGreaterThanTwenty.test(p))
                .collect(Collectors.toList());

        for (Person p : newPeeps) {
            System.out.println(p.getName());
        }

        peeps.stream()
                .forEach(p -> p.greet());

        //alternatively:
        peeps.forEach(Person::greet);


        //define a functional interface
        //use this functional interface as a parameter in the 'customMessage()' method
        //pass lambda function to this interface as below
        //customMessage then calls ".say()" on the lambda'd implementation of the interface
        Trab.customMessage(() -> System.out.println("hey yo"));
        Danae.customMessage(() -> System.out.println("Wowee"));
    }
}
