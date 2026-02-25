package ObjectCreationAndConstructor;

class Student {
    String name;
    String regNo;
    
    public Student(String n, String r) {
        name = n;
        regNo = r;
    }
    
    void display() {
        System.out.println("Student Name: " + name);
        System.out.println("Registration No: " + regNo);
        System.out.println("-------------------------");
    }
}

public class ObjectCreationAndConstructor {
    public static void main(String[] args) {
        Student s1 = new Student("John", "REG001");
        Student s2 = new Student("Jane", "REG002");
        
        s1.display();
        s2.display();
    }
}