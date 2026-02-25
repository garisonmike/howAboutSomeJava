/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InterfaceLooseCoupling;

/**
 *
 * @author spiderman
 */

// Interface defines contract
interface Notification {
    void send(String message);
}

// Different implementations
class EmailNotification implements Notification {
    public void send(String msg) {
        System.out.println("Email: " + msg);
    }
}

class SMSNotification implements Notification {
    public void send(String msg) {
        System.out.println("SMS: " + msg);
    }
}

// Loose coupling - code depends on interface
class NotificationService {
    private Notification notifier;
    
    public NotificationService(Notification n) {
        this.notifier = n;
    }
    
    public void notify(String msg) {
        notifier.send(msg);
    }
}
    
public class InterfaceLooseCoupling {
    public static void main(String[] args) {
        Notification email = new EmailNotification();
        NotificationService service = new NotificationService(email);
        
        service.notify("Hello, Spiderman!");
    }
        
}
