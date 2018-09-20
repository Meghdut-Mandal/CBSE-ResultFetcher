/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newton.test;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author MICROSOFT
 */
class CardLayoutTest {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("CardLayout Test");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = frame.getContentPane();
        // Add a Next JButton in a JPanel to the content pane
        JPanel buttonPanel = new JPanel();
        JButton nextButton = new JButton("Next");
        buttonPanel.add(nextButton);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        // Create a JPanel and set its layout to CardLayout
        final JPanel cardPanel = new JPanel();
        final CardLayout cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        // Add five JButtons as cards to the cardPanel
        for (int i = 1; i <= 5; i++) {
            JButton card = new JButton("Card " + i);
            card.setPreferredSize(new Dimension(200, 200));
            String cardName = "card" + 1;
            cardPanel.add(card, cardName);
        }
        // Add the cardPanel to the content pane
        contentPane.add(cardPanel, BorderLayout.CENTER);
        // Add an action listener to the Next button
        nextButton.addActionListener(e -> cardLayout.next(cardPanel));
        frame.pack();
        frame.setVisible(true);
    }
}
