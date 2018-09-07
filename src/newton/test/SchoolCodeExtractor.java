/*
 * Copyright 2018 Meghdut  Mandal.
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
package newton.test;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Meghdut Mandal
 */
public class SchoolCodeExtractor {

    private static void process(String s) {

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        File file = new File("cls.txt");
        Scanner sc = new Scanner(file);
        File fo = new File("out.txt");
        StringBuilder Fout = new StringBuilder();
        StringBuilder temp = new StringBuilder(), temp2 = new StringBuilder();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.length() < 40) {
                continue;
            }

            if (line.contains("AFF NO")) {
                System.out.println(line);
                sc.nextLine();
                sc.nextLine();
                System.out.println(sc.nextLine());
            }

        }
    }

}
