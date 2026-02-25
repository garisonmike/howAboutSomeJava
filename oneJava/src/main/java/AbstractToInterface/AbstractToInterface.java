package AbstractToInterface;

interface Shape {
    double area();
}

class Circle implements Shape {
    private double radius;
    
    public Circle(double r) { radius = r; }
    
    public double area() {
        return 3.14 * radius * radius;
    }
}

class Rectangle implements Shape {
    private double length, width;
    
    public Rectangle(double l, double w) {
        length = l; width = w;
    }
    
    public double area() {
        return length * width;
    }
}

public class AbstractToInterface {
    public static void main(String[] args) {
        Shape circle = new Circle(5.0);
        Shape rectangle = new Rectangle(4.0, 6.0);
        
        System.out.println("Circle Area: " + circle.area());
        System.out.println("Rectangle Area: " + rectangle.area());
    }
}