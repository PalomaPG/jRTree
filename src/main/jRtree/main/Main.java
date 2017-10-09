package main;


import exception.RTreeDiskAccessException;
import exception.RTreeException;
import structure.*;
import utils.Constants;

import java.io.*;

public class Main {

    public static void main(String [] args) throws RTreeDiskAccessException, RTreeException {
        //readData();
        createDirIfNotExist(args[0]);
        createDirIfNotExist(args[1]);
        createDirIfNotExist(args[2]);
        execTasks(args[0], args[1], args[2], args[3]);
        System.out.println("Done!!");

    }

    public static  void createDirIfNotExist(String path){

        File theDir = new File(path);
        // if the directory does not exist, create it
        if (!theDir.exists()) {
            try{
                theDir.mkdir();
            } catch(SecurityException se){
                //handle it
                se.printStackTrace();
            }
        }
    }

    public static void execTasks(String input_path, String output_path,
                                 String written_nodes, String pythonScript){

        String prefix = Constants.PREFIX;

        for(int i =9; i<=10; i++){
            testTasksLinear(input_path, output_path, written_nodes, i,pythonScript );
            //testTasksGreene(input_path, output_path, written_nodes,i, pythonScript);
        }
    }

    public static void testTasksLinear(String input_path, String output_path,
                                       String written_nodes,int i, String pythonScript){

        try {

            String insert_path = input_path+Constants.synth_insert_dir+Constants.PREFIX+i+Constants.SUFFIX;
            String search_path = input_path+Constants.synth_search_dir+Constants.PREFIX+i+Constants.SUFFIX;
            String insert_out = output_path+Constants.OUTPUT_INSERT_LIN+i+Constants.SUFFIX;
            String search_out = output_path+Constants.OUTPUT_SEARCH_LIN+i+Constants.SUFFIX;

            Tasks tasks = new Tasks(insert_path,new FileWriter(insert_out),
                    search_path, new FileWriter(search_out), new LinearSplitter(),
                    2000, written_nodes);
            tasks.insertTask();
            System.err.println("Insert task: DONE\n");
            //tasks.searchTask();
            System.err.println("Search task: DONE\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void testTasksGreene(String input_path, String output_path,
                                       String written_nodes,int i, String pythonScript){

        try {

            String insert_path = input_path+Constants.synth_insert_dir+Constants.PREFIX+i+Constants.SUFFIX;
            String search_path = input_path+Constants.synth_search_dir+Constants.PREFIX+i+Constants.SUFFIX;
            String insert_out = output_path+Constants.OUTPUT_INSERT_GREENE+Constants.SUFFIX;
            String search_out = output_path+Constants.OUTPUT_SEARCH_GREENE+Constants.SUFFIX;

            Tasks tasks = new Tasks(insert_path,new FileWriter(insert_out),
                    search_path, new FileWriter(search_out), new GreeneSplit(),2000,
                    written_nodes);
            tasks.insertTask();
            System.err.println("Insert task: DONE\n");
            tasks.searchTask();
            System.err.println("Search task: DONE\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
