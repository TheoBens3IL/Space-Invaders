import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Projectile {

	private float x, y, r, vy;

	public Projectile() {
		x = 0;
		y = 0;
		r = 0;
		vy = 0;

	}

	public Projectile(float x1, float y1, float r1, float vy1) {
		x = x1;
		y = y1;
		r = r1;
		vy = vy1;
	}

	public float GetRprojectile() {
		return r;
	}

	public float GetVYprojectile() {
		return vy;
	}

	public void dessiner(Graphics g) {
		g.setColor(Color.white);
		g.fillOval(x, y, r/3, r);
	}

	public void deplacer(int delta) {
		y -= vy * delta / 1000f;
	}

	public void deplacer_tirs_ennemis(int delta) {
		y += vy * delta / 1000f;
	}

	public boolean horsEcran() {
		if (y < 0) {
			return true;
		} else {
			return false;
		}
	}

	boolean testCollision_ennemi(Ennemis e) {
		if (this.x >= e.GetX() && this.x <= e.GetX() + 37 && this.y >= e.GetY() && this.y <= e.GetY() + 30) {
			return true;
		} else {
			return false;
		}
	}
	
	boolean testCollision(Vaisseau v) {
		if (this.y >= v.GetYvaisseau() && this.y <= v.GetYvaisseau()+v.GetR() && this.x >= v.GetXvaisseau() && this.x <= v.GetXvaisseau()+v.GetR() ) {
			return true;
		} else {
			return false;
		}
	}
	
	boolean testCollision_obstacle(Obstacle o) {
		if (this.x >= o.getX() && this.x <= o.getX() + o.getC() && this.y >= o.getY() && this.y <= o.getY() + o.getC()/5) {
			return true;
		} else {
			return false;
		}
	}
	

}
