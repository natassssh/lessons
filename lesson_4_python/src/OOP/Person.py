from datetime import datetime

class Person:
    MIN_AGE = 15
    def __init__(self, name: str, middle_name: str, family_name: str, age: int = 15):
        self.__name = name
        self.__middle_name = middle_name
        self.__family_name = family_name
        self.age = age


    # Геттеры
    @property
    def name(self) -> str:
        return self.__name

    @property
    def middle_name(self) -> str:
        return self.__middle_name

    @property
    def family_name(self) -> str:
        return self.__family_name

    @property
    def age(self) -> int:
        return self.__age

    # Сеттеры
    @name.setter
    def name(self, value: str):
        if not value or not value.strip():
            raise ValueError("Имя не может быть пустым")
        self.__name = value

    @middle_name.setter
    def middle_name(self, value: str):
        if not value or not value.strip():
            raise ValueError("Отчество не может быть пустым")
        self.__middle_name = value

    @family_name.setter
    def family_name(self, value: str):
        if not value or not value.strip():
            raise ValueError("Фамилия не может быть пустой")
        self.__family_name = value

    @age.setter
    def age(self, value: int):
        if value < self.MIN_AGE:
            raise ValueError("Возраст не может быть отрицательным")
        self.__age = value

    def get_birth_year(self) -> int:
        current_year = datetime.now().year
        return current_year - self.__age

    def __str__(self) -> str:
        """
        :return: строка в формате "Фамилия Имя Отчество, Возраст: X"
        """
        return f"{self.family_name} {self.name} {self.middle_name}, Возраст: {self.age}"


if __name__ == "__main__":
    person1 = Person("Мария", "Ивановна", "Сидорова", 45)
    print(person1)
    print(f"Год рождения: {person1.get_birth_year()}")

    person1.age = 30
    print(person1)
    print(f"Год рождения: {person1.get_birth_year()}")

    person1.age = 15
    print("\nПосле изменения возраста:")
    print(person1)
    print(f"Новый год рождения: {person1.get_birth_year()}")

    # Попытка присвоения возраста меньше минимального
    # person.age = 10
    # print(person)

    # Создание нового объекта
    # person2 = Person("Иван", "Иванович", "Иванов", 21)
    # print(person2)
    # print(f"Год рождения: {person2.get_birth_year()}")
    # person2.name = "Дмитрий"
    # print("Имя: ", person2.name)
    # person2.middle_name = "Валерьевич"
    # print("Отечество: ", person2.middle_name)
    # person2.family_name = "Соболев"
    # print("Фамилия: ", person2.family_name)

    # Попытка создания пустого имени
    # person2.name = ""
    # print("Имя: ", person2.name)