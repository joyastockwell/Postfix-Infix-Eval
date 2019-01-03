package evaluator;

import java.util.Stack;

public class PostfixEvaluator extends Evaluator {

	@SuppressWarnings("boxing")
	@Override
	public int evaluate(String expression) throws ArithmeticException {
		String[] breakup = expression.split(" ");
		Stack<Integer> operands = new Stack<>();
		for (String element : breakup) {
			//simply stores digets in operands stack
			//if not diget, pops most recent two operands and performs operator's operation on poped digets
			if (isOperator(element)) {
				if (operands.size() < 2) throw new ArithmeticException();
				Integer n2 = operands.pop();
				Integer n1 = operands.pop();
				if (element.equals("*")) {
					operands.push(n1 * n2);
				} else if (element.equals("/")) {
					operands.push(n1 / n2);
				} else if (element.equals("+")) {
					operands.push(n1 + n2);
				} else if (element.equals("^")) {
					operands.push((int) Math.pow(n1, n2));
				} else if (element.equals("-")) {
					operands.push(n1 - n2);
				}
			} else {
				try {
					operands.push(Integer.parseInt(element));
				} catch (NumberFormatException e) {
					throw new ArithmeticException();
				}
				
			}
		}
		if (operands.size() != 1) throw new ArithmeticException();
		return operands.pop();
	}

}
