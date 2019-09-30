package utils;

import java.util.UUID;

public class IDUtil {
    private   int index = 0;
    public static String randomId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
    
    public  int playerIndex() {
        return index++;
    }
}
