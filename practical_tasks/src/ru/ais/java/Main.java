package ru.ais.java;

import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        System.out.println("Введите ваше имя");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        if (s.isEmpty()){
            System.out.println("Привет, неизвестный!");
        }
        else {
            System.out.println("Привет, " + s + "!");
        }
//        Scanner scanner = new Scanner(System.in);
//        String s;
//        do {
//            System.out.println("Введите ваше имя");
//            s = scanner.nextLine();
//        }while (s.isEmpty());
//
//        System.out.println("Привет, " + s + "!");
    }
}