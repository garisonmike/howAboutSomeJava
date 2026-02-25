/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CreatingClass;

/**
 * Assignment: Creating a Class and Objects
 * @author spiderman
 */


class Student {
    String studentId;
    String name;
    String course;
    
    void displayDetails() {
        System.out.println("--- Student Details ---");
        System.out.println("ID: " + studentId);
        System.out.println("Name: " + name);
        System.out.println("Course: " + course);
        System.out.println("-----------------------");
    }
}

public class CreatingClass {
    
    public static void main(String[] args) {
        // Create the first student object
        Student s1 = new Student();
        s1.studentId = "S001";
        s1.name = "John Doe";
        s1.course = "Computer Science";
        
        // Create the second student object
        Student s2 = new Student();
        s2.studentId = "S002";
        s2.name = "Jane Smith";
        s2.course = "Engineering";

        s1.displayDetails();
        s2.displayDetails();
    }
}