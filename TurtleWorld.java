package Turtle;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

/**
 * A complete world class for turtles including canvas and window.
 * Usage:
 * 
 * <pre>
 * TurtleWorld world = new TurtleWorld(1000, 1000);
 * Turtle t = world.Turtle();
 * </pre>
 */
public class TurtleWorld extends JFrame {
	/** The canvas used to display turtle output. Covers the entire window. */
	public TurtleCanvas canvas;
	/** A keyboard input handler for optional usage. */
	public KeyboardInput keyboard;
	/** A mouse input handler for optional usage. */
	public MouseInput mouse;
	/** A list of all turtles registered to this world. */
	protected ArrayList<Turtle> turtles;
	/** An internal task used to properly update the canvas each frame. */
	private RefreshTask refresh;
	/** An internal timing thread used to properly define frame rate. */
	private Timer timer;

	/**
	 * Constructs a new turtle world with given dimensions and locations to search.
	 * Locations will be checked sequentially for a viable turtle sprite.
	 * The first one to succeed will be used.
	 * If none succeed, a blank image will be used.
	 * 
	 * @param w The width of the world and canvas in pixels.
	 * @param h The height of the world and canvas in pixels.
	 * @param locations A varargs array of paths to search for the turtle sprite.
	 * @throws IllegalArgumentException
	 */
	public TurtleWorld(int w, int h, String ... locations) {
		if (w <= 0 || h <= 0) {
			throw new IllegalArgumentException("width and height must be greater than 0.");
		}
		// Attempts to load an image from a set of known locations.
		BufferedImage img = new BufferedImage(19, 19, BufferedImage.TYPE_INT_ARGB);
		File imgFile;
		for (int i = 0; i < locations.length; i++) {
			imgFile = new File(locations[i]);
			if (imgFile.exists()) {
				try {
					img = ImageIO.read(imgFile);
					break;
				} catch (IOException e) {
					System.out.println("Image failed to load. Details: ");
					System.out.println("Path: " + imgFile.getPath());
					System.out.println("Absolute path: " + imgFile.getAbsolutePath());
					e.printStackTrace();
				}
			}
		}

		// Window setup
		turtles = new ArrayList<Turtle>();
		canvas = new TurtleCanvas(w, h, turtles, img);
		add(canvas);
		setSize(w, h);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setTitle("Java Turtle Graphics");

		// Input setup
		// 	Keyboard
		keyboard = new KeyboardInput();
		addKeyListener(keyboard);
		canvas.addKeyListener(keyboard);
		// 	Mouse
		mouse = new MouseInput();
		addMouseListener(mouse);
		canvas.addMouseListener(mouse);
		addMouseMotionListener(mouse);
		canvas.addMouseListener(mouse);

		// Frame rate setup
		createBufferStrategy(3);
		timer = new Timer();
		refresh = new RefreshTask(this);

		// Begin frame refresh
		timer.schedule(refresh, 0, 10);
	}
	/**
	 * Constructs a new turtle world with given dimensions.
	 * Attempts to load turtle image from known compilation destinations.
	 * @param w The width of the world and canvas in pixels.
	 * @param h The height of the world and canvas in pixels.
	 */
	public TurtleWorld(int w, int h) {
		this(w, h,
			"bin/Turtle/turtle.png",
			"Turtle/turtle.png"
		);
	}

	/**
	 * Changes the size of this world and its canvas safely.
	 */
	@Override
	public void setSize(int w, int h) {
		super.setSize(w, h);
		canvas.setSize(w, h);
	}

	/**
	 * Creates a new default turtle registered to this world.
	 * @return {@link Turtle} A new turtle in its default state.
	 */
	public Turtle turtle() {
		Turtle ret = new Turtle(this);
		turtles.add(ret);
		return ret;
	}

	/**
	 * Updates the turtles listing with the included turtle.
	 * Prevents redundant turtle registration.
	 * Removes turtle from another world.
	 * Updates said turtle as well.
	 * @param t The turtle to update.
	 */
	public void addTurtle(Turtle t) {
		if (t.world != null) {
			t.world.remTurtle(t);
		}
		turtles.remove(t);
		turtles.add(t);
		t.world = this;
	}
	/**
	 * Deregisters a turtle from the turtles listing.
	 * Updates said turtle as well.
	 * @param t The turtle to remove.
	 */
	public void remTurtle(Turtle t) {
		turtles.remove(t);
		if (t.world == this) {
			t.world = null;
		}
	}

	/**
	 * Clears the canvas to a given background color.
	 * Does not reset individual turtles.
	 * @param c The color to fill the canvas with.
	 */
	public void clear(Color c) {
		//canvas.g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		canvas.setBackground(c);
		canvas.g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

	/**
	 * Clears the canvas to a blank white background.
	 * Does not reset individual turtles.
	 * Equivalent to {@code world.clear(Color.white);}
	 */
	public void clear() {
		clear(Color.white);
	}
}
