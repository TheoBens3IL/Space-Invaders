import org.newdawn.slick.Color;

import org.newdawn.slick.Graphics;

public class Obstacle {

	private float x, y, c, vie;

	public Obstacle(float x, float y, float c, int vie) {
		this.x = x;
		this.y = y;
		this.c = c;
		this.vie = vie;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x,y,c,c/5);
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getC() {
		return c;
	}

	public void setC(float c) {
		this.c = c;
	}
	
	public float getVie() {
		return vie;
	}

	public void setVie(float vie) {
		this.vie = vie;
	}

}
