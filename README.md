
Create a simple Android mobile application written in Java / Kotlin which loads a list of movies and shows detailed information about each movie. Each list item should display the movie’s name, some basic information (up to you) and a small poster thumbnail. Clicking on any list item should open a new screen with detailed information about the movie (using the second API call). You can choose what to display on the detail screen, but it should include piece of data which is not available in the list and is only available through the seconds API call. Also include the movie name and the poster. 

The API endpoint

Documentation - http://www.omdbapi.com

Loading of movies by name
 http://www.omdbapi.com/?s=Home&apikey=94a221d (“Home is an example movie name”)
Unless you want to do the bonus task, you can fix the url to whatever search query you want but make sure the endpoint returns more than 1 movie.  

Loading of a movie detail by ID 
http://www.omdbapi.com/?i=tt0099785&apikey=94a221d (“tt0099785” is an example ID)

API key is required to be able to access the resource. You can use the example key “94a221d” or register on the website for free and get your own key, in case the example key does not work.
 
Bonus points

Include option for the user to write the name of the movie in a text field. After confirming, the app shows a list of movies based on the user input.
Support orientation
Use Clean Architecture
Use Material design
