
import java.io.*;

class Student implements Serializable
{
    String name;
    int rollNo;
    transient String password;

    Student(String name, int rollNo, String password)
    {
        this.name = name;
        this.rollNo = rollNo;
        this.password = password;
    }
}

public class SaveLoad
{
    public static void main(String[] args) throws Exception
    {
        Student[] students = {
            new Student("Aadya", 89, "pass123"),
            new Student("Riya", 90, "hello456")
        };

        ObjectOutputStream out = new ObjectOutputStream(
            new FileOutputStream("students.dat"));

        out.writeObject(students);
        out.close();

        ObjectInputStream in = new ObjectInputStream(
            new FileInputStream("students.dat"));

        Student[] loaded = (Student[]) in.readObject();
        in.close();

        System.out.println("After loading:");

        for (Student s : loaded)
        {
            System.out.println("Name: " + s.name);
            System.out.println("Roll No: " + s.rollNo);
            System.out.println("Password: " + s.password);
            System.out.println();
        }
    }
}
