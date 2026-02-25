package MarkerInterface;

interface Auditable {
    // Marker interface - no methods
}

class Transaction implements Auditable {
    private String id;
    private double amount;
    
    public Transaction(String id, double amt) {
        this.id = id;
        this.amount = amt;
    }
}

class AuditChecker {
    public static void checkAudit(Object obj) {
        if (obj instanceof Auditable) {
            System.out.println("Auditing enabled");
        } else {
            System.out.println("Not auditable");
        }
    }
}

public class MarkerInterface {
    public static void main(String[] args) {
        Transaction t1 = new Transaction("TXN100", 2500.50);
        String nonAuditable = "Sample Data";

        System.out.print("Transaction object: ");
        AuditChecker.checkAudit(t1);

        System.out.print("String object: ");
        AuditChecker.checkAudit(nonAuditable);
    }
}