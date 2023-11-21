import java.util.function.Predicate;

public class Lambda {
    //boring old class to represent a person
    static class Person {
        String name;
        int age;
        int height;

        String getName() { return name; }
        int getAge() { return age; }
        int getHeight() { return height; }

        Person (String n, int a, int h) {
            this.name=n;
            this.age=a;
            this.height=h;
        }

        boolean setName(String s) {
            name = s;
            return true;
        }
    }

    //an interface is like a blueprint of a class
    //methods can be implemented here, or simply defined abstractly
    //all methods must be implemented when another class implements the interface - if they are not implemented here.
    interface LambdaFace {
        //our ONLY abstract method, which the lambda expression will implement
        String replaceWord(String base, String replace, String newchar);

        //static, implemented method that the interface can still use without being instantiated
        // eg LambdaFace.printIt("x");
        static void printIt(String t) {
            System.out.println(t);
        }

        default void printItDef(String t) {
            System.out.println(t);
        }
    }

    interface RossLambda {
        //this is ignored when implementing the interface via a lambda function
        //it is static because it can be called on the interface (RossLambda.aMethodWeDontIplement("123")) rather than
        //on an instance of a class that implements the interface
        static void aMethodWeDontImplement(String x) {
            System.out.println(x);
        }

        //this is the SINGLE abstract method which MUST be implemented any time the interface is used.
        //this is what the lambda function will implement
        int compareStrings(String a, String b);
    }

    interface rli {
        void onlyAbstractMethod();
    }

    public static void main(String[] args) {
        String result;

        //the below is akin to an anonymous implementation of the interface - instead of creating a class which implements
        //the interface, we are simply implementing it anonymously

        //implement the interface, using a lambda method to implement the single abstract method
        // the lambda begins with the parameters that are being implemented (String base...) etc
        // then there is the -> operator
        // then we have the body of the expression - the code that is executed when the expression is called
        LambdaFace lf = (String base, String replace, String newchar) -> {
            return base.replace(replace, newchar);
        };

        //we can call the lambda'd implementation, and we can also still use the default method from the interface
        //we cannot use the static method from the implementation, and instead have to use it by referencing the interface itself
        //rather than the instantiation of it
        result = lf.replaceWord("a longer sentence to replace things in", "to", "XXX");
        lf.printItDef(result);
        LambdaFace.printIt(result);

        //instead of implementing the method as above, we can use this "method reference" format
        //a method reference replaces an implementation of an abstract method in an interface with this simpler syntax
        LambdaFace lf2 = String::replace;
        LambdaFace lf3 = lf2::replaceWord;


        //because the interface already tells us the parameter types (strings in this case) we can omit them in the
        //lambda expression, because Java can infer them
        //   to further simplify we can remove the brackets around the functionality, IF it is only one line.
        //       PLUS we can remove the return statement part.
        RossLambda rl = (String a, String b ) -> {
            return a.compareTo(b);
        };
        //the above becomes:
        RossLambda rl2 = (a,b) -> a.compareTo(b);
        RossLambda rl3 = String::compareTo; //so we are implementing the "compareStrings" method with the more generic compareTo functionality

        System.out.println(rl.compareStrings("Hello", "World"));

        rli rli1 = () -> System.out.println("zero param method");
        rli1.onlyAbstractMethod();

        //LAMBDA EXPRESSIONS ARE OBJECTS, AS ARE ANONYMOUS IMPLEMENTATIONS
        //this means we can pass them around, and re-implement them:
        rli rli2 = rli1;
        rli2.onlyAbstractMethod();



        Person ross = new Person("Ross", 29, 188);
        //use the Predicate interface to pass a condition (or set of conditions) to the printSome method which has general functionality
        printSome(ross, p -> p.setName("Ross2")); //nonsense to show it working 
    }

    public static void printSome(Person p, Predicate<Person> tester) {
        if (tester.test(p)) {
            System.out.println(p.getName());
        }
    }
}
