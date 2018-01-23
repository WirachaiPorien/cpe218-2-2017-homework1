//import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;
//import java.util.*;
//import java.io.*;
import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
import javax.imageio.metadata.IIOMetadataNode;

public class Homework1 {
	private static Scanner scanner;
	static Stack restack = new Stack();
	static Stack stack = new Stack();
	static  Node root;
	//public static Node current=root;
	
	
	


	public static boolean isOperator(char c){
		  if(c == '+' || c == '-' || c == '*' || c =='/')
		   return true;
		  return false;
		 }
	
//	public static String infix(String postfix){
//		  Stack<String> s = new Stack<>();
//		   
//		  for(int i = 0; i < postfix.length(); i++){
//		   char c = postfix.charAt(i);
//		   if(isOperator(c)){
//		    String b = s.pop();
//		    String a = s.pop();
//		    s.push("("+a+c+b+")");
//		   }
//		   else
//		    s.push(""+c);
//		  }
//		  return s.pop();
//		 }

	public static void infix(Node n) {
		if(n!=null) {
			char key = n.getNodeName().charAt(0);
			if(isOperator(key)&&n!=root){
				System.out.print("(");
			}
			infix(n.getChildNodes().item(1));
			System.out.print(n.getNodeName());
			infix(n.getChildNodes().item(0));
			if(isOperator(key)&&n!=root){
				System.out.print(")");
			}		}
		
	}
	
	
	public static void inorder(Node n) {
		char key = n.getNodeName().charAt(0);
		if(!isOperator(key)){
			n.setNodeValue(n.getNodeName());
			return;
		}
		String secondData = stack.pop().toString();
		Node secondNum = new IIOMetadataNode(secondData.toString());
		inorder(secondNum);//re
		String firstData = stack.pop().toString();
		Node firstNum = new IIOMetadataNode(firstData.toString());
		inorder(firstNum);//re
		n.appendChild(secondNum);
		n.appendChild(firstNum);
		calculate(n);		
   }

	
	public static void calculate (Node n) {
		String key = n.getNodeName(); 
		String seNum = n.getChildNodes().item(0).getNodeValue();
		String fiNum = n.getChildNodes().item(1).getNodeValue(); 
		String result;
		int num1 = Integer.valueOf(seNum);
		int num2 = Integer.valueOf(fiNum);
		switch (key) {
    	case ("+"):	result = Integer.toString(num2+num1);
    				n.setNodeValue(result);
    				break;
		case ("-"):	result = Integer.toString(num2-num1);
					n.setNodeValue(result);
					break;
    	case ("*"):	result = Integer.toString(num2*num1);
					n.setNodeValue(result);
					break;
    	case ("/"):	result = Integer.toString(num2/num1);
					n.setNodeValue(result);
					break;
    	}	
		
		
	}
	
	public static void main(String[] args) {
		String data = "251-*32*+";
		if(args.length>0) {
			data = args[0];
		}else System.exit(1);
//		scanner = new Scanner(System.in);
//		String data = scanner.nextLine();
		char[] newData = data.toCharArray();
		for(int i=0 ; i<data.length() ; i++) {
			String x = Character.toString(newData[i]);
			stack.push(x);
			}
		//System.out.println(stack);
		//System.out.print(infix(data) +"=");
		root = new IIOMetadataNode(stack.pop().toString());
		inorder(root);
		infix(root);
		System.out.println("="+root.getNodeValue());
		TreeDemo.main(root);
		
	}
	
}

		




