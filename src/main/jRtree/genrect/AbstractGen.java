package genrect;

import java.io.FileWriter;

public abstract class AbstractGen {

		protected int N;
		protected String filename;
		protected FileWriter fw;
		
		public void setN(int n) {
			this.N= (int)Math.pow(2,n);
		}
		public abstract void writeFile();
		public abstract double[] coords(double [] rd); 
		public abstract double[] coordLengthAndWidth();
}
