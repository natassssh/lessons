package ru.ais.java;

import java.util.Scanner;

public class DigitsSum {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите целое число: ");
        int number = scanner.nextInt();

        System.out.println("Сумма всех цифр: " + sumAllDigits(number));
        System.out.println("Сумма нечетных цифр: " + sumOddDigits(number));
        System.out.println("Максимальная цифра: " + maxDigit(number));
    }

    public static int sumAllDigits(int number) {
        int sum = 0;
        number = Math.abs(number);

        while (number > 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }

    public static int sumOddDigits(int number) {
        int sum = 0;
        number = Math.abs(number);

        while (number > 0) {
            int digit = number % 10;
            if (digit % 2 != 0) {
                sum += digit;
            }
            number /= 10;
        }
        return sum;
    }

    public static int maxDigit(int number) {
        int max = 0;
        number = Math.abs(number);

        while (number > 0) {
            int digit = number % 10;
            if (digit > max) {
                max = digit;
            }
            number /= 10;
        }
        return max;
    }
}

