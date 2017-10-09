package main;

import exception.RTreeDiskAccessException;
import exception.RTreeException;
import structure.Coord2D;
import structure.MBR;
import structure.NodeSplitter;
import structure.RTree;
import utils.Constants;
import org.apache.commons.io.FileUtils;

import java.io.*;

public class Tasks {

    protected String insertInPath,  searchInPath, insertPython, outpath;
    protected FileWriter insertOutPath, searchOutPath;
    protected RTree tree;
    protected int experimentBuffer;
    protected long[] insertionTime;
    protected long[] searchingTime;


    public Tasks(String insertInPath, FileWriter insertOutPath,
                 String searchInPath, FileWriter searchOutPath,
                 NodeSplitter splitter, int experimentBuffer,
                 String written_nodes){

        this.insertInPath = insertInPath;
        this.insertOutPath = insertOutPath;
        this.searchInPath = searchInPath;
        this.searchOutPath = searchOutPath;
        insertionTime = new long[experimentBuffer];
        searchingTime = new long[experimentBuffer];
        this.experimentBuffer = experimentBuffer;


        try {
            FileUtils.cleanDirectory(new File(written_nodes));
            this.tree = new RTree(Constants.CAPACITY, splitter, written_nodes);

        } catch (RTreeException e) {
            e.printStackTrace();
        } catch (RTreeDiskAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insertTask(){

        try {

            FileReader fr = new FileReader(insertInPath);
            BufferedReader br = new BufferedReader(fr);

            String line;
            int i = 0;
            while((line=br.readLine())!=null) {

                String[] coords = line.split(",");
                double minX = Double.parseDouble(coords[0]);
                double minY = Double.parseDouble(coords[1]);
                double maxX = Double.parseDouble(coords[2]);
                double maxY = Double.parseDouble(coords[3]);

                MBR mbr = new MBR(new Coord2D(minX, minY), new Coord2D(maxX, maxY));
                long t0 = System.nanoTime();
                tree.insert(mbr);
                long t1 = System.nanoTime();
                int unsavedData = i%experimentBuffer;
                insertionTime[unsavedData] = t1 - t0;
                if (unsavedData == 0 && i>0){
                    saveStats(insertionTime, insertOutPath, experimentBuffer);
                }
                i++;

            }
            br.close();
            fr.close();
            saveStats(insertionTime, insertOutPath, i%experimentBuffer);
            insertOutPath.close();
            /*
            Process p = Runtime.getRuntime().exec("python3 "+insertPython+" "+"/home/paloma/out.txt");
            System.err.println("HOASAJSDJASDSDKA");
            try {
                p.waitFor();
                System.err.println(p.exitValue());
                System.err.println("QUE ONDAAAA");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void searchTask(){

        try {
            FileReader fr = new FileReader(searchInPath);
            BufferedReader br = new BufferedReader(fr);

            String line;
            int i = 0;
            while((line=br.readLine())!=null) {

                String[] coords = line.split(",");
                double minX = Double.parseDouble(coords[0]);
                double minY = Double.parseDouble(coords[1]);
                double maxX = Double.parseDouble(coords[2]);
                double maxY = Double.parseDouble(coords[3]);

                MBR mbr = new MBR(new Coord2D(minX, minY), new Coord2D(maxX, maxY));
                long t0 = System.nanoTime();
                tree.search(mbr);
                long t1 = System.nanoTime();
                int unsavedData = i%experimentBuffer;
                searchingTime[unsavedData] = t1 - t0;
                if (unsavedData == 0 && i>0){
                    saveStats(searchingTime, searchOutPath, experimentBuffer);
                }
                i++;

            }
            br.close();
            fr.close();

            saveStats(searchingTime, searchOutPath, i%experimentBuffer);
            searchOutPath.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveStats(long[] data, FileWriter outputFile, int buffer){
        StringBuilder s = new StringBuilder();
        for (int i=0; i<buffer; i++){
            s.append(data[i]);
            s.append('\n');
        }
        try {
            outputFile.write(s.toString());
            outputFile.flush();  // To save data while the experiment runs
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}