
package JavaPWithMosh;

public class JavaWM {
    public static void main (String[] args) {
//        System.out.println("Hello again");
          String name = "Mike";
          System.out.println("So your name is " + name);
          System.out.printf("So your name is %s\n", name);
          byte age = 20;
          System.out.println("Lemme guess, you're " + age + " years old?");
          System.out.printf("Lemme guess, you're %d years old?\n", age);
          float netWorth = 8_723_423.32F;
          System.out.println("Your networth should be somewhere above $" + netWorth + " right now.");
          System.out.printf("Your networth should be somewhere above $%.2f right now.\n", netWorth);
    }
}