"""
Author: Luis Moreno
Date: November 15, 2022

Lab 4:
This is a command line menu-driven python application providing users
the ability to input values for two 3x3 matrices and perform operations
on them. The user is also prompted to enter a phone number and zip code
that is checked for proper format.
"""

import numpy as np


def mult_by_elem(a_mat, b_mat):
    """Multiplies two 3x3 matrices element-by-element
    and prints the results to console.

    :param a_mat: (numpy 2D array) 3x3 matrix of numbers
    :param b_mat: (numpy 2D array) 3x3 matrix of numbers
    """

    mult_mat = a_mat * b_mat
    mult_mat = np.array(mult_mat)
    row_mean = mult_mat.sum(axis=1) / 3
    col_mean = mult_mat.sum(axis=0) / 3

    row_str = ""
    for num in row_mean:
        row_str += f'{num:.3f}'.rstrip("0").rstrip(".") + ", "
    col_str = ""
    for num in col_mean:
        col_str += f'{num:.3f}'.rstrip("0").rstrip(".") + ", "

    print("\nYou selected multiplication by element. The results are:")
    print_matrix(mult_mat)

    print("\nThe transpose is:")
    print_matrix(mult_mat.transpose())

    print("\nThe row and column mean values of the results are:")
    print("Row: ", row_str[:-2])
    print("Column: ", col_str[:-2])


def multiplication(a_mat, b_mat):
    """Multiplies two 3x3 matrices and prints the results to console.

    :param a_mat: (numpy 2D array) 3x3 matrix of numbers
    :param b_mat: (numpy 2D array) 3x3 matrix of numbers
    """

    multiplied_mat = np.matmul(a_mat, b_mat)
    multiplied_mat = np.array(multiplied_mat)
    row_mean = multiplied_mat.sum(axis=1) / 3
    col_mean = multiplied_mat.sum(axis=0) / 3

    row_str = ""
    for num in row_mean:
        row_str += f'{num:.3f}'.rstrip("0").rstrip(".") + ", "
    col_str = ""
    for num in col_mean:
        col_str += f'{num:.3f}'.rstrip("0").rstrip(".") + ", "

    print("\nYou selected Multiplication. The results are:")
    print_matrix(multiplied_mat)

    print("\nThe transpose is:")
    print_matrix(multiplied_mat.transpose())

    print("\nThe row and column mean values of the results are:")
    print("Row: ", row_str[:-2])
    print("Column: ", col_str[:-2])


def subtraction(a_mat, b_mat):
    """Subtracts two 3x3 matrices and prints the results to console.

    :param a_mat: (numpy 2D array) 3x3 matrix of numbers
    :param b_mat: (numpy 2D array) 3x3 matrix of numbers
    """

    subbed_mat = a_mat - b_mat
    subbed_mat = np.array(subbed_mat)
    row_mean = subbed_mat.sum(axis=1) / 3
    col_mean = subbed_mat.sum(axis=0) / 3

    row_str = ""
    for num in row_mean:
        row_str += f'{num:.3f}'.rstrip("0").rstrip(".") + ", "
    col_str = ""
    for num in col_mean:
        col_str += f'{num:.3f}'.rstrip("0").rstrip(".") + ", "

    print("\nYou selected Subtraction. The results are:")
    print_matrix(subbed_mat)

    print("\nThe transpose is:")
    print_matrix(subbed_mat.transpose())

    print("\nThe row and column mean values of the results are:")
    print("Row: ", row_str[:-2])
    print("Column: ", col_str[:-2])


def addition(a_mat, b_mat):
    """Adds two 3x3 matrices and prints the results to console.

    :param a_mat: (numpy 2D array) 3x3 matrix of numbers
    :param b_mat: (numpy 2D array) 3x3 matrix of numbers
    """

    added_mat = a_mat + b_mat
    added_mat = np.array(added_mat)
    row_mean = added_mat.sum(axis=1) / 3
    col_mean = added_mat.sum(axis=0) / 3

    row_str = ""
    for num in row_mean:
        row_str += f'{num:.3f}'.rstrip("0").rstrip(".") + ", "
    col_str = ""
    for num in col_mean:
        col_str += f'{num:.3f}'.rstrip("0").rstrip(".") + ", "

    print("\nYou selected Addition. The results are:")
    print_matrix(added_mat)

    print("\nThe transpose is:")
    print_matrix(added_mat.transpose())

    print("\nThe row and column mean values of the results are:")
    print("Row: ", row_str[:-2])
    print("Column: ", col_str[:-2])


def get_option():
    """Validates and returns the matrix prompt selection.

    :return: character: (str) value can be one of the following:
                        {a, b, c, d, e}
    """

    options = {"a", "b", "c", "d", "e"}
    while True:
        character = input()
        if character in options:
            return character
        print("Enter a valid option.")
        matrix_prompt()


def matrix_prompt():
    """Menu prompt for matrix operations is printed to console."""

    print("\nSelect a Matrix Operation from the list below:\n")
    print("a. Addition")
    print("b. Subtraction")
    print("c. Matrix Multiplication")
    print("d. Element by element multiplication")
    print("e. Exit matrix operations")


def matrix_operations(a_mat, b_mat):
    """Controls flow for matrix operations by providing a prompt
    and input to the user. The user selects which operation to perform.

    :param a_mat: (numpy 2D array) 3x3 matrix of numbers
    :param b_mat: (numpy 2D array) 3x3 matrix of numbers
    """

    while True:
        matrix_prompt()
        option = get_option()

        if option == "a":
            addition(a_mat, b_mat)
        if option == "b":
            subtraction(a_mat, b_mat)
        if option == "c":
            multiplication(a_mat, b_mat)
        if option == "d":
            mult_by_elem(a_mat, b_mat)
        if option == "e":
            break


def print_matrix(matrix):
    """Prints a 3x3 matrix to console.

    :param matrix: (numpy 2D array) 3x3 matrix of numbers
    :return: prints matrix to console with the following format:
            three decimal points, remove trailing zeroes, no brackets/braces
    """

    print(f'{matrix[0][0]:.3f}'.rstrip("0").rstrip("."),
          f'{matrix[0][1]:.3f}'.rstrip("0").rstrip("."),
          f'{matrix[0][2]:.3f}'.rstrip("0").rstrip("."))
    print(f'{matrix[1][0]:.3f}'.rstrip("0").rstrip("."),
          f'{matrix[1][1]:.3f}'.rstrip("0").rstrip("."),
          f'{matrix[1][2]:.3f}'.rstrip("0").rstrip("."))
    print(f'{matrix[2][0]:.3f}'.rstrip("0").rstrip("."),
          f'{matrix[2][1]:.3f}'.rstrip("0").rstrip("."),
          f'{matrix[2][2]:.3f}'.rstrip("0").rstrip("."))


def make_array(line):
    """Converts numeric strings to float type. Returns the converted array.

    :param line: (str) input for a line of a matrix
    :return: array of numbers
    """

    line = line.split()
    return [float(numeric_string) for numeric_string in line]


def valid_line(line):
    """Checks if an input line contains only three number elements.

    :param line: (str) input for a line of a matrix
    :return: (bool) True if line matches
                    a matrix line format and specifications,
                    False if not
    """

    if len(line.split()) != 3:
        print("Enter three numbers per matrix line.")
        return False
    try:
        make_array(line)
    except ValueError:
        print("Input includes non-numeric characters.")
        return False

    return True


def get_matrix(mat_num):
    """Creates and returns a 3x3 matrix with user input for matrix elements.

    :param mat_num: (str) used in prompt to let user know which matrix is being created
    :return: (numpy array) formatted user input into 3x3 matrix
    """

    while True:
        print("Enter your " + mat_num + " 3x3 matrix:")
        first_line = input()
        if not valid_line(first_line):
            continue
        first = make_array(first_line)
        second_line = input()
        if not valid_line(second_line):
            continue
        second = make_array(second_line)
        third_line = input()
        if not valid_line(third_line):
            continue
        third = make_array(third_line)

        mat_list = [first, second, third]
        matrix = np.array(mat_list)
        matrix.reshape((3, 3))
        return matrix


def valid_num(number):
    """Uses param length and indices to determine proper format for a phone number.

    :param number: (str) representation of a phone number
    :return: (bool) True if phone number follows format, False if not
    """

    number_indices = {0, 1, 2, 4, 5, 6, 8, 9, 10, 11}

    if len(number) != 12:
        print("Incorrect amount of input characters.")
        return False
    for i in number:
        if i.isalpha():
            print("Please input only numbers and dashes.")
            return False
    for i in number_indices:
        if not number[i].isdigit():
            print("Input using this format: XXX-XXX-XXXX (X = number)")
            return False
    if (number[3] != "-") or (number[7] != "-"):
        print("Incorrect placement of \"-\" in input.")
        return False
    return True


def valid_zip(zip_code):
    """Uses param length and indices to determine proper format for a zip-code.

    :param zip_code: (str) representation of a zip-code
    :return: (bool) True if zip-code follows format, False if not
    """

    number_indices = {0, 1, 2, 3, 4, 6, 7, 8, 9}

    if len(zip_code) != 10:
        print("Incorrect amount of input characters.")
        return False
    for i in zip_code:
        if i.isalpha():
            print("Please input only numbers and dashes.")
            return False
    for i in number_indices:
        if not zip_code[i].isdigit():
            print("Input using this format: XXXXX-XXXX (X = number)")
            return False
    if zip_code[5] != "-":
        print("Incorrect placement of \"-\" in zip code input.")
        return False
    return True


def get_phone_num():
    """Validates and returns user input for a phone number.

    :return: user_phone: (str) phone number representation in XXX-XXX-XXXX format
    """

    while True:
        try:
            user_phone = input("Enter your phone number (XXX-XXX-XXXX): ")
            if valid_num(user_phone):
                return user_phone
        except ValueError:
            print("Invalid input.")


def get_zip():
    """Validates and returns user input for a zip-code.

    :return: user_zip_code: (str) zip-code in XXXXX-XXXX format
    """

    while True:
        try:
            user_zip_code = input("Enter your zip code (XXXXX-XXXX): ")
            if valid_zip(user_zip_code):
                return user_zip_code
        except ValueError:
            print("Invalid input.")


def get_yes_no(message):
    """Looped prompt for user input that is used to continue/end the program.

    :param message: (str) Prompt for user input
    :return: user_choice: (str) yes or no
    """

    while True:
        try:
            user_choice = input(message)
            user_choice = user_choice.lower()
            if user_choice in ("yes", "no"):
                return user_choice
            print("Please enter \"yes\" or \"no\"")
        except ValueError:
            print("Invalid input.")


if __name__ == '__main__':
    print("******** Welcome to the Python Matrix Application ********\n")
    while True:
        choice = get_yes_no("Would you like to play the matrix game? ")
        if choice == "no":
            print("\n******** Thanks for playing Python Numpy! ********")
            break

        phone_num = get_phone_num()
        user_zip = get_zip()

        first_mat = get_matrix("first")
        print("Your first 3x3 matrix is:")
        print_matrix(first_mat)
        second_mat = get_matrix("second")
        print("Your second 3x3 matrix is:")
        print_matrix(second_mat)

        matrix_operations(first_mat, second_mat)
