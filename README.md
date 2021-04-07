# Asteroid Radar

Asteroid Radar is an app to view the asteroids detected by [NASA](https://api.nasa.gov/) 
that pass near Earth, you can view all the detected asteroids in a period of time, their data 
(Size, velocity, distance to Earth) and if they are potentially hazardous.

The app is consists of two screens: A Main screen with a list of all the detected asteroids 
and a Details screen that is going to display the data of that asteroid once it´s selected 
in the Main screen list. The main screen will also show the NASA image of the day to make 
the app more striking.

## Instructions

1. Go to the [NASA URL] (https://api.nasa.gov/) and scroll down until you find the 
form *Generate API Key*

2. Fill the required fields, click the Sigup button and your are going to get your API key
instantly.

3. Go to _com/udacity/asteroidradar/utils/Constants.kt_ and assign you key to the variable _API_KEY_