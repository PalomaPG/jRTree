package main;


import exception.RTreeDiskAccessException;
import exception.RTreeException;
import structure.*;
import utils.Constants;

import java.io.*;

public class Main {

    static RTree tree;
    public static void main(String [] args) throws RTreeDiskAccessException, RTreeException {


        //readData();
        testTasks();
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
                int minX = Integer.parseInt(coords[0]);
                int minY = Integer.parseInt(coords[1]);
                int maxX = Integer.parseInt(coords[2]);
                int maxY = Integer.parseInt(coords[3]);

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

    public static void execTasks(String path, NodeSplitter splitter){

        String prefix = Constants.PREFIX;

        for(int i =9; i<=25; i++){

            //Tasks tasks = new Tasks(path+"/"+prefix+i+Constants.SUFFIX, );
        }

    }

    public static void testTasks(){

        try {
            Tasks tasks = new Tasks("./synthdata-N33554432.csv",new FileWriter("./output/out-test.csv"),
                    "./synthdata-N8.csv", new FileWriter("./output/search-test.csv"), new LinearSplitter(),2000);
            tasks.insertTask();
            tasks.searchTask();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
