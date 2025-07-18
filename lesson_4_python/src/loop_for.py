def is_multiple_of_four(number):
    return number % 4 == 0

for num in range(100, 0, -1):
    if is_multiple_of_four(num):
        print(num, end=' ')
