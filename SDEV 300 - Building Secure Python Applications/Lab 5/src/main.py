"""
Author: Luis Moreno
Date: November 17, 2022

Lab 5:
This command-line driven program uses data from csv files to create
a histogram and perform calculations on that data.

One of the data files contains information for population
changes in different U.S. regions. The other file
contains information for houses.
"""

import csv
import matplotlib.pyplot as plt
import numpy as np


def print_graph(file, col):
    """Opens and reads a csv file with a passed file path.
    Using matplotlib, a graph for the selected csv column is
    displayed along with other calculations printed to the
    console.

    :param file: (str) path to file to be read
    :param col: (int) flog for csv column to read in
    """

    the_list = []

    # labels and titles are in this order to match the passed col value
    house_labels = ["Age", "Number of Bedrooms",
                    "Year Built", "", "Number of Rooms", "", "Utilities"]
    pop_titles = ["", "", "", "", "Population April 1",
                  "Population July 1", "Population Change"]

    try:
        with open(file, encoding="utf-8") as csvfile:
            read_csv = csv.reader(csvfile, delimiter=",")
            for row in read_csv:
                the_list.append(row[col])

        # first element in a column is the column label
        the_list.pop(0)

        the_list = np.array([float(numeric_string) for numeric_string in the_list])
        print("The statistics for this column are:")
        print("Count = " + f'{the_list.size:.3f}'.rstrip("0").rstrip("."))
        print("Mean = " + f'{the_list.mean():.3f}'.rstrip("0").rstrip("."))
        print("Standard Deviation = " + f'{the_list.std():.3f}'.rstrip("0").rstrip("."))
        print("Min = " + f'{the_list.min():.3f}'.rstrip("0").rstrip("."))
        print("Max = " + f'{the_list.max():.3f}'.rstrip("0").rstrip("."))
        print("The Histogram of this column is now displayed.")

        plt.hist(the_list, 'auto', edgecolor='black')

        # set labels and title depending on file
        if file == "PopChange.csv":
            plt.xlabel("Population")
            plt.ylabel("Region Count")
            plt.title(pop_titles[col])
        if file == "Housing.csv":
            plt.xlabel(house_labels[col])
            plt.ylabel("Houses")
            plt.title("Housing Data")

        plt.grid(True)
        plt.show()
    except ValueError:
        print("Unexpected data value in file!")
    except IOError:
        print("File failed to load!")


def letter_option(data_set):
    """Prints the secondary menu, then requests
    user input for menu selection.

    :param data_set: (str) flag to determine menu selection
    :return: choice: (str) single character from set {a, b, c, d, e, f}
    """

    letters = {"a", "b", "c", "d"}
    house_letters = {"a", "b", "c", "d", "e", "f"}

    # menu for population data selection
    if data_set == "pop":
        print("You have entered Population Data.\n"
              "Select the Column you want to analyze:\n"
              "a. Pop Apr 1\n"
              "b. Pop Jul 1\n"
              "c. Change Pop\n"
              "d. Exit Column")

    # menu for house data selection
    if data_set == "house":
        print("You have entered Housing Data.\n"
              "Select the Column you want to analyze:\n"
              "a. Age\n"
              "b. Bedrooms\n"
              "c. Built\n"
              "d. Rooms\n"
              "e. Utility\n"
              "f. Exit Column")

    while True:
        if data_set == "pop":
            choice = input()
            if choice in letters:
                return choice
            print("Enter a valid option.")
        if data_set == "house":
            choice = input()
            if choice in house_letters:
                return choice
            print("Enter a valid option.")


def house_data():
    """Controls flow to secondary Housing Data
    menu and graphing functions."""

    file_name = "Housing.csv"

    while True:
        choice = letter_option("house")

        if choice == "a":
            print_graph(file_name, 0)
        if choice == "b":
            print_graph(file_name, 1)
        if choice == "c":
            print_graph(file_name, 2)
        if choice == "d":
            print_graph(file_name, 4)
        if choice == "e":
            print_graph(file_name, 6)
        if choice == "f":
            break


def pop_data():
    """Controls flow to secondary Population Data
    menu and graphing functions."""

    file_name = "PopChange.csv"

    while True:
        choice = letter_option("pop")
        if choice == "a":
            print_graph(file_name, 4)
        if choice == "b":
            print_graph(file_name, 5)
        if choice == "c":
            print_graph(file_name, 6)
        if choice == "d":
            break


def main_option():
    """Prints the program main menu, then requests
    user input for menu selection.

    :return: user_option: (str) integer 1, 2, or 3
    """

    print("Select the file you want to analyze:")
    print("1. Population Data")
    print("2. Housing Data")
    print("3. Exit the program")

    while True:
        try:
            user_option = int(input())
            if user_option in {1, 2, 3}:
                return user_option
            print("Invalid option.")
        except ValueError:
            print("Enter a valid numerical option.")


# main program loop
if __name__ == '__main__':
    while True:
        option = main_option()
        if option == 1:
            pop_data()
        if option == 2:
            house_data()
        if option == 3:
            print("******** Thank you for using"
                  " the Data Analysis App ********")
            break
