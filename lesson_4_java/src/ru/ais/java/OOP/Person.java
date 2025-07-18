package ru.ais.java.OOP;

import java.time.Year;

public class Person {
    private String name;
    private String middleName;
    private String familyName;
    public static final int MIN_AGE = 15;
    private int age;

    public Person(String name, String middleName, String familyName, int age) {
        this.name = name;
        this.middleName = middleName;
        this.familyName = familyName;
        setAge(age);
    }

    public static int getMinAge() {
        return MIN_AGE;
    }
    public String getName() {
        return name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public int getAge() {
        return age;
    }


    public int getBirthYear() {
        int currentYear = Year.now().getValue();
        return currentYear - age;
    }

    public void setName(String name) {
        if (!(name.isEmpty())) {
            this.name = name;
            System.out.println("Имя было изменено");
        }else {
            System.out.println("Имя не было изменено, передано пустое имя");
        }
    }

    public void setMiddleName(String middleName) {
        if (!(middleName.isEmpty())) {
            this.middleName = middleName;
            System.out.println("Отчество было изменено");
        }else {
            System.out.println("Отчество не было изменено, передано пустое отчество");
        }
    }

    public void setFamilyName(String familyName) {
        if (!(familyName.isEmpty())) {
            this.familyName = familyName;
            System.out.println("Фамилия была изменена");
        } else {
            System.out.println("Фамилия не была изменена, передана пустая фамилия");
        }
    }

    public void setAge(int age) {
        if (age >= MIN_AGE) {
            this.age = age;
            System.out.println("Возраст был изменен");
        } else {
            throw new IllegalArgumentException("Возраст не может быть меньше " + MIN_AGE + " лет");
        }
    }

    @Override
    public String toString() {
        return "{" + familyName + " " + name + " " + middleName + " " + age + "}";
    }

    public void printName(){
        System.out.println("Name = " + name);
    }
}
