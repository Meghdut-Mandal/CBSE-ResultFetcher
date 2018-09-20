/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newton.resultApi;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

//  Dav Durgapur Roll 6625902 School Code :=08423 Center Number :-6219
//  HSMS Durgapur roll 6622710 School Cde :=09909 Center Number := 6213
//  DAV PUB SCHool pandebeshwar roll :=6626420  Schcode =56008 Centre no. 6221
//  Tegh Bahadur Dgp Roll no. 6623750 School no- 08484 Centre no-6215
//   Khasi Narayana Roll: 6624583 School no:- 6216  center code  56074
//  QUEENS' COLLEGE KHANDWA ROAD INDORE M P Roll  1717311  school no 03298 centre no 1735

/**
 *
 * @author MICROSOFT
 */
class RobotClient {

    private static Robot robot;

    private static void type(String text) throws InterruptedException {

        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);
        //  clipboard.setContents(stringSelection, stringSelection);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }

    /**
     *
     * @param args
     * @throws AWTException
     * @throws InterruptedException
     */
    @SuppressWarnings("SleepWhileInLoop")
    public static void main(String[] args) throws AWTException, InterruptedException {
        robot = new Robot();
        Object[] options = {"Yes, please"};

        int arr[] = {646, 721, 722, 750, 906, 921, 951};

        for (int i = 5; i < arr.length; i++) {

            String roll = "6625" + arr[i], schode = "08423", centrcode = "6219";

            Thread.sleep(2000);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            type(roll);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            Thread.sleep(100);

            type(schode);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);

            Thread.sleep(100);
            type(centrcode);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);

            JOptionPane.showOptionDialog(null,
                    "has the page loaded ? ",
                    "A  Question",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_S);
            robot.keyRelease(KeyEvent.VK_S);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            Thread.sleep(1000);
            type(roll);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            Thread.sleep(1000);
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_LEFT);
            robot.keyRelease(KeyEvent.VK_LEFT);
            robot.keyRelease(KeyEvent.VK_ALT);
//            n = JOptionPane.showOptionDialog(null,
//                    "has the  back page loaded ? ",
//                    "A  Question",
//                    JOptionPane.YES_NO_CANCEL_OPTION,
//                    JOptionPane.QUESTION_MESSAGE,
//                    null,
//                    options,
//                    options[0]);
        }

    }
}
