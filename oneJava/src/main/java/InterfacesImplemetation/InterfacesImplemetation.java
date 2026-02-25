package InterfacesImplemetation;

interface Payable {
    double calculatePay();
}

class Employee implements Payable {
    private double salary;
    
    public Employee(double s) {
        salary = s;
    }
    
    public double calculatePay() {
        return salary;
    }
}

class Contractor implements Payable {
    private double hourlyRate;
    private int hoursWorked;
    
    public Contractor(double rate, int hours) {
        hourlyRate = rate;
        hoursWorked = hours;
    }
    
    public double calculatePay() {
        return hourlyRate * hoursWorked;
    }
}

public class InterfacesImplemetation {
    public static void main(String[] args) {
        Payable emp = new Employee(50000.0);
        Payable cont = new Contractor(50.0, 160);
        
        System.out.println("Employee Pay: " + emp.calculatePay());
        System.out.println("Contractor Pay: " + cont.calculatePay());
    }
}