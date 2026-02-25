package Constructor;

/**
 * Assignment: Using Parameterized Constructors
 * @author spiderman
 */

class Student {
    String studentId;
    String name;
    String course;
    
    // Parameterized constructor - initializes the object attributes
    public Student(String id, String n, String c) {
        studentId = id;
        name = n;
        course = c;
    }
    
    void displayDetails() {
        System.out.println("----- Student Info -----");
        System.out.println("ID: " + studentId);
        System.out.println("Name: " + name);
        System.out.println("Course: " + course);
        System.out.println("------------------------");
    }
}

public class Constructors {
    public static void main(String[] args) {
        Student s1 = new Student("S001", "John", "CS");
        Student s2 = new Student("S002", "Jane", "IT");
        
        s1.displayDetails();
        s2.displayDetails();
    }
}