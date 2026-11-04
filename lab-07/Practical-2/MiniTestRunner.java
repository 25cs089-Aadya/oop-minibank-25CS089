
import java.lang.annotation.*;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Run {
}

class MyTests {

    @Run
    public void testOne() {
        System.out.println("Test 1 running");
    }

    @Run
    public void testTwo() {
        System.out.println("Test 2 running");
    }

    public void normalMethod() {
        System.out.println("Normal method");
    }
}

public class MiniTestRunner {
    public static void main(String[] args) throws Exception {

        MyTests obj = new MyTests();
        int count = 0;

        for (Method m : MyTests.class.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Run.class) &&
                m.getParameterCount() == 0) {

                m.invoke(obj);
                count++;
            }
        }

        System.out.println("Tests run: " + count);
    }
}
