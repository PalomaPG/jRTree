package structure;

import org.junit.BeforeClass;

public class LinearSplitTest extends DistanceBasedTest{

    @BeforeClass
    public static void setUpSplitter(){
        ls = new LinearSplitter();
    }

}
