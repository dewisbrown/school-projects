"""
This script contains routes for the following html files:
login, logout (redirects to login), register.

These webpages allow a user to create and use an account for this website.
"""
# pylint: disable=no-member
import string
from flask import Blueprint, render_template, \
    redirect, url_for, request, flash
from werkzeug.security import generate_password_hash, check_password_hash
from flask_login import login_user, login_required, logout_user, current_user
from . import db, date
from .models import User

auth = Blueprint('auth', __name__)


def is_complex(password):
    """Iterates through password string checking for uppercase,
    lowercase, number, and special character. Returns boolean
    whether password meets criteria.

    :param password: (str) user password from form input
    :return: (bool) True if meets password criteria: one character
    of each: uppercase, lowercase, number, special character.
    """

    upper = False
    lower = False
    digit = False
    spec = False

    for character in password:
        if character.isupper():
            upper = True
        if character.islower():
            lower = True
        if character.isdigit():
            digit = True
        if character in string.punctuation:
            spec = True

    if upper and lower and digit and spec:
        return True

    return False


@auth.route('/login', methods={'GET', 'POST'})
def login():
    """User can log in if form input fields match
    database user information.

    :return: renders login.html, redirects to profile.html
    if successful login
    """
    if request.method == 'POST':
        username = request.form.get('username')
        password = request.form.get('password')

        # searches database for username
        user = User.query.filter_by(username=username).first()

        # if a user with that username is found, and the password
        # entered matches the database password, login successful
        if user:
            if check_password_hash(user.password, password):
                flash("Login successful!", category='success')
                login_user(user, remember=True)
                return redirect(url_for('views.profile'))
            flash("Password is incorrect! Try again.", category='error')
        else:
            flash("Username does not exist.", category='error')

    return render_template("login.html", user=current_user, datetime=date())


@auth.route('/logout')
@login_required
def logout():
    """Route is reached only when user is already
    logged in. The user is then logged out.

    :return: redirect to login.html
    """

    flash('Logout successful!', category='success')
    logout_user()
    return redirect(url_for("auth.login"))


@auth.route('/register', methods={'GET', 'POST'})
def register():
    """A user account is created and added to database.db if
    form input fields meet criteria.

    :return: render register.html, redirects to login.html
    if registration is successful
    """

    # collects information from registration form
    if request.method == 'POST':
        username = request.form.get('username')
        password1 = request.form.get('password1')
        password2 = request.form.get('password2')

        # user is used to check if username already exists (first if statement below)
        user = User.query.filter_by(username=username).first()

        # flashes error/success message after submission
        if user:
            flash('An account with this username already exists.', category='error')
        elif len(username) < 4:
            flash('Username must contain more than 4 characters.', category='error')
        elif len(username) > 20:
            flash('Username must contain less than 20 characters.', category='error')
        elif password1 != password2:
            flash('Passwords do not match.', category='error')
        elif len(password1) < 12:
            flash('Password must be greater than 12 characters.', category='error')
        elif not is_complex(password1):
            flash('Password must contain one character of each of the following: '
                  'uppercase, lowercase, number, special.', category='error')
        else:
            new_user = User(username=username,
                            password=generate_password_hash(password1, method='sha256'))
            db.session.add(new_user)
            db.session.commit()

            flash('Account creation successful!', category='success')
            return redirect(url_for('auth.login', user=current_user, datetime=date()))

    return render_template("register.html", user=current_user, datetime=date())
