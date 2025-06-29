import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class MainSpaceInvaders {

	public static void main(String[] args) throws SlickException {

		AppGameContainer app = new AppGameContainer(new SpaceInvaders("Space Invaders"));
		app.setShowFPS(false);
		app.setTargetFrameRate(200);
		app.setDisplayMode(640, 600, false);
		app.start();
		
	}

}
