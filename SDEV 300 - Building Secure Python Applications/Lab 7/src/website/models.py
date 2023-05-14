"""
Defines database models for users and user solve times.
"""
from flask_login import UserMixin
from . import db


class Times(db.Model):
    """
    Table holds text submissions from profile.html textarea.
    There is no validation or formatting, but this can be
    added later to represent puzzle solve times.
    """

    # define columns for table
    id = db.Column(db.Integer, primary_key=True)
    data = db.Column(db.String(10))

    # associate time with user by passing id of the user
    user_id = db.Column(db.Integer, db.ForeignKey('user.id'))


class User(db.Model, UserMixin):
    """
    Table holds information for user accounts. This includes
    a username, hashed password, and ID. Other fields can
    be added if necessary.
    """

    # define columns for table
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(20), unique=True)
    password = db.Column(db.String(150))

    # allows access to times the user has created
    times = db.relationship('Times')
