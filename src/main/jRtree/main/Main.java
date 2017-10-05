package main;


import exception.RTreeDiskAccessException;
import exception.RTreeException;
import structure.Coord2D;
import structure.LinearSplitter;
import structure.MBR;
import structure.RTree;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    static RTree tree;
    public static void main(String [] args) throws RTreeDiskAccessException, RTreeException {


        readData();
        System.out.println("Done!!");

    }

    public static void readData() throws RTreeDiskAccessException, RTreeException {

        tree = new RTree(2, new LinearSplitter());

        try {
            FileReader reader = new FileReader("./synthdata-N8.csv");
            BufferedReader br = new BufferedReader(reader);
            String line;
            while((line=br.readLine())!=null) {

                String[] coords = line.split(",");
                double minX = Double.parseDouble(coords[0]);
                double minY = Double.parseDouble(coords[1]);
                double maxX = Double.parseDouble(coords[2]);
                double maxY = Double.parseDouble(coords[3]);

                MBR mbr = new MBR(new Coord2D(minX, minY), new Coord2D(maxX, maxY));
                tree.insert(mbr);
            }
            br.close();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
