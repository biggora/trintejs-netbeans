package org.netbeans.modules.trintejs.tools;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * @author Aleks
 */
public class TrinteJSFileReader {

    public static String read(String fileName) {
        String out = "";
        try {
            FileInputStream fstream = new FileInputStream(fileName);
            DataInputStream fin = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(fin));
            String str;

            while ((str = br.readLine()) != null) {
                out += str;
            }
            fin.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        return out;
    }
}