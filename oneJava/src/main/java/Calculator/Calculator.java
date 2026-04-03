/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Calculator;

import java.util.Scanner;
public class Calculator {
    public static void main(String[] args){
        
        double num1;
        double num2; // chose double just incase the number is so big,result will be double too,
        double answer = 0;
        // Input system
        Scanner input = new Scanner(System.in); // convert to try with resources warning
        // Welcome user
        System.out.println("\tHello, Welcome to my Calculator!");
        
        String operations = "\n a. Addition \n b. Subtraction \n c. Multiplication \n d. Division \n";
        // Ask user to select an operation
        System.out.printf("\n Select a choice to the operation you want: %s ", operations);
        System.out.print("Your choice: ");
        String choice = input.nextLine();
        
        // Get numbers from user
        System.out.print("Enter the first number: ");
        num1 = input.nextDouble();
        System.out.print("Enter the second number: ");
        num2 = input.nextDouble();
        
        // doing the math
        switch (choice) { // convert switch to rule switch warning
            case "a" :
                answer = num1 + num2;
                break;
            case "b" :
                answer = num1 - num2;
                break;
            case "c" :
                answer = num1 * num2;
                break;
            case "d" :
                if (num2 == 0) {
                    System.out.println("You can't divide by Zero bruh!");
                    input.close();
                    return;
                }
                answer = num1 / num2;
                break;
            default :
                System.out.println("You didn't select from the choices.");
                input.close();
                return; // didn't use break because return does it's job
        }
        // print answer
        System.out.printf("Your answer is : %.2f \n", answer);
        input.close();
    }
}

// gemini's version
/* 
package Calculator;

import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        
        double num1; 
        double num2; 
        double answer = 0; 
        
        System.out.println("\tHello, Welcome to my Calculator!");
        String operations = "\n a. Addition \n b. Subtraction \n c. Multiplication \n d. Division \n";
        System.out.printf("\n Select a choice to the operation you want: %s ", operations);
        
        // TRY-WITH-RESOURCES: This automatically closes the scanner when the block ends
        try (Scanner input = new Scanner(System.in)) {
            
            System.out.print("Your choice: ");
            String choice = input.nextLine();
            
            System.out.print("Enter the first number: ");
            num1 = input.nextDouble();
            System.out.print("Enter the second number: ");
            num2 = input.nextDouble();
            
            // MODERN RULE SWITCH: Uses "->" instead of ":". No "break;" needed!
            switch (choice) { 
                case "a" -> answer = num1 + num2;
                case "b" -> answer = num1 - num2;
                case "c" -> answer = num1 * num2;
                case "d" -> {
                    if (num2 == 0) {
                        System.out.println("You can't divide by Zero bruh!");
                        return; // Exits the program
                    }
                    answer = num1 / num2;
                }
                default -> {
                    System.out.println("You didn't select from the choices.");
                    return; // Exits before printing the answer
                }
            }
            
            System.out.printf("Your answer is : %.2f \n", answer);
        } // Scanner is automatically and safely closed right here!
    }
}
 */