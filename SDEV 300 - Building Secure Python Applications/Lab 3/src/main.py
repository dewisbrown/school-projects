"""
Author: Luis Moreno
Date: November 08, 2022

Lab 3:
This is a command line menu-driven python application providing users
with the ability to search and display U.S. State Capital, population and flowers.

The images must be kept in same directory as main.py to display properly.
"""
import matplotlib.pyplot as plt


class State:
    """Represents a U.S. state with the following fields:
    name, capital, population, flower, flower image."""

    def __init__(self, name, capital, population, flower, url):
        """Constructor for State object.

        :param name: (str) name of state
        :param capital: (str) capital of state
        :param population: (int) population of state
        :param flower: (str) state flower
        :param url: (str) url for image in folder
        """

        self.name = name
        self.capital = capital
        self.population = population
        self.flower = flower
        self.url = url

    def __str__(self):
        """Overriden __str__ method for State object.

        :return: (str) State object with all fields printed to console
        """

        return f"[State: {self.name}, Capital: {self.capital}, Population: {self.population}" \
               f", State Flower: {self.flower}]"

    def set_population(self, pop):
        """Updates the population field of a State object.

        :param pop: (int) new population to set for selected state
        """

        self.population = pop

    def print_img(self):
        """Uses the image url of a State object to display
        the state flower image in a matplot figure.
        """

        try:
            # read image
            img = plt.imread(self.url)
            # create figure
            fig = plt.figure()
            fig.suptitle(self.name + ": " + self.flower)
            # creates axes for figure
            ax_fig = fig.subplots()
            ax_fig.imshow(img)
            ax_fig.axis('off')
            # display image
            plt.show()
        except FileNotFoundError:
            print("Image load unsuccessful.")


# list of states (already alphabetized)
state_names = ["Alabama", "Alaska", "Arizona", "Arkansas",
               "California", "Colorado", "Connecticut", "Delaware",
               "Florida", "Georgia", "Hawaii",
               "Idaho", "Illinois", "Indiana", "Iowa",
               "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland",
               "Massachusetts", "Michigan",
               "Minnesota", "Mississippi", "Missouri", "Montana",
               "Nebraska", "Nevada", "New Hampshire", "New Jersey",
               "New Mexico", "New York",
               "North Carolina", "North Dakota", "Ohio", "Oklahoma",
               "Oregon", "Pennsylvania", "Rhode Island",
               "South Carolina", "South Dakota",
               "Tennessee", "Texas", "Utah", "Vermont", "Virginia",
               "Washington", "West Virgina", "Wisconsin", "Wyoming"]

# list of state flowers
state_flowers = ["Camellia", "Forget Me Not", "Saguaro cactus blossom",
                 "Apple blossom", "Golden poppy", "Rocky Mountain columbine",
                 "Mountain laurel", "Peach blossom", "Orange blossom", "Cherokee rose",
                 "Pua Aloalo", "Syringa", "Violet", "Peony", "Wild rose",
                 "Sunflower", "Goldenrod", "Magnolia", "White pine cone and tassel",
                 "Black-eyed susan", "Mayflower", "Apple blossom",
                 "Pink & White Lady slipper", "Magnolia", "White Hawthorn Blossom",
                 "Bitterroot", "Goldenrod", "Sagebrush", "Purple lilac", "Violet",
                 "Yucca", "Rose", "Dogwood", "Wild prairie rose", "Red Carnation",
                 "Mistletoe", "Oregon Grape", "Mountain Laurel", "Violet",
                 "Yellow Jessamine", "American Pasque", "Iris", "Bluebonnet",
                 "Sego Lily", "Red Clover", "American Dogwood", "Coast Rhododendron",
                 "Rhododendron", "Wood Violet", "Indian Paintbrush"]

# list of populations
state_populations = [5039877, 732673, 7276316, 3025891, 39237836,
                     5812069, 3605597, 1003384, 21781128,
                     10799566, 1441553, 1900923, 12671469, 6805985,
                     3193079, 2934582, 4509394, 4624047, 1372247,
                     6165129, 6984723, 10050811, 5707390, 2949965,
                     6168187, 1104271, 1963692, 3143991,
                     1388992, 9267130, 2115877, 19835913, 10551162,
                     774948, 11780017, 3986639, 4246155,
                     12964056, 1095610, 5190705, 895376, 6975218, 29527941,
                     3337975, 645570, 8642274, 7738692, 1782959,
                     5895908, 578803]

# list of capitals
state_capitals = ["Montgomery", "Juneau", "Phoenix", "Little Rock",
                  "Sacramento", "Denver", "Hartford", "Dover",
                  "Tallahassee", "Atlanta", "Honolulu", "Boise",
                  "Springfield", "Indianapolis", "Des Moines",
                  "Topeka", "Frankfort", "Baton Rouge", "Augusta",
                  "Annapolis", "Boston", "Lansing", "Saint Paul",
                  "Jackson", "Jefferson City", "Helena", "Lincoln",
                  "Carson City", "Concord", "Trenton",
                  "Santa Fe", "Albany", "Raleigh", "Bismarck",
                  "Columbus", "Oklahoma City", "Salem",
                  "Harrisburg", "Providence", "Columbia", "Pierre",
                  "Nashville", "Austin", "Salt Lake City", "Montpelier",
                  "Richmond", "Olympia", "Charleston", "Madison", "Cheyenne"]

# list of image urls
pic_urls = ['alabama.jpg', 'alaska.jpg', 'arizona.jpg', 'arkansas.jpg',
            'california.jpg', 'colorado.jpg', 'connecticut.jpg', 'delaware.jpg',
            'florida.jpg', 'georgia.jpg', 'hawaii.jpg', 'idaho.jpg', 'illinois.jpg',
            'indiana.jpg', 'iowa.jpg', 'kansas.jpg', 'kentucky.jpg', 'louisiana.jpg',
            'maine.jpg', 'maryland.jpg', 'massachusetts.jpg', 'michigan.jpg',
            'minnesota.jpg', 'mississippi.jpg', 'missouri.jpg', 'montana.jpg',
            'nebraska.jpg', 'nevada.jpg', 'new hampshire.jpg', 'new jersey.jpg',
            'new mexico.jpg', 'new york.jpg', 'north carolina.jpg', 'north dakota.jpg',
            'ohio.jpg', 'oklahoma.jpg', 'oregon.jpg', 'pennsylvania.jpg', 'rhode island.jpg',
            'south carolina.jpg', 'south dakota.jpg', 'tennessee.jpg', 'texas.jpg',
            'utah.jpg', 'vermont.jpg', 'virginia.jpg', 'washington.jpg', 'west virgina.jpg',
            'wisconsin.jpg', 'wyoming.jpg']

# used to generate list of image names
# pic_urls = []
# for i in range(50):
# (indent) pic_url = state_names[i].lower() + ".jpg"
# (indent) pic_urls.append(pic_url)

# list of State objects with fields: name, capital, population, flower, flower image url
us_states = []
for i in range(50):
    state_name = state_names[i]
    state_capital = state_capitals[i]
    state_population = state_populations[i]
    state_flower = state_flowers[i]
    state_flower_url = pic_urls[i]

    us_states.append(State(state_name, state_capital,
                           state_population, state_flower, state_flower_url))


def get_int(message):
    """Returns a positive integer, notifying user if input is invalid.

    :param message: (str) Prints message for input request
    :return: (int) positive integer
    """
    while True:
        try:
            user_int = int(input(message))
            if user_int > 0:
                return user_int
            print("Enter a positive numerical value.")
        except ValueError:
            print("Enter a valid numerical value.")


def get_state_str(message):
    """Requests and returns user input if matches state name from us_states list

    :param message: (str) supplied message requesting user input
    """

    while True:
        user_string = input(message)
        user_string = user_string.lower()
        for obj in range(50):
            valid_state = us_states[obj].name.lower()
            if user_string == valid_state:
                return user_string
        print(user_string + " is not a valid U.S. state.")


def search_state():
    """Find and return state from list of U.S. States

    :return: (State) object from us_states list
    """

    state = get_state_str("What state would you like to find? ")
    state = state.lower()

    for unit in range(50):
        valid_us_state = us_states[unit].name.lower()
        if state == valid_us_state:
            return us_states[unit]
    return State("None", "None", 1, "None", "none.jpg")


def create_graph():
    """Displays bar graph of 5 most populous states in list."""

    # sorted by population (x: x.population)
    most_pop = sorted(us_states, key=lambda x: x.population, reverse=True)
    bar_graph_pops = []
    bar_graph_names = []
    for k in range(5):
        bar_graph_pops.append(most_pop[k].population)
        bar_graph_names.append(most_pop[k].name)

    try:
        fig = plt.figure()

        # Title and label axes
        fig.suptitle("Top 5 Most Populous States")
        plt.ylabel("Population (10 millions)")

        # create and display bar graph
        plt.bar(bar_graph_names, bar_graph_pops)
        plt.show()
        plt.close()
    except FileNotFoundError:
        print("Figure failed to generate.")


def update_pop():
    """Selects and updates state population to user input."""

    the_state = search_state()
    old_pop = the_state.population
    the_state.set_population(get_int("What would like to update the population to? "))
    print("You changed the population of " + the_state.name +
          " from " + str(old_pop) + " to " + str(the_state.population))


def display_flower():
    """Selects and prints state info, then displays state flower image."""

    the_state = search_state()
    print(the_state)
    the_state.print_img()


def menu():
    """Prints the program main menu"""

    print("\n\t\t Main Menu")
    print("----------------------------")
    print("1. Display all U.S. States in Alphabetical order along with "
          "the Capital, State Population, and Flower")
    print("2. Search for a specific state and display the appropriate Capital name, "
          "State Population, and an image of the associated State Flower.")
    print("3. Provide a Bar graph of the top 5 populated States showing their overall population")
    print("4. Update the overall state population for a specific state.")
    print("5. Exit the program")


def menu_select(choice):
    """Processes each main menu selection with given input choice.

    :param choice: (int) represents selection for main menu
    """

    if choice == 1:
        for j in range(50):
            print(us_states[j])
    elif choice == 2:
        display_flower()
    elif choice == 3:
        create_graph()
    elif choice == 4:
        update_pop()
    else:
        print("Enter a valid menu option.")


if __name__ == "__main__":
    print("\nWelcome to the Lab3 App! Choose an option from the following menu.")

    # main program loop
    while True:
        menu()
        user_choice = get_int("")
        if user_choice == 5:
            print("\nThank you for using this app! Goodbye!")
            break
        menu_select(user_choice)
