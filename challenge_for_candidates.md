# Android Developer - Challenge

The challenge we're giving you includes a coding part and an open question.
In the coding part we're interested in seeing how you solve a technical problem in an existing app by coding.
In the open question part we are interested in how you generally approach a problem.

We expect the challenge to require a few hours of work.
However, it’s fine if you don’t manage to finish all the tasks.
We’d rather see good (and working) solutions rather then things that we’re done half way because of time pressure.
If you feel like you had to compromise on some tasks due to time limit, please do mention it.

If you have any questions, please don’t hesitate to ask us on Skype.

# Coding Challenge

The starting point for this challenge is an example app called 'android-boilerplate'
that we often use internally in Mobisol as a reference to what we consider good Android development practices.
You can find the code for it on Github:
https://github.com/ribot/android-boilerplate/

What the app currently does is to fetch a list of people profiles from a server and display
the names and the defined color of those profiles in a list (see attachment 1).
Those profiles are called "Ribots" in the app.
You can see the json that is returned from the server in the attachted 'ribots.json’ file.

Your task is to adapt the existing app as follows:

- Some profiles contain an avatar image. If they do, that image should be displayed in the
profile list instead of the color that is defined in the 'hexColor' attribute.
See attachment 2 that shows what this could look like.

- Clicking on a profile should open a new "Ribot" screen that shows more information on
that specific profile. This information includes: The avatar of the person if available, their full name,
email, biography and date of birth in a localized format. This data is available as part of the Profile class.
You are free to design this screen in whatever way you find suitable, however we are providing
for this screen attachment 3, an example layout from the material design guideline.
You can use this reference as a guideline for the screen you build, but you don’t have to produce the same design.

- Bonus: Allow clicking on the email address of the person which will then open an email app and
allow sending an email to that person.

You can submit your solution as a zipped Android Studio project, or by forking the
'android-boilerplate' repository and sending us a link to your fork which includes the solution.

# Open Question

Imagine an Android app that is used to send images, taken by a phone and sent to a backend server.
The mobile phone is connected via a slow and unreliable GPRS connection
and at least 3 images (2 containing a photographed document and one an image of a person)
have to be transferred to the server.

What would be your approach to solve this task?
You are not expected to deliver a working program but rather to explain how you would approach solving this problem.

