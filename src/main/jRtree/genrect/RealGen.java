package genrect;


import com.vividsolutions.jts.geom.MultiPolygon;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.store.ContentFeatureCollection;
import org.geotools.data.store.ContentFeatureSource;
import org.geotools.geojson.geom.MultiPolygonHandler;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.geometry.Geometry;


import java.io.*;
import java.net.MalformedURLException;



public class RealGen extends AbstractGen {


    File file;
    ShapefileDataStore dataStore;
    public RealGen(String shp){
        file = new File(shp);
        try {
            dataStore= new ShapefileDataStore(file.toURL());
            ContentFeatureSource featureSource = dataStore.getFeatureSource();
            if (featureSource==null) System.err.println("ES NULO!");
            ContentFeatureCollection featureCollection = featureSource.getFeatures();
           SimpleFeatureIterator sfit=featureCollection.features();

           while (sfit.hasNext()){
               SimpleFeature feature = sfit.next();
               MultiPolygon mp = (MultiPolygon) feature.getDefaultGeometry();
               System.err.println(mp.getBoundary().getEnvelope().getCoordinate().x);
               //System.err.println(geometry.getBoundary().getEnvelope().getLowerCorner().getCoordinate()[0]);

           }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void writeFile() {
		// TODO Auto-generated method stub

	}

	public double[] coords(double[] rd) {
		return new double[0];
	}



}
