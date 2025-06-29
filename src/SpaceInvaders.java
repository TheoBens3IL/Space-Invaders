import java.util.ArrayList;
import java.util.concurrent.*;
import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.Collections;
import java.util.List;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

public class SpaceInvaders extends BasicGame {

	private Vaisseau v = new Vaisseau();
	private ArrayList<Ennemis> e = new ArrayList<Ennemis>();
	private ArrayList<Obstacle> o = new ArrayList<Obstacle>();
	private ArrayList<Projectile> p = new ArrayList<Projectile>();
	private ArrayList<Projectile> p_ennemi = new ArrayList<Projectile>();
	private Ennemis max_ennemi;
	private float vitesse_ennemis = 500;
	private int ennemi_direction = 1, b = 1, c = 1, d = 1;
	private boolean tir = true, tir_ennemi = true, deplacement = true, GameOver = false, win = false;
	private double timer_tir = 0, timer_tir_ennemi = 0, timer_ennemi = 0, timer_fin = 0, delta_tir_ennemi = 1900, delta_ennemi = 1100;
	Image background;

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public SpaceInvaders(String title) {
		super(title);
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void init(GameContainer gc) throws SlickException {
		background = new Image("images/space_background.png");
		Image [] couleur_ennemi = {
				new Image("images/mob_rouge.png"),
				new Image("images/mob_orange.png"),
				new Image("images/mob_jaune.png"),
				new Image("images/mob_vert.png"),
				new Image("images/mob_bleu.png")
			};
		
		// Cr�ation des ennemis
		float b = 0;
		for (int i = 0; i < 5; i++) {
			float a = 0;
			for (int j = 0; j < 11; j++) {
				e.add(new Ennemis(155 + a, 100 + b, couleur_ennemi[i], vitesse_ennemis));
				a += 40;
			}
			b += 35;
		}
		max_ennemi = GetLastEnnemi();

		// Cr�ation des obstacles
		float xo = 0;
		for (int i = 0; i < 3; i++) {
			o.add(new Obstacle(130 + xo, 450f, 60f, 10));
			xo += 170;
		}

	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void update(GameContainer gc, int delta) throws SlickException {
		Input inp = gc.getInput();
		
		// D�placement du vaisseau
		v.droite(gc,delta);
		v.gauche(gc,delta);
		
		// Projectiles Vaisseau
		timer_tir += delta;
		if (timer_tir > 800 * b) {
			tir = true;
			b++;
		}
		if (inp.isKeyPressed(Input.KEY_SPACE) && tir == true) {
			p.add(new Projectile(v.GetXvaisseau() + 24, v.GetYvaisseau(), 10, 400));
			tir = false;
		}
		
		// D�placements des ennemis
		timer_ennemi += delta;
		if (timer_ennemi >= delta_ennemi * d) {
			deplacement = true;
			d++;
		}
		delta_ennemi = delta_ennemi / 1.0002;
		
		for (int j = 0; j < e.size(); j++) {
			if (deplacement == true) {
				e.get(j).move_X(ennemi_direction, delta);
			}
		}
		deplacement = false;

		// Collision Ennemi-Mur
		if ((max_ennemi.GetX() + 50 > 640 && ennemi_direction > 0) || (max_ennemi.GetX() < 0 && ennemi_direction < 0)) {
			ennemi_direction = -ennemi_direction;
			for (int i = 0; i < e.size(); i++) {
				e.get(i).move_Y();
				//e.get(i).setSpeed(e.get(i).getSpeed()+500);
			}
			max_ennemi = GetLastEnnemi();
		}

		// Projectiles ennemis
		int random_ennemi = (int) (Math.random() * e.size());
		timer_tir_ennemi += delta;
		if (timer_tir_ennemi > delta_tir_ennemi * c) {
			tir_ennemi = true;
			c++;
		}
		delta_tir_ennemi = delta_tir_ennemi / 1.0001;

		if (tir_ennemi == true) {
			p_ennemi.add(new Projectile(e.get(random_ennemi).GetX() + 25, e.get(random_ennemi).GetY() + 20, 10, 400));
			tir_ennemi = false;
		}


		// D�placement ProjectilesEnnemis
		for (int i = 0; i < p_ennemi.size(); i++) {
			p_ennemi.get(i).deplacer_tirs_ennemis(delta);
			if (p_ennemi.get(i).horsEcran()) {
				p_ennemi.remove(i);
				continue;
			}
			// Collision ProjectilesEnnemis-Vaisseau
			if (p_ennemi.get(i).testCollision(v)) {
				GameOver = true;
			}
			// 
			for (int j = 0; j < o.size(); j++) {
				// Collision ProjectilesEnnemis-Obstacle
				if (p_ennemi.get(i).testCollision_obstacle(o.get(j))) {
					p_ennemi.remove(i);
					o.get(j).setVie(o.get(j).getVie() - 1);
					break;
				}
				// Suppression Obstacle si plus de vies
				if (o.get(j).getVie() == 0) {
					o.remove(j);
					continue;
				}
			}
		}

		// Projectile Hors Ecran
		for (int i = 0; i < p.size(); i++) {
			p.get(i).deplacer(delta);
			if (p.get(i).horsEcran()) {
				p.remove(i);
				continue;
			}

			// Collision Projectile-Ennemi
			for (int j = 0; j < e.size(); j++) {
				if (p.size() > i && p.get(i).testCollision_ennemi(e.get(j))) {
					p.remove(i);
					e.remove(j);
					max_ennemi = GetLastEnnemi();
					continue;
				}
			}
			// Collision Projectile-Obstacle
			for (int j = 0; j < o.size(); j++) {
				if (p.size() > i && p.get(i).testCollision_obstacle(o.get(j))) {
					p.remove(i);
					o.get(j).setVie(o.get(j).getVie() - 1);
					continue;
				}
				if (o.get(j).getVie() == 0) {
					o.remove(j);
					break;
				}
			}
		}

		// Collision entre Ennemis et Vaisseau
		for (int i = 0; i < e.size(); i++) {
			if (e.get(i).Collision_Ennemi_Vaisseau(v)) {
				GameOver = true;
				break;
			}
		}

		// Test si ennemi sort de l'ecran
		for (int i = 0; i < e.size(); i++) {
			if (e.get(i).horsEcran()) {
				GameOver = true;
			}
		}
		
		// Win quand tous les ennemis sont morts
		if (e.size() == 0) {
			win = true;
		}
		
		// Fin
		if (GameOver || win) {
			timer_fin += delta;
		}
		if (timer_fin >= 2000) {
			gc.exit();
		}

	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.drawImage(background,0,0);
		
		v.dessiner(g);

		for (int i = 0; i < e.size(); i++) {
			e.get(i).dessiner(g);
		}

		for (int i = 0; i < p.size(); i++) {
			p.get(i).dessiner(g);
		}

		for (int i = 0; i < p_ennemi.size(); i++) {
			p_ennemi.get(i).dessiner(g);
		}

		for (int i = 0; i < o.size(); i++) {
			o.get(i).draw(g);
		}

		if (GameOver) {
			g.setColor(Color.black);
			g.fillRect(0,0,800,800);
			g.setColor(Color.white);
			g.drawString("Game Over",270,260);
			//gc.exit();
		}
		
		if (win) {
			g.setColor(Color.black);
			g.fillRect(0,0,800,800);
			g.setColor(Color.white);
			g.drawString("You Win",275,260);
		}

	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Ennemis GetLastEnnemi() {
		Ennemis result = e.get(0);

		for (int i = 0; i < e.size(); i++) {
			if (ennemi_direction > 0) {
				if (e.get(i).GetX() > result.GetX()) {
					result = e.get(i);
				}
			}
			if (ennemi_direction < 0) {
				if (e.get(i).GetX() < result.GetX()) {
					result = e.get(i);
				}
			}
		}
		return result;
	}

}
