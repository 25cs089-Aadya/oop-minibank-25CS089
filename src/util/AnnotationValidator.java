package util;

import model.annotation.MaxLength;
import model.annotation.Positive;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class AnnotationValidator
{
    public static String[] validate(Object obj)
    {
        ArrayList<String> errors = new ArrayList<>();

        // fields like balance are declared in Account, so check the parent classes too
        Class<?> c = obj.getClass();

        while (c != null)
        {
            for (Field field : c.getDeclaredFields())
            {
                try
                {
                    field.setAccessible(true);
                    Object value = field.get(obj);

                    if (field.isAnnotationPresent(Positive.class))
                    {
                        Positive p = field.getAnnotation(Positive.class);

                        if (value instanceof Number n && n.longValue() <= 0)
                        {
                            errors.add(field.getName() + ": " + p.message());
                        }
                    }

                    if (field.isAnnotationPresent(MaxLength.class))
                    {
                        MaxLength m = field.getAnnotation(MaxLength.class);

                        if (value instanceof String s && s.length() > m.value())
                        {
                            errors.add(field.getName() + ": length must be <= " + m.value());
                        }
                    }
                }
                catch (IllegalAccessException e)
                {
                    errors.add(field.getName() + ": cannot be read");
                }
            }

            c = c.getSuperclass();
        }

        return errors.toArray(new String[0]);
    }
}
