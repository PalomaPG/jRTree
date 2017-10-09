package genrect;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class SynthGen extends AbstractGen {
	
	final int COORD_MAX = 500000;
	final int DIM_MAX = 100;
	private int n;
	
	public SynthGen(int n, String pwd, boolean search) {
		this.n = n;
		this.N = (int)Math.pow(2, n);
		if(search) this.N = this.N/10;
		this.filename = String.format(pwd+"/synthdata-N%d.csv", n);
		try {
			this.fw = new FileWriter(this.filename);
		} catch (IOException e) {
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
		rd[2] = rd[0]+rd[2];
		rd[3] = rd[1]+rd[3];
		return rd;
	}



	@Override
	public void writeFile() {
		// TODO Auto-generated method stub
		for(int i=0; i<N; i++) {
			double [] d = coords(genSingleRectangle());
			try {

				this.fw.write(String.format("%f,%f,%f,%f\n", d[0], d[1], d[2], d[3]));
				if(i%1000==0) this.fw.flush();
				}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			this.fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
