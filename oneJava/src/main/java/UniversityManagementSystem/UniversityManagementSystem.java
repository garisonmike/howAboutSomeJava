package UniversityManagementSystem;

interface UserActions {
    void login();
    void viewDashboard();
}

class Student implements UserActions {
    public void login() {
        System.out.println("Student logged in");
    }
    
    public void viewDashboard() {
        System.out.println("Viewing courses and grades");
    }
}

class Lecturer implements UserActions {
    public void login() {
        System.out.println("Lecturer logged in");
    }
    
    public void viewDashboard() {
        System.out.println("Viewing class lists and assignments");
    }
}

class Administrator implements UserActions {
    public void login() {
        System.out.println("Admin logged in");
    }
    
    public void viewDashboard() {
        System.out.println("Viewing system management tools");
    }
}

public class UniversityManagementSystem {
    public static void main(String[] args) {
        UserActions[] users = {
            new Student(),
            new Lecturer(),
            new Administrator()
        };

        for (UserActions user : users) {
            user.login();
            user.viewDashboard();
            System.out.println("--------------------");
        }
    }
}