# Introduction

This program is meant as a new and improved version of the basic text editor NotePad with the added feature of *Notes* and *Pads*.
I personally find my self using the default Notepad extensively for my day-to-day use. One of the main issues that I had was that you couldn't easily transition between different notes. 

When making this project, I wanted to implement best practices and many of the design principles and patterns that I have learned recently.

# Program Structure

**Model:** 

There are two main components; Notes and Pads. Notes contain text and Pads contain a list of notes and the currently selected note.

NotePad is a Singleton class with contains a selected Pad and an arbitrarly sized list of Pads.