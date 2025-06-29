import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Ennemis {

	private float x, y;
	private float speed;
	private Image img;

	public Ennemis(float x1, float y1, Image i, float v) {
		x = x1;
		y = y1;
		img = i;
		speed = v;
	}

	public void dessiner(Graphics g) throws SlickException {
		g.drawImage(img,x,y);
		//g.setColor(c);
		//g.fillRect(x, y, 30, 30);
	}

	public void SetX(float x) {
		this.x = x;
	}

	public void SetY(float y) {
		this.y = y;
	}

	public float GetX() {
		return this.x;
	}

	public float GetY() {
		return this.y;
	}
	
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public void move_X (float direction, int delta) {
		x += speed*direction*delta/1000f;
	}
	
	public void move_Y () {
		y += 30;
		speed += 5;
	}
	
	public boolean horsEcran() {
		if (y < 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean Collision_Ennemi_Vaisseau(Vaisseau v) {
		if (this.y >= v.GetYvaisseau() && this.y <= v.GetYvaisseau()+v.GetR()/2 && this.x >= v.GetXvaisseau() && this.x <= v.GetXvaisseau()+v.GetR() ) {
			return true;
		} else {
			return false;
		}
	}

}
