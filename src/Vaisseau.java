import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Vaisseau {

	private float x, y, r, vx;
	private Image picture;

	public Vaisseau() {
		x = 300;
		y = 520;
		r = 50;
		vx = 100;
	}
	
	public float GetXvaisseau() {
		return x;
	}
	
	public float GetYvaisseau() {
		return y;
	}
	
	public float GetR() {
		return r;
	}

	public void dessiner(Graphics g) throws SlickException {
		//g.setColor(Color.white);
		//g.fillOval(x, y, r, r/2);
		picture = new Image("images/vaisseau.png");
		g.drawImage(picture,x,y);
	}

	public void gauche(GameContainer gc, int delta) {
		Input inp = gc.getInput();

		if (inp.isKeyDown(Input.KEY_LEFT) && x > 0) {
			x -= vx*delta/1000f;
		}
	}

	public void droite(GameContainer gc, int delta) {
		Input inp = gc.getInput();

		if (inp.isKeyDown(Input.KEY_RIGHT) && x + 50 < 640) {
			x += vx*delta/1000f;
		}

	}

}
