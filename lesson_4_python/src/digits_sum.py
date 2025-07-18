def sum_of_digits(number):
    number = abs(number)
    total = 0
    while number > 0:
        total += number % 10
        number = number // 10
    return total

def sum_of_odd_digits(number):
    number = abs(number)
    total = 0
    while number > 0:
        digit = number % 10
        if digit % 2 != 0:
            total += digit
        number = number // 10
    return total

def max_digit(number):
    number = abs(number)
    max_d = 0
    while number > 0:
        digit = number % 10
        if digit > max_d:
            max_d = digit
        number = number // 10
    return max_d


if __name__ == "__main__":
    num = int(input("Введите целое число: "))

    print(f"\nСумма всех цифр: {sum_of_digits(num)}")
    print(f"Сумма нечётных цифр: {sum_of_odd_digits(num)}")
    print(f"Максимальная цифра: {max_digit(num)}")
