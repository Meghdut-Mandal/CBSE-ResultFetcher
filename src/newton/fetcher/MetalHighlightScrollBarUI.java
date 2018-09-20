/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newton.fetcher;

import javax.swing.*;
import javax.swing.plaf.metal.MetalScrollBarUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 *
 * @author Meghdut Mandal
 */
class MetalHighlightScrollBarUI extends MetalScrollBarUI {

    private final JTextComponent textArea;
    private Color hiliteColor = Color.RED;

    /**
     *
     * @param textArea
     */
    MetalHighlightScrollBarUI(JTextComponent textArea) {
        super();
        this.textArea = textArea;
    }

    /**
     *
     * @param g
     * @param c
     * @param r
     */
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Color color = new Color(200, 200, 100, 200);
        JScrollBar sb = (JScrollBar) c;

        if (!sb.isEnabled()) {
            return;
        } else if (isDragging) {
            color = color.brighter().brighter();
            color = new Color(color.getRed(), color.getBlue(), color.getGreen(), 200);
        } else if (isThumbRollover()) {
            color = color.brighter();
            color = new Color(color.getRed(), color.getBlue(), color.getGreen(), 200);
        } else {
            color = new Color(color.getRed(), color.getBlue(), color.getGreen(), 200);
        }
        g2.setPaint(color);
        g2.fillRect(r.x, r.y, r.width, r.height);
        g2.setPaint(Color.WHITE);
        g2.drawRect(r.x, r.y, r.width, r.height);
        g2.dispose();
    }

    /**
     *
     * @param g
     * @param c
     * @param trackBounds
     */
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
