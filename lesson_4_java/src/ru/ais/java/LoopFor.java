package ru.ais.java;

public class LoopFor {
    public static void main(String[] args) {
        for (int i = 100; i >= 1; i--) {
            if (isMultiple4(i)) {
                System.out.print(i + " ");
            }
        }
    }

    public static boolean isMultiple4(int number) {
        return number % 4 == 0;
    }
}
