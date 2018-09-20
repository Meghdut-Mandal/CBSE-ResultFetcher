/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newton.test;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 *
 * @author Meghdut Mandal
 */
public class MoveListner extends MouseMotionAdapter implements java.awt.event.MouseListener {

    private JFrame frame;

    private int xy;
    private int xx;

    /**
     *
     * @param f
     * @param jc
     */
    public static void setUP(JFrame f, javax.swing.JComponent jc) {
        MoveListner moveListner = new MoveListner(f);
        jc.addMouseMotionListener(moveListner);
        jc.addMouseListener(moveListner);
    }

    /**
     *
     * @param frame
     */
    private MoveListner(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void mouseDragged(MouseEvent evt) {
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        frame.setLocation(x - xx, y - xy);
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
        xx = evt.getX();
        xy = evt.getY();
    }

    //<editor-fold defaultstate="collapsed" desc="unused">
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
//</editor-fold>
}
