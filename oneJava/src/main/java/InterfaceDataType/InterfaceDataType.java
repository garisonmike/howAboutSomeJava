package InterfaceDataType;

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

public class InterfaceDataType {
    public static void main(String[] args) {
        Payable p = new Employee(5000);
        System.out.println("Pay: " + p.calculatePay());
        
        p = new Contractor(50, 160);
        System.out.println("Pay: " + p.calculatePay());
    }
}