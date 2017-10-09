package genrect;


import java.io.File;

public class Main {

        static String synth_insert_dir = "/SYNTH-INSERT/";
        static String synth_search_dir = "/SYNTH-SEARCH/";

		public static void main(String[] args) {
		    allSynth(args[0]);
        }
		
		public static void synthgenRoutine(String path, int i, boolean b) {
		    ifDirDoesNotExist(path);
			SynthGen sg = new SynthGen(i, path, b);
			sg.writeFile();
		}

		public static void allSynth(String path){

		    for(int i=9; i<=25; i++){
		        /*Gen insert data*/
                synthgenRoutine(path+synth_insert_dir, i, false);
		        /*Gen search data*/
                synthgenRoutine(path+synth_search_dir, i, true);
            }

        }

        public static void ifDirDoesNotExist(String path){


            File theDir = new File(path);

            if (!theDir.exists()) {
                System.out.println("creating directory: " + theDir.getName());
                boolean result = false;
                try{
                    theDir.mkdir();
                    result = true;
                }
                catch(SecurityException se){
                    //handle it
                    se.printStackTrace();
                }
                if(result) {
                    System.out.println("DIR created");
                }
            }


        }
}

