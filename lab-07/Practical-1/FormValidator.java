
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotBlank {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value();
}

class SignupForm {
    @NotBlank
    String name;

    @NotBlank
    @MaxLength(10)
    String username;

    SignupForm(String name, String username) {
        this.name = name;
        this.username = username;
    }
}

public class FormValidator {

    public static List<String> validate(Object obj) {
        List<String> errors = new ArrayList<>();

        for (Field f : obj.getClass().getDeclaredFields()) {
            try {
                f.setAccessible(true);
                String value = (String) f.get(obj);

                if (f.isAnnotationPresent(NotBlank.class)) {
                    if (value == null || value.trim().isEmpty()) {
                        errors.add(f.getName() + " cannot be blank");
                    }
                }

                if (f.isAnnotationPresent(MaxLength.class)) {
                    int max = f.getAnnotation(MaxLength.class).value();

                    if (value != null && value.length() > max) {
                        errors.add(f.getName() + " exceeds " + max + " characters");
                    }
                }
            }
            catch (Exception e) {
                System.out.println("Error checking field");
            }
        }

        return errors;
    }

    public static void main(String[] args) {
        SignupForm form = new SignupForm("", "averylongusername");

        List<String> errors = validate(form);

        for (String error : errors) {
            System.out.println(error);
        }
    }
}

