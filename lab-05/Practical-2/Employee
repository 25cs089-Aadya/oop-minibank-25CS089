abstract class Employee
{
    String name;
    int id;

    Employee(String name, int id)
    {
        this.name = name;
        this.id = id;
    }

    abstract double monthlySalary();
}

class FullTime extends Employee
{
    double fixedSalary;

    FullTime(String name, int id, double fixedSalary)
    {
        super(name, id);
        this.fixedSalary = fixedSalary;
    }

    double monthlySalary()
    {
        return fixedSalary;
    }
}

class PartTime extends Employee
{
    int hours;
    double rate;

    PartTime(String name, int id, int hours, double rate)
    {
        super(name, id);
        this.hours = hours;
        this.rate = rate;
    }

    double monthlySalary()
    {
        return hours * rate;
    }
}

class Intern extends Employee
{
    double stipend;

    Intern(String name, int id, double stipend)
    {
        super(name, id);
        this.stipend = stipend;
    }

    double monthlySalary()
    {
        return stipend;
    }
}

public class Employe
{
    public static void main(String[] args)
    {
        Employee[] employees =
        {
            new FullTime("Rahul", 11, 30000),
            new PartTime("Aman", 10, 10, 300),
            new Intern("Riya", 12, 10000)
        };

        double total = 0;

        for (Employee e : employees)
        {
            double salary = e.monthlySalary();

            System.out.println(e.name + " Salary = " + salary);

            if (e instanceof Intern)
            {
                System.out.println("Note: This employee is an Intern.");
            }

            total += salary;
        }

        System.out.println("Total Salary = " + total);
    }
}