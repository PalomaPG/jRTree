package structure;

import org.junit.BeforeClass;

public class GreeneSplitTest extends structure.DistanceBasedTest {

    @BeforeClass
    public static void setUpSplitter(){
        ls = new GreeneSplit();
    }
}
