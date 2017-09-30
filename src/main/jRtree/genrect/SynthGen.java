package genrect;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class SynthGen extends AbstractGen {
	
	final double COORD_MAX = 500000;
	final double DIM_MAX = 100;

	
	public SynthGen(int n, String pwd) {
		this.N = (int)Math.pow(2, n);
		this.filename = String.format(pwd+"/synthdata-N%d.csv", N);
		try {
			this.raf = new RandomAccessFile(filename, "rw");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public double[] genSingleRectangle() {
		double [] rect_desc = new double[4];
		Random rnd = new Random();
		
		rect_desc[0] = (rnd.nextDouble() * COORD_MAX);
		rect_desc[1] = (rnd.nextDouble() * COORD_MAX);
		rect_desc[2] = (rnd.nextDouble() * (DIM_MAX-1))+1;//width
		rect_desc[3] = (rnd.nextDouble() * (DIM_MAX-1))+1;//length
		
		return rect_desc;
	}
	
	
	@Override
	public double[] coords(double [] rd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] coordLengthAndWidth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeFile() {
		// TODO Auto-generated method stub
		for(int i=0; i<N; i++) {
			double [] d = genSingleRectangle();
			try {

				this.raf.writeChars(String.format("%f,%f,%f,%f\n", d[0], d[1], d[2], d[3]));
				}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
}
