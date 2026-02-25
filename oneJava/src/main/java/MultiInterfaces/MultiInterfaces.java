package MultiInterfaces;

interface Printable {
    void print();
}

interface Scannable {
    void scan();
}

class MultiFunctionPrinter implements Printable, Scannable {
    public void print() {
        System.out.println("Printing document...");
    }
    
    public void scan() {
        System.out.println("Scanning document...");
    }
}

public class MultiInterfaces {
    public static void main(String[] args) {
        MultiFunctionPrinter mfp = new MultiFunctionPrinter();
        
        mfp.print();
        mfp.scan();
    }
}