package main.java.util;

import java.util.Random;

public class RandomEmailGenerator {

    public static String generate() {
        String str1 = "abcdefghijklmnoparstuvwxyz";
        String str2 = "0123456789";
        StringBuilder sb = new StringBuilder(8);
        Random r = new Random();
        for (int i = 0; i < 5; i++) {
            sb.append(str1.charAt(r.nextInt(str1.length())));
        }
        for (int j = 0; j < 3; j++) {
            sb.append(str2.charAt(r.nextInt(str2.length())));
        }
        return sb.toString() + "@gmail.com";
    }


}
