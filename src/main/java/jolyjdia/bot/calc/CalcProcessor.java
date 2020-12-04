package jolyjdia.bot.calc;

import java.util.*;

public final class CalcProcessor {

    private CalcProcessor() {}

    public static void solve(String expression) {
        CalcTokenizer tokenizer = new CalcTokenizer(expression);
        Queue<Token> stack = new PriorityQueue<>();

        while (tokenizer.hasNext()) {
            String s = tokenizer.nextToken();
            int len = s.length(); char c;
            if (len == 1 && CalcTokenizer.isDelimiter(c = s.charAt(0))) {
                stack.add(new OperatorToken(c));//()+-/*
            } else {
                stack.add(new NumberToken<>(Integer.parseInt(s)));
            }
        }
    }
    static List<String> list = new ArrayList<>();
    static {
        CalcTokenizer calcTokenizer = new CalcTokenizer("(3+1+2+(7+4+(5+0)))+7");

        while (calcTokenizer.hasNext()) {
            list.add(calcTokenizer.nextToken());
        }
    }
    static Node current;
    static Node tail;

    public static void main(String[] args) {
        for (String s : list) {
            int len = s.length(); char c;
            if (len == 1 && CalcTokenizer.isDelimiter(c = s.charAt(0))) {
                if (c == '(') {
                    if (current != null) {
                        Node node = new Node();
                        node.next = current;
                        current = node;
                    } else {
                        current = new Node();
                        tail = current;
                    }
                } else if (c == ')') {
                    Node node = current;
                    while (node != null) {
                        node.end();
                        node = node.next;
                    }
                } else {
                    current.add(new OperatorToken(c));
                }
            } else {
                current.add(new IntToken(Integer.parseInt(s)));
            }
        }
        Node node = current;
        double d = 0, temp;
        while (node != null) {//(5)+(1)+(2)
            temp = node.finRes;
            OperatorToken op = node.operatorNext;
            if (op == null) {
                d = temp;
            } else {
                d = op.solve(d, temp);
            }
            node = node.next;
        }
        System.out.println(d);
    }
    //(77+1+(1+2))*2
    //(1+2)
    //(77+1+pair1)
    public static class Node {
        private Node next;
        //2 + 3 * 4 + 1 = 15
        //1: 2 + 3 = 5 - 3
        //2: 3 * 4 = 12
        //3: 4 + 1 = 5 - 4
        private final List<Token> result = new LinkedList<>();
        private double finRes;
        private OperatorToken operatorNext;

        public void add(Token r) {
            this.result.add(r);
        }
        public void end() {
            double temp = 0, resultF = 0;
            OperatorToken op = null;
            for (Token token : result) {
                if (token instanceof OperatorToken) {
                    op = ((OperatorToken)token);
                } else {
                    int o = ((IntToken)token).c;
                    if (op == null) {
                        temp = o;
                    } else {
                        resultF = op.solve(temp, o);
                        temp = resultF;
                        op = null;
                    }
                }
            }
            operatorNext = op;
            finRes = resultF;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "result=" + finRes +
                    ", next=" + next +
                    '}';
        }
    }
    public static class Pair {
        private IntToken first;
        private OperatorToken op;
        private IntToken second;
    }
    public static class NumberToken<V extends Number> implements Token {
        final V c;

        public NumberToken(V c) {
            this.c = c;
        }

        public V getNumber() {
            return c;
        }

        @Override
        public String toString() {
            return "NumberToken(" + c + ')';
        }
    }
    public static class IntToken implements Token {
        final int c;

        public IntToken(int c) {
            this.c = c;
        }

        public int getNumber() {
            return c;
        }

        @Override
        public String toString() {
            return "IntToken(" + c + ')';
        }
    }
    public static class OperatorToken implements Token, Solver {
        final char c;

        public OperatorToken(char c) {
            this.c = c;
        }

        public char getOperator() {
            return c;
        }

        @Override
        public String toString() {
            return "OperatorToken(" + c + ')';
        }

        @Override
        public double solve(double x, double y) {
            return switch (c) {
                case '+' -> x + y;
                case '-' -> x - y;
                case '/' -> x / y;
                case '*' -> x * y;
                default -> 0;
            };
        }
    }
    public interface Token { }

    public interface Solver {
        double solve(double x, double y);
    }
}
