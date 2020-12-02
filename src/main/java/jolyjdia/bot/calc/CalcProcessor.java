package jolyjdia.bot.calc;

import java.util.Stack;

public final class CalcProcessor {

    private CalcProcessor() {}

    public static void solve(String expression) {
        CalcTokenizer tokenizer = new CalcTokenizer(expression);
        Stack<Token> stack = new Stack<>();
        while (tokenizer.hasNext()) {
            String s = tokenizer.nextToken();
            int len = s.length(); char c;
            if (len == 1 && CalcTokenizer.isDelimiter(c = s.charAt(0))) {
                stack.add(new OperatorToken(c));
            } else {
                stack.add(new NumberToken<>(Integer.parseInt(s)));
            }
        }
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
    public static class OperatorToken implements Token {
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
    }
    public interface Token { }
}