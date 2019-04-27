package eg.edu.alexu.csd.datastructure.stack.cs30;

import java.util.EmptyStackException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class usestack {

	public static void main(String[] args) {
		mystack s=new mystack();
		while(true) {
		Scanner input=new Scanner(System.in);
		System.out.println("\nPlease choose an action:\n1: Push\r\n" + 
				"2: Pop\r\n" + 
				"3: Peek\r\n" + 
				"4: Get size\r\n" + 
				"5: Check if empty");
		int choice;
		try {
			choice=input.nextInt();
		}catch(InputMismatchException e) {
			System.out.println("Please enter a number from 1 to 5");
			continue;
		}
		if(choice<1||choice>5) {
			System.out.println("Please enter a number from 1 to 5");
			continue;
		}
		if(choice==1) {
			Scanner input1=new Scanner(System.in);
			System.out.println("Please enter the object in the next line");
			Object New=input1.next();
			s.push(New);
			System.out.println("the object "+New+" is added");
		}else if(choice ==2) {
			try {
				Object old=s.pop();
				System.out.println("the object "+old+" is removed");
			}catch(EmptyStackException e) {
				System.out.println("Can't pop from an empty stack");
				continue;
			}
		}else if(choice ==3) {
			try {
				Object top=s.peek();
				System.out.println("the object "+top+" is at peek");
			}catch(EmptyStackException e) {
				System.out.println("Can't get peek of an empty stack");
				continue;
			}
		}else if(choice ==4) {
			System.out.println("size is:"+s.size());
		}else {
			if(s.isEmpty())
				System.out.println("the stack is empty.");
			else
				System.out.println("the stack is not empty.");
		}
		
		}
	}
}
