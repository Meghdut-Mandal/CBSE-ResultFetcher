/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newton.test;

import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;

/**
 *
 * @author Meghdut Mandal
 */
public class ButtonRollOver implements java.awt.event.MouseListener, java.awt.event.MouseMotionListener {

    private Color pressed, moveover, normal;
    private javax.swing.JComponent jc;

    /**
     *
     * @param pressed
     * @param moveover
     * @param normal
     * @param jc
     */
    public ButtonRollOver(Color pressed, Color moveover, Color normal, JComponent jc) {
        this.pressed = pressed;
        this.moveover = moveover;
        this.normal = normal;
        this.jc = jc;
        jc.addMouseListener(this);
        jc.addMouseMotionListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        jc.setBackground(Color.BLACK);
        jc.revalidate();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        jc.setBackground(Color.WHITE);
        jc.revalidate();

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        jc.setBackground(Color.WHITE);
        jc.revalidate();

    }

    @Override
    public void mouseExited(MouseEvent e) {
        jc.setBackground(this.normal);
        jc.revalidate();

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        jc.setBackground(Color.WHITE);
        jc.revalidate();

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        jc.setBackground(Color.WHITE);
        jc.revalidate();

    }

}
