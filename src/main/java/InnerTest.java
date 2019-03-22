class Outer {
    private int value = 0;

    Outer getInner() {
        return new Outer() {
            void printValue() {
                System.out.println(value++);
            }
        };
    }

    void printValue() {
        System.out.println(value);
    }
}

public class InnerTest {
    public static void main(String[] args) {
        Outer outer0 = new Outer();
        Outer outer1 = outer0.getInner();
        outer1.printValue();
        outer1.printValue();
        outer0.printValue();
        outer0.printValue();
    }
}
