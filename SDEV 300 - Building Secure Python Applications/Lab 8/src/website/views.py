"""
Author: Luis Moreno
Date: December 9, 2022

Stores routes for the following html files: index,
guides, records, and profile.
"""
# pylint: disable=no-member
from flask import Blueprint, render_template, request, flash
from flask_login import login_required, current_user
from .models import Times
from . import db, date

views = Blueprint('views', __name__)


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
                 Record('3x3x3 One-Handed', 6.20, 'Max Park', 'United States', 2022)]


@views.route('/')
def home():
    """Route for the website home page.

    :return: renders index.html
    """
    return render_template("index.html", user=current_user, datetime=date())


@views.route('/guides')
def guides():
    """Route for the website guides page.

    :return: renders guides.html
    """
    return render_template("guides.html", user=current_user, datetime=date())


@views.route('/records')
def records():
    """Route for the website records page.

    :return: renders records.html
    """
    return render_template("records.html", user=current_user,
                           records=world_records, datetime=date())


@views.route('/profile', methods={'GET', 'POST'})
@login_required
def profile():
    """Accessible only when logged-in, route for the website
    profile page. Includes option to input and add text to
    database.db file.

    :return: renders profile.html
    """

    # adds data to user/profile by typing in text area and clicking button
    if request.method == 'POST':
        time = request.form.get('time')

        if time:
            new_time = Times(data=time, user_id=current_user.id)
            db.session.add(new_time)
            db.session.commit()
        else:
            flash('Input your time in the text box before clicking submit.', category='error')

    return render_template("profile.html", user=current_user, datetime=date())
