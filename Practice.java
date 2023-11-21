import java.util.List;

public class Practice {
    interface adderFace {
        int addThem(int a, int b);
    }

    interface concatStrings {
        String concatThem(CharSequence ... argmts);
    }

    public static void main(String[] args) {
        int x;
        adderFace af = (a,b) -> a+b;
        x = af.addThem(5, 7);
        System.out.println(x);


        concatStrings cs = (CharSequence ... argmts) -> {
            return String.join(argmts[1], argmts[2]);
        };

        System.out.println(cs.concatThem(" ","hello","my","name","is","ross"));
    }
}
