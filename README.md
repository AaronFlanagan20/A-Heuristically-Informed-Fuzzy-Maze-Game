# A-Heuristically-Informed-Fuzzy-Maze-Game

Developer: Aaron Flanagan - G00330035

Github: https://github.com/AaronFlanagan20/A-Heuristically-Informed-Fuzzy-Maze-Game

# Background
This project was developed for my 4th year of Software Development module Artificial Intelligence. It is based on the ZX81 Mazogs game developed in 1981 found [here](http://www.zx-gaming.co.uk/games/mazogs/default.htm.). It combines a maze generation algorithm, with fuzzy logic and some uninformed and informed search algoritms. The maze generation algorithm used is called Prim's. Examples found [here](https://en.wikipedia.org/wiki/Maze_generation_algorithm#Randomized_Prim.27s_algorithm) and [here](http://jonathanzong.com/blog/2012/11/06/maze-generation-with-prims-algorithm). The JFuzzyLogic API used is found [here](http://jfuzzylogic.sourceforge.net/html/manual.html#fcl) this was used for it's fuzzy constraint language. The project brief has been added to this repository also under [aiAssignment2016.pdf](aiAssignment2016.pdf).

# Gameplay
Your goal is to find the exit and avoid enemies. A number of weapons are available to pick up tp fight of enemies and it includes bombs to clear out some walls. The bombs use a depth limited depth-first search to destroy n rows and columns to the depth specified.

# Images
The theme of the original Mazogs ZX81 game has been used for this assignment. All images, including walls, enemies, the sword and all directions of the players movement with and without the sword in hand have been used from the original game.

# Commands
* Forward: Up arrow key or W key
* Backwards: Down arrow key or S key
* Right: Right arrow key or D key
* Left: Left arrow key or A key
* Z: Toggle zoom in and zoom out

To pick up a weapon or use a bomb walk into the wall were the object is. Same rule applies once the exit is found.

# Features
* Player: You move around as a player. The player has various images for all directions, and images for when a weapon is being held.
* Enemies: Enemies all have thier own thread to control their movement. They make use of two brute force search algorithms (Depth-first search, Breadth-first search) to move independently. Enemies are the spiders and they have two images which simulate the spider bouncing up and down.
* Weapons: 3 weapons are available, a toothpick, sword and spider spray. Each have a determined power for the fuzzy logic Fighting class, a toothpicks power ranges between 1-3, sword is 4-7 and spiderspray 7-10. The more powerful the weapon(closer to 10) the more damage is dealt to enemies.
* Health: Your player has a health bar that ranges from 25-100, once you kill an enemy your health is replenished but if you dont you can die depending on the power of your weapon and the enemy. This is all computed in the Fuzzy logic class [Fighting.java](https://github.com/AaronFlanagan20/A-Heuristically-Informed-Fuzzy-Maze-Game/blob/master/src/ie/gmit/sw/fuzzylogic/Fighting.java)
* Steps: Your player has a finite number of steps before he gets tired and dies. You must find the exit before this happens.

# How to run this project
First you can either download the zip file of this repository or clone it via git. You can then either open it up and run it in eclipse as a java project or open a command prompt and change directory to the project folder or jar file and run the command java -cp ./Fuzzy-Maze-Project.jar ie.gmit.sw.runner.Runner
