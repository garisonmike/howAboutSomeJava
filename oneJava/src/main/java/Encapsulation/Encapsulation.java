package Encapsulation;

class BankAccount {
    private String accountNumber;
    private double balance;
    
    public BankAccount(String accNum, double bal) {
        accountNumber = accNum;
        balance = bal;
    }
    
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
        }
    }
    
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount);
        } else {
            System.out.println("Insufficient balance");
        }
    }
    
    public double getBalance() {
        return balance;
    }
}

public class Encapsulation {
    public static void main(String[] args) {
        BankAccount myAccount = new BankAccount("123456", 1000.0);
        
        myAccount.deposit(500.0);
        myAccount.withdraw(200.0);
        
        System.out.println("Final Balance: " + myAccount.getBalance());
    }
}