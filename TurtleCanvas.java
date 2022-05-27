package Turtle;

import javax.swing.*;
import java.awt.geom.AffineTransform;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TurtleCanvas extends JPanel {
	private BufferedImage img;
	public Graphics g;
	public Graphics2D g2;
	private ArrayList<Turtle> turtles;
	private BufferedImage turtleImg;
	
	public TurtleCanvas(int w, int h) {
		img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		g = img.getGraphics();
		g2 = (Graphics2D) g;
	}

	public TurtleCanvas(int w, int h, ArrayList<Turtle> turtles, BufferedImage turtleImg) {
		img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		g = img.getGraphics();
		g2 = (Graphics2D) g;
		this.turtles = turtles;
		this.turtleImg = turtleImg;
	}

	@Override
	public void setSize(int w, int h) {
		super.setSize(w, h);
		Color tmpColor = g.getColor();
		g.dispose();
		g2.dispose();
		BufferedImage imgNew = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		g = imgNew.getGraphics();
		g2 = (Graphics2D) g;
		g.setColor(Color.white);
		g.drawImage(img, 0, 0, null);
		g.setColor(tmpColor);
		img = imgNew;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// g.setColor(Color.black);
		// g.drawLine(0,0,100,100);
		g.drawImage(img, 0, 0, null);
		if (turtles != null) {
			// BufferedImage v;
			for (int i = 0; i < turtles.size(); i++) {
				// v = rotate(turtleImg, turtles.get(i).heading() + 90);
				// g.drawImage(v, (int)(turtles.get(i).getX() - v.getWidth() / 2), (int)(turtles.get(i).getY() - v.getHeight() / 2), this);
				drawImg(g2, turtleImg, (int)turtles.get(i).getX(), (int)turtles.get(i).getY(), turtles.get(i).heading());
			}
		}
		g2.setStroke(new java.awt.BasicStroke(1));
	}

	private void drawImg(Graphics2D g2, BufferedImage img, int x, int y, double r) {
		x -= img.getWidth() / 2;
		y -= img.getHeight() / 2;
		AffineTransform backup = g2.getTransform();
		AffineTransform a = AffineTransform.getRotateInstance(Math.toRadians(r+90), x + img.getWidth() / 2, y + img.getHeight() / 2);
		a.concatenate(backup);
		g2.setTransform(a);
		g2.drawImage(img, x, y, null);
		g2.setTransform(backup);
	}

	public BufferedImage getImage() {
		return img;
	}
	public void setImage(BufferedImage v) {
		Color tmpColor = g.getColor();
		g.dispose();
		g2.dispose();
		BufferedImage imgNew = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		g = imgNew.getGraphics();
		g2 = (Graphics2D) g;
		g.setColor(Color.white);
		g.drawImage(v, 0, 0, null);
		g.setColor(tmpColor);
		img = imgNew;
	}

	public static BufferedImage rotate(BufferedImage bimg, Double angle) {
		double sin = Math.abs(Math.sin(Math.toRadians(angle))),
				cos = Math.abs(Math.cos(Math.toRadians(angle)));
		int w = bimg.getWidth();
		int h = bimg.getHeight();
		int neww = (int) Math.floor(w * cos + h * sin),
				newh = (int) Math.floor(h * cos + w * sin);
		BufferedImage rotated = new BufferedImage(neww, newh, bimg.getType());
		Graphics2D graphic = rotated.createGraphics();
		graphic.translate((neww - w) / 2, (newh - h) / 2);
		graphic.rotate(Math.toRadians(angle), w / 2, h / 2);
		graphic.drawRenderedImage(bimg, null);
		graphic.dispose();
		return rotated;
	}
}
