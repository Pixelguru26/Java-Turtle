package Turtle;

import java.awt.*;

/**
 * A class containing volatile readable data about a turtle's current drawing state.
 * @see Turtle
 */
public class TurtleState {
	/** The current color to be used for drawing lines. */
	public Color color;
	/** The current pen stroke object to be used for drawing lines. Should be castable to type {@link BasicStroke} */
	public Stroke stroke;
	/** Indicates whether the turtle's drawing pen is engaged or not. */
	public boolean penState;

	/** The current x coordinate of the turtle in pixels right from the top left corner of the turtle world. */
	public double x;
	/** The current y coordinate of the turtle in pixels below the top left corner of the turtle world. */
	public double y;
	/** The current angle of the turtle in clockwise degrees from East. */
	public double theta;

	/**
	 * Copies data from another state into a new state.
	 * @param state The state to copy data from.
	 */
	public TurtleState(TurtleState state) {
		color = state.color;
		stroke = state.stroke;
		penState = state.penState;
		x = state.x;
		y = state.y;
		theta = state.theta;
	}
	/**
	 * Creates a new default state in the current world.
	 * @param world The world to place the turtle in.
	 */
	public TurtleState(TurtleWorld world) {
		color = Color.black;
		stroke = new BasicStroke(1);
		penState = true;
		x = world.getWidth() / 2;
		y = world.getHeight() / 2;
		theta = 0;
	}
	
	/**
	 * Constructs a state from given data.
	 * 
	 * @param color The color the turtle will use to draw lines.
	 * @param stroke The current pen stroke object to be used for drawing lines.  Should be castable to type {@link BasicStroke}
	 * @param penState Indicates whether the turtle's drawing pen is engaged or not.
	 * @param x The initial x coordinate for the turtle state.
	 * @param y The initial y coordinate for the turtle state.
	 * @param theta The initial heading for the turtle state.
	 */
	public TurtleState(Color color, Stroke stroke, boolean penState, double x, double y, double theta) {
		this.color = color;
		this.stroke = stroke;
		this.penState = penState;
		this.x = x;
		this.y = y;
		this.theta = theta;
	}

	/**
	 * Swaps all data between two turtlestates. Useful for buffering.
	 * @param v The state to swap information with.
	 */
	public void swap(TurtleState v) {
		Color tmpColor = v.color;
		v.color = color;
		color = tmpColor;

		Stroke tmpStroke = v.stroke;
		v.stroke = stroke;
		stroke = tmpStroke;

		boolean tmpPen = v.penState;
		v.penState = penState;
		penState = tmpPen;

		double tmpX = v.x;
		v.x = x;
		x = tmpX;

		double tmpY = v.y;
		v.y = y;
		y = tmpY;

		double tmpT = v.theta;
		v.theta = theta;
		theta = tmpT;
	}

	/**
	 * Copies all data into this state.
	 * @param v The state to copy from.
	 */
	public void copy(TurtleState v) {
		color = v.color;
		stroke = v.stroke;
		penState = v.penState;

		x = v.x;
		y = v.y;
		theta = v.theta;
	}
}
