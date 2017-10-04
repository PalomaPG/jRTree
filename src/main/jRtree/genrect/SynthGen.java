package genrect;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.Random;

public class SynthGen extends AbstractGen {
	
	final double COORD_MAX = 500000;
	final double DIM_MAX = 100;

	
	public SynthGen(int n, String pwd) {
		this.N = (int)Math.pow(2, n);
		this.filename = String.format(pwd+"/synthdata-N%d.csv", N);
		try {
			this.fw = new FileWriter(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
        double [] extremes = new double[4];
        rd[2] = rd[0]+rd[2];
        rd[3] = rd[1]+rd[3];
		return rd;
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
			double [] d = coords(genSingleRectangle());
			try {
                DecimalFormat formatter = new DecimalFormat("000000.000000");
				this.fw.write(String.format("%s,%s,%s,%s\n", formatter.format(d[0]),
                        formatter.format(d[1]), formatter.format(d[2]), formatter.format(d[3])));
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
