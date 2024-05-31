package nobinobi.editable.frame;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * classe DocumentFilter per consentire solo numeri interi
 */
class IntegerFilter extends DocumentFilter {
    @Override
    public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
        super.insertString(fb, offset, extractStringBuilder(text).toString(), attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text != null) {
            text = extractStringBuilder(text).toString();
        }
        super.replace(fb, offset, length, text, attrs);
    }

    /**
     * Ritorna il numero dopo i tagli
     * @param text text non filtrato
     * @return il numero in Stringbuilder
     */
    public StringBuilder extractStringBuilder(String text) {
        StringBuilder sb = new StringBuilder(text);
        for (int i = sb.length() - 1; i >= 0; i--) {
            char c = sb.charAt(i);
            if (!Character.isDigit(c)) {
                sb.deleteCharAt(i);
            }
        }
        return sb;
    }
}
