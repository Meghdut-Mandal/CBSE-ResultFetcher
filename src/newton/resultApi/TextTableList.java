package newton.resultApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author MICROSOFT
 */
public class TextTableList {

    private static final String[] BLINE = {"-", "\u2501"};
    private static final String[] CROSSING = {"-+-", "\u2548"};
    private static final String[] VERTICAL_TSEP = {"|", "\u2502"};
    private static final String[] VERTICAL_BSEP = {"|", "\u2503"};
    private static final String TLINE = "\u2500";
    private static final String CORNER_TL = "\u250c";
    private static final String CORNER_TR = "\u2510";
    private static final String CORNER_BL = "\u2517";
    private static final String CORNER_BR = "\u251b";
    private static final String CROSSING_L = "\u2522";
    private static final String CROSSING_R = "\u252a";
    private static final String CROSSING_T = "\u252c";
    private static final String CROSSING_B = "\u253b";

    private String[] descriptions;
    private ArrayList<String[]> table;
    private int[] tableSizes;
    private int rows;
    private int findex;
    private String filter;
    private boolean ucode;
    private Comparator<String[]> comparator;
    private int spacing;
    private EnumAlignment aligns[];

    /**
     *
     * @param descriptions
     */
    private TextTableList(String... descriptions) {
        this(descriptions.length, descriptions);
    }

    /**
     *
     * @param columns
     * @param descriptions
     */
    public TextTableList(int columns, String... descriptions) {
        if (descriptions.length != columns) {
            throw new IllegalArgumentException();
        }
        this.filter = null;
        this.rows = columns;
        this.descriptions = descriptions;
        this.table = new ArrayList<>();
        this.tableSizes = new int[columns];
        this.updateSizes(descriptions);
        this.ucode = false;
        this.spacing = 1;
        this.aligns = new EnumAlignment[columns];
        this.comparator = null;
        for (int i = 0; i < aligns.length; i++) {
            aligns[i] = EnumAlignment.LEFT;
        }
    }

    private void updateSizes(String[] elements) {
        for (int i = 0; i < tableSizes.length; i++) {
            if (elements[i] != null) {
                int j = tableSizes[i];
                j = Math.max(j, elements[i].length());
                tableSizes[i] = j;
            }
        }
    }

    /**
     *
     * @param c
     * @return
     */
    private TextTableList compareWith(Comparator<String[]> c) {
        this.comparator = c;
        return this;
    }

    /**
     *
     * @param column
     * @return
     */
    public TextTableList sortBy(int column) {
        return this.compareWith((o1, o2) -> o1[column].compareTo(o2[column]));
    }

    /**
     *
     * @param column
     * @param align
     * @return
     */
    public TextTableList align(int column, EnumAlignment align) {
        aligns[column] = align;
        return this;
    }

    /**
     *
     * @param spacing
     * @return
     */
    public TextTableList withSpacing(int spacing) {
        this.spacing = spacing;
        return this;
    }

    /**
     * Adds a row to the table with the specified elements.
     */
    public TextTableList addRow(String... elements) {
        if (elements.length != rows) {
            throw new IllegalArgumentException();
        }
        table.add(elements);
        updateSizes(elements);
        return this;
    }

    /**
     *
     * @param par0
     * @param pattern
     * @return
     */
    public TextTableList filterBy(int par0, String pattern) {
        this.findex = par0;
        this.filter = pattern;
        return this;
    }

    /**
     *
     * @param ucodeEnabled
     * @return
     */
    public TextTableList withUnicode(boolean ucodeEnabled) {
        this.ucode = ucodeEnabled;
        return this;
    }

    @Override
    public String toString() {

        java.io.StringWriter out = new java.io.StringWriter();
        StringBuilder line = null;

        if (ucode) {
            for (int i = 0; i < rows; i++) {
                if (line != null) {
                    line.append(CROSSING_T);
                } else {
                    line = new StringBuilder();
                    line.append(CORNER_TL);
                }
                for (int j = 0; j < tableSizes[i] + 2 * spacing; j++) {
                    line.append(TLINE);
                }
            }
            Objects.requireNonNull(line).append(CORNER_TR);
            out.write(line.toString() + String.format("%n"));

            line = null;
        }
        out.flush();
        // toString header
        for (int i = 0; i < rows; i++) {
            if (line != null) {
                line.append(gc(VERTICAL_TSEP));
            } else {
                line = new StringBuilder();
                if (ucode) {
                    line.append(gc(VERTICAL_TSEP));
                }
            }
            StringBuilder part = new StringBuilder(descriptions[i]);
            while (part.length() < tableSizes[i] + spacing) {
                part.append(" ");
            }
            for (int j = 0; j < spacing; j++) {
                line.append(" ");
            }
            line.append(part);
        }
        if (ucode) {
            Objects.requireNonNull(line).append(gc(VERTICAL_TSEP));
        }
        out.write(Objects.requireNonNull(line).toString() + String.format("%n"));
        out.flush();

        // toString vertical seperator
        line = null;
        for (int i = 0; i < rows; i++) {
            if (line != null) {
                line.append(gc(CROSSING));
            } else {
                line = new StringBuilder();
                if (ucode) {
                    line.append(CROSSING_L);
                }
            }
            for (int j = 0; j < tableSizes[i] + 2 * spacing; j++) {
                line.append(gc(BLINE));
            }
        }
        if (ucode) {
            Objects.requireNonNull(line).append(CROSSING_R);
        }
        out.write(Objects.requireNonNull(line).toString() + String.format("%n", ""));
        out.flush();

        line = null;
        ArrayList<String[]> localTable = table;

        if (filter != null) {
            Pattern p = Pattern.compile(filter);
            localTable.removeIf(arr -> {
                String s = arr[findex];
                return !p.matcher(s).matches();
            });
        }

        if (localTable.isEmpty()) {
            String[] sa = new String[rows];
            localTable.add(sa);
        }

        localTable.forEach(arr -> {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == null) {
                    arr[i] = "";
                }
            }
        });

        if (comparator != null) {
            localTable.sort(comparator);
        }

        for (String[] strings : localTable) {
            for (int i = 0; i < rows; i++) {
                if (line != null) {
                    line.append(gc(VERTICAL_BSEP));
                } else {
                    line = new StringBuilder();
                    if (ucode) {
                        line.append(gc(VERTICAL_BSEP));
                    }
                }
                StringBuilder part = new StringBuilder();
                for (int j = 0; j < spacing; j++) {
                    part.append(" ");
                }
                if (strings[i] != null) {
                    switch (aligns[i]) {
                        case LEFT:
                            part.append(strings[i]);
                            break;
                        case RIGHT:
                            for (int j = 0; j < tableSizes[i] - strings[i].length(); j++) {
                                part.append(" ");
                            }
                            part.append(strings[i]);
                            break;
                        case CENTER:
                            for (int j = 0; j < (tableSizes[i] - strings[i].length()) / 2; j++) {
                                part.append(" ");
                            }
                            part.append(strings[i]);
                            break;
                    }
                }
                while (part.length() < tableSizes[i] + spacing) {
                    part.append(" ");
                }
                for (int j = 0; j < spacing; j++) {
                    part.append(" ");
                }
                line.append(part);
            }
            if (ucode) {
                Objects.requireNonNull(line).append(gc(VERTICAL_BSEP));
            }
            out.write(Objects.requireNonNull(line).toString() + String.format("%n", ""));

            line = null;
        }
        out.flush();

        if (ucode) {
            for (int i = 0; i < rows; i++) {
                if (line != null) {
                    line.append(CROSSING_B);
                } else {
                    line = new StringBuilder();
                    line.append(CORNER_BL);
                }
                for (int j = 0; j < tableSizes[i] + 2 * spacing; j++) {
                    line.append(gc(BLINE));
                }
            }
            Objects.requireNonNull(line).append(CORNER_BR);
            out.write(line.toString() + String.format("%n", ""));
        }
        out.flush();
        try {
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(TextTableList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out.getBuffer().toString();

    }

    private String gc(String[] src) {
        return src[ucode ? 1 : 0];
    }

    /**
     *
     */
    public enum EnumAlignment {

        /**
         *
         */
        LEFT, 

        /**
         *
         */
        CENTER, 

        /**
         *
         */
        RIGHT
    }

}
