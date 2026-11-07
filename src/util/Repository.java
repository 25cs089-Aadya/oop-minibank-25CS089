package util;

import model.annotation.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class Repository<T>
{
    private HashMap<String, T> items = new HashMap<>();

    public T save(T entity)
    {
        items.put(findId(entity), entity);
        return entity;
    }

    @SafeVarargs
    public final void saveAll(T... entities)
    {
        for (T e : entities)
        {
            save(e);
        }
    }

    public T findById(String id)
    {
        return items.get(id);
    }

    public ArrayList<T> findAll()
    {
        return new ArrayList<>(items.values());
    }

    public boolean delete(String id)
    {
        return items.remove(id) != null;
    }

    public int count()
    {
        return items.size();
    }

    // reads the value of the field marked @Id, looking in parent classes as well
    private String findId(T entity)
    {
        Class<?> c = entity.getClass();

        while (c != null)
        {
            for (Field field : c.getDeclaredFields())
            {
                if (field.isAnnotationPresent(Id.class))
                {
                    try
                    {
                        field.setAccessible(true);
                        return String.valueOf(field.get(entity));
                    }
                    catch (IllegalAccessException e)
                    {
                        throw new RuntimeException("Cannot read id of " + entity);
                    }
                }
            }

            c = c.getSuperclass();
        }

        throw new IllegalArgumentException("No @Id field in " + entity.getClass().getSimpleName());
    }

    public static <N extends Number> double average(N[] values)
    {
        if (values.length == 0)
            return 0;

        double sum = 0;

        for (N v : values)
        {
            sum += v.doubleValue();
        }

        return sum / values.length;
    }
}
