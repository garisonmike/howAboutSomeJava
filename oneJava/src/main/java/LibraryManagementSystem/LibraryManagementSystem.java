package LibraryManagementSystem;

// Interface
interface Borrowable {
    void borrowItem();
    void returnItem();
}

// Class 1: Book
class Book implements Borrowable {
    private String isbn;
    private String title;
    private boolean isBorrowed;
    
    // Constructor
    public Book(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
        this.isBorrowed = false;
    }
    
    // Overloaded constructor
    public Book(String isbn, String title, boolean borrowed) {
        this.isbn = isbn;
        this.title = title;
        this.isBorrowed = borrowed;
    }
    
    public void borrowItem() {
        if (!isBorrowed) {
            isBorrowed = true;
            System.out.println(title + " borrowed successfully.");
        } else {
            System.out.println(title + " is already borrowed.");
        }
    }
    
    public void returnItem() {
        if (isBorrowed) {
            isBorrowed = false;
            System.out.println(title + " returned successfully.");
        } else {
            System.out.println(title + " was not borrowed.");
        }
    }
}

// Class 2: Member
class Member {
    private String memberId;
    private String name;
    
    public Member(String id, String name) {
        this.memberId = id;
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}

// Class 3: Library
class Library {
    private String libraryName;
    
    public Library(String name) {
        this.libraryName = name;
    }
    
    public void displayInfo() {
        System.out.println("Library: " + libraryName);
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        // Initialize Library and Member
        Library myLibrary = new Library("Nairobi City Library");
        Member member = new Member("M001", "Spiderman");
        
        // Initialize Books using different constructors
        Book book1 = new Book("978-0134685991", "Effective Java");
        Book book2 = new Book("978-0132350884", "Clean Code", true); // Already borrowed

        myLibrary.displayInfo();
        System.out.println("Member: " + member.getName());
        System.out.println("--------------------------------");

        // Test borrowing and returning
        book1.borrowItem();
        book2.borrowItem(); // Should show already borrowed
        book1.returnItem();
        book1.returnItem(); // Should show was not borrowed
    }
}