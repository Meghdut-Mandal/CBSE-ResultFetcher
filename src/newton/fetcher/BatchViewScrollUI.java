/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newton.fetcher;

import com.sun.java.swing.plaf.windows.WindowsScrollBarUI;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Meghdut Mandal
 */
class BatchViewScrollUI extends WindowsScrollBarUI {

    private BatchView view;
    private Color hiliteColor = Color.RED;

    /**
     *
     * @param view
     */
    public BatchViewScrollUI(BatchView view) {
        this.view = view;
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
        Color color = this.thumbColor.darker().darker();
        JScrollBar sb = (JScrollBar) c;

        if (!sb.isEnabled()) {
            return;
        } else if (isDragging) {
            color = color.brighter();
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
        if (!view.getPatternText().trim().isEmpty()) {
            JScrollPane pne = view.getResultScrollView();
            double max = pne.getVerticalScrollBar().getMaximum();
            double sy = trackBounds.getHeight() / max;
            g.setColor(hiliteColor);
            int count = (int) view.getResultViewList().stream().filter(r -> r.hasMatch(view.getPatternText())).count();
            store str = new store();
            str.h = 2;
            if (2 * count > trackBounds.height) {
                str.h = 1;
            }
            view.getResultViewList().stream().filter(r -> r.hasMatch(view.getPatternText())).forEach((res) -> {

                double loc = res.getLocation().y - (res.getHeight() / 2);
                loc = loc * sy;
                int y = (int) loc;
                g.fillRect(trackBounds.x, trackBounds.y + y, trackBounds.width, str.h);
            });

        }
    }

    class store {

        int h;
    }

}
