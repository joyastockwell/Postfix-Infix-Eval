package evaluator;

import java.util.Stack;

public class InfixEvaluator extends Evaluator {

	@Override
	public int evaluate(String expression) throws ArithmeticException {
		return new PostfixEvaluator().evaluate(this.convertToPostfix(expression));
	}

	public String convertToPostfix(String string) {
		// scan down the input string, placing numbers on a new string
		// operands go to a stack, then, when popped, are put on the new string
		StringBuilder sb = new StringBuilder();
		String[] input = string.split(" ");
		Stack<String> operators = new Stack<>();
		int p = 0;

		// assuming no parentheses involved, when considering operand m:
		// if m is of lower or equal precidence than the ones that are already
		// on the stack,
		// pop the operands off the stack until you reach one of higher
		// precidence than m
		// if m is higher precicdence than the ones already on the stack,
		// simply place m on the stack
		// pop all the operands when the end of the input has been scanned

		// after scanning an open parenthesis
		// place the open parenthesis on the stack
		// all things after the open parenthesis are treated like their own
		// stack
		// when a close parenthesis is scanned, it is not put on the stack
		// instead, all operators before the open parenthesis are popped and put
		// on the new string
		// the open parenthesis is popped but not put on the string

		for (String t : input) {
			if (isOperator(t)) {
				if (t.equals("(")) {
					operators.push(t);
					p++;
				} else if (t.equals(")")) {
					if(popUntil(operators, sb)) throw new ArithmeticException();
					p--;
				} else {
					// compares operator m with those on the stack
					// until an open parenthesis is encountered in the stack or
					// the stack ends
					while (true) {
						if (comparePrecidence(operators, t)) {
							operators.add(t);
							break;
						}
						sb.append(operators.pop() + " ");
					}
				}
			} else {
				sb.append(t + " ");
			}
		}
		popUntil(operators, sb);
		if (p != 0) throw new ArithmeticException();
		//unless substring taken, extra space appears at end
		return sb.toString().substring(0, sb.length() - 1);
	}

	// returns true if t's precidence is greater than that of peeked
	// this is to say, returns true if the operator m is of greater precidence
	// than that of the one from the stack
	// also returns true if the stack is empty
	// otherwise returns false
	// i.e. returns true if m is to be added to the stack
	private static boolean comparePrecidence(Stack<String> stack, String t) {
		if (stack.isEmpty())
			return true;
		return assignPrecidence(stack.peek()) < assignPrecidence(t);
	}

	// assigns Precidence to operators
	private static int assignPrecidence(String str) {
		if (str.equals("(")) {
			return -1;
		}
		if (str.equals("+") || str.equals("-")) {
			return 0;
		}
		if (str.equals("*") || str.equals("/")) {
			return 1;
		}
		return 2;
	}

	//pops until the stack is empty or a ( is encountered
	//returns true if the stack is empty, false otherwise
	private static boolean popUntil(Stack<String> s, StringBuilder sb) {
		while (true) {
			if (s.isEmpty())
				return true;
			if (s.peek().equals("(")) {
				s.pop();
				return false;
			}
			sb.append(s.pop() + " ");
		}
	}
}
