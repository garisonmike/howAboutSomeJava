/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Practice;

import java.util.Scanner;
        
public class Practice {
    public static void main (String[] args){
//        System.out.println("Hello world");
//          byte age = 30;
          
//          if (age < 18){
//              System.out.println("Lucky bastard. Enjoy while it lasts.");
//          }
//          else if (age == 20){
//              System.out.print("Ik you think you can now watch porn and drink alcohol,");
//              System.out.printf(" don't be stupid kiddo, at %d you should be making money!!", age);
//          }
//          else{
//              System.out.println("You ought to have started locking in yesterday!");
//          }
          
          Scanner input = new Scanner(System.in);
          
          System.out.print("Enter your name: ");
          String name = input.nextLine();
          
          System.out.print("How old are you? ");
          if (input.hasNextByte() == false){
              System.out.println("Your input is illegal.");
              return;
          }
          byte age = input.nextByte();
          
          System.out.printf("Hello %s\n", name);
          System.out.printf("So you are %d years old.\n", age);
    }
}