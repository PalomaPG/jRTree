package genrect;


public class Main {


		public static void main(String[] args) {
			
			synthgenRutine();
			sample2search();
		}
		
		public static void synthgenRutine() {
		    String path = ".";
			System.err.println("INIT - Synthetic data generation");
			SynthGen sg = new SynthGen(3, path);
			sg.writeFile();
			System.err.println("END - Synthetic data generation");
		}

		public static void sample2search(){

			String pref = "sam";
			String path = ".";
			String dataset = "synthdata-N8.csv";
			Sample2search s2s = new Sample2search(pref,dataset, path,4);
			s2s.createSample();
		}
}

