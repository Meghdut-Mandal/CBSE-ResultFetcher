/*
 * Copyright 2018 newton.
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
import java.nio.file.Files;
import static java.nio.file.StandardOpenOption.CREATE_NEW;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author newton
 */
public class GrabberState implements java.io.Serializable {

    long serialVersionUID = 5453435;
    private java.util.Hashtable<String, String> hashtable;
    private String last;
    private File dataFile;

    /**
     *
     */
    public GrabberState() {

        hashtable = new java.util.Hashtable<>();
        last = "NOL:AST";
        dataFile = new File("data.props");

    }

    /**
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void load() throws IOException, ClassNotFoundException {
        if (!dataFile.exists()) {
            System.out.println("No previousdata found ");
            return;
        }
        try (java.io.ObjectInputStream oi = new java.io.ObjectInputStream(Files.newInputStream(dataFile.toPath()))) {
            oi.defaultReadObject();
            System.out.println("After loading " + this.hashtable.toString());
        }

    }
    int count = 0;

    /**
     *
     * @return
     */
    public synchronized Enumeration<String> keys() {
        return hashtable.keys();
    }

    /**
     *
     * @param value
     * @return
     */
    public synchronized boolean contains(Object value) {
        return hashtable.contains(value);
    }

    /**
     *
     * @param key
     * @return
     */
    public synchronized boolean containsKey(Object key) {
        return hashtable.containsKey(key);
    }

    /**
     *
     * @param key
     * @return
     */
    public synchronized String get(Object key) {
        return hashtable.get(key);
    }

    /**
     *
     * @param key
     * @param value
     */
    public void save(String key, String value) {
        this.hashtable.put(key, value);
        System.out.println("Saved " + key + " " + value);
        if (count % 4 == 0) {
            System.out.println("Writing to file !");
            try {
                File temp = new File(dataFile.getParentFile(), "temp.dat");
                Files.newOutputStream(temp.toPath(), CREATE_NEW);
                dataFile.delete();
                temp.renameTo(dataFile);
            } catch (IOException ex) {
                Logger.getLogger(GrabberState.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
