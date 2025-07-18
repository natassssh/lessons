package ru.ais.java.OOP;

public class Main {
    public static void main(String[] args) {

        Person person1 = new Person("Иван", "Иванович", "Иванов", 16);

        System.out.println("Создан человек: " + person1);
        System.out.println("Год рождения:" + person1.getBirthYear());

        person1.setAge(28);
        System.out.println("Год рождения:" + person1.getBirthYear());

//        person1.setName("Сергей");
//        System.out.println("После изменения имени: " + person1);
//        person1.setMiddleName("Петрович");
//        System.out.println("После изменения отчества: " + person1);
//        person1.setFamilyName("Романов");
//        System.out.println("После изменения фамилии: " + person1);
//        person1.setAge(14);
//        System.out.println("После изменения возраста: " + person1);
//        person1.setAge(20);
//        System.out.println("После изменения возраста: " + person1);
//
//        System.out.println("Фамилия: " + person1.getFamilyName());
//        System.out.println("Имя: " + person1.getName());
//        System.out.println("Отчество: " + person1.getMiddleName());
//
//        Person person2 = new Person("Мария", "Сергеевна", "Петрова", 14);
//        System.out.println("\nЕще один человек: " + person2);
//
    }
}
