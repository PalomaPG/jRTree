package genrect;


public class Main {


		public static void main(String[] args) {
			
			synthgenRoutine();
		}
		
		public static void synthgenRoutine() {
		    String path = ".";
			System.err.println("INIT - Synthetic data generation");
			SynthGen sg = new SynthGen(25, path);
			sg.writeFile();
			System.err.println("END - Synthetic data generation");
		}
}

