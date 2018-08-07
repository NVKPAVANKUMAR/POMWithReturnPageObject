package main.java.util;

import java.util.Random;

public class RandomPasswordGenerator {

    public static String generate() {
        StringBuilder sb = new StringBuilder(6);
        Random rand = new Random();
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "abcdefghijklmnopqrstuvwxyz" +
                "0123456789";
        for (int i = 0; i < 6; i++) {
            sb.append(str.charAt(rand.nextInt(str.length())));
        }
        return sb.toString();
    }


}
