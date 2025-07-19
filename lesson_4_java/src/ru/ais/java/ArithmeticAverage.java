package ru.ais.java;

import java.util.Scanner;

public class ArithmeticAverage {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int start;
        int end;

        do {
            System.out.print("Введите начальное число диапазона (>1): ");
            start = scanner.nextInt();

            System.out.print("Введите конечное число диапазона: ");
            end = scanner.nextInt();

            if (start <= 1) {
                System.out.println("Ошибка: начальное число должно быть >1");
            }
            if (end <= start) {
                System.out.println("Ошибка: конечное число должно быть больше начального");
            }

        } while ((start <= 1) || (end < start));

        double average = calculateAverage(start, end);
        double averageEvenNumbers = calculateEvenNumbersAverage(start, end);

        System.out.printf("Среднее арифметическое чисел от %d до %d: %.2f%n", start, end, average);
        System.out.printf("Среднее арифметическое только четных чисел от %d до %d: %.2f%n", start, end, averageEvenNumbers);
    }

    public static double calculateAverage(int start, int end) {
        int sum = 0;
        int count = 0;

        for (int i = start; i <= end; i++) {
            sum += i;
            count++;
        }

        return (double) sum / count;
    }

    public static double calculateEvenNumbersAverage(int start, int end) {
        int sum = 0;
        int count = 0;

        for (int i = start; i <= end; i++) {
            if (i%2 == 0) {
                sum += i;
                count++;
            }
        }

        return (double) sum / count;
    }
}
