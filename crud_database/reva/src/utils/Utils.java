package utils;

public class Utils {
    public static void clrsrc() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
