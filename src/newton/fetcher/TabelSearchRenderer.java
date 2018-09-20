/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newton.fetcher;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Meghdut Mandal
 */
public class TabelSearchRenderer implements TableCellRenderer {

    private static final Color BACKGROUND_SELECTION_COLOR = new Color(220, 240, 255);
    private final transient Highlighter.HighlightPainter highlightPainter
            = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
    private final JTextField field = new JTextField();
    private String pattern = "";
    // private java.util.ArrayList<ResultView> matchList;
    private boolean ignoreCase;
    private String prev;

    /**
     *
     */
    public TabelSearchRenderer() {
        super();
        //   this.matchList = new java.util.ArrayList<>();
        field.setOpaque(true);
        field.setBorder(BorderFactory.createEmptyBorder());
        field.setForeground(Color.BLACK);
        field.setBackground(Color.WHITE);
        field.setEditable(false);
    }

    /**
     *
     * @return
     */
    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    /**
     *
     * @param ignoreCase
     */
    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    /**
     *
     * @param str
     * @return
     */
    public boolean setPattern(String str) {
        if (str == null || str.equals(pattern)) {
            return false;
        } else {
            prev = pattern;
            pattern = str;
            return true;
        }
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        String txt = Objects.toString(value, "");
        Highlighter highlighter = field.getHighlighter();
        highlighter.removeAllHighlights();
        field.setText(txt);
        int start, end;
        field.setBackground(isSelected ? BACKGROUND_SELECTION_COLOR : Color.WHITE);
        if (pattern != null && !pattern.isEmpty() && !pattern.equals(prev)) {
            Matcher matcher = Pattern.compile(pattern).matcher(txt);
            if (matcher.find()) {

                start = matcher.start();
                end = matcher.end();
                try {
                    highlighter.addHighlight(start, end, highlightPainter);
                } catch (BadLocationException e) {
                }
                Objects.requireNonNull(findResultView(table)).setMatch(true);

            } else if (txt.toLowerCase().contains(pattern.toLowerCase())) {

                start = txt.toLowerCase().indexOf(pattern.toLowerCase());
                end = start + pattern.length();

                try {
                    highlighter.addHighlight(start, end, highlightPainter);
                } catch (BadLocationException e) {
                }
                Objects.requireNonNull(findResultView(table)).setMatch(true);

            } else {
                Objects.requireNonNull(findResultView(table)).setMatch(false);

            }

        }
        return field;
    }
//
//    private void remove(JTable table) {
//        ResultView view = this.findResultView(table);
//        if (view != null) {
//            if (this.matchList.contains(view)) {
//                this.matchList.remove(view);
//            }
//        }
//    }
//
//    private void addMatch(JTable table) {
//        ResultView view = this.findResultView(table);
//        if (view != null) {
//            if (!this.matchList.contains(view)) {
//                this.matchList.add(view);
//            }
//
//        }
//    }

//    public ResultView getFirstMatch() {
//        if (this.matchList.isEmpty()) {
//            return null;
//        } else {
//            return this.matchList.get(0);
//        }
//    }
    private static ResultView findResultView(Component jc) {
        Container parent = jc.getParent();
        if (parent instanceof JFrame) {
            return null;
        } else if (parent instanceof ResultView) {
            return (ResultView) parent;
        } else {
            return findResultView(parent);
        }

    }

}
