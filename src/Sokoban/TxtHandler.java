package Sokoban;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TxtHandler {
public static ArrayList<String> readToArrayList(String path) {
        ArrayList<String> list = new ArrayList<String>();
        try {
            FileInputStream fstream = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
               list.add(strLine);
            }
            fstream.close();
            br.close();
            
            
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return list;
    }



public static ArrayList<String> readToArrayList(String path, String id) {
        boolean found = false;
        ArrayList<String> list = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            String strLine;
            
            while ((strLine = br.readLine()) != null ) {
                if(found && Character.isDigit(strLine.charAt(0))){
                    br.close();
                    return list;
                }
                if(found ){
                    list.add(strLine);
                }
                if(strLine == null ? id == null : strLine.equals(id)){
                   found = true;
                }
            }
            System.err.println("this lvl not exist!");
            br.close();

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return list;
    }
    public static void write(String path, String text){
        BufferedWriter outputFile;
        try {
            outputFile = new BufferedWriter(new FileWriter(path));
            outputFile.append(text);
            outputFile.close();
            
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
