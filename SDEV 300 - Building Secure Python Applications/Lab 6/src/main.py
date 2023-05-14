"""
Author: Luis Moreno
Date: November 29, 2022

Lab 6:
This program creates and runs a simple webpage using Flask,
HTML and CSS.
"""

from datetime import datetime
from flask import Flask, render_template, flash

app = Flask(__name__)


class User:
    """
    Website users create a profile and the user information
    is used to create a User object. Fields include name,
    age, nationality, and a list of their cube solve times.
    """

    def __init__(self):
        self.time = 0

    def add_time(self, time):
        self.time = time


class Record:
    """
    Represents a world record for a given puzzle-solving competition.
    This includes the record holder's name and country of origin,
    the official world record time, the year the record was set,
    and the name of the category for the record.
    """

    def __init__(self, category, time, name, country, year):
        self.category = category
        self.time = f'{time:.2f}'
        self.name = name
        self.country = country
        self.year = year

    # setters
    def set_cat(self, category):
        """Sets record category.

        :param category: (str) world cube association record category
        """

        self.category = category

    def set_time(self, time):
        """Sets record time formatted to two decimal points.

        :param time: (float) time in seconds
        """

        self.time = f'{time:.2f}'

    def set_name(self, name):
        """Sets record holder name.

        :param name: (str) name of record holder
        """

        self.name = name

    def set_country(self, country):
        """Sets country of record holder.

        :param country: (str) record holder country of residence
        """

        self.country = country

    def set_year(self, year):
        """Sets record year.

        :param year: (int) year that record was set
        """

        self.year = year


# list of world records represented by Record objects
# this list can be expanded to include more records to pass to records.html
world_records = [Record('3x3x3 Single', 3.47, 'Yusheng Du', 'China', 2018),
                 Record('3x3x3 Average', 4.86, 'Max Park', 'United States', 2022),
                 Record('3x3x3 Average', 4.86, 'Tymon Kolasinski', 'Poland', 2022),
                 Record('3x3x3 Blindfolded', 14.51, 'Tommy Cherry', 'United States', 2022),
                 Record('3x3x3 One-Handed', 6.20, 'Max Park', 'United States', 2022),]


def date():
    """Format of date is [Month Day, Year Hour:Minute].
    Using this to return the formatted date string allows
    the date and time to update everytime a template is rendered.

    :return: (str) formatted datetime
    """

    return datetime.now().strftime('%b %d, %Y - %H:%M')


@app.route("/")
def index():
    """Renders index.html"""

    return render_template("index.html", datetime=date())


@app.route("/guides/")
def guides():
    """Renders guides.html"""

    return render_template("guides.html", datetime=date())


@app.route("/records/")
def records():
    """Renders records.html"""

    return render_template("records.html", datetime=date(), records=world_records)


@app.route("/login/")
def login():
    """Renders login.html"""

    return render_template("login.html", datetime=date())


@app.route("/register/")
def register():
    """Renders register.html"""

    return render_template("register.html", datetime=date())


if __name__ == '__main__':
    app.run(debug=True)
