"""
Author: Luis Moreno
Date: November 15, 2022

Lab 4:
Script used to generate and hash 20 different passwords.
Results are printed to the console.
"""
import hashlib
import secrets
import string


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


# used in Lab 2, altered to return the password and print fewer statements
def get_password():
    """Generates password with user-given parameters.

    :return: password: (str) randomly generated string
    """

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
    else:
        print("A password cannot be generated with the given parameters.\n")


def get_salted(password):
    """Adds salt to password.

    :param password: (str) user input password
    :return (str) password with salt added
    """

    return password + "thisisthesalt"


def hash_and_print(password):
    """Hashes password using MD-5, SHA-256, and SHA-512. Prints each to console.

    :param password: (str) user input password
    """

    print("\nPassword:", password)
    password = password.encode()

    password_md5 = hashlib.md5(password).hexdigest()
    password_sha256 = hashlib.sha256(password).hexdigest()
    password_sha512 = hashlib.sha512(password).hexdigest()

    print("\nPassword hashed with MD-5, SHA-256, SHA-512:")
    print(password_md5)
    print(password_sha256)
    print(password_sha512)


passwords = []

# input 10 passwords to add to passwords list
for i in range(10):
    passwords.append(input("Enter password: "))

# input 5 passwords to get salted, then add to passwords list
for i in range(5):
    passwords.append(get_salted(input("Enter password to salt: ")))

# randomly generate 5 passwords giving password parameters, then add to passwords list
for i in range(5):
    passwords.append(get_password())

# hash and print all passwords in passwords list
for p in passwords:
    hash_and_print(p)
