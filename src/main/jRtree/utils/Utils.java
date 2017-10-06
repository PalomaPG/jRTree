package utils;

public class Utils {

    private static long idSeed = 1;

    public static synchronized long getRandomId() {
        return idSeed++;
    }
}
