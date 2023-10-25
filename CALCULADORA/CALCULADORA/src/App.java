import java.util.Scanner;
import java.util.Stack;

public class App{

    public static int avaliarExpressao(String expressao) {
        String[] tokens = expressao.split(" ");
        Stack<Integer> stack = new Stack<>();

        for (String token : tokens) {
            if (token.matches("[0-9]+")) {
                stack.push(Integer.parseInt(token));
            } else {
                int operand2 = stack.pop();
                int operand1 = stack.pop();
                switch (token) {
                    case "+":
                        stack.push(operand1 + operand2);
                        break;
                    case "-":
                        stack.push(operand1 - operand2);
                        break;
                    case "*":
                        stack.push(operand1 * operand2);
                        break;
                    case "/":
                        stack.push(operand1 / operand2);
                        break;
                }
            }
        }
        return stack.pop();
    }

    public static String infixToPostfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (char c : infix.toCharArray()) {
            if (c == ' ') {
                continue;
            }
            if (Character.isDigit(c)) {
                postfix.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(' ').append(stack.pop());
                }
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop(); // Remover '(' da pilha
                }
            } else {
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                    postfix.append(' ').append(stack.pop());
                }
                stack.push(c);
                postfix.append(' ');
            }
        }

        while (!stack.isEmpty()) {
            postfix.append(' ').append(stack.pop());
        }

        return postfix.toString().trim();
    }

    public static String infixToPrefix(String infix) {
        StringBuilder prefix = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);
            if (c == ' ') {
                continue;
            }
            if (Character.isDigit(c)) {
                prefix.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    prefix.append(' ').append(stack.pop());
                }
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop(); // Remover '(' da pilha
                }
            } else {
                while (!stack.isEmpty() && precedence(c) < precedence(stack.peek())) {
                    prefix.append(' ').append(stack.pop());
                }
                stack.push(c);
                prefix.append(' ');
            }
        }

        while (!stack.isEmpty()) {
            prefix.append(' ').append(stack.pop());
        }

        return prefix.reverse().toString().trim();
    }

    public static int precedence(char c) {
        switch (c) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite a expressão matemática:");
        String expressaoInfixa = scanner.nextLine();

        String expressaoPosfixa = infixToPostfix(expressaoInfixa);
        String expressaoPrefixa = infixToPrefix(expressaoInfixa);
        int resultado = avaliarExpressao(expressaoPosfixa);

        System.out.println("Expressão Infixa: " + expressaoInfixa);
        System.out.println("Expressão Pós-fixa: " + expressaoPosfixa);
        System.out.println("Expressão Pré-fixa: " + expressaoPrefixa);
        System.out.println("Resultado: " + resultado);

        scanner.close();
}
}