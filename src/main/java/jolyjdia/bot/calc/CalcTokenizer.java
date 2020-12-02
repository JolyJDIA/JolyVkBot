package jolyjdia.bot.calc;

import java.util.NoSuchElementException;

public class CalcTokenizer {
    private static final char[] OPERATORS = {'+', '-', '/', '*', '(', ')'};
    private int currentPosition;
    private int newPosition;
    private final int maxPosition;
    private final String str;
    private boolean delimsChanged;
    private int maxDelimCodePoint;

    public CalcTokenizer(String str) {
        currentPosition = 0;
        newPosition = -1;
        delimsChanged = true;
        this.str = str;
        maxPosition = str.length();
        setMaxDelimCodePoint();
    }

    public boolean hasNext() {
        this.newPosition = this.currentPosition;
        return this.newPosition < this.maxPosition;
    }
    private void setMaxDelimCodePoint() {
        int m = 0;
        for (char c : OPERATORS) {
            if (m < c)
                m = c;
        }
        maxDelimCodePoint = m;
    }

    private int scanToken(int startPos) {
        int position = startPos;
        while (position < maxPosition) {
            int c = str.codePointAt(position);
            if ((c <= maxDelimCodePoint) && isDelimiter(str.charAt(position))) {
                if (startPos == position) {
                    position += Character.charCount(c);
                }
                break;
            }
            position += Character.charCount(c);
        }
        return position;
    }


    public String nextToken() {
        /*
         * If next position already computed in hasMoreElements() and
         * delimiters have changed between the computation and this invocation,
         * then use the computed value.
         */
        if (newPosition >= 0 && !delimsChanged) {
            currentPosition = newPosition;
        }

        /* Reset these anyway */
        delimsChanged = false;
        newPosition = -1;

        if (currentPosition >= maxPosition)
            throw new NoSuchElementException();
        int start = currentPosition;
        currentPosition = scanToken(currentPosition);
        return str.substring(start, currentPosition);
    }
    public static boolean isDelimiter(char codePoint) {
        for (char delimiterCodePoint : OPERATORS) {
            if (delimiterCodePoint == codePoint) {
                return true;
            }
        }
        return false;
    }
}
