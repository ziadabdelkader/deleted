package eg.edu.alexu.csd.datastructure.stack.cs30;

import java.util.EmptyStackException;

public class myIExpressionEvaluator implements IExpressionEvaluator {

	@Override
	public String infixToPostfix(String expression) {
		String postfix=new String();
		mystack s=new mystack();
		if(!checkBeforeHandle(expression)) {
			System.out.println("Invalid experssion");
			return null;
		}
		expression=handle(expression);
		if(!check(expression)) {
			System.out.println("Invalid experssion");
			return null;
		}
		for(int i=0;i<expression.length();i++) {
			char mychar=expression.charAt(i);
			if(mychar==' ') {/*do noting*/}
			else if(mychar<('0'+10) && mychar>=('0'+0)) {
				postfix=postfix+mychar;
				if(i+1==expression.length())
					postfix=postfix+" ";
				else if( !(expression.charAt(i+1)<('0'+10) && expression.charAt(i+1)>=('0'+0)))
					postfix=postfix+" ";
			}
			else if(mychar=='(')
				s.push(mychar);
			else if(mychar=='+'||mychar=='-') {
				if(s.isEmpty())
					s.push(mychar);
				else {
					char top=(char) s.peek();
					if(top=='(')
						s.push(mychar);
					else if(top=='+'||top=='-') {
						postfix=postfix+s.pop()+" ";
						s.push(mychar);
					}
					else if(top=='*'||top=='/') {
						postfix=postfix+s.pop()+" ";
						int flag=1;
						try{
							top=(char) s.peek();
						}catch(EmptyStackException e) {
							flag=0;
						}
						if(flag==1 && (top=='+'||top=='-')) {
							postfix=postfix+s.pop()+" ";
							s.push(mychar);
						}else
							s.push(mychar);
					}else System.out.println("someting went wrong 1");
				}
			}
			else if(mychar=='*'||mychar=='/') {
				if(s.isEmpty())
					s.push(mychar);
				else {
					char top=(char) s.peek();
					if(top=='(')
						s.push(mychar);
					else if(top=='*'||top=='/') {
						postfix=postfix+s.pop()+" ";
						s.push(mychar);
					}
					else if(top=='+'||top=='-') {
						s.push(mychar);
					}else System.out.println("someting went wrong 2");
				}
			}
			else if(mychar==')') {
				try {
					while((char)s.peek()!='(')
						postfix=postfix+s.pop()+" ";
					s.pop();
				}catch (EmptyStackException e) {
					System.out.println("Invalid experssion");
					return null;
				}
			}else System.out.println("someting went wrong 3");
		}
		while(!s.isEmpty()) {
			if((char)s.peek()!='(')
				postfix=postfix+s.pop()+" ";
			else {
				System.out.println("Invalid experssion");
				return null;
			}
		}
		return postfix;
	}

	@Override
	public int evaluate(String expression) {
		if(expression==null) {
			System.out.println("Invalid experssion");
			return 0;
		}
		mystack s=new mystack();
		for(int i=0;i<expression.length();i++) {
			if(isDiget(expression.charAt(i))) {
				String number=new String();
				number=number+expression.charAt(i);
				i++;
				while(expression.charAt(i)!=' ') {
					number=number+expression.charAt(i);
					i++;
				}
				s.push(number);
			}else if(expression.charAt(i)=='+') {
				String s1=(String) s.pop();
				String s2=(String) s.pop();
				double result=Double.parseDouble(s2)+Double.parseDouble(s1);
				
				s.push(Double.toString(result));
			}else if(expression.charAt(i)=='-') {
				String s1=(String) s.pop();
				String s2=(String) s.pop();
				double result=Double.parseDouble(s2)-Double.parseDouble(s1);
				
				s.push(Double.toString(result));
			}else if(expression.charAt(i)=='*') {
				String s1=(String) s.pop();
				String s2=(String) s.pop();
				double result=Double.parseDouble(s2)*Double.parseDouble(s1);
				
				s.push(Double.toString(result));
			}else if(expression.charAt(i)=='/') {
				String s1=(String) s.pop();
				String s2=(String) s.pop();
				double result=Double.parseDouble(s2)/Double.parseDouble(s1);
				
				s.push(Double.toString(result));
			}
		}
		double end=Double.parseDouble((String) s.pop());
		return (int) Math.round(end);
	}
	private boolean isDiget(char c) {
		if(c<('0'+10) && c>=('0'+0))
			return true;
		return false;
	}
	private String handle(String expression) {
		int k=0;
		//remove white spaces
		while(k<expression.length()) {
			if(expression.charAt(k)==' ')
				expression=expression.substring(0, k)+expression.substring(k+1);
			else
				k++;
		}
		//handle -
		for(int i=0;i<expression.length();i++) {
			if(expression.charAt(i)=='-') {
				if(i==0) {
					if(1<expression.length()&&isDiget(expression.charAt(1))){
						int lastIndex=lastDigetIndex(expression,0);
						expression="(0"+expression.substring(0,lastIndex+1)+")"+expression.substring(lastIndex+1);
					}
				}
				else if((expression.charAt(i-1)=='('||isOperation(expression.charAt(i-1))) && i+1<expression.length() &&isDiget(expression.charAt(i+1))){
					int lastIndex=lastDigetIndex(expression,i+1);
					expression=expression.substring(0,i)+"(0"+expression.substring(i,lastIndex+1)+")"+expression.substring(lastIndex+1);
				}
			}
		}
		//handle *
		for(int i=0;i<expression.length();i++) {
			if(expression.charAt(i)=='(') {
				if(i==0) {}
				else if(expression.charAt(i-1)==')'){
					expression=expression.substring(0, i)+"*"+expression.substring(i);
				}else if(isDiget(expression.charAt(i-1))) {
					expression=expression.substring(0, i)+"*"+expression.substring(i);
				}
			}
		}
		//handle +
		int i=0;
		while(i<expression.length()) {
			if(expression.charAt(i)=='+') {
				if(i==0) {
					if(1<expression.length() && isDiget(expression.charAt(1))) {
						expression=expression.substring(1);
					}else i++;
				}
				else if(i==expression.length()-1){
					i++;
				}else if( (isOperation(expression.charAt(i-1)) || expression.charAt(i+1)=='(' ) &&(isDiget(expression.charAt(i+1)))) {
					expression=expression.substring(0, i)+expression.substring(i+1);
				}else
					i++;
			}else 
				i++;
		}
		return expression;
	}
	private boolean isOperation(char c) {
		if(c=='+'||c=='-'||c=='/'||c=='*')
			return true;
		return false;
	}
	private int lastDigetIndex(String expression,int index) {
		while(index+1<expression.length() && isDiget(expression.charAt(index+1)))
			index++;
		return index;
	}
	private boolean check(String expression) {
		for(int i=0;i<expression.length();i++) {
			char currentChar=expression.charAt(i);
			if(isDiget(currentChar)) {}
			else if(isOperation(currentChar)) {
				if(i==0 || i==expression.length()-1)
					return false;
				else if(isOperation(expression.charAt(i+1)) || expression.charAt(i+1)==')')
					return false;
			}else if(currentChar=='(') {
				if(i==expression.length()-1)
					return false;
				else if(isOperation(expression.charAt(i+1)) || expression.charAt(i+1)==')')
					return false;
			}else if(currentChar==')') {
				if(i==0)
					return false;
				else if(i+1<expression.length() && isDiget(expression.charAt(i+1)))
					return false;
			}else
				return false;
		}
		return true;
	}
	private boolean checkBeforeHandle(String expression) {
		//refuse something like this"23 43"
		for(int i=0;i<expression.length();i++) {
			if(expression.charAt(i)==' ') {
				if(i!=0 && i!=expression.length()-1) {
					if( (isDiget(expression.charAt(i-1)) || expression.charAt(i-1)==' ') && ( isDiget(expression.charAt(i+1)) || expression.charAt(i+1)==' ' ))
						return false;
				}
			}
		}
		return true;
	}
	
}
