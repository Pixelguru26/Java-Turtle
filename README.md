# Java Turtle

A turtle implementation for Java.

Turtle is an excellent and time-honored tool for teaching kay concepts to new programmers. It's an essential part of my curriculum, but when I attempted to integrate it into a Java course, I found it impossible to procure a ready-to-use implementation online.

So I made one.

If you have any feature requests, feel free to contact me or (preferably) open an issue for it. I'll be happy to help tinker with whatever needs changing.

# Installation

This package is intended to be a drop-in solution for Visual Studio Code with the Extension Pack for Java. Other systems may not be so well supported until suggestions are made.
To install, extract the source code into a folder titled "Turtle" in your source directory. Most compilers should support this as well. It should be that simple. Contact me if it isn't.

# Documentation

Note: Javadoc documentation is included for the core features of the package. This is a secondary reference to act as a cheat sheet.

## Basic Usage

Once included in a project, either as a package or a compiled jar, basic turtle setup looks like this:

	import Turtle.*; // import all components of the turtle library

	public class main {
		public static void main(String[] args) {
			TurtleWorld world = new TurtleWorld(1000,800); // create a new turtle window 1000px wide and 800px tall
			Turtle t = world.turtle(); // create a new turtle in the world
		}
	}

After that, you're able to use any of the turtle's movement and drawing methods.

## Turtle commands

For this section, assume that `t` is the turtle you wish to control.

+ `t.backward(double dist);` : Moves the turtle `dist` pixels backwards according to its current heading.
+ `t.beginFill();` : Begins the construction of a polygon. All points the turtle moves to past this statement will be registered as corners of the polygon.
+ `t.color(Color c);` : Sets the turtle's drawing color.
+ `t.dot(int r);` : Creates a dot at the turtle's location with a radius of `r` pixels. `r` is optional.
+ `t.endFill();` : Ends the construction of a polygon and renders it to the canvas. It will be rendered in front of all objects drawn up to this point.
+ `t.fillColor(Color c);` : Sets the color to be used when filling polygons.
+ `t.flush();` : Renders the turtle's last movements to the canvas and waits `t.delay` milliseconds. Called at the end of every movement operation.
+ `t.forward(double dist);` : Moves the turtle `dist` pixels forward according to its current heading.
+ `t.getColor();` : Retrieves the turtle's current drawing color.
+ `t.getFillColor(Color c);` : Retrieves the turtle's current fill color for polygons.
+ `t.getImage();` : Retrieves the turtle's current image or null if there is none.
+ `t.getState();` : Retrieves the turtle's current state information in its entirety.
+ `t.getWidth();` : Retrieves the turtle's line drawing width in pixels.
+ `t.getWorld();` : Retrieves the world which the turtle currently occupies.
+ `t.getX();` : Retrieves the turtle's current x coordinate.
+ `t.getY();` : Retrieves the turtle's current y coordinate. Note that y coordinates are measured down from the top of the window.
+ `t.heading();` : Retrieves the turtle's current heading in degrees.
+ `t.left(double angle);` : Rotates the turtle counter-clockwise by `angle` degrees.
+ `t.loadImage(String location);` : Attempts to load an image from the given relative path and set it as the turtle's current image. Returns `true` if successful, otherwise `false`.
+ `t.move(double dx, double dy);` : Moves the turtle a given distance `dx` pixels to the right and `dy` pixels down.
+ `t.pendown();` : Lowers the turtle's pen, allowing it to draw.
+ `t.penup();` : Lifts the turtle's pen, stopping it from drawing.
+ `t.position();` : Retrieves the turtle's current position as a 2-element array.
+ `t.pushState();` : Copies the turtle's current state to its last state, readying it for modification.
+ `t.reset();` : Resets the turtle to default values.
+ `t.right(double angle):` : Rotates the turtle clockwise by `angle` degrees.
+ `t.setImage(BufferedImage image);` : Sets the turtle's current image which can be drawn and will be used as the turtle's avatar. Setting to `null` uses the default avatar (`turtle.png`)
+ `t.setState(TurtleState v);` : Sets the turtle's current state information without drawing anything.
+ `t.setWidth(double v);` : Sets the turtle's line drawing width to `v` pixels.
+ `t.slide(double forward, double sideways);` : Moves the turtle forward sideways relative to its current position and heading. Measured in pixels.
+ `t.strafeLeft(double dist);` : Moves the turtle at a 90 degree counter-clockwise angle from its current heading by `dist` pixels.
+ `t.strafeRight(double dist);` : Moves the turtle at a 90 degree clockwise angle from its current heading by `dist` pixels.
+ `t.teleport(double x, double y);` : Teleports the turtle to the location given by `(x, y)`
+ `t.unloadImage();` : Resets the turtle's image to `null`.

## Turtle World Methods

+ `world.addTurtle(Turtle t);` : Adds an existing turtle to this world, removing it from all others.
+ `world.clear(Color c);` : Clears the canvas to the given color. `c` is optional.
+ `world.remTurtle(Turtle t);` : Removes a turtle from this world. The turtle will no longer be able to draw.
+ `world.setSize(int w, int h);` : Sets the world and its canvas to the given dimensions in pixels. Content is preserved.
+ `world.turtle();` : Creates a new default turtle in this world.

## Other

Turtle includes a system to poll for keyboard and mouse information. However, this has only limited support at this time and little documentation. Here are the basic functions:

+ `world.keyboard.keyDown(int keyCode);` : Returns `true` if the key is currently pressed. Keycodes provided by `java.awt.KeyEvent`
+ `world.keyboard.poll();` : Updates keyboard information.
+ `world.mouse.buttonDown(int button);` : Returns `true` if the button is currently pressed. Button codes provided by `java.awt.MouseEvent`
+ `world.mouse.getPosition();` : Returns a point indicating the current position of the mouse cursor.
+ `world.mouse.poll();` : Updates mouse information.
