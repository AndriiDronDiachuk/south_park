package tk.droncompany.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import tk.droncompany.GameScreen;
import tk.droncompany.SouthPark;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "South Park";
		config.width = 1280;
		config.height = 720;
		new LwjglApplication(new SouthPark(), config);
	}
}
