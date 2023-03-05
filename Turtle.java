package Turtle;

import java.awt.*;
import java.util.ArrayList;

/**
 * A class for turtles which inhabit and draw on a turtle world.
 * @see TurtleWorld
 */
public class Turtle {
	/** The world this turtle is registered to. */
	protected TurtleWorld world;
	/** The previous readable state of the turtle. */
	private TurtleState lastState;
	/** The current readable state of the turtle. */
	private TurtleState state;

	/** Milliseconds between actions, used only in flush(); */
	public int delay;

	/** The fill color to be used for polygons. */
	private Color fillColor;
	/** A variable indicating that a polygon is currently being constructed. */
	private boolean polygonMode;
	/** A list of x coordinates to be used in the points of the next polygon. */
	private ArrayList<Double> polygonXPoints;
	/**	A list of y coordinates to be used in the points of the next polygon. */
	private ArrayList<Double> polygonYPoints;

	/**
	 * Creates a new turtle in the given world with default values.
	 * Intended to be called from a {@link TurtleWorld} object.
	 * @param world The world to place the turtle in.
	 */
	public Turtle(TurtleWorld world) {
		polygonXPoints = new ArrayList<Double>();
		polygonYPoints = new ArrayList<Double>();
		reset(world);
	}

	/**
	 * Resets a turtle according to a given turtle world, transferring it
	 * (but not its drawings or its data) in the process.
	 * Draws nothing.
	 * @param world The new world to place the turtle into.
	 */
	public void reset(TurtleWorld world) {
		if (this.world != null) {
			this.world.turtles.remove(this);
		}
		world.turtles.add(this);
		this.world = world;
		state = new TurtleState(world);
		lastState = new TurtleState(state);

		delay = 1;

		fillColor = Color.white;
		polygonMode = false;
		polygonXPoints.clear();
		polygonYPoints.clear();
	}
	/**
	 * Resets the turtle according to its current turtle world.
	 * Draws nothing.
	 */
	public void reset() {
		reset(world);
	}

	/**
	 * Pushes a new state to the turtle without drawing between the two.
	 * Draws nothing.
	 * @param v The new state of the turtle.
	 */
	public void setState(TurtleState v) {
		lastState.copy(state);
		state.copy(v);
	}
	/**
	 * @return {@link TurtleState} The current state of the turtle, containing key alterable information.
	 */
	public TurtleState getState() {
		return new TurtleState(state);
	}

	/**
	 * Pushes polygon points and draws lines between the current and previous states.
	 * Delays current thread, but will fail to do so without exception if interrupted.
	 */
	public void flush() {
		if (polygonMode && (state.x != lastState.x || state.y != lastState.y)) {
			polygonXPoints.add(state.x);
			polygonYPoints.add(state.y);
		}
		if (state.penState && world != null) {
			Graphics2D g2 = world.canvas.g2;
			Color tmpColor = g2.getColor();
			Stroke tmpStroke = g2.getStroke();
			g2.setColor(state.color);
			g2.setStroke(state.stroke);
			g2.drawLine((int)(lastState.x+0.5), (int)(lastState.y+0.5), (int)(state.x+0.5), (int)(state.y+0.5));
			g2.setColor(tmpColor);
			g2.setStroke(tmpStroke);
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// nothing, this is fine.
			}
			//state.world.update(state.world.getGraphics());
		}
	}

	// ==========================================

	/**
	 * Moves the turtle forwards a specified number of pixels.
	 * @param dist The distance, in pixels, to move.
	 */
	public void forward(double dist) {
		lastState.copy(state);
		state.x += dist * Math.cos(Math.toRadians(state.theta));
		state.y += dist * Math.sin(Math.toRadians(state.theta));
		flush();
	}
	/**
	 * Moves the turtle forwards 100 pixels.
	 */
	public void forward() {
		forward(100);
	}
	
	/**
	 * Moves the turtle backwards a specified number of pixels.
	 * Equivalent to {@code forward(-dist);}
	 * @param dist The distance, in pixels, to move.
	 */
	public void backward(double dist) {
		forward(-dist);
	}
	/**
	 * Moves the turtle backwards 100 pixels.
	 */
	public void backward() {
		backward(100);
	}

	/**
	 * Rotates the turtle a specified number of degrees clockwise.
	 * Draws nothing.
	 * @param angle The angle to rotate, in degrees.
	 */
	public void right(double angle) {
		lastState.copy(state);
		state.theta += angle;
		state.theta %= 360.0;
		if (state.theta < 0) {
			state.theta += 360;
		}
	}
	/**
	 * Rotates the turtle 90 degrees clockwise.
	 * Draws nothing.
	 */
	public void right() {
		right(90);
	}
	
	/**
	 * Rotates the turtle a specified number of degrees counter-clockwise.
	 * Draws nothing.
	 * Equivalent to {@code right(-angle);}
	 * @param angle The angle to rotate, in degrees.
	 */
	public void left(double angle) {
		right(-angle);
	}
	/**
	 * Rotates the turtle 90 degrees counter-clockwise.
	 * Draws nothing.
	 */
	public void left() {
		left(90);
	}

	/**
	 * Moves the turtle right relative to its current direction by a specified
	 * amount.
	 * @param dist The number of pixels to move to the turtle's right.
	 */
	public void strafeRight(double dist) {
		lastState.copy(state);
		state.x += dist * Math.cos(Math.toRadians(state.theta + 90));
		state.y += dist * Math.sin(Math.toRadians(state.theta + 90));
		flush();
	}
	
	/**
	 * Moves the turtle left relative to its current direction by a specified
	 * amount.
	 * Equivalent to {@code strafeRight(-dist);}
	 * @param dist The number of pixels to move to the turtle's left.
	 */
	public void strafeLeft(double dist) {
		strafeRight(-dist);
	}

	/**
	 * Disengages the turtle's pen, preventing it from drawing lines from this point on.
	 * This does not prevent it from recording polygon points.
	 * Engaged by default.
	 */
	public void penup() {
		lastState.copy(state);
		state.penState = false;
	}
	/**
	 * Engages the turtle's pen, causing it to draw lines from this point on.
	 * Engaged by default.
	 */
	public void pendown() {
		lastState.copy(state);
		state.penState = true;
	}

	/**
	 * Draws a dot at the turtle's location with radius r.
	 * @param r The radius for the dot.
	 */
	public void dot(int r) {
		if (world != null) {
			Graphics2D g2 = world.canvas.g2;
			Color tmpColor = g2.getColor();
			g2.setColor(state.color);
			g2.drawOval((int)(state.x+0.5), (int)(state.y+0.5), r, r);
			g2.setColor(tmpColor);
		}
	}
	/**
	 * Draws a dot at the turtle's location.
	 */
	public void dot() {
		dot(state.dotRadius);
	}

	/**
	 * Sets the color to use for lines drawn by the turtle after this point.
	 * Black by default.
	 * @param v The color that will be used.
	 */
	public void color(Color v) {
		lastState.copy(state);
		state.color = v;
	}
	/**
	 * Sets the color to use for lines drawn by the turtle after this point.
	 * Black by default.
	 * @param r 0-255
	 * @param g 0-255
	 * @param b 0-255
	 */
	public void color(int r, int g, int b) {
		color(new Color(
			Math.min(Math.max(r, 0), 255),
			Math.min(Math.max(g, 0), 255), 
			Math.min(Math.max(b, 0), 255))
			);
	}
	
	/**
	 * Sets the color to use for lines drawn by the turtle after this point.
	 * Black by default.
	 * 
	 * @param r 0.0-1.0
	 * @param g 0.0-1.0
	 * @param b 0.0-1.0
	 */
	public void color(double r, double g, double b) {
		color((int)(r*255), (int)(g*255), (int)(b*255));
	}
	/**
	 * Black by default.
	 * @return {@link Color} Returns the current color being used to draw lines.
	 */
	public Color getColor() {
		return state.color;
	}
	/**
	 * Sets the color to be used for filling polygons after this point.
	 * White by default.
	 * @param v The color that will be used.
	 */
	public void fillColor(Color v) {
		fillColor = v;
	}
	/**
	 * White by default.
	 * @return {@link Color} The color to be used for filling polygons.
	 */
	public Color getFillColor() {
		return fillColor;
	}

	/**
	 * Sets the width of lines that will be drawn after this point.
	 * {@code 1.0} by default.
	 * @param v The width in pixels for the line.
	 */
	public void setWidth(double v) {
		lastState.copy(state);
		state.stroke = new BasicStroke((float)v);
	}
	/**
	 * {@code 1.0} by default.
	 * @return double Returns the current width of drawn lines.
	 */
	public double getWidth() {
		return ((BasicStroke)state.stroke).getLineWidth();
	}

	/**
	 * 0.0 by default.
	 * @return double Returns the current direction of the turtle in degrees.
	 */
	public double heading() {
		return state.theta;
	}
	/**
	 * World center by default.
	 * @return double[] a 2-element array containing the x and y coordinates of the turtle in the current turtle world.
	 */
	public double[] position() {
		return new double[]{state.x, state.y};
	}

	/**
	 * World center x by default.
	 * @return double Returns the turtle's current x location in the turtle world.
	 */
	public double getX() {
		return state.x;
	}
	/**
	 * World center y by default.
	 * @return double Returns the turtle's current y location in the turtle world.
	 */
	public double getY() {
		return state.y;
	}

	/**
	 * Teleports the turtle to a new location on the turtle world.
	 * Can be used to draw a line to an absolute location.
	 * @param x The x coordinate to teleport to.
	 * @param y The y coordinate to teleport to.
	 */
	public void teleport(double x, double y) {
		lastState.copy(state);
		state.x = x;
		state.y = y;
		flush();
	}

	/**
	 * Move shifts the turtle by a specified amount on the turtle world.
	 * Can be used to draw a line relative to the current location.
	 * @param dx Delta x is the distance to move on the x axis of the world.
	 * @param dy Delta y is the distance to move on the y axis of the world.
	 */
	public void move(double dx, double dy) {
		lastState.copy(state);
		state.x += dx;
		state.y += dy;
		flush();
	}

	/**
	 * Slide moves the turtle forward and sideways simultaneously.
	 * (sideways is 90 degrees clockwise from heading)
	 * 
	 * @param forward The distance to move forward in pixels.
	 * @param sideways The distance to move laterally in pixels.
	 */
	public void slide(double forward, double sideways) {
		lastState.copy(state);
		state.x += forward * Math.cos(Math.toRadians(state.theta));
		state.y += forward * Math.sin(Math.toRadians(state.theta));
		state.x += sideways * Math.cos(Math.toRadians(state.theta+90));
		state.y += sideways * Math.sin(Math.toRadians(state.theta+90));
		flush();
	}

	/**
	 * Nothing happens.
	 */
	public void xyzzy() {
		System.out.println("Nothing happens.");
	}

	/**
	 * Retrieves the world this turtle is currently registered to.
	 * @return {@link TurtleWorld} The world this turtle is currently registered to.
	 */
	public TurtleWorld getWorld() {
		return world;
	}

	/**
	 * Begins recording points for a polygon.
	 */
	public void beginFill() {
		polygonMode = true;
		polygonXPoints.clear();
		polygonYPoints.clear();
	}
	/**
	 * Clears the current polygon points record
	 * and fills a polygon.
	 * Uses fillColor for the fill itself, lines are drawn normally.
	 * The fill is by necessity drawn over the lines.
	 */
	public void endFill() {
		polygonMode = false;
		int[] xPoints = new int[polygonXPoints.size()];
		int[] yPoints = new int[polygonXPoints.size()];
		for (int i = 0; i < xPoints.length; i++) {
			xPoints[i] = (int)((double)polygonXPoints.get(i)+0.5);
			yPoints[i] = (int)((double)polygonYPoints.get(i)+0.5);
		}
		polygonXPoints.clear();
		polygonYPoints.clear();
		Graphics g = world.canvas.g;
		Color tmpColor = g.getColor();
		g.setColor(fillColor);
		g.fillPolygon(xPoints, yPoints, xPoints.length);
		g.drawPolygon(xPoints, yPoints, xPoints.length);
		g.setColor(tmpColor);
	}
}
