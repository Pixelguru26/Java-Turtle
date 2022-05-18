package Turtle;

import java.util.TimerTask;

/**
 * An internal task used to schedule frame updates for TurtleWorld.
 */
public class RefreshTask extends TimerTask {
	/** The world being updated by this task. */
	TurtleWorld world;

	/**
	 * Constructs a refresh task assigned to a given world.
	 * @param world The world to be updated by this task.
	 */
	public RefreshTask(TurtleWorld world) {
		this.world = world;
	}

	/**
	 * The function called on each frame.
	 * Repaints the assigned world.
	 */
	@Override
	public void run() {
		world.repaint();
	}

}
