package genrect;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class SynthGen extends AbstractGen {
	
	final int COORD_MAX = 500000;
	final int DIM_MAX = 100;

	
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
	
	public int[] genSingleRectangle() {
		int [] rect_desc = new int[4];
		Random rnd = new Random();
		
		rect_desc[0] = (int)(rnd.nextDouble() * COORD_MAX);
		rect_desc[1] = (int)(rnd.nextDouble() * COORD_MAX);
		rect_desc[2] = (int)(rnd.nextDouble() * (DIM_MAX-1))+1;//width
		rect_desc[3] = (int)(rnd.nextDouble() * (DIM_MAX-1))+1;//length
		
		return rect_desc;
	}
	
	
	@Override
	public int[] coords(int [] rd) {
		// TODO Auto-generated method stub
		rd[2] = rd[0]+rd[2];
		rd[3] = rd[1]+rd[3];
		return rd;
	}



	@Override
	public void writeFile() {
		// TODO Auto-generated method stub
		for(int i=0; i<N; i++) {
			int [] d = coords(genSingleRectangle());
			try {

				this.raf.writeChars(String.format("%d,%d,%d,%d\n", d[0], d[1], d[2], d[3]));
				}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
}
