
import java.util.ArrayList;

public class StudentService {
    ArrayList<Student> students = new ArrayList<>();

    public void addStudent(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid name");
        }

        students.add(new Student(name));
    }

    public int getCount() {
        return students.size();
    }

    public void removeStudent(int index) {
        if (index < 0 || index >= students.size()) {
            throw new IllegalArgumentException("Invalid index");
        }

        students.remove(index);
    }
}
