def calculate_averages(start, end):
    total_sum = 0
    count = 0

    for i in range(start, end + 1):
        total_sum += i
        count += 1

    average_all = total_sum / count if count > 0 else 0

    print(f"\nСреднее арифметическое всех чисел: {average_all:.2f}")

def calculate_averages_even_numbers(start, end):
    even_sum = 0
    even_count = 0

    for i in range(start, end + 1):
        if i % 2 == 0:
            even_sum += i
            even_count += 1

    average_even = even_sum / even_count if even_count > 0 else 0

    print(f"Среднее арифметическое четных чисел: {average_even:.2f}")

if __name__ == "__main__":
    start = 0
    end = 0

    while (start <= 1) or (end < start):
        start = int(input("Введите начальное число (>1): "))
        end = int(input("Введите конечное число: "))
        if start <= 1:
            print("Ошибка: начальное число должно быть >1")
        if end <= start:
            print("Ошибка: конечное число должно быть больше начального")

    calculate_averages(start, end)
    calculate_averages_even_numbers(start, end)