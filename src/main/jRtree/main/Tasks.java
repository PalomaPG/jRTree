package main;

import structure.Coord2D;
import structure.MBR;
import structure.RTree;

import java.io.*;

public class Tasks {

    protected String insertInPath,  searchInPath;
    protected FileWriter insertOutPath, searchOutPath;

    public Tasks(String insertInPath, FileWriter insertOutPath,
                 String searchInPath, FileWriter searchOutPath){

        this.insertInPath = insertInPath;
        this.insertOutPath = insertOutPath;
        this.searchInPath = searchInPath;
        this.searchOutPath = searchOutPath;

    }

    public void insertTask(RTree tree){

        try {
            FileReader fr = new FileReader(insertInPath);
            BufferedReader br = new BufferedReader(fr);

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
            fr.close();

            insertOutPath.write(String.format("%f,%f,%f", tree.getBT(), tree.getSpace(), tree.percentage()));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void searchTask(RTree tree){

        try {
            FileReader fr = new FileReader(searchInPath);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while((line=br.readLine())!=null) {

                String[] coords = line.split(",");
                int minX = Integer.parseInt(coords[0]);
                int minY = Integer.parseInt(coords[1]);
                int maxX = Integer.parseInt(coords[2]);
                int maxY = Integer.parseInt(coords[3]);

                MBR mbr = new MBR(new Coord2D(minX, minY), new Coord2D(maxX, maxY));
                tree.search(mbr);
                searchOutPath.write(String.format("%f, %f", tree.getST(), tree.getVisNodes()));
            }
            br.close();
            fr.close();



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
