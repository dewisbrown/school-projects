"""
Author: Luis Moreno
Date: December 7, 2022

Lab 7:
This website builds upon the Lab 6 website, adding routes for
login, logout, and user profile.

For this site, the profile page is only accessible when a
user is logged-in. User information is stored in a database file
using SQLAlchemy. This allows users to log in and out, as well as
input text information on the profile webpage.

The create_app() function configures the flask app, which
is then run from this script.
"""
from website import create_app

app = create_app()

if __name__ == '__main__':
    # 'debug=True' allows server to update as python code is altered
    app.run()
