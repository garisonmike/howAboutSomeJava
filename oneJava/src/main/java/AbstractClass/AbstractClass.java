package AbstractClass;

abstract class Shape {
    abstract double area();
    
    void display() {
        System.out.println("Area: " + area());
    }
}

class Circle extends Shape {
    private double radius;
    
    public Circle(double r) {
        radius = r;
    }
    
    @Override
    double area() {
        return 3.14 * radius * radius;
    }
}

class Rectangle extends Shape {
    private double length, width;
    
    public Rectangle(double l, double w) {
        length = l;
        width = w;
    }
    
    @Override
    double area() {
        return length * width;
    }
}

public class AbstractClass {
    public static void main(String[] args) {
        Shape circle = new Circle(5.0);
        Shape rectangle = new Rectangle(4.0, 6.0);
        
        System.out.print("Circle ");
        circle.display();
        
        System.out.print("Rectangle ");
        rectangle.display();
    }
}