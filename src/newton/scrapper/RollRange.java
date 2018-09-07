/*
 * Copyright 2018 Nitin.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package newton.scrapper;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Nitin
 */
public class RollRange {

    private java.util.List<AdmitCard> cardList;
    private String prefix;
    private File folder;
    private final static int GAP = 50;
    private static final File parent = new File("ResultScraper\\ac\\");

//</editor-fold>

    /**
     *
     * @param card
     * @return
     */
        public static AdmitCard getAdmitcard(File card) {
        java.io.FileInputStream fin = null;
        try {
            java.io.ObjectInputStream oin = null;
            fin = new java.io.FileInputStream(card);
            oin = new java.io.ObjectInputStream(fin);
            AdmitCard ac = null;
            ac = (AdmitCard) oin.readObject();
            oin.close();
            return ac;
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(" the File " + card);
            Logger.getLogger(RollRange.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                fin.close();
            } catch (IOException ex) {
                Logger.getLogger(RollRange.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    /**
     *
     * @param prefix
     */
    public RollRange(String prefix) {
        this.prefix = prefix;
    }

    /**
     *
     * @return
     */
    public boolean loadRange() {
        folder = new File(parent.getAbsoluteFile(), prefix);

        try {
            this.cardList = Arrays.asList(folder.listFiles()).stream().map(RollRange::getAdmitcard).filter(o -> {
                return o != null;
            }).sorted((a, b) -> {

                try {
                    return a.getRollno().compareTo(b.getRollno());
                } catch (Exception e) {
                    Logger.getLogger(RollRange.class.getName()).log(Level.SEVERE, null, e);
                    return 0;
                }
            }).collect(Collectors.toList());
            System.out.println("Range Loaded ");
            cardList.stream().map(AdmitCard::getRollno).forEach(str -> {
                System.out.print(str + " , ");
            });
        } catch (Exception e) {
            Logger.getLogger(RollRange.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
        return true;
    }

    private void fillRange(int loc) throws IOException {
        int r = Integer.parseInt(cardList.get(loc).getRollno()) + 40;
        System.out.println("Looking up " + r);
        AdmitCard lookUp = AdmitCardProber.lookUp(r + "", "12");
        if (lookUp.equals(NullAdmitCard.ins)) {
            System.out.println("Not found for " + r);
        } else {
            System.out.println(" Downloaded  " + lookUp);
        }
        save(lookUp);
        this.cardList.add(loc + 1, lookUp);
    }

    private void save(AdmitCard card) throws IOException {
        File newCard = new File(folder, "out" + card.getRollno() + ".obj");
        try (OutputStream outStr = Files.newOutputStream(newCard.toPath())) {
            java.io.ObjectOutputStream objOut = new java.io.ObjectOutputStream(outStr);
            objOut.writeObject(card);
            objOut.flush();
        }
    }

    private void testStart() throws IOException {

        int roll = Integer.parseInt(this.cardList.get(0).getRollno());
        System.out.println("Testing Start at " + roll);

        for (int i = roll % 10000 - 50; i > 0; i -= 50) {
            String r = prefix + AdmitCardProber.padNumber(4, i);
            System.out.println("Start :Looking up " + r);

            AdmitCard lookUp = AdmitCardProber.lookUp(r, "12");

            if (lookUp.equals(NullAdmitCard.ins)) {
                System.out.println("Not found for " + r);
            } else {
                System.out.println(" Downloaded  " + lookUp);
            }
            save(lookUp);
            cardList.add(0, lookUp);
        }

    }

    private void testEnd() throws IOException {

        int roll = Integer.parseInt(cardList.get(cardList.size() - 1).getRollno());
        System.out.println("Testing end at " + roll);
        for (int i = roll % 10000 + 50; i < 9999; i += 50) {
            String r = prefix + AdmitCardProber.padNumber(4, i);
            System.out.println("End :Looking up " + r);
            AdmitCard lookUp = AdmitCardProber.lookUp(r, "12");
            if (lookUp.equals(NullAdmitCard.ins)) {
                System.out.println("Not found for " + r);
            } else {
                System.out.println("End:  Downloaded  " + lookUp);
            }
            save(lookUp);
            cardList.add(cardList.size() - 1, lookUp);
        }
    }

    /**
     *
     */
    public void processRange() {
        System.out.println("Prefix for " + prefix);
        try {
            loadRange();
            testStart();
            testEnd();
            for (int i = 0; i < cardList.size() - 1; i++) {

                int r = Integer.parseInt(cardList.get(i).getRollno());
                int r2 = Integer.parseInt(cardList.get(i + 1).getRollno());
                if (r2 - r > GAP) {
                    this.fillRange(i);
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(RollRange.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @param prefix
     * @return
     */
    public static RollRange toRollRange(String prefix) {
        return new RollRange(prefix);
    }

    private static void loadAll() {
//         List<Integer> ch = Arrays.asList(
//                16,17, 18, 19,21, 26, 27, 28, 29, 36, 37, 38, 39, 46, 47, 48, 49, 56, 57, 58, 59,
//                61, 62, 66,
//                67, 68, 69, 76, 77, 78, 79, 91, 92, 93, 94, 95, 96, 97, 98, 99);
//        List<String> strlst = new java.util.ArrayList<>(ch.size()*10);
//        ch.stream().forEach((get) -> {
//            for (int j = 0; j < 10; j++) {
//                strlst.add(get+""+j);
//            }
//        });
//        System.out.println(" "+strlst.toString());
        Arrays.asList(parent.listFiles()).stream().filter(fol -> {
            return fol.list().length != 0;
        }).map(File::getName).map(RollRange::toRollRange).forEach(RollRange::processRange);
//        strlst.stream().parallel().map(RollRange::toRollRange).forEach(RollRange::processRange);
    }

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
//        RollRange rr = new RollRange("173");
//        rr.processRange();
        //loadAll();
        toRollRange("666").processRange();
    }
}
