"""
Author: Luis Moreno
Date: December 9, 2022

Lab 8:
This website builds upon the Lab 7 website by adding an option
to update a user password and logging failed login attempts.

The reset password function was added to a new HTML file
with a form similar to the registration HTML file. A username
request is made on that page so that the user can be found in
the database. The password is updated if it meets all criteria.

The logger is configured when the app is configured in the
create_app() function. auth.py imports the logger and is
able to log errors to 'app.log'.
"""
from website import create_app

app = create_app()

if __name__ == '__main__':
    # 'debug=True' allows server to update as python code is altered
    app.run()
