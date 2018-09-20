/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newton.test;
//-*- mode:java; encoding:utf-8 -*-
// vim:set fileencoding=utf-8:
//https://ateraimemo.com/Swing/ScrollBarSearchHighlighter.html

import com.sun.java.swing.plaf.windows.WindowsScrollBarUI;

import javax.swing.*;
import javax.swing.plaf.metal.MetalScrollBarUI;
import javax.swing.text.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 *
 * @author MICROSOFT
 */
class MainPanel extends JPanel {

    /**
     *
     */
    private static final String PATTERN = "MAD";

    /**
     *
     */
    protected static final String INITTXT
            = "Trail: Creating a GUI with JFC/Swing\n"
            + "Lesson: Learning Swing by Example\n"
            + "This lesson explains the concepts you need to\n"
            + " use Swing components in building a user interface.\n"
            + " First we examine the simplest Swing application you can write.\n"
            + " Then we present several progressively complicated examples of creating\n"
            + " user interfaces using components in the javax.swing package.\n"
            + " We cover several Swing components, such as buttons, labels, and text areas.\n"
            + " The handling of events is also discussed,\n"
            + " as are layout management and accessibility.\n"
            + " This lesson ends with a set of questions and exercises\n"
            + " so you can test yourself on what you've learned.\n"
            + "https://docs.oracle.com/javase/tutorial/uiswing/learn/index.html\n";

    /**
     *
     */
    private final transient Highlighter.HighlightPainter highlightPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);

    /**
     *
     */
    private final org.jdesktop.swingx.JXEditorPane textArea = new org.jdesktop.swingx.JXEditorPane();

    /**
     *
     */
    private final JScrollPane scroll = new JScrollPane(textArea);

    /**
     *
     */
    private final JScrollBar scrollbar = new JScrollBar(Adjustable.VERTICAL);


    /**
     *
     */
    private MainPanel() {
        super(new BorderLayout());
        //  textArea.setEditable(false);
        try {
            //  textArea.setText(INITTXT + INITTXT + INITTXT);
            textArea.setPage(new File("merge.html").toURI().toURL());
        } catch (IOException ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (scrollbar.getUI() instanceof WindowsScrollBarUI) {
            scrollbar.setUI(new WindowsHighlightScrollBarUI(textArea));
        } else {
            scrollbar.setUI(new MetalHighlightScrollBarUI(textArea));
        }

        scrollbar.setUnitIncrement(10);
        scroll.setVerticalScrollBar(scrollbar);
        JLabel label = new JLabel(new HighlightIcon(textArea, scrollbar));
        label.setBorder(BorderFactory.createLineBorder(Color.RED));
        // scroll.setRowHeaderView(label);

        /*
         // [JDK-6826074] JScrollPane does not revalidate the component hierarchy after scrolling - Java Bug System
         // https://bugs.openjdk.java.net/browse/JDK-6826074
         // Affected Versions: 6u12, 6u16, 7
         // Fixed Versions: 7 (b134)
         JViewport vp = new JViewport() {
         @Override public void setViewPosition(Point p) {
         super.setViewPosition(p);
         revalidate();
         }
         };
         vp.setView(new JLabel(new HighlightIcon(textArea, scrollbar)));
         scroll.setRowHeader(vp);
         */
        JCheckBox check = new JCheckBox("LineWrap");
//        check.addActionListener(e -> textArea.setLineWrap(((JCheckBox) e.getSource()).isSelected()));

        JButton highlight = new JButton("highlight");
        highlight.addActionListener(e -> setHighlight(textArea, PATTERN));

        JButton clear = new JButton("clear");
        clear.addActionListener(e -> {
            textArea.getHighlighter().removeAllHighlights();
            scroll.repaint();
        });

        Box box = Box.createHorizontalBox();
        box.add(check);
        box.add(Box.createHorizontalGlue());
        box.add(highlight);
        box.add(Box.createHorizontalStrut(2));
        box.add(clear);

        add(box, BorderLayout.SOUTH);
        add(scroll);
        setPreferredSize(new Dimension(320, 240));
    }

    /**
     *
     */
    private static void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        JFrame frame = new JFrame("ScrollBarSearchHighlighter");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(new MainPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * @param args
     */
    public static void main(String... args) {
        EventQueue.invokeLater(MainPanel::createAndShowGUI);
    }

    /**
     *
     * @param jtc
     * @param pattern
     */
    private void setHighlight(JTextComponent jtc, String pattern) {
        Highlighter highlighter = jtc.getHighlighter();
        highlighter.removeAllHighlights();
        Document doc = jtc.getDocument();
        try {
            String text = doc.getText(0, doc.getLength());
            Matcher matcher = Pattern.compile(pattern).matcher(text);
            int pos = 0;
            while (matcher.find(pos)) {
                int start = matcher.start();
                int end = matcher.end();
                highlighter.addHighlight(start, end, highlightPainter);
                pos = end;
            }
        } catch (BadLocationException | PatternSyntaxException ex) {
            ex.printStackTrace();
        }

        repaint();
    }
}

class HighlightIcon implements Icon {

    private static final Color THUMB_COLOR = new Color(0, 0, 255, 50);
    private final Rectangle thumbRect = new Rectangle();
    private final JTextComponent textArea;
    private final JScrollBar scrollbar;

    HighlightIcon(JTextComponent textArea, JScrollBar scrollbar) {
        this.textArea = textArea;
        this.scrollbar = scrollbar;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        //Rectangle rect   = textArea.getBounds();
        //Dimension sbSize = scrollbar.getSize();
        //Insets sbInsets  = scrollbar.getInsets();
        //double sy = (sbSize.height - sbInsets.top - sbInsets.bottom) / rect.getHeight();
        int itop = scrollbar.getInsets().top;
        BoundedRangeModel range = scrollbar.getModel();
        double sy = range.getExtent() / (double) (range.getMaximum() - range.getMinimum());
        AffineTransform at = AffineTransform.getScaleInstance(1d, sy);
        Highlighter highlighter = textArea.getHighlighter();

        //paint Highlight
        Graphics2D g2 = (Graphics2D) g.create();
        g2.translate(x, y);
        g2.setPaint(Color.RED);
        try {
            for (Highlighter.Highlight hh : highlighter.getHighlights()) {
                Rectangle r = textArea.modelToView(hh.getStartOffset());
                Rectangle s = at.createTransformedShape(r).getBounds();
                int h = 2; //Math.max(2, s.height - 2);
                g2.fillRect(0, itop + s.y, getIconWidth(), h);
            }
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }

        //paint Thumb
        if (scrollbar.isVisible()) {
            //JViewport vport = Objects.requireNonNull((JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, textArea));
            //Rectangle thumbRect = vport.getBounds();
            thumbRect.height = range.getExtent();
            thumbRect.y = range.getValue(); //vport.getViewPosition().y;
            g2.setColor(THUMB_COLOR);
            Rectangle s = at.createTransformedShape(thumbRect).getBounds();
            g2.fillRect(0, itop + s.y, getIconWidth(), s.height);
        }
        g2.dispose();
    }

    @Override
    public int getIconWidth() {
        return 4;
    }

    @Override
    public int getIconHeight() {
        //return scrollbar.getHeight();
        JViewport vport = Objects.requireNonNull((JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, textArea));
        return vport.getHeight();
    }
}

class WindowsHighlightScrollBarUI extends WindowsScrollBarUI {

    private final JTextComponent textArea;
    private Color hiliteColor = Color.RED;

    WindowsHighlightScrollBarUI(JTextComponent textArea) {
        super();
        this.textArea = textArea;
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        super.paintTrack(g, c, trackBounds);

        Rectangle rect = textArea.getBounds();
        double sy = trackBounds.getHeight() / rect.getHeight();
        AffineTransform at = AffineTransform.getScaleInstance(1d, sy);
        Highlighter highlighter = textArea.getHighlighter();
        g.setColor(hiliteColor);
        try {
            for (Highlighter.Highlight hh : highlighter.getHighlights()) {
                Rectangle r = textArea.modelToView(hh.getStartOffset());
                Rectangle s = at.createTransformedShape(r).getBounds();
                int h = 2; //Math.max(2, s.height - 2);
                g.fillRect(trackBounds.x, trackBounds.y + s.y, trackBounds.width, h);
            }
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }
}

class MetalHighlightScrollBarUI extends MetalScrollBarUI {

    private final JTextComponent textArea;

    MetalHighlightScrollBarUI(JTextComponent textArea) {
        super();
        this.textArea = textArea;
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        super.paintTrack(g, c, trackBounds);

        Rectangle rect = textArea.getBounds();
        double sy = trackBounds.getHeight() / rect.getHeight();
        AffineTransform at = AffineTransform.getScaleInstance(1d, sy);
        Highlighter highlighter = textArea.getHighlighter();
        g.setColor(Color.YELLOW);
        try {
            for (Highlighter.Highlight hh : highlighter.getHighlights()) {
                Rectangle r = textArea.modelToView(hh.getStartOffset());
                Rectangle s = at.createTransformedShape(r).getBounds();
                int h = 2; //Math.max(2, s.height - 2);
                g.fillRect(trackBounds.x, trackBounds.y + s.y, trackBounds.width, h);
            }
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }
}
