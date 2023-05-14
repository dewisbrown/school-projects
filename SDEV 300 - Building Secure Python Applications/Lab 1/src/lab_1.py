"""This program simulates a voter registration sign-up."""


def valid_age(user_age):
    """Checks if user input is within valid range 0 - 120

    Parameter:
    user_age (int): number representing user age

    Returns:
    True if within range, False if not

    """

    # age must be between 0 and 120
    if user_age < 0 or user_age > 120:
        print("Age not within valid range (0-120)")
        return False

    return True


def get_age():
    """Prompts user to input numerical age

    Returns:
    user_age (int): numerical value representing age

    """

    # prompt age input until valid
    while True:
        try:
            user_age = int(input("Enter your age: "))
            if valid_age(user_age):
                break
        except ValueError:
            print("Enter a numerical value within valid range.")
    return user_age


def verify_continue():
    """Provides option to exit program

    Returns:
    True if user enters yes, False if no

    """

    choice = input("Do you want to continue the Voter Registration? (yes or no) ")

    # prompt until yes or no is entered
    while True:
        lower_choice = choice.lower()
        if lower_choice == "yes":
            return True
        if lower_choice == "no":
            return False

        choice = input("Please enter yes or no: ")


def get_name():
    """Prompts user to input first and last name

    Returns:
    full_name (str): formatted name by combining first_name and last_name

    """

    while True:
        first_name = input("Enter your first name: ")

        # checks if input contains numbers or symbols
        if first_name.isalpha():
            break
        print("Enter a valid name.")

    while True:
        last_name = input("Enter your last name: ")

        # checks if input contains numbers or symbols
        if last_name.isalpha():
            break
        print("Enter a valid name.")

    full_name = first_name + " " + last_name
    return full_name


def is_citizen():
    """Prompts user to verify U.S. citizenship

    Returns:
    True if user enters yes, False if no

    """

    choice = input("Are you a U.S. citizen? (yes or no) ")

    # prompt until yes or no is entered
    while True:
        lower_choice = choice.lower()
        if lower_choice == "yes":
            return True
        if lower_choice == "no":
            return False

        choice = input("Please enter yes or no: ")


def get_state():
    """Prompts user to enter state of residence

    Returns:
    upper_choice (str): two letter state abbreviation formatted to uppercase

    """

    choice = input("Enter your state of residence: (two letter abbreviation) ")

    # prompt state input until valid
    while True:
        upper_choice = choice.upper()

        # checks if input is only two characters long
        if len(choice) != 2:
            choice = input("Please enter the two-letter abbreviation of your state: ")
        # compares input with array of all state abbreviations
        elif not is_state(upper_choice):
            choice = input("Please enter a valid state: ")
        else:
            return upper_choice


def get_zip():
    """Prompts user to enter numerical zipcode

    Returns:
    str_zipcode (str): numerical zipcode as a string

    """

    # prompt zipcode input until valid
    while True:
        try:
            user_zipcode = int(input("Enter your zipcode: "))

            if user_zipcode < 0 or user_zipcode > 99999:
                print("Zipcode out of valid range.")
            else:
                str_zipcode = str(user_zipcode)
                num_dig = len(str_zipcode)
                num_zero = 5 - num_dig

                if num_dig != 5:
                    str_zipcode = str_zipcode.rjust(num_zero + len(str_zipcode), '0')
                return str_zipcode
        except ValueError:
            print("Enter a numerical value.")


def is_state(user_state):
    """Prompts user to enter numerical zipcode

    Parameter:
    user_state (str): two letter string representing a U.S. state

    Returns:
    True if valid state abbreviation, False if not

    """

    states = ["AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL",
              "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA",
              "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE",
              "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK",
              "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT",
              "VA", "WA", "WV", "WI", "WY"]
    for valid_state in states:
        if user_state == valid_state:
            return True
    return False


def exit_reason(reason):
    """Prompts user to enter numerical zipcode

    Parameter:
    reason (int): represents when/why user exited the program

    """

    # message displayed when user enters "no" for continue prompt
    if reason == 0:
        print("\nYou have exited the voter registration.")
    # message displayed when user enters age less than 18
    elif reason == 1:
        print("\nUnfortunately, you do not meet the "
              "minimum age requirement (18) to register to vote.")
    # message displayed when program is run through completely
    else:
        print("\nThank you for using the digital Voter Registration!")


# flag for exit message
N = 0

# user interface
while True:
    print("Welcome to the digital Voter Registration!\n")

    name = get_name()
    if not verify_continue():
        break

    # exits program if age value is less than 18
    age = get_age()
    if age < 18:
        N = 1
        break
    if not verify_continue():
        break

    if is_citizen():
        CITIZEN = "Yes"
    else:
        CITIZEN = "No"
    if not verify_continue():
        break

    state = get_state()
    if not verify_continue():
        break

    ZIPCODE = get_zip()
    N = 5
    break

# prints message for end of program depending on exit reason
exit_reason(N)

# prints all information if program is not prematurely exited
if N == 5:
    print("\nHere is the information you have provided:\n")
    print("Name: " + name)
    print("Age: " + str(age))
    print("U.S. Citizen: " + CITIZEN)
    print("State: " + state)
    print("Zipcode: " + str(ZIPCODE))
