package DefaultAndParameterizedConstructors;

class Book {
    String title;
    int pages;
    
    // Parameterized constructor
    public Book(String t, int p) {
        title = t;
        pages = p;
    }

    void displayInfo() {
        System.out.println("Book Title: " + title);
        System.out.println("Total Pages: " + pages);
        System.out.println("-------------------------");
    }
}

public class DefaultAndParameterizedConstructors {
    public static void main(String[] args) {
        // Creating objects using the parameterized constructor
        Book book1 = new Book("Java Programming", 500);
        Book book2 = new Book("Cybersecurity Essentials", 350);
        
        book1.displayInfo();
        book2.displayInfo();
    }
}