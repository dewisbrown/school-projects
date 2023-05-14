"""
This command-line driven program provides
five options to perform different calculations.

The user can exit the program at any point from the main menu.
"""

import math
import string
import secrets
import datetime


def menu():
    """The main menu of the program."""

    print("\n1. Generate secure password")
    print("2. Calculate and format a percentage")
    print("3. How many days from today until July 4, 2025")
    print("4. Use the law of cosines to calculate the leg of a triangle")
    print("5. Calculate the volume of a Right Circular Cylinder")
    print("6. Exit the program")


def law_cosines(side_a, side_b, angle):
    """Uses passed args and the law of cosines to calculate the value of the missing triangle side.

    :param side_a: (float) length of "side a" of a triangle
    :param side_b: (float) length of "side b" of a triangle
    :param angle: (float) value of angle (in radians) opposite of "side c" of a triangle
    :return: (float) length of "side c" of a triangle
    """

    # law of cosine --> c^2 = a^2 + b^2 − 2ab*cos(C)
    return math.sqrt(math.pow(side_a, 2) + math.pow(side_b, 2) -
                     (2 * side_a * side_b * math.cos(angle)))


def is_spec(spec):
    """Determines if a given string is a special character.

    :param spec: (str) a single character passed by generate_password()
    :return: (bool) True if the character is a special character, False if not
    """

    if spec in string.punctuation:
        return True
    return False


def password_combo(upper, lower, nums, specs):
    """With given boolean params, an integer is returned to represent
    the possible combination of True/False values for all four params

    :param upper: (bool) True if password needs uppercase, False if not
    :param lower: (bool) True if password needs lowercase, False if not
    :param nums: (bool) True if password needs numbers, False if not
    :param specs: (bool) True if password needs special characters, False if not
    :return: (int) represents parameter combination
    """
    if upper:
        if lower:
            if nums:
                if specs:
                    # upper, lower, nums, specs
                    return 1
                # upper, lower, nums
                return 2
            if specs:
                # upper, lower, specs
                return 3
            # upper, lower
            return 4
        if nums:
            if specs:
                # upper, nums, specs
                return 5
            # upper, nums
            return 6
        if specs:
            # upper, specs
            return 7
        # only upper
        return 8

    if lower:
        if nums:
            if specs:
                # lower, nums, specs
                return 9
            # lower, nums
            return 10
        if specs:
            # lower, specs
            return 11
        # only lower
        return 12

    if nums:
        if specs:
            # nums, specs
            return 13
        # only nums
        return 14

    if specs:
        # only specs
        return 15


def get_alphabet(combo):
    """Used to get characters from which to generate a password from.

    :param combo: (int) represents combination of True/False password params
    :return: (str) list of letters to use for password generation
    """
    alphabet = ""

    if combo in {1, 2, 3, 4, 5, 6, 7, 8}:
        alphabet += string.ascii_uppercase
    if combo in {1, 2, 3, 4, 9, 10, 11, 12}:
        alphabet += string.ascii_lowercase
    if combo in {1, 2, 5, 6, 9, 10, 13, 14}:
        alphabet += string.digits
    if combo in {1, 3, 5, 7, 9, 11, 13, 15}:
        alphabet += string.punctuation

    return alphabet


def generate_password(length, combo, alphabet):
    """Generates a password with parameters supplied by the user.

    :param alphabet: (str) characters to use for password generation
    :param length: (int) length of the password to be generated
    :param combo: (int) represents combination of
                        true/false password character parameters
    :return: (str) password generated following the given parameters
    """

    # there is probably a better way to code this. this is all I could think of
    while True:
        password = "".join(secrets.choice(alphabet) for i in range(length))

        if combo == 1:
            if (any(c.isupper() for c in password)
                    and any(c.islower() for c in password)
                    and any(c.isdigit() for c in password)
                    and any(is_spec(c) for c in password)):
                break
        if combo == 2:
            if (any(c.isupper() for c in password)
                    and any(c.islower() for c in password)
                    and any(c.isdigit() for c in password)):
                break
        if combo == 3:
            if (any(c.isupper() for c in password)
                    and any(c.islower() for c in password)
                    and any(is_spec(c) for c in password)):
                break
        if combo == 4:
            if (any(c.isupper() for c in password)
                    and any(c.islower() for c in password)):
                break
        if combo == 5:
            if (any(c.isupper() for c in password)
                    and any(c.isdigit() for c in password)
                    and any(is_spec(c) for c in password)):
                break
        if combo == 6:
            if (any(c.isupper() for c in password)
                    and any(c.isdigit() for c in password)):
                break
        if combo == 7:
            if (any(c.isupper() for c in password)
                    and any(is_spec(c) for c in password)):
                break
        if combo == 8:
            if any(c.isupper() for c in password):
                break
        if combo == 9:
            if (any(c.islower() for c in password)
                    and any(c.isdigit() for c in password)
                    and any(is_spec(c) for c in password)):
                break
        if combo == 10:
            if (any(c.islower() for c in password)
                    and any(c.isdigit() for c in password)):
                break
        if combo == 11:
            if (any(c.islower() for c in password)
                    and any(is_spec(c) for c in password)):
                break
        if combo == 12:
            if any(c.islower() for c in password):
                break
        if combo == 13:
            if (any(c.isdigit() for c in password)
                    and any(is_spec(c) for c in password)):
                break
        if combo == 14:
            if any(c.isdigit() for c in password):
                break
        if combo == 15:
            if any(is_spec(c) for c in password):
                break

    return password


def calc_percent(numerator, denominator, dec):
    """Calculate the percentage representation of a given fraction and amount of decimal points

    :param numerator: (float) numerator of a fraction
    :param denominator: (float) denominator of a fraction
    :param dec: (int) the amount of decimal points to format the percentage

    :return: (float) formatted percent representation of the given numerator,
                     denominator, and number of decimal points
    """

    percent = (numerator / denominator) * 100
    return f'{percent:.{dec}f}'


def get_float(message):
    """Condense code a little by providing input validation function.

    :param message: (String) printed message to notify user of input
    :return: number: (float) a valid, user-input number
    """
    while True:
        try:
            number = float(input(message))
            if number > 0:
                return number
            print("Enter a positive number.")
        except ValueError:
            print("Enter a valid number.")


def create_cylinder():
    """Prints the volume of a cylinder with user input measurements."""

    print("You have chosen to calculate the volume of a Right Circular Cylinder.\n")

    # get radius
    while True:
        try:
            radius = int(input("What is the radius of the cylinder base? "))
            if radius > 0:
                break
            print("Enter a positive numerical value")
        except ValueError:
            print("Enter a valid value")

    # get height
    while True:
        try:
            height = int(input("What is the height of the cylinder? "))
            if height > 0:
                break
            print("Enter a positive numerical value")
        except ValueError:
            print("Enter a valid value")

    volume = radius * height * math.pi
    print("The volume of the cylinder is " + f'{volume:.3f}' + "\n")


def get_password():
    """Prints a randomly generated password with user-given parameters."""

    print("You have chosen to generate a secure password.\n")

    # get password length
    while True:
        try:
            pass_length = int(input("How many characters would you like your password to be? "))
            if 3 < pass_length < 25:
                break
            print("Enter a numerical value greater than 3 and less than 25.")
        except ValueError:
            print("Enter a numerical value greater than 3 and less than 25.")

    # include/exclude uppercase characters
    while True:
        upper_case = input("Would you like to include upper case characters? (Y or N) ")
        if upper_case in ("Y", "y"):
            has_upper = True
            break
        if upper_case in ("N", "n"):
            has_upper = False
            break
        print("Enter Y or N")

    # include/exclude lowercase letters
    while True:
        lower_case = input("Would you like to include lower case characters? (Y or N) ")
        if lower_case in ("Y", "y"):
            has_lower = True
            break
        if lower_case in ("N", "n"):
            has_lower = False
            break
        print("Enter Y or N")

    # include/exclude numbers
    while True:
        numbers = input("Would you like to include numerical characters? (Y or N) ")
        if numbers in ("Y", "y"):
            has_numbers = True
            break
        if numbers in ("N", "n"):
            has_numbers = False
            break
        print("Enter Y or N")

    # include/exclude special characters
    while True:
        spec_char = input("Would you like to include special characters? (Y or N) ")
        if spec_char in ("Y", "y"):
            has_spec = True
            break
        if spec_char in ("N", "n"):
            has_spec = False
            break
        print("Enter Y or N")

    # user must select at least one type of character to include in password
    if has_upper or has_lower or has_numbers or has_spec:
        combo = password_combo(has_upper, has_lower, has_numbers, has_spec)
        alphabet = get_alphabet(combo)

        password = generate_password(pass_length, combo, alphabet)
        return password
        #print("Your password: " + password + "\n")
    else:
        print("A password cannot be generated with the given parameters.\n")


def get_percent():
    """Prints the percentage representation of a given fraction."""

    print("You have chosen to calculate a percentage.\n")

    # get numerator
    numerator = get_float("What is the numerator of your fraction? ")

    # get denominator
    denominator = get_float("What is the denominator of your fraction? ")

    # get decimal format count
    while True:
        try:
            dec = int(input("How many decimal points to format your percentage? "))
            if dec >= 0:
                break
            if dec < 0:
                print("Enter a positive numerical value.")
        except ValueError:
            print("Enter a valid numerical value.")

    # calculate and print formatted percentage
    percentage = calc_percent(numerator, denominator, dec)
    print("\nThe percent representation of " + str(numerator) + "/" +
          str(denominator) + " is " + str(percentage) + "%\n")


def get_days():
    """Prints amount of days until July 4, 2025, using current time/day."""

    days = (datetime.datetime(2025, 7, 4) - datetime.datetime.now()).days + 1
    print("\nThere are " + str(days) + " days until July 4, 2025.\n")


def get_triangle_leg():
    """Prints the calculation of "side c" of a triangle
     after receiving all missing values from user."""

    print("You have chosen to calculate the leg of a triangle.\n")

    # get length of side A
    while True:
        try:
            side_a = float(input("What is the length of side A? "))
            if side_a > 0:
                break
            print("Enter a positive numerical value.")
        except ValueError:
            print("Enter a positive numerical value.")

    # get length of side B
    while True:
        try:
            side_b = float(input("What is the length of side B? "))
            if side_b > 0:
                break
            print("Enter a positive numerical value.")
        except ValueError:
            print("Enter a positive numerical value.")

    # get angle opposite of side C
    while True:
        try:
            angle_c = float(input("What is the degree value of the angle opposite of side C? "))
            if 0 < angle_c < 180:
                # converts degrees to radians
                angle_c = angle_c * (math.pi / 180)
                break
            print("Enter a positive value less than 180.")
        except ValueError:
            print("Enter a positive numerical value.")

    # calculate and print side C
    side_c = law_cosines(side_a, side_b, angle_c)
    print("The length of side C is " + f'{side_c:.3f}' + "\n")


def menu_select(choice):
    """Processes selection from the main menu to navigate to the proper function.

    :param choice: (int) represents main menu selection from user
    """
    if choice == 1:
        get_password()
    if choice == 2:
        get_percent()
    if choice == 3:
        get_days()
    if choice == 4:
        get_triangle_leg()
    if choice == 5:
        create_cylinder()


# main program loop
while True:
    try:
        print("Select from one of the following: ")
        menu()
        selection = int(input())
        if 0 < selection < 6:
            menu_select(selection)
        elif selection == 6:
            print("\nThank you for using this program.")
            break
        else:
            print(str(selection) + " is not a valid menu option\n")
    except ValueError:
        print("Enter a valid number\n")
