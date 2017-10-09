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
        execTasks(args[0], args[1]);
        System.out.println("Done!!");

    }


    public static void execTasks(String input_path, String output_path){

        String prefix = Constants.PREFIX;

        for(int i =9; i<=25; i++){
            testTasksLinear(input_path, output_path, i);
            testTasksGreene(input_path, output_path, i);

        }

    }

    public static void testTasksLinear(String input_path, String output_path,int i){

        try {

            String insert_path = input_path+Constants.synth_insert_dir+Constants.PREFIX+i+Constants.SUFFIX;
            String search_path = input_path+Constants.synth_search_dir+Constants.PREFIX+i+Constants.SUFFIX;
            String insert_out = output_path+Constants.OUTPUT_INSERT_LIN;
            String search_out = output_path+Constants.OUTPUT_SEARCH_LIN;

            Tasks tasks = new Tasks(insert_path,new FileWriter(insert_out),
                    search_path, new FileWriter(search_out), new LinearSplitter(),2000);
            tasks.insertTask();
            System.err.println("Insert task: DONE\n");
            tasks.searchTask();
            System.err.println("Search task: DONE\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void testTasksGreene(String input_path, String output_path,int i){

        try {

            String insert_path = input_path+Constants.synth_insert_dir+Constants.PREFIX+i+Constants.SUFFIX;
            String search_path = input_path+Constants.synth_search_dir+Constants.PREFIX+i+Constants.SUFFIX;
            String insert_out = output_path+Constants.OUTPUT_INSERT_GREENE;
            String search_out = output_path+Constants.OUTPUT_SEARCH_GREENE;

            Tasks tasks = new Tasks(insert_path,new FileWriter(insert_out),
                    search_path, new FileWriter(search_out), new GreeneSplit(),2000);
            tasks.insertTask();
            System.err.println("Insert task: DONE\n");
            tasks.searchTask();
            System.err.println("Search task: DONE\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
