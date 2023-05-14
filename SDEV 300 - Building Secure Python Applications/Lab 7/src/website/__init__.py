"""
Allows 'website' folder to be considered
a python package. In 'main.py', functions from
this file can be imported and used.
"""
from datetime import datetime
from os import path
from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from flask_login import LoginManager

db = SQLAlchemy()
DB_NAME = "database.db"


def date():
    """Format of date is [Month Day, Year Hour:Minute].
    Using this to return the formatted date string allows
    the date and time to update everytime a template is rendered.

    :return: (str) formatted datetime
    """

    return datetime.now().strftime('%b %d, %Y - %H:%M')


def create_app():
    """Configures the following for the flask app:
    secret_key, creates and links database file with the app,
    registers blueprints, configures login manager.

    :return: (Flask app) Configured and ready to run
    """
    app = Flask(__name__)
    app.config['SECRET_KEY'] = 'key'
    app.config['SQLALCHEMY_DATABASE_URI'] = f'sqlite:///{DB_NAME}'
    db.init_app(app)

    # register blueprints
    from .views import views
    from .auth import auth

    app.register_blueprint(views, url_prefix='/')
    app.register_blueprint(auth, url_prefix='/')

    # ensures database models are loaded/defined before being created
    from .models import User, Times

    if not path.exists('website/' + DB_NAME):
        with app.app_context():
            db.create_all()

    login_manager = LoginManager()

    # redirects user to login page if not logged-in
    login_manager.login_view = 'auth.login'
    login_manager.init_app(app)

    # configures how to find/load a user
    @login_manager.user_loader
    def load_user(id):
        """Used to maintain current user login.

        :param id: (int) unique user id number
        :return: (User) from database
        """

        return User.query.get(int(id))

    return app
